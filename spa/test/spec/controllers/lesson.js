'use strict';

describe('Controller: LessonCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var LessonCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    LessonCtrl = $controller('LessonCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(LessonCtrl.awesomeThings.length).toBe(3);
  });
});
