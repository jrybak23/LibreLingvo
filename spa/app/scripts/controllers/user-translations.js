'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:UserTranslationsCtrl
 * @description
 * # UserTranslationsCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('UserTranslationsCtrl', function ($scope, $state, $translate, $stateParams, MessageBox, Translations) {
    $scope.maxRecords = 5;
    $scope.maxPagSize = 5;

    $scope.searchSubstring = $stateParams['search-substring'];
    $scope.partOfSpeech = $stateParams['part-of-speech'] || 'ALL';

    if ($stateParams['source-lang-key'] && $stateParams['result-lang-key'])
      $scope.langCodesPair = JSON.stringify(
        {
          source: $stateParams['source-lang-key'],
          result: $stateParams['result-lang-key']
        }
      );
    else
      $scope.langCodesPair = 'null';

    $scope.sortField = $stateParams['sort-field'];
    $scope.sortOrder = $stateParams['sort-order'];

    $scope.updateTranslations = function () {
      var langCodesPair = JSON.parse($scope.langCodesPair);
      Translations.get(
        {
          userId: 'me',
          'max-records': $scope.maxRecords,
          'page-index': $scope.pageIndex,
          'search-substring': $scope.searchSubstring,
          'part-of-speech': $scope.partOfSpeech == 'ALL' ? null : $scope.partOfSpeech,
          'source-lang-key': langCodesPair ? langCodesPair.source : null,
          'result-lang-key': langCodesPair ? langCodesPair.result : null,
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

          $state.transitionTo('user-translations',
            {
              'page-index': $scope.pageIndex,
              'search-substring': $scope.searchSubstring,
              'part-of-speech': $scope.partOfSpeech == 'ALL' ? null : $scope.partOfSpeech,
              'source-lang-key': langCodesPair ? langCodesPair.source : null,
              'result-lang-key': langCodesPair ? langCodesPair.result : null,
              'sort-field': $scope.sortField,
              'sort-order': $scope.sortOrder
            },
            {
              notify: false
            });
        }
      );
    };

    $scope.deleteTranslation = function (translation) {
      MessageBox.showGeneralQuestion('question.on.delete.translation').then(
        function () {
          Translations.delete(
            {
              userId: 'me',
              translationId: translation.id
            },
            function () {
              $scope.updateTranslations();
            },
            function (error) {
              if (error.data && error.data.errorCode === 404)
                $scope.updateTranslations();
            }
          );
        }
      );
    };

    setTimeout(function () {
      $scope.pageIndex = $stateParams['page-index'] || 1;
      $scope.updateTranslations();
    }, 0);

  });
