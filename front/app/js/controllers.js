var appControllers = angular.module('appControllers', []);

appControllers.controller('EventListCtrl', [ '$scope', '$location', 'Event', 'configuration',
    function ($scope, $location, Event, configuration) {
        $scope.staticServer = configuration.STATIC_SERVER_CONFIG;
        $scope.events = Event.query({
            type: $location.search().type
        });
        $scope.orderProp = 'start';

        $scope.create = function () {
            $location.path("/event/new");
        }
    } ]);

appControllers.controller('EventCreateCtrl', [ '$scope', 'Event', 'configuration',
    function ($scope, Event, configuration) {
        $scope.event = {};
        $scope.dateFormat = "dd/MM/yyyy";
        $scope.create = function () {
            Event.create($scope.event);
        };
        $scope.dateOptions = {
            'year-format': "'yyyy'",
            'starting-day': 1,
        };
        $scope.open = function ($event, opened) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope[opened] = true;
        };
    } ]);

appControllers.controller('EventDetailCtrl', [
    '$scope',
    '$routeParams',
    '$location',
    '$modal',
    'Event',
    'configuration',
    function ($scope, $routeParams, $location, $modal, Event, configuration) {
        var rows = 20;
        var columns = 7;
        $scope.page = 1;

        $scope.rows = rows;
        $scope.columns = columns;
        $scope.staticServer = configuration.STATIC_SERVER_CONFIG;
        $scope.dateFormat = "DD/MM/YYYY";

//        initialize the start enddate with the current date because otherwise, the two way data binding does not work :)
        $scope.event = {};
        $scope.event.start = moment().toDate();
        $scope.event.end = moment().toDate();

        var spinner = new Spinner({top: '50px'}).spin($("#spinner-container")[0]);
        Event.get({
            id: $routeParams.eventId
        }, function (data) {
            spinner.stop();
            $scope.event = data.event;
//            reformat the date to a js date so the directive works :)
            $scope.event.start = moment($scope.event.start).toDate();
            $scope.event.end = moment($scope.event.end).toDate();
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
        });

        $scope.save = function () {
            $scope.event.start = moment($scope.event.start).format("YYYY-MM-DD HH:mm:ss");
            $scope.event.end = moment($scope.event.end).format("YYYY-MM-DD HH:mm:ss");
            Event.update({eventId: $scope.event.id}, $scope.event);
        };

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
        $scope.delete = function () {
            Event.remove({eventId: $scope.event.id});
            $location.path("/events");
        }
        $scope.open = function (photo) {
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
    'photoService',
    'selectedPhoto',
    'configuration',
    function ($scope, $modalInstance, $http, photoService, selectedPhoto, configuration) {
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
                data: $.param({
                    id: $scope.photo.id,
                    event: $scope.photo.event,
                    title: $scope.photo.title,
                    directory: $scope.photo.directory,
                    live: $scope.photo.live == "1" ? 1 : 0,
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
            location.reload(true);
            photoService.remove({photoId: $scope.photo.id});
            $modalInstance.dismiss('deleted');
        };
    } ]);

appControllers.controller('ArchiefCtrl', [
    '$scope',
    'configuration',
    'Event',
    function ($scope, configuration, Event) {
        var spinner = new Spinner({top: '250px'}).spin($("#timeline")[0]);
        Event.query(function (events) {
            spinner.stop();
            var timeline = {
                timeline: {
                    header: "Digitaal archief - Chiro Elzestraat",
                    type: "default",
                    text: "",
                    date: [],
                    era: []
                }
            };
            for (var i = 0; i < events.length; i++) {
                timeline.timeline.date
                    .push({
                        startDate: moment(events[i].start).format("YYYY,MM,DD"),
                        endDate: moment(events[i].end).format("YYYY,MM,DD"),
                        headline: events[i].name,
                        text: events[i].description,
                        asset: {
                            "media": createNivoSlider(events[i].photos)
                        }
                    });
//                timeline.timeline.era.push({
//                    "startDate":"2011,09,01",
//                    "endDate":"2012,05,30",
//                    "headline":"Chirojaar 2011 - 2012",
//                    "text":"<p>Body text goes here, some HTML is OK</p>",
//                    "tag":"This is Optional"
//                })
            }
            createStoryJS({
                type: 'timeline',
                source: timeline,
                embed_id: 'timeline',
                debug: false,
                css: 'css/vendor/timeline/timeline.css',
                js: 'js/vendor/timeline/timeline.js',
                lang: 'nl',
                start_at_end:true
            });
        });

        createNivoSlider = function (photos) {
            if (photos.length == 0) {
                return "";
            }
            var html = "<div class='slider-wrapper theme-default'><div class='ribbon'></div><div class='nivoSlider'>";
            for (var i = 0; i < photos.length; i++) {
                var photoDir = configuration.STATIC_SERVER_CONFIG + "data/" + photos[i].directory;
                html += "<a class='popup-image' href='" + photoDir + "'>" +
                    "<img src='" + photoDir
                    + "' alt='" + photos[i].title
                    + "'/></a>";
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
