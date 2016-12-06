'use strict';

describe('Controller: InputDialogModalCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var InputDialogModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    InputDialogModalCtrl = $controller('InputDialogModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(InputDialogModalCtrl.awesomeThings.length).toBe(3);
  });
});
