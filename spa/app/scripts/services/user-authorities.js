'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.userAuthorities
 * @description
 * # userAuthorities
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('UserAuthorities', function ($resource, HostUrl) {
    return $resource(HostUrl + "/api/users/me/authorities");
  });
