angular.module("app").controller('HomeController', ['$scope', '$http', 'Properties', function ($scope,$http,properties) {

    $http.get(properties.apiHost+"admin/api/picture/latest").then(function(response){
        $scope.latestPicture = response.data.path;
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