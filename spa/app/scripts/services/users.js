'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.users
 * @description
 * # users
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Users', function ($resource, HostUrl) {
    return $resource(HostUrl + '/api/v1/users/:userId', {userId: '@id'}, {
        update: {method: 'PUT'}
      });
  });
