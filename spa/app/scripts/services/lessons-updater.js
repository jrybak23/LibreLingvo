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
                                       Lessons,
                                       NotificationType,
                                       amUtcFilter,
                                       amLocalFilter,
                                       amDifferenceFilter,
                                       audio) {
    var getSecondsDiff = function (date) {
      var seconds = amDifferenceFilter(
        amLocalFilter(
          amUtcFilter(date)
        ), null, 'seconds');
      return (seconds > 2) ? seconds : -1;
    };

    return {
      updateLessons: function (timerStopped) {
        return Lessons.query(
          function (respose) {

            respose.forEach(function (lesson) {
              lesson.diffInSeconds = getSecondsDiff(lesson.waitUnitNextLessonPart);
            });

            $rootScope.lessons = respose;

            var availableLesson = $rootScope.lessons.find(function (lesson) {
              return lesson.diffInSeconds < 0;
            });

            if (availableLesson) {
              $rootScope.notification = NotificationType.LESSON_AVAILABLE;
              if (timerStopped)
                audio.playAlert();
              return;
            }

            var notAvailableLesson = $rootScope.lessons.find(function (lesson) {
              return lesson.diffInSeconds > 0;
            });

            if (notAvailableLesson) {
              $rootScope.notification = NotificationType.LESSON_NOT_AVAILABLE;
              return;
            }

            $rootScope.notification = NotificationType.NO_LESSONS;
          }).$promise;
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
