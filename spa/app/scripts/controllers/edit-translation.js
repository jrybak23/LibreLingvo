'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EditTranslationCtrl
 * @description
 * # EditTranslationCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('EditTranslationCtrl', function ($scope, $state, $stateParams, Translations) {
    Translations.get(
      {
        userId: 'me',
        translationId: $stateParams.translationId
      },
      function (response) {
        $scope.translation = response;
      }
    );

    $scope.edit=function () {
      Translations.update(
        {
          userId: 'me',
          translationId: $stateParams.translationId
        },
        {
          "sourceText" : $scope.translation.sourceWord.text,
          "sourceLangCode" : $scope.translation.sourceWord.langCode,
          "resultText" : $scope.translation.resultWord.text,
          "resultLangCode" : $scope.translation.resultWord.langCode,
          "partOfSpeech" : $scope.translation.partOfSpeech,
          "learned" : $scope.translation.learned
        }
      );
    }
  });
