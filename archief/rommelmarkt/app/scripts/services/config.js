'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        BACKEND_SERVER_CONFIG: 'http://chiroelzestraat.be/archief/backend/public/',
        UPLOAD_SERVER_CONFIG: 'http://chiro.local:8080/archief/backend/uploads/',
        STATIC_SERVER_CONFIG: 'http://chiro.local:8080/archief'
  });
