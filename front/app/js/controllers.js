var appControllers = angular.module('appControllers', []);

appControllers.controller('EventListCtrl', [ '$scope', 'Event',
		function($scope, Event) {
			$scope.events = Event.query();
			$scope.orderProp = 'start';
		} ]);

appControllers.controller('EventDetailCtrl', [
		'$scope',
		'$routeParams',
		'$location',
		'$modal',
		'Photo',
		'Event',
		'STATIC_SERVER_CONFIG',
		function($scope, $routeParams, $location, $modal, Photo, Event,
				STATIC_SERVER_CONFIG) {
			$scope.event = Event.get({
				id : $routeParams.eventId
			});

			var rows = 5;
			var columns = 7;
			$scope.page = 1;

			$scope.rows = rows;
			$scope.columns = columns;
			$scope.staticServer = STATIC_SERVER_CONFIG;
			
			var opts= {
					top:'50px'
			};
			var spinner = new Spinner(opts).spin($("#spinner-container")[0]);

			Photo.query({
				eventId : $routeParams.eventId
			}, function(photos) {
				
				
				$scope.totalNumber = photos.length;
				$scope.maxPage = Math.ceil($scope.totalNumber
						/ (rows * columns));
				var photoArrays = [];
				for ( var i = 0; i < photos.length; i++) {
					if (i % columns == 0) {
						photoArrays.push([]);
					}
					photoArrays[photoArrays.length - 1].push(photos[i]);
				}
				$scope.photos = photoArrays;
				spinner.stop();
			});

			$scope.incrementPage = function() {
				$scope.page = Math.min($scope.page + 1, $scope.maxPage);
			};
			$scope.decreasePage = function() {
				$scope.page = Math.max($scope.page - 1, 1);
			};
			$scope.changePage = function(page) {
				$scope.page = page + 1;
			};
			$scope.newNumberRows = function() {
				$scope.maxPage = Math.ceil($scope.totalNumber
						/ (eval($scope.rows) * eval($scope.columns)));
			};
			$scope.changeEvent = function() {
				$location.path("/photos/" + $scope.event);
			};
			var modalInstance = $scope.open = function(photo) {
				$modal.open({
					templateUrl : 'template/photo-detail.html',
					controller : 'PhotoCtrl',
					resolve : {
						selectedPhoto : function() {
							return photo;
						}
					}
				});
			};
		} ]);

appControllers.controller('PhotoCtrl', [
		'$scope',
		'$modalInstance',
		'Photo',
		'selectedPhoto',
		'STATIC_SERVER_CONFIG',
		function($scope, $modalInstance, Photo, selectedPhoto,
				STATIC_SERVER_CONFIG) {
			$scope.photo = selectedPhoto;
			$scope.staticServer = STATIC_SERVER_CONFIG;
			$scope.toggleState = function() {
				$scope.photo.active = !$scope.photo.active;
			};
			$scope.close = function() {
				$modalInstance.dismiss('cancel');
			};
			$scope.save = function() {
				Photo.save({
					photo : $scope.photo
				});
				$modalInstance.dismiss('saved');
			};
			$scope.remove = function() {
				$scope.photo.$delete();
				$modalInstance.dismiss('deleted');
			};
		} ]);

appControllers.controller('ArchiefCtrl', [ '$scope', function($scope) {
	/*$(document).ready(function() {
        $.ajax({
                url : backendHost + "/public/events/getAllEventPhotos",
                success : function(data) {
                        var events = JSONBuilder.parseEvents(data);
                        addEventsToTimeline(events);
                        createStoryJS({
                                type : 'timeline',
                                source : timeline,
                                embed_id : 'timeline',
                                debug : true,
                                css: 'styles/vendor/timeline/timeline.css',
                                js: 'scripts/vendor/timeline/timeline.js'
                        });
                }
        });
});*/
} ]);

appControllers.controller('PhotoListCtrl', [
		'$scope',
		'$routeParams',
		'$location',
		'Photo',
		'Event',
		'STATIC_SERVER_CONFIG',
		function($scope, $routeParams, $location, Photo, Event,
				STATIC_SERVER_CONFIG) {
			$scope.events = Event.query(function(events) {
				$scope.event = $routeParams.eventId;
			});

			var rows = 3;
			var columns = 7;
			$scope.page = 1;

			$scope.rows = rows;
			$scope.columns = columns;
			$scope.staticServer = STATIC_SERVER_CONFIG;

			Photo.query({
				eventId : $routeParams.eventId
			}, function(photos) {
				$scope.totalNumber = photos.length;
				$scope.maxPage = Math.ceil($scope.totalNumber
						/ (rows * columns));
				var photoArrays = [];
				for ( var i = 0; i < photos.length; i++) {
					if (i % columns == 0) {
						photoArrays.push([]);
					}
					photoArrays[photoArrays.length - 1].push(photos[i]);
				}
				$scope.photos = photoArrays;
			});

			$scope.incrementPage = function() {

				$scope.page = Math.min($scope.page + 1, $scope.maxPage);
			};
			$scope.decreasePage = function() {
				$scope.page = Math.max($scope.page - 1, 1);
			};
			$scope.newNumberRows = function() {
				$scope.maxPage = Math.ceil($scope.totalNumber
						/ (eval($scope.rows) * eval($scope.columns)));
			};
			$scope.changeEvent = function() {
				$location.path("/photos/" + $scope.event);
			};
		} ]);