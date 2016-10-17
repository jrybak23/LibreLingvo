'use strict';

describe('Service: fooFactory', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var fooFactory;
  beforeEach(inject(function (_fooFactory_) {
    fooFactory = _fooFactory_;
  }));

  it('should do something', function () {
    expect(!!fooFactory).toBe(true);
  });

});
