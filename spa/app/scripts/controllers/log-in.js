'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LogInCtrl
 * @description
 * # LogInCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LogInCtrl', function ($scope,$state, Oauth2,MessageBox) {
    $scope.submitAuthorization = function () {
      Oauth2.logIn($scope.email, $scope.password).then(
        function () {
          $state.go('profile');
        }
      );
    }
  });
