'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        ARCHIVE_SERVER_CONFIG: '@@ARCHIVE_SERVER_CONFIG',
        STATIC_SERVER_CONFIG: '@@STATIC_SERVER_CONFIG'
  });
