'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:NoteDetailCtrl
 * @description
 * # NoteDetailCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('NoteDetailCtrl', function ($scope,$stateParams,Translations) {
    $scope.translationId=$stateParams.translationId;
    Translations.get(
      {
        userId: 'me',
        translationId: $stateParams.translationId,
        field: 'note'
      },
      function (response) {
        $scope.note = response.note;
      }
    );
  });
