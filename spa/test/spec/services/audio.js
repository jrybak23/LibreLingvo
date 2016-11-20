'use strict';

describe('Service: audio', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var audio;
  beforeEach(inject(function (_audio_) {
    audio = _audio_;
  }));

  it('should do something', function () {
    expect(!!audio).toBe(true);
  });

});
