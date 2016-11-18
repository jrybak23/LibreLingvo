'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:CancelUserEnablingCtrl
 * @description
 * # CancelUserEnablingCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('CancelUserEnablingCtrl', function ($scope, $state, $stateParams, VerificationTokens) {
    if ($stateParams.verificationToken) {
      $state.go('main');
      VerificationTokens.delete({verificationToken: $stateParams.verificationToken});
    }
  });
