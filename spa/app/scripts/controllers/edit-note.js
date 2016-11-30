'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EditNoteCtrl
 * @description
 * # EditNoteCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('EditNoteCtrl', function ($scope, $stateParams, Translations) {
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

    $scope.edit = function () {
      Translations.update(
        {
          userId: 'me',
          translationId: $stateParams.translationId,
          field: 'note'
        },
        {note: $scope.note}
      );
    };

    $scope.tinymceOptions = {
      setup: function (editor) {
        editor.on('init', function(args) {
          editor = args.target;

          editor.on('NodeChange', function(e) {
            if (e && e.element.nodeName.toLowerCase() == 'img') {
              tinyMCE.DOM.setAttribs(e.element, {'height': 'auto', 'style' : 'max-width:300px;'});
            }
          });
        });

          editor.on('change', function(args) {
            editor = args.target;
          });
      },
      inline: false,
      plugins: 'advlist autolink link image lists charmap print preview autoresize',
      skin: 'lightgray',
      theme: 'modern'
    };
  });
