'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:ChangePasswordCtrl
 * @description
 * # ChangePasswordCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ChangePasswordCtrl', function ($scope,$state,Users) {
    $scope.changePassword = function () {
      Users.update(
        {
          userId: 'me',
          field: 'password'
        },
        {
          oldPassword:$scope.oldPassword,
          password:$scope.password
        },
        function () {
          $state.go('profile')
        }
      );
    }
  });
