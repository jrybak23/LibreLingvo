'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.translations
 * @description
 * # translations
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Translations', function (HostUrl, $resource) {
    return $resource(HostUrl + "/api/v1/users/:userId/translations/:translationId/:field",
      {
        userId: '@id',
        field: '@fieldName'
      },
      {
        update: {method: 'PUT'}
      });
  });
