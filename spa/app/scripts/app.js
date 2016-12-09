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
    'angular-loading-bar',
    'ui.tinymce',
    'angularMoment',
    'ui.scrollpoint',
    'timer',
    'ui.sortable',
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
        name: 'activate-user',
        url: '/activate-user?activation-key',
        controller: 'ActivateUserCtrl',
        controllerAs: 'activateUser'
      })
      .state({
        name: 'cancel-activation',
        url: '/cancel-activation?activation-key',
        controller: 'CancelActivationCtrl',
        controllerAs: 'cancelActivation'
      })
      .state({
        name: 'user-translations',
        url: '/user-translations?page-index&search-substring&part-of-speech&sort-field&sort-order&source-lang-code&result-lang-code&learned',
        templateUrl: 'views/user-translations.html',
        controller: 'UserTranslationsCtrl',
        controllerAs: 'userTranslations'
      })
      .state({
        name: 'translation.detail',
        url: '/detail',
        views: {
          'main': {
            templateUrl: 'views/translation-detail.html',
            controller: 'TranslationDetailCtrl',
            controllerAs: 'translationDetail'
          },
          'note': {
            templateUrl: 'views/note-detail.html',
            controller: 'NoteDetailCtrl',
            controllerAs: 'noteDetail'
          }
        }
      })
      .state({
        name: 'translation.edit',
        url: '/edit',
        views: {
          'main': {
            templateUrl: 'views/edit-translation.html',
            controller: 'EditTranslationCtrl',
            controllerAs: 'editTranslation'
          },
          'note': {
            templateUrl: 'views/edit-note.html',
            controller: 'EditNoteCtrl',
            controllerAs: 'editNote'
          }
        }
      })
      .state({
        name: 'translation',
        url: '/translation/:translationId',
        templateUrl: 'views/translation.html',
        controller: 'TranslationCtrl',
        controllerAs: 'translation'
      })
      .state({
        name: 'lesson',
        url: '/lesson/:lessonId',
        templateUrl: 'views/lesson.html',
        controller: 'LessonCtrl',
        controllerAs: 'lesson'
      })
      .state({
        name: 'lesson.exam',
        url: '/exam',
        templateUrl: 'views/lesson-exam.html',
        controller: 'LessonExamCtrl'
      })
      .state({
        name: 'lesson.view',
        url: '/view',
        templateUrl: 'views/lesson-view.html',
        controller: 'LessonViewCtrl'
      })
      .state({
        name: 'admin',
        url: '/admin',
        templateUrl: 'views/admin.html',
        controller: 'AdminCtrl',
        controllerAs: 'admin'
      })
      .state({
        name: 'admin.users',
        url: '/users',
        templateUrl: 'views/users.html',
        controller: 'UsersCtrl',
        controllerAs: 'users'
      })
      .state({
        name: 'admin.utils',
        url: '/utils',
        templateUrl: 'views/utils.html',
        controller: 'UtilsCtrl',
        controllerAs: 'utils'
      })
      .state({
        name: 'reset-password',
        url: '/reset-password?reset-key',
        templateUrl: 'views/reset-password.html',
        controller: 'ResetPasswordCtrl'
      })
      .state({
        name: 'change-password',
        url: '/change-password',
        templateUrl: 'views/change-password.html',
        controller: 'ChangePasswordCtrl'
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
          var messageBox = $injector.get('messageBox');
          if (response.status === -1) {
            //var translate = $injector.get('$translate');
            //alert(translate.instant('error.connection.refused'));
            //messageBox.show('error.connection.refused', MessageType.ERROR);
          }
          else if (response.data) {
            if (response.data.error_description === 'Bad credentials')
              messageBox.show('error.invalid.password', MessageType.ERROR);
            else if (response.data.error_description === 'User is disabled')
              messageBox.show('error.disabled.account', MessageType.ERROR);
            else if (response.data.error_description === 'User account is locked')
              messageBox.show('error.locked.account', MessageType.ERROR);
            else if (response.data.error === 'invalid_token')
              $injector.get('oauth2').handleInvalidToken();
            else if (response.data.errorCode === 401)
              messageBox.show(response.data.message, MessageType.ERROR).then(
                function () {
                  $injector.get('oauth2').logOut();
                  $injector.get('oauth2').handleInvalidToken();
                }
              );
            else if (response.data.message)
              messageBox.show(response.data.message, MessageType.ERROR);
            else if (response.data.fieldErrors)
              messageBox.showValidationErrorMessage(response.data.fieldErrors);
          }
          else
            messageBox.show(response, MessageType.ERROR);
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
  })
  .run(function ($rootScope,
                 $q,
                 lessonsUpdater,
                 oauth2) {

    oauth2.updateAuthoritiesCallback(function (authorities) {
      $rootScope.hasUserAuthority = authorities.indexOf('ROLE_USER') > -1;
      $rootScope.hasAdminAuthority = authorities.indexOf('ROLE_ADMIN') > -1;
      $rootScope.isAnnonymos = authorities.indexOf('ROLE_ANONYMOUS') > -1;

      if ($rootScope.hasUserAuthority)
        lessonsUpdater.updateLessons();
    });

    $rootScope.hideAffix = false;
  });

// update popover template for binding unsafe html
angular.module("uib/template/popover/popover.html", []).run(function ($templateCache) {
  //$templateCache.removeAll();

  $templateCache.put("uib/template/popover/popover.html",
    "<div class=\"arrow\"></div>\n" +
    "<div class=\"popover-inner\">\n" +
    "    <h3 class=\"popover-title\" ng-bind=\"uibTitle\" ng-if=\"uibTitle\"></h3>\n" +
    "    <div class=\"popover-content\" ng-bind-html=\"content\"></div>\n" +
    "</div>\n" +
    "");
});

