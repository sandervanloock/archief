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

    .controller('UserDetailCtrl', ['$scope', '$routeParams', '$http', '$location','Users', 'Event', 'Groups',
        function ($scope, $routeParams, $http,  $location, Users, Events, Groups) {
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
        if($routeParams.userId){
            Users.get({id: $routeParams.userId},function(data){
                $scope.user = data.user;
            });
        }
        $scope.saveUser = function () {
            if($scope.user.id){
                Users.update({userId: $scope.user.id}, $scope.user);
            } else{
                Users.save($scope.user);
            }
        };
        $scope.removeUser = function () {
            Users.remove({userId: $scope.user.id});
            $location.path("/users");
        };
        $scope.checkNewmembership = function(){
            var numberOfMemberships = $scope.user.memberships.length;
            var lastMembership = $scope.user.memberships[numberOfMemberships - 1];
            //the alst milestone must be completely filled in
            if(lastMembership.groupid && lastMembership.from && lastMembership.to){
                //retrieve the events from the last memberships
                var fromFormatted = moment(lastMembership.from).format("YYYY-MM");
                var toFormatted = moment(lastMembership.to).format("YYYY-MM");
                Events.query({from: fromFormatted, to: toFormatted},function(events){
                	var milestones = [];
                	events.forEach(function(event){
                		milestones.push({
                			eventid: event.id,
                			name: event.name,
                			start: event.start,
                			end: event.end
                		});
                	});
                    $scope.user.memberships[numberOfMemberships-1].milestones = milestones;
                });
            }
        };
        $scope.addNewMembership = function(){
            if(!$scope.user.memberships){
                $scope.user.memberships = [];
            }
        	$scope.user.memberships.push({
                groupid: "0"
              });
        };
        $scope.groups = Groups.query();
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