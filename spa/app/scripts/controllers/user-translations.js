'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:UserTranslationsCtrl
 * @description
 * # UserTranslationsCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('UserTranslationsCtrl', function ($scope, Translations) {
    $scope.maxRecords = 10;
    $scope.maxPagSize = 5;
    $scope.pageIndex = 1;
    $scope.loadTranslations = function () {
      Translations.get({userId: 'me', maxRecords: $scope.maxRecords, pageIndex: $scope.pageIndex},
        function (response) {
          $scope.translations = response.translations;
          $scope.totalRecords = response.totalRecords;
        });
    };
    $scope.loadTranslations();
  });
