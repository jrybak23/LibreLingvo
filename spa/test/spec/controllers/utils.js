'use strict';

describe('Controller: UtilsCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var UtilsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UtilsCtrl = $controller('UtilsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(UtilsCtrl.awesomeThings.length).toBe(3);
  });
});
