'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.verificationTokens
 * @description
 * # verificationTokens
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('VerificationTokens', function ($resource, HostUrl) {
    return $resource(HostUrl+'/api/v1/verification-tokens/:verificationToken/user/:field',
      {
        verificationToken:'@id',
        field: '@fieldName'
      },
      {
        update: {method: 'PUT'}
      }
    );
  });
