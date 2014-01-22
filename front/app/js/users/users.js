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

    .controller('RegisterUserCtrl', ['$scope', function ($scope) {
        $scope.users = {};

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

    .controller('UserDetailCtrl', ['$scope', '$routeParams', 'Users', function ($scope,  $routeParams,  Users) {
        $scope.user = Users.get({id: $routeParams.userId})
    }]);

angular.module('userService', ['ngResource']);

angular.module('userService').factory('Users', ['$resource', 'ARCHIVE_SERVER_CONFIG',
    function ($resource, ARCHIVE_SERVER_CONFIG) {
        return $resource(ARCHIVE_SERVER_CONFIG + 'users', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);
