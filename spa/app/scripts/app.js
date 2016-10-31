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
  .constant('GRUNT_SERVE_URL', 'http://localhost:9000')
  .constant('TOMCAT_URL', 'http://localhost:8080')
  .factory('HostUrl', function (GRUNT_SERVE_URL, TOMCAT_URL) {
    var originUrl = location.origin;
    var hostUrl = originUrl == GRUNT_SERVE_URL ? TOMCAT_URL : originUrl;
    return hostUrl;
  })
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
      })
      .state({
        name: 'user-translations',
        url: '/user-translations?page-index&search-substring&part-of-speech',
        templateUrl: 'views/user-translations.html',
        controller: 'UserTranslationsCtrl',
        controllerAs: 'userTranslations'
      })
      .state({
        name: 'translation-detail',
        url: '/translation-detail/:translationId',
        templateUrl: 'views/translation-detail.html',
        controller: 'TranslationDetailCtrl',
        controllerAs: 'translationDetail'
      });

    $urlRouterProvider
      .otherwise('/log-in');

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
          if (response.status === -1)
            messageBox.show('error.connection.refused', MessageType.ERROR);
          else if (response.data) {
            if (response.data.error_description === 'Bad credentials')
              messageBox.show('error.invalid.password', MessageType.ERROR);
            else if (response.data.error_description === 'User is disabled')
              messageBox.show('error.disabled.account', MessageType.ERROR);
            else if (response.data.error_description === 'User account is locked')
              messageBox.show('error.locked.account', MessageType.ERROR);
            else if (response.data.error === 'invalid_token')
              $injector.get('Oauth2').handleInvalidToken();
            else if (response.data.errorCode === 401) {
              messageBox.show(response.data.message, MessageType.ERROR);
              $injector.get('$state').go('log-in');
            }
            else if (response.data.message)
              messageBox.show(response.data.message, MessageType.ERROR, false);
            else if (response.data.fieldErrors)
              messageBox.showValidationErrorMessage(response.data.fieldErrors);
          }
          else
            messageBox.show(response, MessageType.ERROR, false);
          return $q.reject(response);
        }
      };
    });
  })
  .config(function ($translateProvider, HostUrlProvider) {
    var hostUrl = HostUrlProvider.$get();
    $translateProvider.useUrlLoader(hostUrl + '/messageBundle');
    $translateProvider.preferredLanguage('en');
    $translateProvider.fallbackLanguage('en');
    $translateProvider.useSanitizeValueStrategy('escapeParameters');
  });

$('.dropdown-toggle').dropdown();
