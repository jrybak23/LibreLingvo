'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:NoteDetailCtrl
 * @description
 * # NoteDetailCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('NoteDetailCtrl', function ($scope,$stateParams,TranslationNote) {
    $scope.translationId=$stateParams.translationId;
    TranslationNote.get(
      {
        userId: 'me',
        translationId: $stateParams.translationId
      },
      function (response) {
        $scope.note = response.note;
      }
    );
  });
