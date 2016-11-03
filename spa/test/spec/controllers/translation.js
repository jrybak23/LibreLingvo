'use strict';

describe('Controller: TranslationCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var TranslationCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    TranslationCtrl = $controller('TranslationCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(TranslationCtrl.awesomeThings.length).toBe(3);
  });
});
