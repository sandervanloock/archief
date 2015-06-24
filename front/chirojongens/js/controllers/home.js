angular.module("app").controller('HomeController', ['$scope','Constants',function ($scope, Constants) {
    $scope.getDayClass = function(date, mode){}

    $scope.groups = Constants.groups;
}]);