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

    .controller('UserDetailCtrl', ['$scope', '$routeParams', '$http', '$location', '$modal','Users', 'Event', 'Groups',
        function ($scope, $routeParams, $http,  $location, $modal, Users, Events, Groups) {
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
//                Groups.query({},function(groups){
//                    $scope.groups = groups;
//                    $scope.user.memberships.forEach(function(membership){
//                        membership.groupid = $.grep($scope.groups, function(e){ return e.id == membership.groupid; })[0];
//                    })
//                });
            });
        } else {
            //retrieve all chiro groups
        }
        Groups.query({},function(groups){
            $scope.groups = groups;
        });
        $scope.saveUser = function () {
//            if($scope.user.memberships){
//                $scope.user.memberships.forEach(function(membership){
//                    membership.groupid = membership.groupid.id;
//                })
//            }
            if($scope.user.id){
                Users.update({userId: $scope.user.id}, $scope.user);
            } else{
                Users.save($scope.user,function(){
                    $scope.showSuccessSubmit = true;
                    $scope.showSuccessFailure = false;
                },function(){
                    $scope.showSuccessSubmit = false;
                    $scope.showSuccessFailure   = true;
                });
            }
//            if($scope.user.memberships){
//                $scope.user.memberships.forEach(function(membership){
//                    membership.groupid = $.grep($scope.groups, function(e){ return e.id == membership.groupid; })[0];
//                })
//            }
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
        $scope.openRemoveMembershipDialog = function(membership, index){
            $scope.user.memberships.splice(index,1);
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
    }])
    .controller('RemoveMembershipCtrl',['$scope', '$modalInstance', 'membership', 'group', 'Users', function($scope, $modalInstance, membership,group,Users){
        $scope.membership = membership;
        $scope.group = group;
        $scope.removeMembership = function(membership){
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