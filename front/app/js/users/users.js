angular.module('users', ['userService', 'security.authorization'])

    .config(['$routeProvider', 'securityAuthorizationProvider', function ($routeProvider, securityAuthorizationProvider) {
        $routeProvider.when('/users', {
            templateUrl: 'js/users/user-list.html',
            controller: 'UsersViewCtrl',
            resolve: {
                users: ['Users', function (Users) {
                    return Users.query();
                }],
                authenticatedUser: securityAuthorizationProvider.requireAuthenticatedUser
            }}).when('/users/:userId', {
                templateUrl: 'js/users/user-detail.html',
                controller: 'UserDetailCtrl',
                resolve: securityAuthorizationProvider.requireAdminUser
            }).when('/register', {
                templateUrl: 'js/users/user-detail.html',
                controller: 'UserDetailCtrl'
            });
    }])

    .controller('UsersViewCtrl', ['$scope', '$location', 'users', function ($scope, $location, users) {
        $scope.users = users;
        $scope.createUser = function () {
            $location.path("/register");
        }
        $scope.editUser = function (id) {
            $location.path("/users/" + id);
        }
    }])

    .controller('UserDetailCtrl', ['$scope', '$routeParams', '$http', '$location', '$modal', 'Users', 'Event', 'Groups',
        function ($scope, $routeParams, $http, $location, $modal, Users, Events, Groups) {
            $scope.dateOptions = {
                'year-format': "'yyyy'",
                'starting-day': 1
            };
            $scope.dateFormat = "dd/MM/yyyy";
            $scope.open = function ($event, opened) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope[opened] = true;
            };
            $scope.user = {};
            if ($routeParams.userId) {
                Users.get({id: $routeParams.userId}, function (data) {
                    $scope.user = data.user;
                    //retrieve all chiro groups and bind the membership to the correct user
                    Groups.query({}, function (groups) {
                        $scope.groups = groups;
                        $scope.user.memberships.forEach(function (membership) {
                            membership.groupid = $.grep($scope.groups, function (e) {
                                return e.id == membership.groupid;
                            })[0];
                        })
                    });
                });
            } else {
                //retrieve all chiro groups
                Groups.query({}, function (groups) {
                    $scope.groups = groups;
                });
            }
            $scope.lat = "0";
            $scope.lng = "0";
            $scope.accuracy = "0";
            $scope.error = "";
            $scope.model = { myMap: undefined };
            $scope.myMarkers = [];

            $scope.showResult = function () {
                return $scope.error == "";
            }

            $scope.mapOptions = {
                center: new google.maps.LatLng($scope.lat, $scope.lng),
                zoom: 15,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            $scope.showPosition = function (position) {
                $scope.lat = position.coords.latitude;
                $scope.lng = position.coords.longitude;
                $scope.accuracy = position.coords.accuracy;
                if(!$scope.$$phase) {
                    $scope.$apply();
                }
                var latlng = new google.maps.LatLng($scope.lat, $scope.lng);
                $scope.model.myMap.setCenter(latlng);
                $scope.myMarkers.push(new google.maps.Marker({ map: $scope.model.myMap, position: latlng }));
            }

            $scope.showError = function (error) {
                switch (error.code) {
                    case error.PERMISSION_DENIED:
                        $scope.error = "User denied the request for Geolocation."
                        break;
                    case error.POSITION_UNAVAILABLE:
                        $scope.error = "Location information is unavailable."
                        break;
                    case error.TIMEOUT:
                        $scope.error = "The request to get user location timed out."
                        break;
                    case error.UNKNOWN_ERROR:
                        $scope.error = "An unknown error occurred."
                        break;
                }
                $scope.$apply();
            }

            $scope.getLocation = function () {
                if(!$scope.model.myMap){
                    $scope.model.myMap = new google.maps.Map(document.getElementById("map_canvas"),$scope.mapOptions);
                }
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition($scope.showPosition, $scope.showError);
                }
                else {
                    $scope.error = "Geolocation is not supported by this browser.";
                }
            }
            $scope.getLocation();
            $scope.saveUser = function () {
                if ($scope.user.id) {
                    Users.update({userId: $scope.user.id}, $scope.user);
                } else {
                    Users.save($scope.user, function () {
                        $scope.showSuccessSubmit = true;
                        $scope.showSuccessFailure = false;
                    }, function () {
                        $scope.showSuccessSubmit = false;
                        $scope.showSuccessFailure = true;
                    });
                }
            };
            $scope.removeUser = function () {
                Users.remove({userId: $scope.user.id});
                $location.path("/users");
            };
            $scope.$watch('user.addressDetails', function () {
                var componentForm = {
                    street_number: 'short_name',
                    route: 'long_name',
                    locality: 'long_name',
                    administrative_area_level_1: 'short_name',
                    country: 'long_name',
                    postal_code: 'short_name'
                };
                var place = $scope.user.addressDetails;
                if ($scope.user.addressDetails) {
                    $scope.showPosition({
                        coords: {
                            latitude: $scope.user.addressDetails.geometry.location.k,
                            longitude: $scope.user.addressDetails.geometry.location.A,
                            accuracy: 20
                        }
                    });
                    for (var i = 0; i < place.address_components.length; i++) {
                        var addressType = place.address_components[i].types[0];
                        if (componentForm[addressType]) {
                            var val = place.address_components[i][componentForm[addressType]];
                            switch (addressType) {
                                case 'street_number':
                                    $scope.user.houseNumber = val;
                                case 'route':
                                    $scope.user.street = val;
                                case 'locality':
                                    $scope.user.city = val;
                                case 'postal_code':
                                    $scope.user.postalCode = val;
                            }
                        }
                    }
                }
            });
            $scope.checkNewmembership = function () {
                var numberOfMemberships = $scope.user.memberships.length;
                var lastMembership = $scope.user.memberships[numberOfMemberships - 1];
                //the alst milestone must be completely filled in
                if (lastMembership.groupid && lastMembership.from && lastMembership.to) {
                    //retrieve the events from the last memberships
                    var fromFormatted = moment(lastMembership.from).format("YYYY-MM");
                    var toFormatted = moment(lastMembership.to).format("YYYY-MM");
                    Events.query({from: fromFormatted, to: toFormatted}, function (events) {
                        var milestones = [];
                        events.forEach(function (event) {
                            milestones.push({
                                eventid: event.id,
                                name: event.name,
                                start: event.start,
                                end: event.end
                            });
                        });
                        $scope.user.memberships[numberOfMemberships - 1].milestones = milestones;
                    });
                }
            };
            $scope.addNewMembership = function () {
                if (!$scope.user.memberships) {
                    $scope.user.memberships = [];
                }
                $scope.user.memberships.push({
                    groupid: "0"
                });
            };
            $scope.openRemoveMembershipDialog = function (membership, index) {
                $scope.user.memberships.splice(index, 1);
//            var openRemoveMembershipDialogInstance = $modal.open({
//                templateUrl: 'js/users/membership-remove.html',
//                controller: 'RemoveMembershipCtrl',
//                resolve: {
//                    membership: function () {
//                        return membership;
//                    },
//                    group: function(){
//                        return $.grep($scope.groups, function(e){ return e.id == membership.groupid; })[0];
//                    }
//                },
//                backdrop: false
//            });
            };
            $scope.addNewMembership();
        }])
    .controller('RemoveMembershipCtrl', ['$scope', '$modalInstance', 'membership', 'group', 'Users', function ($scope, $modalInstance, membership, group, Users) {
        $scope.membership = membership;
        $scope.group = group;
        $scope.removeMembership = function (membership) {
            //TODO remove membership and milestone in cascade
            $modalInstance.dismiss('saved');
        };
        $scope.close = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);

angular.module('userService', ['ngResource']);

angular.module('userService').factory('Users', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'user/:userId', {}, {
            query: {method: 'GET', isArray: true},
            get: {url: configuration.ARCHIVE_SERVER_CONFIG + 'user/:id', method: 'GET'},
            update: {method: 'PUT', url: configuration.ARCHIVE_SERVER_CONFIG + 'user/:userId'}
        });
    }]);