'use strict';

describe('Service: MessageBoxService', function () {

  // load the service's module
  beforeEach(module('libreLingvoApp'));

  // instantiate service
  var MessageBoxService;
  beforeEach(inject(function (_MessageBoxService_) {
    MessageBoxService = _MessageBoxService_;
  }));

  it('should do something', function () {
    expect(!!MessageBoxService).toBe(true);
  });

});
