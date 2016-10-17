'use strict';

describe('Controller: ModalMessageCtrlCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var ModalMessageCtrlCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ModalMessageCtrlCtrl = $controller('ModalMessageCtrlCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ModalMessageCtrlCtrl.awesomeThings.length).toBe(3);
  });
});
