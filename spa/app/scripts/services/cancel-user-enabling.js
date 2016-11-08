'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.cancelUserEnabling
 * @description
 * # cancelUserEnabling
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('CancelUserEnabling', function ($resource, HostUrl) {
    return $resource(HostUrl + '/api/v1/users/cancel-enabling/:verificationToken', {verificationToken: '@id'});
  });
