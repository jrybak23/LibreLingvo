'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.userAuthorities
 * @description
 * # userAuthorities
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('UserAuthorities', function ($resource, HOST_URL) {
    return $resource(HOST_URL + "/api/users/me/authorities");
  });
