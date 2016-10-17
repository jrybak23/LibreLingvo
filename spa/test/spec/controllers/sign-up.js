'use strict';

describe('Controller: SignUpCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var SignUpCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SignUpCtrl = $controller('SignUpCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SignUpCtrl.awesomeThings.length).toBe(3);
  });
});
