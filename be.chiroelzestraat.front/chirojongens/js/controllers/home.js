angular.module("app").controller('HomeController', ['$scope', '$http',function ($scope,$http) {

    $http.get("admin/api/picture/latest").then(function(response){
        $scope.latestPicture = response.data.path;
    })

}]);