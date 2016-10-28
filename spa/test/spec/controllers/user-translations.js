'use strict';

describe('Controller: UserTranslationsCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var UserTranslationsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UserTranslationsCtrl = $controller('UserTranslationsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(UserTranslationsCtrl.awesomeThings.length).toBe(3);
  });
});
