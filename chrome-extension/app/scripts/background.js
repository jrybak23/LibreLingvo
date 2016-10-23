'use strict';

var BASE_URL = 'http://localhost:9000';

chrome.runtime.onInstalled.addListener(function (details) {
  console.log('previousVersion', details.previousVersion);
});

chrome.extension.onRequest.addListener(function (request, sender, sendResponse) {
  if (request === "getAccessToken") {
    chrome.cookies.get({url: BASE_URL, name: 'access_token'}, function (cookie) {
      sendResponse(cookie.value);
    });
  }
});