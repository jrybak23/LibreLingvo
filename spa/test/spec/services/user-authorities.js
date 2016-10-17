'use strict';

describe('Service: userAuthorities', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var userAuthorities;
  beforeEach(inject(function (_userAuthorities_) {
    userAuthorities = _userAuthorities_;
  }));

  it('should do something', function () {
    expect(!!userAuthorities).toBe(true);
  });

});
