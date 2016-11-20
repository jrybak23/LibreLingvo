'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.lessonsUpdater
 * @description
 * # lessonsUpdater
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('lessonsUpdater', function ($rootScope,
                                       amUtcFilter,
                                       amLocalFilter,
                                       amDifferenceFilter,
                                       Lessons,
                                       NotificationType) {
    var promiseContainer = {};

    return {
      updateLessons: function () {
        if ((!promiseContainer.lessonsPromise) || (promiseContainer.lessonsPromise && promiseContainer.lessonsPromise.$$state.status)) {
          promiseContainer.lessonsPromise = Lessons.query(
            function (respose) {
              $rootScope.lessons = respose;
              $rootScope.lessons.forEach(function (lesson) {
                lesson.diffInSeconds = amDifferenceFilter(
                  amLocalFilter(
                    amUtcFilter(lesson.waitUnitNextLessonPart)
                  ), null, 'seconds'
                );
              });

              $rootScope.$watch('lessons', function () {
                var lessonsAvailable = $rootScope.lessons.find(function (lesson) {
                  return lesson.diffInSeconds < 0;
                });

                if (lessonsAvailable) {
                  $rootScope.notification = NotificationType.LESSON_AVAILABLE;
                  return;
                }

                var lessonNotAvailable = $rootScope.lessons.find(function (lesson) {
                  return lesson.diffInSeconds > 0;
                });

                if (lessonNotAvailable) {
                  $rootScope.notification = NotificationType.LESSON_NOT_AVAILABLE;
                  return;
                }

                $rootScope.notification = NotificationType.NO_LESSONS;
              });
            }).$promise;
        }
        return promiseContainer.lessonsPromise;
      }
    };
  })
  .constant('NotificationType',
    {
      LESSON_AVAILABLE: 1,
      LESSON_NOT_AVAILABLE: 2,
      NO_LESSONS: 3
    }
  );
