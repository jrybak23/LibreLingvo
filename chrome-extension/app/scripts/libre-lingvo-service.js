/**
 * Created by igorek2312 on 22.10.16.
 */
'use strict'
var HOSTING = true;
var HOSTING_URL = 'https://libre-lingvo.herokuapp.com/';
var HOST_URL = HOSTING ? HOSTING_URL : 'http://localhost:8080';
var BASE_URL = HOSTING ? HOSTING_URL : 'http://localhost:9000';

var TranslationDto = function (sourceText,
                               sourceLangCode,
                               resultText,
                               resultLangCode,
                               partOfSpeech,
                               note) {
  this.sourceText = sourceText;
  this.sourceLangCode = sourceLangCode;
  this.resultText = resultText;
  this.resultLangCode = resultLangCode;
  this.partOfSpeech = partOfSpeech;
  this.note = note;
};

var RequestSettings = function (method, url, data) {
  this.method = method;
  this.url = url;
  this.data = data;
  this.headers = {
    'content-type': 'application/json',
    'accept-language': 'en'
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
    var requestSettings = new RequestSettings('GET', HOST_URL + '/api/v1/users/me/authorities');
    var message = {
      action: 'sendAjaxRequest',
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  },
  getUserTranslations: function (sourceText, sourceLangCode, resultLangCode) {

    var requestSettings = new RequestSettings(
      'GET',
      HOST_URL + '/api/v1/users/me/translations?source-text=' + sourceText +
      '&source-lang-code=' + sourceLangCode +
      '&result-lang-code=' + resultLangCode
    );

    var message = {
      action: 'sendAjaxRequest',
      baseUrl: BASE_URL,
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  },
  saveTranslation: function (translationDto) {
    var requestSettings = new RequestSettings(
      'POST',
      HOST_URL + '/api/v1/users/me/translations',
      JSON.stringify(translationDto)
    );

    var message = {
      action: 'sendAjaxRequest',
      baseUrl: BASE_URL,
      requestSettings: requestSettings
    };

    return sendAjaxRequest(message);
  }
};




