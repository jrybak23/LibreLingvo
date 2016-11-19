'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('UsersCtrl', function ($scope, Users, MessageBox) {
    $scope.updateUsers = function () {
      Users.query(function (response) {
        $scope.users = response;
      });
    };

    $scope.updateUser = function (user) {
      Users.update(
        {
          userId: user.id
        },
        {
          nonLocked: user.nonLocked,
          enabled: user.enabled
        }
      );
    };

    $scope.deleteUser = function (user) {
      MessageBox.showGeneralQuestion("Do you really want to delete this user?").then(
        function () {
          Users.delete(
            {
              userId: user.id
            },
            function () {
              $scope.updateUsers();
            },
            function (error) {
              if (error.data && error.data.errorCode === 404)
                $scope.updateUsers();
            }
          );
        }
      );
    };

    $scope.updateUsers();
  });
