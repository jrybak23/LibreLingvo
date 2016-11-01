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
  });
