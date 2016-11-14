'use strict';

/**
 * @ngdoc function
 * @name libreLingvoApp.controller:LessonExamCtrl
 * @description
 * # LessonExamCtrl
 * Controller of the libreLingvoApp
 */
angular.module('libreLingvoApp')
  .controller('LessonExamCtrl', function ($scope, $state,$timeout,$rootScope, Lessons) {
    var n = 5;
    $scope.reverse = false;
    $scope.showAnswers = false;

    function shuffle(a) {
      var j, x, i;
      for (i = a.length; i; i--) {
        j = Math.floor(Math.random() * i);
        x = a[i - 1];
        a[i - 1] = a[j];
        a[j] = x;
      }
    }

    function getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    $scope.setVariants = function () {
      var translations = $scope.lesson.translations;
      var temp = translations.slice();
      shuffle(temp);
      var current = translations[$scope.index];

      $scope.variants = temp.splice(0, n);

      if ($scope.variants.indexOf(current) === -1)
        $scope.variants[getRandomInt(0, $scope.variants.length - 1)] = current;
    };

    $scope.answer = function (variant) {
      if ($scope.showAnswers) return;
      var translations = $scope.lesson.translations;
      if (variant === translations[$scope.index]) {
        if (translations[$scope.index + 1]){
          $scope.index++;
          $scope.setVariants();
        }
        else {
          if ($scope.reverse) {
            Lessons.update(
              {
                lessonId: $scope.lesson.id,
                field: 'lesson-part'
              },
              {},
              function () {
                $rootScope.updateLessons().then(
                  function () {
                    $state.go('user-translations');
                  }
                );
              }
            );
          }
          else {
            $scope.reverse = true;
            $scope.index = 0;
            $scope.setVariants();
          }
        }
      }
      else {
        $scope.showAnswers=true;
        $timeout(function () {
            $scope.showAnswers=false;
            $scope.index = 0;
            $scope.setVariants();
        }, 2000);
      }
    };

    $scope.lessonPromise.then(
      function () {
        $scope.setVariants();

        $scope.$watch('index',function () {
          console.log($scope.index);
          $scope.sourceText = $scope.lesson.translations[$scope.index].sourceWord.text;
          $scope.sourceLangCode = $scope.lesson.translations[$scope.index].sourceWord.langCode;
          $scope.resultText = $scope.lesson.translations[$scope.index].resultWord.text;
          $scope.resultLangCode = $scope.lesson.translations[$scope.index].resultWord.langCode;
          $scope.partOfSpeech = $scope.lesson.translations[$scope.index].partOfSpeech;
        });
      }
    );

    $scope.rightVariant = function (variant) {
      return variant===$scope.lesson.translations[$scope.index];
    }
  });
