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
                controller: 'RegisterUserCtrl'
            });
    }])

    .controller('RegisterUserCtrl', ['$scope', 'Users', function ($scope, Users) {
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
        $scope.saveUser = function () {
            Users.save($scope.user);
        }

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
        Users.get({id: $routeParams.userId},function(data){
            $scope.user = data.user;
            $scope.user.milestones = [];
            addNewMileStone($scope.user.milestones);
        });
        $scope.saveUser = function () {
            Users.update({userId: $scope.user.id}, $scope.user);
        };
        $scope.removeUser = function () {
            Users.remove({userId: $scope.user.id});
            $location.path("/users");
        };
        $scope.checkNewMileStone = function(){
            var numberOfMilestones = $scope.user.milestones.length;
            var lastMilestone = $scope.user.milestones[numberOfMilestones - 1];
            //the alst milestone must be completely filled in
            if(lastMilestone.group && lastMilestone.from && lastMilestone.to){
                //retrieve the events from the last milestones
                var fromFormatted = moment(lastMilestone.from).format("YYYY-MM");
                var toFormatted = moment(lastMilestone.to).format("YYYY-MM");
                Events.query({from: fromFormatted, to: toFormatted},function(events){
                    $scope.user.milestones[numberOfMilestones-1].events = events;
                });
                addNewMileStone($scope.user.milestones);
            }
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

addNewMileStone =  function(milestones){
    milestones.push({
        group: "0"
//        from: moment(new Date()).format("MM/YYYY"),
//        to: moment(new Date()).format("MM/YYYY"),
//        events: [{
//            wasPresent: true,
//            name: "",
//            extraInfo: ""
//        }]
    });
}
