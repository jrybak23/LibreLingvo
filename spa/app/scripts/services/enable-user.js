'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.enableUser
 * @description
 * # enableUser
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('EnableUser', function ($resource,HOST_URL) {
    return $resource(HOST_URL+'/api/users/enable/:verificationToken',{verificationToken:'@id'});
  });
