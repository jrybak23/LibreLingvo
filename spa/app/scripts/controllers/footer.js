/**
 * Created by igorek2312 on 14.11.16.
 */
'use strict'

angular.module('libreLingvoApp')
  .controller('FooterCtrl', function ($scope, $cookies, $http, $translate) {
    $scope.changeLanguage = function (lang_key) {
      $http.defaults.headers.common['Accept-Language'] = lang_key;
      $cookies.put("default_lang_key", lang_key);
      $scope.currentLanguage = lang_key;
      $translate.use(lang_key);
    };

    $scope.changeLanguage($cookies.get("default_lang_key") || "en");
  });
