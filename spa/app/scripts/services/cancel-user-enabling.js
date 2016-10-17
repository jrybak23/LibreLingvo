'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.cancelUserEnabling
 * @description
 * # cancelUserEnabling
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('CancelUserEnabling', function ($resource, HOST_URL) {
    return $resource(HOST_URL + '/api/users/cancel-enabling/:verificationToken', {verificationToken: '@id'});
  });
