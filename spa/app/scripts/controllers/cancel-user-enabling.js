'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:CancelUserEnablingCtrl
 * @description
 * # CancelUserEnablingCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('CancelUserEnablingCtrl', function ($scope, $state, $stateParams, CancelUserEnabling) {
    if ($stateParams.verificationToken) {
      $state.go('main');
      CancelUserEnabling.get({verificationToken: $stateParams.verificationToken});
    }
  });
