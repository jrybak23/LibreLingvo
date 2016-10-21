'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.currentUser
 * @description
 * # currentUser
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('CurrentUser', function ($resource, HostUrl) {
    return $resource(HostUrl + '/api/users/me', null, {'update': {method: 'PUT'}});
  });
