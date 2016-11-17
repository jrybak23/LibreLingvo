/**
 * Created by igorek2312 on 14.11.16.
 */
'use strict'

angular.module('libreLingvoApp')
  .controller('NavBarCtrl', function ($scope,
                                      $window,
                                      $rootScope,
                                      Oauth2,
                                      MessageBox,
                                      Lessons,
                                      LessonsUpdater,
                                      NotificationType) {

    var w = angular.element($window);
    $scope.$watch(
      function () {
        return $window.innerWidth;
      },
      function (value) {
        $scope.windowWidth = value;
      },
      true
    );

    w.bind('resize', function () {
      $scope.$apply();
    });

    $scope.logOut = function () {
      Oauth2.logOut();
    };

    $scope.$on('timer-stopped', function (event, args) {
      console.log('timer-stopped');
      LessonsUpdater.updateLessons();
    });

    $scope.deleteLesson = function (lesson) {
      MessageBox.showGeneralQuestion('question.on.delete.lesson').then(
        function () {
          Lessons.delete(
            {lessonId: lesson.id},
            function () {
              $rootScope.updateLessons();
            },
            function (error) {
              if (error.data && error.data.errorCode === 404)
                $rootScope.updateLessons();
            }
          );
        }
      );
    };

    $scope.getBellClass = function () {
      switch ($rootScope.notification) {
        case NotificationType.LESSON_AVAILABLE:
          return "text-info";
        case NotificationType.LESSON_NOT_AVAILABLE:
          return "text-warning";
      }
    }
  });
