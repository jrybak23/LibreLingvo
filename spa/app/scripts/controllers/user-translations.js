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
    function ($scope, $state, $translate, $stateParams, MessageBox, Translations, TTS, Users) {
      Users.get({'userId': 'me'}, function (response) {
        $scope.maxRecords = response.translationsInOneExam;
      });

      $scope.hideAffix = false;
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
      $scope.play = TTS.play;

      $scope.toggleAllCheckboxes = function (toggle) {
        if (toggle) {
          $scope.translations.forEach(function (translation) {
            $scope.selectedTranslationIds.push(translation.id);
          });
        }
        else
          $scope.selectedTranslationIds = [];

        console.log(toggle, $scope.selectedTranslationIds);
      };


      $scope.updateTranslations = function () {
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
            'sort-order': ($scope.sortField && !$scope.sortOrder) ? 'ASC' : $scope.sortOrder
          },
          function (response) {
            $scope.translations = response.translations;

            $scope.filteredRecords = response.filteredRecords;
            $scope.totalRecords = response.totalRecords;
            $scope.recordsCount = {
              filteredRecords: $scope.filteredRecords,
              totalRecords: $scope.totalRecords
            };

            $scope.langCodesPairs = response.langCodesPairs;
            $scope.partsOfSpeech = response.partsOfSpeech;

            $scope.interpol={'translationsCount':$scope.translations.length};

            $state.transitionTo('user-translations',
              {
                'page-index': $scope.pageIndex,
                'search-substring': $scope.searchSubstring,
                'part-of-speech': $scope.partOfSpeech == 'ALL' ? null : $scope.partOfSpeech,
                'source-lang-code': langCodesPair ? langCodesPair.source : null,
                'result-lang-code': langCodesPair ? langCodesPair.result : null,
                'sort-field': $scope.sortField,
                'sort-order': $scope.sortOrder
              },
              {
                notify: false
              });
          }
        );
      };

      $scope.deleteTranslations = function () {
        $scope.hideAffix = true;
        MessageBox.showGeneralQuestion('question.on.delete.translations').then(
          function () {
            $scope.hideAffix = false;
            Translations.delete(
              {
                userId: 'me',
                ids: $scope.selectedTranslationIds
              },
              function () {
                $scope.updateTranslations();
                $scope.selectedTranslationIds = [];
              },
              function (error) {
                if (error.data && error.data.errorCode === 404)
                  $scope.updateTranslations();
              }
            );
          },
          function () {
            $scope.hideAffix = false;
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

      setTimeout(function () {
        $scope.pageIndex = $stateParams['page-index'] || 1;
        $scope.updateTranslations();
      }, 0);

    });
