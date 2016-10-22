'use strict';

var SAVED_ICON_URL = 'https://goo.gl/qFVQnz';
var NOT_SAVED_ICON_URL = 'https://goo.gl/CyvLz1';

var modal = {};

var configureModal = function () {
  // Get the modal
  modal = document.getElementById('mainModal');

// Get the <span> element that closes the modal
  var span = document.getElementsByClassName("close")[0];

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
};

$.get(chrome.extension.getURL('/templates/modal.html'), function (data) {
  $($.parseHTML(data)).appendTo('body');
  configureModal();
});

var buttonTemplate = '<div id="save-button" role="button"></div>';
var saveButton = $(buttonTemplate);
saveButton.insertBefore('#gt-pb-star');

var search = function () {
  var dto = {};
  dto.source = $('#source').val();
  dto.result = $('#result_box').text();
  dto.simpleSource = $('.gt-card-ttl-txt').first().text();
  //dto.definitionHtml = $('.gt-cd.gt-cd-md').html();
  console.log(dto);
};

var toggle = true;
var onClick = function (e) {
  modal.style.display = "block";

  if (toggle)
    $('#save-button').css('background', 'url(' + SAVED_ICON_URL + ') no-repeat center');
  else
    $('#save-button').css('background', 'url(' + NOT_SAVED_ICON_URL + ') no-repeat center');

  toggle = !toggle;

  search();
};
saveButton.on('click', onClick);