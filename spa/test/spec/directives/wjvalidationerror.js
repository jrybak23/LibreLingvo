'use strict';

describe('Directive: wjValidationError', function () {

  // load the directive's module
  beforeEach(module('libreLingvoApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<wj-validation-error></wj-validation-error>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the wjValidationError directive');
  }));
});
