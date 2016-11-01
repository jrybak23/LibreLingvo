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
    return $resource(HostUrl + "/api/users/:userId/translations/:translationId",
      {
        userId: '@id'
      },
      {
        update: {method: 'PUT'}
      });
  });
