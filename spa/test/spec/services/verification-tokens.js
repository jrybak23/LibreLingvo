'use strict';

describe('Service: verificationTokens', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var verificationTokens;
  beforeEach(inject(function (_verificationTokens_) {
    verificationTokens = _verificationTokens_;
  }));

  it('should do something', function () {
    expect(!!verificationTokens).toBe(true);
  });

});
