'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:ValidationErrorModalMessageCtrl
 * @description
 * # ValidationErrorModalMessageCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ValidationErrorModalMessageCtrl', function ($scope, $uibModalInstance, fieldErrors) {
    $scope.fieldErrors=fieldErrors;

    $scope.onOk = function () {
      $uibModalInstance.close();
    };
  });
