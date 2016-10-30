/**
 * Created by igorek2312 on 22.10.16.
 */
'use strict'

var HOST_URL = 'http://localhost:8080';
var BASE_URL = 'http://localhost:9000';

var TranslationDto = function (sourceText,
                               sourceLangKey,
                               resultText,
                               resultLangKey,
                               partOfSpeech) {
  this.sourceText = sourceText;
  this.sourceLangKey = sourceLangKey;
  this.resultText = resultText;
  this.resultLangKey = resultLangKey;
  this.partOfSpeech = partOfSpeech;
  this.note = null;
};

var RequestSettings = function (method, url, data) {
  this.method = method;
  this.url = url;
  this.data = data;
  this.headers = {
    "content-type": "application/json",
    "accept-language": "en"
  };
  this.crossDomain = true;
  this.async = true;
};

var sendAjaxRequest = function (message) {
  return new Promise(function (resolve, reject) {
    chrome.extension.sendMessage(message, function (result) {
      if (result.status === 'success')
        resolve(result.response);
      if (result.status === 'error')
        reject(result.error);
    });
  });
};

var libreLingvoService = {
  viewAuthorities: function () {
    var requestSettings = new RequestSettings('GET', HOST_URL + '/api/users/me/authorities');
    var message = {
      action: 'sendAjaxRequest',
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  },
  getUserTranslations: function (sourceText, sourceLangKey, resultLangKey) {
    console.log('getUserTranslations request:', sourceText, sourceLangKey, resultLangKey);

    var requestSettings = new RequestSettings(
      'GET',
      HOST_URL + '/api/users/me/translations?sourceText='+sourceText+
      '&sourceLangKey='+sourceLangKey+
      '&resultLangKey='+resultLangKey
    );

    var message = {
      action: 'sendAjaxRequest',
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  },
  saveTranslation: function (translationDto) {
    console.log('saveTranslation request', translationDto);

    var requestSettings = new RequestSettings(
      'POST',
      HOST_URL + '/api/users/me/translations',
      JSON.stringify(translationDto)
    );

    var message = {
      action: 'sendAjaxRequest',
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  }
};




