'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:ModalMessageCtrl
 * @description
 * # ModalMessageCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ModalMessageCtrl', function ($scope, $uibModalInstance, content, title, panelClass) {
    $scope.content = content;
    $scope.title = title;
    $scope.panelClass = panelClass;

    $scope.onOk = function () {
      $uibModalInstance.close();
    };
  });
