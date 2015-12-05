angular.module("app").controller('HomeController', ['$scope', '$http', '$sce','Properties', function ($scope,$http,$sce,properties) {

    $http.get(properties.apiHost+"admin/api/picture/latest").then(function(response){
        $scope.latestPicture = response.data.path;
    })

    $http.get(properties.apiHost+"admin/api/program/latest").then(function(response){
        $scope.latestProgram = response.data;
        $scope.latestProgram.file.path = $sce.trustAsResourceUrl("js/lib/pdf.js/web/viewer.html?file="+response.data.file.path);
    })

    $scope.viewLoaded = function(){
        $('.social-feed-container').socialfeed({
            twitter: {
                accounts: ['@chiroelzestraat'],
                limit: 4,
                consumer_key: 'KCTMiEDzQnJdvGdlIETXQ',
                consumer_secret: 'zxHEFXAvhKtrrfuzwuw8z6GaXBYNRgc9OTvykOUNmZo'
            },

            // GENERAL SETTINGS
            length:400,template: "templates/feed-template.tpl.html",
            show_media:true
            });
    }

}]);