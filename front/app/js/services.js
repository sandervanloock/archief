var eventService = angular.module('eventService', ['ngResource']);

eventService.factory('Event', ['$resource', 'ARCHIVE_SERVER_CONFIG',
  function($resource, ARCHIVE_SERVER_CONFIG){
    return $resource(ARCHIVE_SERVER_CONFIG + 'events/:eventId', {}, {
      query: {method:'GET', isArray:true},
      getAllEventPhotos: {url: ARCHIVE_SERVER_CONFIG + 'event/getAllEventPhotos', isArray:true, method: 'GET'}
    });
  }]);

var photoService = angular.module('photoService', ['ngResource']);

photoService.factory('Photo', ['$resource', 'ARCHIVE_SERVER_CONFIG',
		function($resource, ARCHIVE_SERVER_CONFIG){
			return $resource(ARCHIVE_SERVER_CONFIG + 'photos/', {}, {
				save: {method: 'POST', url: ARCHIVE_SERVER_CONFIG + 'photos/save/'}
			});
}]);