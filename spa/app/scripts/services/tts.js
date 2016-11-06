'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.tts
 * @description
 * # tts
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('TTS', function () {
    var SpeechSynthesisUtterance = window.webkitSpeechSynthesisUtterance ||
      window.mozSpeechSynthesisUtterance ||
      window.msSpeechSynthesisUtterance ||
      window.oSpeechSynthesisUtterance ||
      window.SpeechSynthesisUtterance;

    if (SpeechSynthesisUtterance) {
      var utterance = new SpeechSynthesisUtterance();
      var voices = window.speechSynthesis.getVoices();
      utterance.voice = voices[0]; // Note: some voices don't support altering params
      //msg.voiceURI = 'native';

      utterance.volume = 1; // 0 to 1
      utterance.rate = 0.7; // 0.1 to 10
      //msg.pitch = 1; //0 to 2
      utterance.lang = 'en';
    }

    return {
      play: function (langCode, text) {
        if (SpeechSynthesisUtterance) {
          utterance.lang = langCode;
          utterance.text = text;
          speechSynthesis.speak(utterance);
        }
      }
    };
  });
