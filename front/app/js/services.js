var eventService = angular.module('eventService', ['ngResource']);

eventService.factory('Event', ['$resource', 'configuration',
  function($resource, configuration){
    return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'events/:eventId', {}, {
      query: {method:'GET', isArray:true},
      getAllEventPhotos: {url: configuration.ARCHIVE_SERVER_CONFIG + 'event/getAllEventPhotos', isArray:true, method: 'GET'}
    });
  }]);

var photoService = angular.module('photoService', ['ngResource']);

photoService.factory('Photo', ['$resource', 'configuration',
		function($resource, configuration){
			return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'photos/', {}, {
				save: {method: 'POST', url: configuration.ARCHIVE_SERVER_CONFIG + 'photos/save/'}
			});
}]);