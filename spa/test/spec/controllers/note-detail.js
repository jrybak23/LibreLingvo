'use strict';

describe('Controller: NoteDetailCtrl', function () {

  // load the controller's module
  beforeEach(module('libreLingvoApp'));

  var NoteDetailCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    NoteDetailCtrl = $controller('NoteDetailCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(NoteDetailCtrl.awesomeThings.length).toBe(3);
  });
});
