'use strict';

describe('Controller: SelectTagModalCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var SelectTagModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SelectTagModalCtrl = $controller('SelectTagModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SelectTagModalCtrl.awesomeThings.length).toBe(3);
  });
});
