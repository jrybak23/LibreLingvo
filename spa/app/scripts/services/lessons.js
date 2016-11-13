'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.lessons
 * @description
 * # lessons
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('Lessons', function ($resource, HostUrl) {
    return $resource(HostUrl + "/api/v1/users/me/lessons/:lessonId/:field", {
        lessonId: '@id',
        field: '@fieldName'
      },
      {
        update: {method: 'PUT'}
      });
  });
