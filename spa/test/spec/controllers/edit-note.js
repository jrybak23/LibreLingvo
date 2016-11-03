'use strict';

describe('Controller: EditNoteCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var EditNoteCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    EditNoteCtrl = $controller('EditNoteCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(EditNoteCtrl.awesomeThings.length).toBe(3);
  });
});
