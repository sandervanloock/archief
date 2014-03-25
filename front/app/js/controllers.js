var appControllers = angular.module('appControllers', []);

appControllers.controller('EventListCtrl', [ '$scope', '$location', 'Event',
    function ($scope, $location, Event) {
        $scope.events = Event.query({
            type: $location.search().page
        });
        $scope.orderProp = 'start';
    } ]);

appControllers.controller('EventDetailCtrl', [
    '$scope',
    '$routeParams',
    '$location',
    '$modal',
    'Photo',
    'Event',
    'configuration',
    function ($scope, $routeParams, $location, $modal, Photo, Event, configuration) {
        var rows = 5;
        var columns = 7;
        $scope.page = 1;

        $scope.rows = rows;
        $scope.columns = columns;
        $scope.staticServer = configuration.STATIC_SERVER_CONFIG;
        var spinner = new Spinner({top: '50px'}).spin($("#spinner-container")[0]);
        Event.get({
            id: $routeParams.eventId
        },function (data) {
            spinner.stop();
            $scope.event = data.event;
            var photos = data.event.photos;
            $scope.totalNumber = photos.length;
            $scope.maxPage = Math.ceil($scope.totalNumber
                / (rows * columns));
            var photoArrays = [];
            for (var i = 0; i < photos.length; i++) {
                if (i % columns == 0) {
                    photoArrays.push([]);
                }
                photoArrays[photoArrays.length - 1].push(photos[i]);
            }
            $scope.photos = photoArrays;
        }).event;


        $scope.incrementPage = function () {
            $scope.page = Math.min($scope.page + 1, $scope.maxPage);
        };
        $scope.decreasePage = function () {
            $scope.page = Math.max($scope.page - 1, 1);
        };
        $scope.changePage = function (page) {
            $scope.page = page + 1;
        };
        $scope.newNumberRows = function () {
            $scope.maxPage = Math.ceil($scope.totalNumber
                / (eval($scope.rows) * eval($scope.columns)));
        };
        $scope.changeEvent = function () {
            $location.path("/photos/" + $scope.event);
        };
        var modalInstance = $scope.open = function (photo) {
            $modal.open({
                templateUrl: 'template/photo-detail.html',
                controller: 'PhotoCtrl',
                resolve: {
                    selectedPhoto: function () {
                        return photo;
                    }
                }
            });
        };
    } ]);

appControllers.controller('PhotoCtrl', [
    '$scope',
    '$modalInstance',
    '$http',
    'selectedPhoto',
    'configuration',
    function ($scope, $modalInstance, $http, selectedPhoto, configuration) {
        $scope.photo = selectedPhoto;
        $scope.staticServer = configuration.STATIC_SERVER_CONFIG;
        $scope.toggleState = function () {
            $scope.photo.live = !$scope.photo.live;
        };
        $scope.close = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.save = function () {
            $http({
                url: configuration.ARCHIVE_SERVER_CONFIG + 'photo/' + $scope.photo.id,
                method: 'PUT',
                data:  $.param({
                    id: $scope.photo.id,
                    event: $scope.photo.event,
                    title: $scope.photo.title,
                    directory: $scope.photo.directory,
                    live: $scope.photo.live  == "1" ? 1 : 0,
                    deleted: $scope.photo.deleted == "1" ? 1 : 0,
                    created: $scope.photo.created,
                    modified: moment().format("YYYY-MM-DD HH:mm:ss")
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).
                success(function (data) {
                    $scope.success = true;
                }).
                error(function (response) {
                    data = angular.fromJson(response)
                    $scope.success = false;
                    $scope.errors = data.errors;
                });
            $modalInstance.dismiss('saved');
        };
        $scope.remove = function () {
            $scope.photo.$delete();
            $modalInstance.dismiss('deleted');
        };
    } ]);

appControllers.controller('ArchiefCtrl', [
    '$scope',
    'configuration',
    'Event',
    function ($scope, configuration, Event) {
        Event
            .getAllEventPhotos(function (events) {
                var timeline = {
                    timeline: {
                        header: "Digitaal archief - Chiro Elzestraat",
                        type: "default",

                        text: "",
                        date: []
                    }
                };
                for (var i = 0; i < events.length; i++) {
                    $scope.test = "test" + i;
                    timeline.timeline.date
                        .push({
                            startDate: new Date(
                                moment(events[i].start)),
                            endDate: new Date(
                                moment(events[i].end)),
                            headline: events[i].name,
                            text: "<p></p>",
                            asset: {
                                "media": createNivoSlider(events[i].photos)
                            }
                        });
                }
                createStoryJS({
                    type: 'timeline',
                    source: timeline,
                    embed_id: 'timeline',
                    debug: false,
                    css: 'css/vendor/timeline/timeline.css',
                    js: 'js/vendor/timeline/timeline.js'
                });
            });

        createNivoSlider = function (photos) {
            var html = "<div class='slider-wrapper theme-default'><div class='ribbon'></div><div class='nivoSlider'>";
            for (var i = 0; i < photos.length; i++) {
                html += "<img src='" + configuration.STATIC_SERVER_CONFIG
                    + "data/" + photos[i].directory
                    + "' alt='" + photos[i].title
                    + "'/>";
            }
            html += "</div></div>";
            return html;
        };
    } ]);

appControllers.controller('PhotoListCtrl', [
    '$scope',
    '$routeParams',
    '$location',
    'Photo',
    'Event',
    'configuration',
    function ($scope, $routeParams, $location, Photo, Event, configuration) {
        $scope.events = Event.query(function (events) {
            $scope.event = $routeParams.eventId;
        });

        var rows = 3;
        var columns = 7;
        $scope.page = 1;

        $scope.rows = rows;
        $scope.columns = columns;
        $scope.staticServer = configuration.STATIC_SERVER_CONFIG;

        Photo.query({
            eventId: $routeParams.eventId
        }, function (photos) {
            $scope.totalNumber = photos.length;
            $scope.maxPage = Math.ceil($scope.totalNumber
                / (rows * columns));
            var photoArrays = [];
            for (var i = 0; i < photos.length; i++) {
                if (i % columns == 0) {
                    photoArrays.push([]);
                }
                photoArrays[photoArrays.length - 1].push(photos[i]);
            }
            $scope.photos = photoArrays;
        });

        $scope.incrementPage = function () {

            $scope.page = Math.min($scope.page + 1, $scope.maxPage);
        };
        $scope.decreasePage = function () {
            $scope.page = Math.max($scope.page - 1, 1);
        };
        $scope.newNumberRows = function () {
            $scope.maxPage = Math.ceil($scope.totalNumber
                / (eval($scope.rows) * eval($scope.columns)));
        };
        $scope.changeEvent = function () {
            $location.path("/photos/" + $scope.event);
        };
    } ]);

appControllers.controller('HeaderCtrl', ['$scope', '$location', 'security', function ($scope, $location, security) {

    $scope.activePage = $location.path() != null ? $location.path().substring(1) : 'home';

    $scope.changePage = function (page) {
        $scope.activePage = page;
    };
    $scope.isAuthenticated = security.isAuthenticated;
    $scope.isAdmin = security.isAdmin;

} ]);
