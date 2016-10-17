'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:SignUpCtrl
 * @description
 * # SignUpCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('SignUpCtrl', function ($scope,$state, Users, MessageBox) {
    $scope.register = function () {
      Users.save($scope.user).$promise.then(
        function () {
          $state.go('log-in');
          MessageBox.show('message.success.registration');
        }
      );
    };
  });
