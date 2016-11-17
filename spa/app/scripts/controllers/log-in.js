'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LogInCtrl
 * @description
 * # LogInCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LogInCtrl', function ($scope, $state, $rootScope, $q, Oauth2) {
    Oauth2.afterUpdateAuthoritiesCallback(function () {
      if ($rootScope.hasUserAuthority)
        $state.go('user-translations');
    });

    $scope.submitAuthorization = function () {
      Oauth2.logIn($scope.email, $scope.password).then(
        function () {
          $state.go('user-translations');
        }
      );
    }
  });
