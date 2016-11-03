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
          "sourceLangKey" : $scope.translation.sourceWord.langKey,
          "resultText" : $scope.translation.resultWord.text,
          "resultLangKey" : $scope.translation.resultWord.langKey,
          "partOfSpeech" : $scope.translation.partOfSpeech,
          "note" : null
        }
      );
    }
  });
