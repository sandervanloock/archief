angular.module("app").controller('HomeController', ['$scope','$routeParams','Constants',function ($scope, $routeParams, Constants) {
    console.log("HomeController init");
    $scope.calendarIds=Constants.calendarIds;

    $scope.getDayClass = function(date, mode){
        console.log(date,mode);
    }
}]);