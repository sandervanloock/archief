'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        BACKEND_SERVER_CONFIG: 'http://localhost:8081/backend/public/',
        UPLOAD_SERVER_CONFIG: 'http://localhost:8081/backend/uploads/',
        STATIC_SERVER_CONFIG: 'http://localhost:8081/'
  });
