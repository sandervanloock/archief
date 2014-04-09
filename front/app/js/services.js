var eventService = angular.module('eventService', ['ngResource']);

eventService.factory('Event', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'event/:eventId', {}, {
            query: {method: 'GET', isArray: true},
            get: {url: configuration.ARCHIVE_SERVER_CONFIG + 'event/:id', method: 'GET'}
        });
    }]);
