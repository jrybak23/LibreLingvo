'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.currentUser
 * @description
 * # currentUser
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('CurrentUser', function ($resource, HOST_URL) {
    return $resource(HOST_URL + '/api/users/me', null, {'update': {method: 'PUT'}});
  });
