'use strict';

/**
 * @ngdoc service
 * @name libreLingvoApp.MessageBoxService
 * @description
 * # MessageBoxService
 * Factory in the libreLingvoApp.
 */
angular.module('libreLingvoApp')
  .factory('MessageBox', function ($uibModal, $translate, MessageType) {
    var messageContent = "none";
    var messageTitle = "none";
    var panelClass = "panel-default";

    var messageModalOptions = {
      animation: true,
      size: "lg",
      resolve: {
        content: function () {
          return messageContent;
        },
        title: function () {
          return messageTitle;
        },
        panelClass: function () {
          return panelClass;
        }
      }
    };

    var getTitle = function (messageType) {
      if (messageType === MessageType.INFO || messageType === MessageType.SUCCESS)
        return "label.message.ok.title";
      else if (messageType === MessageType.WARNING)
        return "label.message.warning.title";
      else if (messageType === MessageType.ERROR)
        return "label.message.error.title";
    };

    var getPanelClass = function (messageType) {
      if (messageType === MessageType.INFO)
        return 'panel-info';
      else if (messageType === MessageType.SUCCESS)
        return 'panel-success';
      else if (messageType === MessageType.WARNING)
        return 'panel-warning';
      else if (messageType === MessageType.ERROR)
        return 'panel-danger';
    };

    return {
      show: function (content, messageType, translate) {
        var translate = translate || true;
        messageContent = translate ? $translate.instant(content) : content;
        messageType= messageType || MessageType.SUCCESS;
        messageTitle = $translate.instant(getTitle(messageType));
        panelClass=getPanelClass(messageType);

        messageModalOptions.templateUrl = 'views/modal-message.html';
        messageModalOptions.controller = 'ModalMessageCtrl';
        return $uibModal.open(messageModalOptions).result;
      }
    };
  })
  .constant('MessageType',
    {
      INFO: 1,
      SUCCESS: 2,
      WARNING: 3,
      ERROR: 4
    }
  );
