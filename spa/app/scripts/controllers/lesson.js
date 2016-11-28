'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LessonCtrl
 * @description
 * # LessonCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LessonCtrl', function ($scope, $state, $stateParams, Lessons, tts, Users, lessonsUpdater) {
    $scope.lessonPromise = Lessons.get(
      {
        lessonId: $stateParams.lessonId
      },
      function (response) {
        $scope.lesson = response;

        if ($scope.lesson.completedPartsOfLesson === 0)
          $state.go('lesson.view');
        else {
          $state.go('lesson.exam');
        }
      },
      function (error) {
        if (error.data && error.data.errorCode === 7) {
          lessonsUpdater.updateLessons();
          $state.go("user-translations");
        }
      }
    ).$promise;

    Users.get({'userId': 'me'}, function (response) {
      $scope.autoPlay = response.autoPlayDuringLesson;
    });

    $scope.index = 0;
    $scope.play = tts.play;
    $scope.supports = tts.supports;
  });
