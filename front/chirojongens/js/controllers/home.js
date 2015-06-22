angular.module("app").controller('HomeController', ['$scope','$routeParams','Constants',function ($scope, $routeParams, Constants) {
    $scope.getDayClass = function(date, mode){}

    $scope.groups = Constants.groups;
}]);