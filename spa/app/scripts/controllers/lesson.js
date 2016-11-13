'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LessonCtrl
 * @description
 * # LessonCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LessonCtrl', function ($scope, $state, $stateParams, Lessons) {
    Lessons.get(
      {
        lessonId: $stateParams.lessonId
      },
      function (response) {
        $scope.lesson = response;

        if ($scope.lesson.completedPartsOfLesson === 0)
          $state.go('lesson.view');
        else
          $state.go('lesson.exam');
      }
    );
  });
