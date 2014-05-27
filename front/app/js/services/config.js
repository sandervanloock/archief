'use strict';

angular.module('services.config', [])
  .constant('configuration', {
        ARCHIVE_SERVER_CONFIG: 'http://www.chiroelzestraat.be/archief/backend/public/',
        STATIC_SERVER_CONFIG: 'http://www.chiroelzestraat.be/archief/backend/'
  });
