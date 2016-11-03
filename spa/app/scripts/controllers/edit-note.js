'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EditNoteCtrl
 * @description
 * # EditNoteCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('EditNoteCtrl', function ($scope,$stateParams,TranslationNote) {
    TranslationNote.get(
      {
        userId: 'me',
        translationId: $stateParams.translationId
      },
      function (response) {
        $scope.note = response.note;
      }
    );

    $scope.edit=function () {
      TranslationNote.update(
        {
          userId: 'me',
          translationId: $stateParams.translationId
        },
        {
          "note" : $scope.note
        }
      );
    }

    $scope.tinymceOptions = {
      onChange: function(e) {
        // put logic here for keypress and cut/paste changes
      },
      inline: false,
      plugins : 'advlist autolink link image lists charmap print preview',
      skin: 'lightgray',
      theme : 'modern'
    };
  });
