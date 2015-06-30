angular.module("app").controller('NavigationController', ['$scope',function ($scope) {
    $scope.setCurrentPage = function(page){
        console.log("Current page is ", page);
        $scope.currentPage = page;
    };

}]);