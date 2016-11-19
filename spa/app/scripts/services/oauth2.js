'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.oauth2
 * @description
 * # oauth2
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Oauth2', function (HostUrl, $http, $cookies, $httpParamSerializer, $q, $state, UserAuthorities) {
    var accessTokenData = {grant_type: "password"};
    var encoded = btoa("webapp:123456");

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

    var updateAuthoritiesPromise = {};
    var updateCallback;
    var updateAuthorities = function () {
      updateAuthoritiesPromise = UserAuthorities.get(
        function (data) {
          updateCallback(data.authorities);
        }
      ).$promise;
    };

    updateAuthorities();

    return {
      logIn: function (username, password) {
        accessTokenData.username = username;
        accessTokenData.password = password;

        logInReq.data = $httpParamSerializer(accessTokenData);

        var deferred = $q.defer();
        $http(logInReq).then(function (data) {
          $http.defaults.headers.common.Authorization = 'Bearer ' + data.data.access_token;
          $cookies.put("access_token", data.data.access_token);

          updateAuthorities();
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
            updateAuthorities();
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
        location.href = location.origin;
      },
      updateAuthoritiesCallback: function (callback) {
        updateCallback = callback;
      },
      afterUpdateAuthoritiesCallback: function (callback) {
        updateAuthoritiesPromise.then(function () {
          callback();
        });
      }
    };
  });
