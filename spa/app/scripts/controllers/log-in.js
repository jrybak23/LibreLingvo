'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LogInCtrl
 * @description
 * # LogInCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LogInCtrl', function ($scope, $state, $rootScope, $q, oauth2) {

    oauth2.updateAuthorities(function () {
      if ($rootScope.hasUserAuthority)
        $state.go('user-translations');
    });

    $scope.submitAuthorization = function () {
      oauth2.logIn($scope.email, $scope.password).then(
        function () {
          $state.go('user-translations');
        }
      );
    };

    $scope.submitReadonly = function () {
      oauth2.logIn("readonly@example.com", "1234").then(
        function () {
          $state.go('user-translations');
        }
      );
    };
  });
