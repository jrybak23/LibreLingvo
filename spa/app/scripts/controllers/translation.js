'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:TranslationCtrl
 * @description
 * # TranslationCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('TranslationCtrl', function ($scope, $stateParams, Translations, Tags) {
    $scope.updateTags = function () {
      Translations.query(
        {
          userId: 'me',
          translationId: $stateParams.translationId,
          field: 'tags'
        },
        function (response) {
          $scope.tags = response;
        }
      );
    };

    $scope.removeTag = function (tag) {
      Tags.delete(
        {
          field:'translations',
          translationId: $stateParams.translationId,
          tagId: tag.id
        },
        function () {
          $scope.updateTags();
        }
      );
    };

    $scope.updateTags();
  });
