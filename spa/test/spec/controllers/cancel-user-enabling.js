'use strict';

describe('Controller: CancelUserEnablingCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var CancelUserEnablingCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CancelUserEnablingCtrl = $controller('CancelUserEnablingCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CancelUserEnablingCtrl.awesomeThings.length).toBe(3);
  });
});
