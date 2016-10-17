'use strict';

describe('Service: oauth2', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var oauth2;
  beforeEach(inject(function (_oauth2_) {
    oauth2 = _oauth2_;
  }));

  it('should do something', function () {
    expect(!!oauth2).toBe(true);
  });

});
