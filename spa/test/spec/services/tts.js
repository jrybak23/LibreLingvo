'use strict';

describe('Service: tts', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var tts;
  beforeEach(inject(function (_tts_) {
    tts = _tts_;
  }));

  it('should do something', function () {
    expect(!!tts).toBe(true);
  });

});
