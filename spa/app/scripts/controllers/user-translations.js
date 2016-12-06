'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:UserTranslationsCtrl
 * @description
 * # UserTranslationsCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('UserTranslationsCtrl',
    function ($scope,
              $state,
              $translate,
              $stateParams,
              $rootScope,
              messageBox,
              Translations,
              tts,
              Users,
              Lessons,
              lessonsUpdater,
              Tags) {
      Users.get({'userId': 'me'}, function (response) {
        $scope.maxRecords = response.translationsInOneLesson;
        setTimeout(function () {
          $scope.pageIndex = $stateParams['page-index'] || 1;
          $rootScope.updateTranslations();
        }, 0);
      });

      $scope.maxPagSize = 5;

      $scope.searchSubstring = $stateParams['search-substring'];
      $scope.partOfSpeech = $stateParams['part-of-speech'] || 'ALL';

      if ($stateParams['source-lang-code'] && $stateParams['result-lang-code'])
        $scope.langCodesPair = JSON.stringify(
          {
            source: $stateParams['source-lang-code'],
            result: $stateParams['result-lang-code']
          }
        );
      else
        $scope.langCodesPair = 'null';

      $scope.sortField = $stateParams['sort-field'];
      $scope.sortOrder = $stateParams['sort-order'];
      $scope.learned = $stateParams['learned'] || 'false';

      $scope.play = tts.play;
      $scope.supports = tts.supports;

      $scope.selectedTranslationIds = [];

      $scope.toggleAllCheckboxes = function (toggle) {
        if (toggle) {
          $scope.translations.forEach(function (translation) {
            $scope.selectedTranslationIds.push(translation.id);
          });
        }
        else
          $scope.selectedTranslationIds.length = 0;
      };

      $scope.selectionInterpol = {};

      $scope.$watch('selectedTranslationIds.length', function (val) {
        $scope.selectionInterpol.selectedTranslationsCount = val;
      });

      $rootScope.updateTranslations = function () {
        $scope.selectedTranslationIds = [];
        var langCodesPair = JSON.parse($scope.langCodesPair);

        Translations.get(
          {
            userId: 'me',
            'max-records': $scope.maxRecords,
            'page-index': $scope.pageIndex,
            'search-substring': $scope.searchSubstring,
            'part-of-speech': $scope.partOfSpeech == 'ALL' ? null : $scope.partOfSpeech,
            'source-lang-code': langCodesPair ? langCodesPair.source : null,
            'result-lang-code': langCodesPair ? langCodesPair.result : null,
            'sort-field': $scope.sortField,
            'sort-order': ($scope.sortField && !$scope.sortOrder) ? 'ASC' : $scope.sortOrder,
            'learned': $scope.learned == 'ALL' ? null : $scope.learned,
            'tag-ids': $scope.selectedTagsIds
          },
          function (response) {
            $scope.translations = response.translations;

            $scope.filteredRecords = response.filteredRecords;
            $scope.totalRecords = response.totalRecords;
            $scope.countInterpol = {
              filteredRecords: $scope.filteredRecords,
              totalRecords: $scope.totalRecords,
            };

            $scope.langCodesPairs = response.langCodesPairs;
            $scope.partsOfSpeech = response.partsOfSpeech;

            $scope.selectionInterpol.translationsCount = $scope.translations.length;

            $state.transitionTo('user-translations',
              {
                'page-index': $scope.pageIndex,
                'search-substring': $scope.searchSubstring,
                'part-of-speech': $scope.partOfSpeech == 'ALL' ? null : $scope.partOfSpeech,
                'source-lang-code': langCodesPair ? langCodesPair.source : null,
                'result-lang-code': langCodesPair ? langCodesPair.result : null,
                'sort-field': $scope.sortField,
                'sort-order': $scope.sortOrder,
                'learned': $scope.learned
              },
              {
                notify: false
              });
          }
        );
      };

      $scope.deleteTranslations = function () {
        $rootScope.hideAffix = true;
        messageBox.showGeneralQuestion('question.on.delete.translations').then(
          function () {
            $rootScope.hideAffix = false;
            Translations.delete(
              {
                userId: 'me',
                ids: $scope.selectedTranslationIds
              },
              function () {
                $rootScope.updateTranslations();
                $scope.selectedTranslationIds = [];
              },
              function (error) {
                if (error.data && error.data.errorCode === 404)
                  $rootScope.updateTranslations();
              }
            );
          },
          function () {
            $rootScope.hideAffix = false;
          }
        );
      };

      $scope.learnTranslations = function (nTranslations) {
        var translationIds = nTranslations
          ? $scope.selectedTranslationIds
          : $scope.translations.map(function (translation) {
          return translation.id;
        });

        Lessons.save(
          {},
          {
            translationIds: translationIds
          },
          function (response) {
            lessonsUpdater.updateLessons().then(
              function () {
                setTimeout(function () {
                  $state.go('lesson', {lessonId: response.id});
                }, 300);
              }
            );
          }
        );
      };

      $scope.toggleSelection = function toggleSelection(id) {
        var index = $scope.selectedTranslationIds.indexOf(id);
        if (index > -1)
          $scope.selectedTranslationIds.splice(index, 1);
        else
          $scope.selectedTranslationIds.push(id);
      };

      $scope.createTag = function () {
        $rootScope.hideAffix = true;
        messageBox.showInputDialog("label.message.title.input.tag.name").then(
          function (name) {
            $rootScope.hideAffix = false;
            Tags.save(
              {},
              {
                name: name
              },
              function () {
                $scope.updateTags();
              }
            );
          },
          function () {
            $rootScope.hideAffix = false;
          }
        );
      };

      $scope.updateTags = function () {
        Tags.query(
          {},
          function (response) {
            $scope.tags = response;
          }
        )
      };

      $scope.selectedTagsIds = [];

      $scope.toggleTag = function (tag) {
        var index = $scope.selectedTagsIds.indexOf(tag.id);
        if (index > -1)
          $scope.selectedTagsIds.splice(index, 1);
        else
          $scope.selectedTagsIds.push(tag.id);

        $rootScope.updateTranslations();
      };

      $scope.isTagSelected = function (tag) {
        return $scope.selectedTagsIds.indexOf(tag.id) > -1;
      };

      $scope.renameTag = function (tag) {
        $rootScope.hideAffix = true;
        messageBox.showInputDialog("label.message.title.input.tag.name", tag.name).then(
          function (name) {
            $rootScope.hideAffix = false;
            Tags.update(
              {tagId: tag.id},
              {name: name},
              function () {
                $scope.updateTags();
              }
            );
          },
          function () {
            $rootScope.hideAffix = false;
          }
        );
      };

      $scope.deleteTag = function (tag) {
        $rootScope.hideAffix = true;
        messageBox.showGeneralQuestion('question.on.delete.tag').then(
          function () {
            $rootScope.hideAffix = false;
            Tags.delete(
              {
                tagId: tag.id,
              },
              function () {
                $scope.updateTags();
              },
              function (error) {
                if (error.data && error.data.errorCode === 404)
                  $scope.updateTags();
              }
            );
          },
          function () {
            $rootScope.hideAffix = false;
          }
        );
      };

      $scope.sortableOptions = {
        stop: function (e, ui) {
          Tags.update(
            {},
            $scope.tags
          );
        }
      };

      $scope.updateTags();

      $scope.tagTranslations = function () {
        messageBox.showSelectTagDialog().then(
          function (tag) {
            Tags.update(
              {
                tagId: tag.id,
                field: 'translations'
              },
              $scope.selectedTranslationIds,
              function () {

              }
            );
          }
        );
      }


    });
