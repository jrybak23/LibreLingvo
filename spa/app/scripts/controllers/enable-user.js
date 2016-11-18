'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EnableUserCtrl
 * @description
 * # EnableUserCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('EnableUserCtrl', function ($scope, $state, $stateParams, VerificationTokens, MessageBox) {
    if ($stateParams.verificationToken) {
      $state.go('log-in');
      VerificationTokens.update(
        {
          verificationToken: $stateParams.verificationToken,
          field:'enabled'
        },
        {},
        function () {
          MessageBox.show('message.success.user.enabling')
        });
    }
  });
