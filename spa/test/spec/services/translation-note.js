'use strict';

describe('Service: translationNote', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var translationNote;
  beforeEach(inject(function (_translationNote_) {
    translationNote = _translationNote_;
  }));

  it('should do something', function () {
    expect(!!translationNote).toBe(true);
  });

});
