'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.oauth2
 * @description
 * # oauth2
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Oauth2', function (HostUrl, $http, $cookies, $httpParamSerializer, $q, $rootScope, $state, UserAuthorities) {
    var accessTokenData = {grant_type: "password"};
    var encoded = btoa("clientapp:123456");

    if ($cookies.get("access_token"))
      $http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("access_token");

    var logInReq = {
      method: 'POST',
      url: HostUrl + "/oauth/token",
      headers: {
        "Authorization": "Basic " + encoded,
        "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
      }
    };

    var logOutReq = {
      method: 'DELETE',
      url: HostUrl + "/api/v1/oauth/revoke-token"
    };

    var authorities = ['ROLE_ANONYMOUS'];

    var updateAuthorities = function () {
      $rootScope.hasUserAuthority = authorities.indexOf('ROLE_USER') > -1;
      $rootScope.hasAdminAuthority = authorities.indexOf('ROLE_ADMIN') > -1;
      $rootScope.isAnnonymos = authorities.indexOf('ROLE_ANONYMOUS') > -1;
    };

    var loadAndUpdateAuthorities = function () {
      var deferred = $q.defer();
      $rootScope.updateAuthoritiesPromise= deferred.promise;
      UserAuthorities.get(function (data) {
        authorities = data.authorities;
        updateAuthorities();
        deferred.resolve();
      });
    };
    loadAndUpdateAuthorities();

    return {
      logIn: function (username, password) {
        accessTokenData.username = username;
        accessTokenData.password = password;

        logInReq.data = $httpParamSerializer(accessTokenData);

        var deferred = $q.defer();
        $http(logInReq).then(function (data) {
          $http.defaults.headers.common.Authorization = 'Bearer ' + data.data.access_token;
          $cookies.put("access_token", data.data.access_token);

          loadAndUpdateAuthorities();
          deferred.resolve();
        }, function (error) {
          deferred.reject(error);
        });

        return deferred.promise;
      },
      logOut: function () {
        var deferred = $q.defer();
        $http(logOutReq).then(function (data) {
            delete $http.defaults.headers.common.Authorization;
            $cookies.remove("access_token");
            loadAndUpdateAuthorities();
            deferred.resolve();
          }, function (error) {
            deferred.reject(error);
          }
        );

        return deferred.promise;
      },
      handleInvalidToken: function () {
        $cookies.remove("access_token");
        delete $http.defaults.headers.common.Authorization;
        location.href=location.origin;
      }
    };
  });
