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
        $scope.user = {
//            first: "pieter",
//            last: "pauwels      ",
//            email: "test@hotmail.com",
//            password: "pass"
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

    .controller('UserDetailCtrl', ['$scope', '$routeParams', '$http', 'Users', 'configuration', function ($scope, $routeParams, $http, Users) {
        Users.get({id: $routeParams.userId},function(data){
            $scope.user = data.user;
        });
    }]);

angular.module('userService', ['ngResource']);

angular.module('userService').factory('Users', ['$resource', 'configuration',
    function ($resource, configuration) {
        return $resource(configuration.ARCHIVE_SERVER_CONFIG + 'user', {}, {
            query: {method: 'GET', isArray: true},
            get: {url: configuration.ARCHIVE_SERVER_CONFIG + 'user/:id', method: 'GET'}
        });
    }]);
