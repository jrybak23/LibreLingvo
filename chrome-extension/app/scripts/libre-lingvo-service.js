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

var libreLingvoService = {
  hasUserAuthority: false,
  accessToken: "",
  updateAuthorities: function () {
    chrome.extension.sendRequest("getAccessToken", function(response) {
      console.log("access_token:", response);
    });
  },
  getUserTranslations: function (checkUserTranslationsDto) {
    //console.log('getUserTranslations request:', checkUserTranslationsDto);
    //rest api request return promise
    return Promise.resolve('list');
  },
  saveTranslation: function (translationDto) {
    console.log('saveTranslation request', translationDto);
    return Promise.resolve('list');
  }
};

libreLingvoService.updateAuthorities();

