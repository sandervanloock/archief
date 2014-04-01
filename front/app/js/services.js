var eventService = angular.module('eventService', ['ngResource']);

eventService.factory('Event', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'event/:eventId', {}, {
            query: {method: 'GET', isArray: true},
            get: {url: configuration.ARCHIVE_SERVER_CONFIG + 'event/:id', method: 'GET'},
//            getAllEventPhotos: {url: configuration.ARCHIVE_SERVER_CONFIG + 'event/getAllEventPhotos', isArray: true, method: 'GET'}
        });
    }]);

var photoService = angular.module('photoService', ['ngResource']);

photoService.factory('Photo', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'photo/:photoId', {}, {
//            save: {method: 'POST', url: configuration.ARCHIVE_SERVER_CONFIG + 'photo/:id', params: {id: '@photo.id'}, headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'} }
        });
    }]);