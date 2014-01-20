angular.module('users', ['userService','security.authorization'])

    .config(['$routeProvider', 'securityAuthorizationProvider', function ($routeProvider, securityAuthorizationProvider) {
        $routeProvider.when('/users', {
            templateUrl:'js/users/user-list.html',
            controller:'UsersViewCtrl',
            resolve:{
                users:['Users', function (Users) {
                    return Users.query();
                }],
                authenticatedUser: securityAuthorizationProvider.requireAuthenticatedUser
            }
        });
    }])

    .controller('UsersViewCtrl', ['$scope', 'users',function ($scope, users) {
        $scope.users = users;
    }]);

angular.module('userService', ['ngResource']);

angular.module('userService').factory('Users', ['$resource', 'ARCHIVE_SERVER_CONFIG',
    function($resource, ARCHIVE_SERVER_CONFIG){
        return $resource(ARCHIVE_SERVER_CONFIG + 'users', {}, {
            query: {method:'GET', isArray:true}
        });
    }]);
