'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.verificationTokens
 * @description
 * # verificationTokens
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('UsersActions', function ($resource, HostUrl) {
    return $resource(HostUrl+'/api/v1/users/:action',
      {
        action:'@actionName'
      },
      {
        update: {method: 'PUT'}
      }
    );
  });
