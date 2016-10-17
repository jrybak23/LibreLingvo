'use strict';

describe('Service: enableUser', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var enableUser;
  beforeEach(inject(function (_enableUser_) {
    enableUser = _enableUser_;
  }));

  it('should do something', function () {
    expect(!!enableUser).toBe(true);
  });

});
