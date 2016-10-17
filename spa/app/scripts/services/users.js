'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.users
 * @description
 * # users
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Users', function ($resource,HOST_URL) {
    return $resource(HOST_URL + '/api/users/:userId', {userId: '@id'}, {
        update: {method: 'PUT'}
      });
  });
