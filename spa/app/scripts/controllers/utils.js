'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:UtilsCtrl
 * @description
 * # UtilsCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('UtilsCtrl', function ($scope, UsersActions) {
    UsersActions.get(
      {
        action: 'read-only'
      },
      function (respose) {
        $scope.enabled = respose.enabled;
      }
    );

    $scope.updateEnabled = function () {
      UsersActions.update(
        {
          action: 'read-only'
        },
        {
          enabled: $scope.enabled
        }
      );
    }
  });
