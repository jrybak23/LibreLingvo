'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:CancelUserEnablingCtrl
 * @description
 * # CancelUserEnablingCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('CancelActivationCtrl', function ($scope, $state, $stateParams, UsersActions) {
    if ($stateParams['activation-key']) {
      $state.go('main');
      UsersActions.delete(
        {
          'activation-key': $stateParams['activation-key']
        },
        function () {
          messageBox.show('message.success.user.enabling')
        }
      );
    }
  });
