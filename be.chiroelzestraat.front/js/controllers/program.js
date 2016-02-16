angular.module("app").controller('ProgramController', ['$scope', '$http', '$sce','Properties', function ($scope,$http,$sce,properties) {

    $http.get(properties.apiHost+"/admin/api/program/latest").then(function(response){
        $scope.latestProgram = response.data;
        if(response.data){
            $scope.latestProgram.file.path = $sce.trustAsResourceUrl("js/lib/pdf.js/web/viewer.html?file="+response.data.file.path);
        }
    })

}]);