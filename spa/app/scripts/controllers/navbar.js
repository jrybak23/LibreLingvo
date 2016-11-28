/**
 * Created by igorek2312 on 14.11.16.
 */
'use strict'

angular.module('libreLingvoApp')
  .controller('NavBarCtrl', function ($scope,
                                      $window,
                                      $rootScope,
                                      oauth2,
                                      messageBox,
                                      Lessons,
                                      lessonsUpdater,
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
      oauth2.logOut();
    };

    $scope.$on('timer-stopped', function (event, args) {
      lessonsUpdater.updateLessons(true);
    });

    $scope.deleteLesson = function (lesson) {
      $rootScope.hideAffix = true;
      messageBox.showGeneralQuestion('question.on.delete.lesson').then(
        function () {
          Lessons.delete(
            {lessonId: lesson.id},
            function () {
              lessonsUpdater.updateLessons();
              $rootScope.updateTranslations();
            },
            function (error) {
              if (error.data && error.data.errorCode === 404) {
                lessonsUpdater.updateLessons();
                $rootScope.updateTranslations();
              }
            }
          );
          $rootScope.hideAffix = false;
        },
        function () {
          $rootScope.hideAffix = false;
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
    };

  });
