'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:IndexCtrl
 * @description
 * # IndexCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('IndexCtrl', function ($scope, $translate, $cookies, $http, $window, Oauth2, Lessons) {

    var w = angular.element($window);
    $scope.$watch(
      function () {
        return $window.innerWidth;
      },
      function (value) {
        $scope.windowWidth = value;
      },
      true
    );

    w.bind('resize', function () {
      $scope.$apply();
    });

    $scope.changeLanguage = function (lang_key) {
      $http.defaults.headers.common['Accept-Language'] = lang_key;
      $cookies.put("default_lang_key", lang_key);
      $scope.currentLanguage = lang_key;
      $translate.use(lang_key);
    };

    $scope.changeLanguage($cookies.get("default_lang_key") || "en");

    $scope.logOut = function () {
      Oauth2.logOut();
    };

    $scope.lessonsUpdating = false;

    $scope.updateLessons = function () {
      if (!$scope.lessonsUpdating) {
        $scope.lessonsUpdating = true;
        Lessons.query(
          function (respose) {
            $scope.lessons = respose;
            $scope.lessonsUpdating = false;
          },
          function () {
            $scope.lessonsUpdating = false;
          }
        );
      }
    };

    $scope.$on('timer-stopped', function (event, args) {
      console.log('timer-stopped args = ', args);
      $scope.updateLessons();
    });

    $scope.updateLessons();
  });
