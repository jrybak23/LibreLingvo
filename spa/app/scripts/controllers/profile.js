'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:ProfileCtrl
 * @description
 * # ProfileCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('ProfileCtrl', function ($scope, Users) {
    Users.get({'userId': 'me'}).$promise.then(
      function (data) {
        $scope.user = data;
      }
    );

    $scope.saveChanges = function () {
      Users.update({'userId': 'me'}, $scope.user);
    }
  });
