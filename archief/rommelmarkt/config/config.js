'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        BACKEND_SERVER_CONFIG: '@@BACKEND_SERVER_CONFIG',
        UPLOAD_SERVER_CONFIG: '@@UPLOAD_SERVER_CONFIG',
        STATIC_SERVER_CONFIG: '@@STATIC_SERVER_CONFIG'
  });
