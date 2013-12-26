var app = angular
		.module('app', 
				[ 'ngRoute', 'appControllers', 'eventService', 'photoService','arrayFilters','ui.bootstrap', 'angularSpinner' ]);

angular.module('app').constant('ARCHIVE_SERVER_CONFIG',
		'http://localhost/Chiro/archief/backend/public/');

angular.module('app').constant('STATIC_SERVER_CONFIG',
	'http://localhost/Chiro/archief/');

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/events', {
		templateUrl : 'template/event-list.html',
		controller : 'EventListCtrl'
	}).when('/events/:eventId', {
		templateUrl : 'template/event-detail.html',
		controller : 'EventDetailCtrl'
	}).when('/photos/:eventId',{
		templateUrl : 'template/photo-list.html',
		controller : 'PhotoListCtrl'
	}).when('/home',{
		templateUrl : 'template/archief.html',
		controller : 'ArchiefCtrl'
	}).otherwise({
		redirectTo : '/home'
	});
} ]);
