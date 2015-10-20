angular.module("app").controller('GroupController', ['$scope','GroupService','Constants',function ($scope,GroupService,Constants) {

    $scope.groups = Constants.groups;

    $scope.openModal = function(groupName){
        $('#'+groupName).modal();
        GroupService.getGroupInfo(groupName).success(function(data){
           $scope.activeGroup = data;
        });
    }

}]);