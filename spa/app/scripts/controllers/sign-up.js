'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:SignUpCtrl
 * @description
 * # SignUpCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('SignUpCtrl', function ($scope, $state, Users, messageBox) {
    $scope.register = function () {
      Users.save($scope.user).$promise.then(
        function () {
          $state.go('log-in');
          messageBox.show('message.success.registration');
        }
      );
    };
  });
