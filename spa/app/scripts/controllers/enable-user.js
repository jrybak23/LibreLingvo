'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:EnableUserCtrl
 * @description
 * # EnableUserCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('EnableUserCtrl', function ($scope, $state, $stateParams, EnableUser, MessageBox) {
    if ($stateParams.verificationToken) {
      $state.go('log-in');
      EnableUser.get({verificationToken: $stateParams.verificationToken},
        function () {
          MessageBox.show('message.success.user.enabling')
        });
    }
  });
