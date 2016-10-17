'use strict';

/**
 * @ngdoc overview
 * @name libreLingvoApp
 * @description
 * # libreLingvoApp
 *
 * Main module of the application.
 */
angular
  .module('libreLingvoApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'ui.bootstrap',
    'pascalprecht.translate',
    'angular-loading-bar'
  ])
  .constant('HOST_URL', 'http://localhost:8080')
  .config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state({
        name: 'main',
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .state({
        name: 'log-in',
        url: '/log-in',
        templateUrl: 'views/log-in.html',
        controller: 'LogInCtrl',
        controllerAs: 'logIn'
      })
      .state({
        name: 'sign-up',
        url: '/sign-up',
        templateUrl: 'views/sign-up.html',
        controller: 'SignUpCtrl',
        controllerAs: 'signUp'
      })
      .state({
        name: 'about',
        url: '/about',
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .state({
        name: 'profile',
        url: '/profile',
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl',
        controllerAs: 'profile'
      })
      .state({
        name: 'enable-user',
        url: '/enable-user/:verificationToken',
        controller: 'EnableUserCtrl',
        controllerAs: 'enableUser'
      })
      .state({
        name: 'cancel-user-enabling',
        url: '/cancel-user-enabling/:verificationToken',
        controller: 'CancelUserEnablingCtrl',
        controllerAs: 'cancelUserEnabling'
      });

    $urlRouterProvider
      .otherwise('/');

  })
  .config(function ($httpProvider, MessageType) {
    $httpProvider.interceptors.push(function ($q, $injector) {
      return {
        request: function (config) {
          return config || $q.resolve(config);
        },
        requestError: function (request) {
          return $q.reject(request);
        },
        response: function (response) {
          return response || $q.resolve(response);
        },
        responseError: function (response) {
          console.log(response);
          var messageBox = $injector.get('MessageBox');
          if (response.data && response.data.error_description === 'Bad credentials')
            messageBox.show('error.invalid.password', MessageType.ERROR);
          else if (response.data && response.data.error_description === 'User is disabled')
            messageBox.show('error.disabled.account', MessageType.ERROR);
          else if (response.data && response.data.error_description === 'User account is locked')
            messageBox.show('error.locked.account', MessageType.ERROR);
          else if (response.data && response.data.message)
            messageBox.show(response.data.message, MessageType.ERROR, false);
          else
            messageBox.show(response, MessageType.ERROR, false);
          return $q.reject(response);
        }
      };
    });
  })
  .config(function ($translateProvider, HOST_URL) {
    $translateProvider.useUrlLoader(HOST_URL + '/messageBundle');
    $translateProvider.preferredLanguage('en');
    $translateProvider.fallbackLanguage('en');
  });
