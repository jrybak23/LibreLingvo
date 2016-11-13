'use strict';

describe('Service: lessons', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var lessons;
  beforeEach(inject(function (_lessons_) {
    lessons = _lessons_;
  }));

  it('should do something', function () {
    expect(!!lessons).toBe(true);
  });

});
