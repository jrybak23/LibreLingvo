'use strict';

describe('Controller: ModalMessageCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var ModalMessageCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ModalMessageCtrl = $controller('ModalMessageCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ModalMessageCtrl.awesomeThings.length).toBe(3);
  });
});
