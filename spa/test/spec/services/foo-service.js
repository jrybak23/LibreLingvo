'use strict';

describe('Service: fooService', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var fooService;
  beforeEach(inject(function (_fooService_) {
    fooService = _fooService_;
  }));

  it('should do something', function () {
    expect(!!fooService).toBe(true);
  });

});
