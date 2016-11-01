'use strict';

describe('Controller: EditTranslationCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var EditTranslationCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    EditTranslationCtrl = $controller('EditTranslationCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(EditTranslationCtrl.awesomeThings.length).toBe(3);
  });
});
