'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.translationNote
 * @description
 * # translationNote
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('TranslationNote', function ($resource,HostUrl) {
    return $resource(HostUrl + "/api/users/:userId/translations/:translationId/note",
      {
        userId: '@id'
      },
      {
        update: {method: 'PUT'}
      });
  });
