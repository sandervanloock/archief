var eventService = angular.module('eventService', ['ngResource']);

eventService.factory('Event', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'event/:eventId', {}, {
            query: {method: 'GET', isArray: true, params:{'type':2}},
            get: {url: configuration.ARCHIVE_SERVER_CONFIG + 'event/:id', method: 'GET'},
            update: {method: 'PUT'},
            create: {method: 'POST'}
        });
    }]);

var groupService = angular.module('groupService', ['ngResource']);

groupService.factory('Groups', ['$resource', 'configuration', function ($resource, configuration) {
    return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'group/:photoId', {}, {
    });
}]);
