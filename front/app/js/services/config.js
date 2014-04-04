'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        ARCHIVE_SERVER_CONFIG: 'http://chiroelzestraat.be/archief/backend/public/',
        STATIC_SERVER_CONFIG: 'http://chiroelzestraat.be/archief/'
  });
