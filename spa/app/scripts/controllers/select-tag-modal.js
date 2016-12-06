'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:SelectTagModalCtrl
 * @description
 * # SelectTagModalCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('SelectTagModalCtrl', function ($scope, $uibModalInstance, Tags) {
    Tags.query(
      {},
      function (response) {
        $scope.tags = response;
      }
    );

    $scope.selectTag = function (tag) {
      $uibModalInstance.close(tag);
    };

    $scope.onCancel = function () {
      $uibModalInstance.dismiss();
    };
  });
