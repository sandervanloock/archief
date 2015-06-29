angular.module("app").controller('GroupController', ['$scope','Constants',function ($scope,Constants) {

    $scope.groups = Constants.groups;

    $scope.openModal = function(groupName){
        console.log("Opening Modal: ",groupName);
        $('#'+groupName).modal();
    }
}]);