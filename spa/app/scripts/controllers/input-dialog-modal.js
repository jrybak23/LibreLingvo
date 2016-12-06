'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:InputDialogModalCtrl
 * @description
 * # InputDialogModalCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('InputDialogModalCtrl', function ($scope, $uibModalInstance, content, title, panelClass) {
    $scope.title = title;
    $scope.panelClass = panelClass;
    $scope.value=content;

    $scope.onSubmit = function () {
      $uibModalInstance.close($scope.value);
    };
    $scope.onCancel = function () {
      $uibModalInstance.dismiss();
    };
  });
