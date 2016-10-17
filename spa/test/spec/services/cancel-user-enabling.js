'use strict';

describe('Service: cancelUserEnabling', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var cancelUserEnabling;
  beforeEach(inject(function (_cancelUserEnabling_) {
    cancelUserEnabling = _cancelUserEnabling_;
  }));

  it('should do something', function () {
    expect(!!cancelUserEnabling).toBe(true);
  });

});
