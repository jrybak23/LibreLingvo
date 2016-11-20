'use strict';

describe('Controller: ResetPasswordCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var ResetPasswordCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ResetPasswordCtrl = $controller('ResetPasswordCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ResetPasswordCtrl.awesomeThings.length).toBe(3);
  });
});
