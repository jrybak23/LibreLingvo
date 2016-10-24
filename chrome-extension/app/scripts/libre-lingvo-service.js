/**
 * Created by igorek2312 on 22.10.16.
 */
'use strict'

var HOST_URL = 'http://localhost:8080';
var BASE_URL = 'http://localhost:9000';

var WordDto = function (text, langKey) {
  this.text = text;
  this.langKey = langKey;
};

var TranslationDto = function (sourceWordDto, resultWordDto, partOfSpeech) {
  this.sourceWordDto = sourceWordDto;
  this.resultWordDto = resultWordDto;
  this.partOfSpeech = partOfSpeech;
};

var CheckUserTranslationsDto = function (sourceWordDto, resultLangKey) {
  this.sourceWordDto = sourceWordDto;
  this.resultLangKey = resultLangKey;
};

var RequestSettings = function (method, url, data) {
  this.method = method;
  this.url = url;
  this.data = data;
  this.headers = {};
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
  getUserTranslations: function (checkUserTranslationsDto) {
    console.log('getUserTranslations request:', checkUserTranslationsDto);
    //rest api request return promise

    return Promise.resolve('list');
  },
  saveTranslation: function (translationDto) {
    console.log('saveTranslation request', translationDto);
    return Promise.resolve('list');
  }
};




