'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.audio
 * @description
 * # audio
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('audio', function () {
    var alert = new Audio('sounds/ring.wav');

    return {
      playAlert: function () {
        alert.play();
      }
    };
  });
