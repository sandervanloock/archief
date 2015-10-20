angular.module("app").controller('HomeController', ['$scope', '$http', 'Properties', function ($scope,$http,properties) {

    $http.get(properties.apiHost+"/admin/api/picture/latest").then(function(response){
        $scope.latestPicture = response.data.path;
    })

}]);