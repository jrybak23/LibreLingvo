'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EnableUserCtrl
 * @description
 * # EnableUserCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ActivateUserCtrl', function ($scope, $state, $stateParams, UsersActions, messageBox) {
    if ($stateParams['activation-key']) {
      $state.go('log-in');
      UsersActions.update(
        {
          'activation-key': $stateParams['activation-key'],
          action: 'enable'
        },
        {},
        function () {
          messageBox.show('message.success.user.enabling')
        }
      );
    }
  });
