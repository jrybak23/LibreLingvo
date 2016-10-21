'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:IndexCtrl
 * @description
 * # IndexCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('IndexCtrl', function ($scope, $translate, $cookies, $http, Oauth2) {

    $scope.changeLanguage = function (lang_key) {
      $http.defaults.headers.common['Accept-Language'] = lang_key;
      $cookies.put("default_lang_key", lang_key);
      $scope.currentLanguage = lang_key;
      $translate.use(lang_key);
    };

    $scope.changeLanguage($cookies.get("default_lang_key") || "en");

    $scope.logOut = function () {
      Oauth2.logOut();
    }
  });
