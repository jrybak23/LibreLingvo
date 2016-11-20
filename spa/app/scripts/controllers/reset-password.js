'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:ResetPasswordCtrl
 * @description
 * # ResetPasswordCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ResetPasswordCtrl', function ($scope,$state, $stateParams, UsersActions, messageBox) {
    $scope.resetKey = $stateParams['reset-key'];

    $scope.setResetKey = function () {
      UsersActions.update(
        {
          action: 'set-reset-key',
          email: $scope.email
        },
        {},
        function () {
          $state.go('log-in');
          messageBox.show('message.password.reset.email.sent');
        }
      );
    };

    $scope.resetPassword = function () {
      UsersActions.update(
        {
          action: 'reset-password',
          'reset-key': $stateParams['reset-key']
        },
        {password: $scope.password},
        function () {
          $state.go('log-in');
        }
      );
    };
  });
