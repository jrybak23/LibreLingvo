'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:GeneralQuestionModalCtrl
 * @description
 * # GeneralQuestionModalCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('GeneralQuestionModalCtrl', function ($scope, $uibModalInstance, content, title, panelClass) {
    $scope.content = content;
    $scope.title = title;
    $scope.panelClass = panelClass;

    $scope.onYes = function () {
      $uibModalInstance.close();
    };
    $scope.onNo = function () {
      $uibModalInstance.dismiss();
    };
  });
