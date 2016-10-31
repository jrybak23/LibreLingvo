'use strict';

describe('Controller: TranslationDetailCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var TranslationDetailCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    TranslationDetailCtrl = $controller('TranslationDetailCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(TranslationDetailCtrl.awesomeThings.length).toBe(3);
  });
});
