'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        ARCHIVE_SERVER_CONFIG: 'http://localhost:8888/public/',
        STATIC_SERVER_CONFIG: 'http://localhost:8888/'
  });
