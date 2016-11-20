'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LessonViewCtrl
 * @description
 * # LessonViewCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LessonViewCtrl', function ($scope, $state, $stateParams, Lessons, messageBox, MessageType) {
    $scope.next = function () {
      if ($scope.lesson.translations[$scope.index + 1])
        $scope.index++;
      else
        MessageBox.showGeneralQuestion('question.after.first.lesson.part', MessageType.INFO).then(
          function () {
            Lessons.update(
              {
                lessonId: $scope.lesson.id,
                field: 'lesson-part'
              },
              {},
              function () {
                $state.go('lesson.exam', {lessonId: $scope.lesson.id})
              }
            );
          }
        );
    };

    $scope.back = function () {
      if ($scope.lesson.translations[$scope.index - 1])
        $scope.index--;
    };

    $scope.lessonPromise.then(
      function () {
        $scope.$watch('index', function () {
          $scope.sourceText = $scope.lesson.translations[$scope.index].sourceWord.text;
          $scope.sourceLangCode = $scope.lesson.translations[$scope.index].sourceWord.langCode;
          $scope.resultText = $scope.lesson.translations[$scope.index].resultWord.text;
          $scope.resultLangCode = $scope.lesson.translations[$scope.index].resultWord.langCode;
          $scope.partOfSpeech = $scope.lesson.translations[$scope.index].partOfSpeech;

          if ($scope.autoPlay && $scope.supports($scope.sourceLangCode))
            $scope.play($scope.sourceLangCode, $scope.sourceText);
        });
      }
    );
  });
