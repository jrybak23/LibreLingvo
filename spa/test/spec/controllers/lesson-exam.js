'use strict';

describe('Controller: LessonExamCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var LessonExamCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    LessonExamCtrl = $controller('LessonExamCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(LessonExamCtrl.awesomeThings.length).toBe(3);
  });
});
