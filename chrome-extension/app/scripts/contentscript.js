'use strict';

var SAVED_ICON_URL = 'https://goo.gl/qFVQnz';
var NOT_SAVED_ICON_URL = 'https://goo.gl/CyvLz1';

var modal = {};
var saveButton = {};
var saveSourceBox = {};
var saveResultBox = {};
var submitSaveButton = {};
var cancelSaveButton = {};
var partOfSpeechCheckbox = {};
var sourceBox = $('#source');
var resultBox = $('#result_box');
var sourceLangKeyInput = $('input[name="sl"]').first();
var resultLangKeyInput = $('input[name="tl"]').first();
//dto.definitionHtml = $('.gt-cd.gt-cd-md').html();
var starButtonId = '#gt-pb-star';


$.get(chrome.extension.getURL('/templates/modal.html'), function (data) {
  $($.parseHTML(data)).appendTo('body');
  // Get the modal
  modal = document.getElementById('mainModal');

  // Get the <span> element that closes the modal
  var span = document.getElementsByClassName('close-modal')[0];

  // When the user clicks on <span> (x), close the modal
  span.onclick = function () {
    modal.style.display = "none";
  };

  // When the user clicks anywhere outside of the modal, close it
  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };

  saveSourceBox = document.getElementById('save-source-box');
  saveResultBox = document.getElementById('save-result-box');
  partOfSpeechCheckbox = document.getElementById('part-of-speech');

  submitSaveButton = document.getElementById('submit-save-button');

  submitSaveButton.onclick = function () {
    var sourceWordDto = new WordDto(saveSourceBox.innerText, sourceLangKeyInput.val());
    var resultWordDto = new WordDto(saveResultBox.value, resultLangKeyInput.val());
    var translationDto = new TranslationDto(sourceWordDto, resultWordDto, partOfSpeechCheckbox.value);
    libreLingvoService.saveTranslation(translationDto).then(
      function (data) {
        setTranslationIsSaved(true);
        console.log(data);
      },
      function (error) {
        console.error(error);
      }
    );
  };

  cancelSaveButton = document.getElementById('cancel-save-button');
  cancelSaveButton.onclick = function () {
    modal.style.display = "none";
  }
});

$.get(chrome.extension.getURL('/templates/save-button.html'), function (data) {
  saveButton = $($.parseHTML(data));
  saveButton.insertBefore(starButtonId);
  saveButton.on('click', function () {
    modal.style.display = "block";
  });
});

var setTranslationIsSaved = function (saved) {
  if (saveButton.css)
    saveButton.css('background', 'url(' + (saved ? SAVED_ICON_URL : NOT_SAVED_ICON_URL) + ') no-repeat center');
};


var checkTranslation = function () {
  var source = sourceBox.val();
  var sourceLangKey = sourceLangKeyInput.val();
  var resultLangKey = resultBox.attr('lang');
  saveSourceBox.innerText = source;
  saveResultBox.value = resultBox.text();

  var sourceWordDto = new WordDto(source, sourceLangKey);
  var checkUserTranslationsDto = new CheckUserTranslationsDto(sourceWordDto, resultLangKey);

  libreLingvoService.getUserTranslations(checkUserTranslationsDto).then(
    function (data) {
      setTranslationIsSaved(true);
     // console.log(data);
    },
    function (error) {
      setTranslationIsSaved(false);
      console.error(error);
    }
  );
};

checkTranslation();
resultBox.on('DOMSubtreeModified', _.debounce(checkTranslation, 800));