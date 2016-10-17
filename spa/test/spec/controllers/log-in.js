'use strict';

describe('Controller: LogInCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var LogInCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    LogInCtrl = $controller('LogInCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(LogInCtrl.awesomeThings.length).toBe(3);
  });
});
