'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.tags
 * @description
 * # tags
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Tags', function ($resource, HostUrl) {
    return $resource(HostUrl + "/api/v1/users/me/tags/:tagId/:field/:translationId", {
        folderId: '@id',
        field: '@fieldName'
      },
      {
        update: {method: 'PUT'}
      });
  });
