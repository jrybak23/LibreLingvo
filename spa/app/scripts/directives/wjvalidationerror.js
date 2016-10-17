'use strict';

/**
 * @ngdoc directive
 * @name libreLingvoApp.directive:wjValidationError
 * @description
 * # wjValidationError
 */
angular.module('libreLingvoApp')
  .directive('wjValidationError', function ($filter) {
    return {
      require: 'ngModel',
      link: function (scope, elm, attrs, ctl) {
        scope.$watch(attrs['wjValidationError'], function (errorMsg) {
          var translateFilter = $filter('translate');
          elm[0].setCustomValidity(translateFilter(errorMsg));
          ctl.$setValidity('wjValidationError', errorMsg ? false : true);
        });
      }
    };
  });
