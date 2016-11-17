'use strict';

describe('Service: lessonsUpdater', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var lessonsUpdater;
  beforeEach(inject(function (_lessonsUpdater_) {
    lessonsUpdater = _lessonsUpdater_;
  }));

  it('should do something', function () {
    expect(!!lessonsUpdater).toBe(true);
  });

});
