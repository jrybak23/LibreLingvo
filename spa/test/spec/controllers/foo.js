'use strict';

describe('Controller: FooCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var FooCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FooCtrl = $controller('FooCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(FooCtrl.awesomeThings.length).toBe(3);
  });
});
