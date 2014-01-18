var app = angular
		.module('app', 
				[ 'ngRoute', 'appControllers', 'eventService', 'photoService','security','arrayFilters','ui.bootstrap', 'angularSpinner' ]);

angular.module('app').constant('ARCHIVE_SERVER_CONFIG',
		'http://localhost/Chiro/archief/backend/public/');

angular.module('app').constant('STATIC_SERVER_CONFIG',
	'http://localhost/Chiro/archief/');

angular.module('app').run(['security', function(security) {
	  // Get the current user when the application starts
	  // (in case they are still logged in from a previous session)
	  security.requestCurrentUser();
}]);

app.config([ '$routeProvider', 'securityAuthorizationProvider', function($routeProvider, securityAuthorizationProvider) {
	$routeProvider.when('/events', {
		templateUrl : 'template/event-list.html',
		controller : 'EventListCtrl',
		resolve: securityAuthorizationProvider.requireAdminUser
	}).when('/events/:eventId', {
		templateUrl : 'template/event-detail.html',
		controller : 'EventDetailCtrl',
		resolve: securityAuthorizationProvider.requireAdminUser
	}).when('/home',{
		templateUrl : 'template/archief.html',
		controller : 'ArchiefCtrl'
	}).otherwise({
		redirectTo : '/home'
	});
} ]);
