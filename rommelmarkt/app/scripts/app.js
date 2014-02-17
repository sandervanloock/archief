'use strict';

angular.module('rommelmarktApp', [
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ngRoute',
        'sponsorService'
    ])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/sponsor', {
                templateUrl: 'views/sponsor-detail.html',
                controller: 'SponsorCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

angular.module('rommelmarktApp').directive("ngFileSelect",function(){

    return {
        link: function($scope,el){

            el.bind("change", function(e){

                $scope.file = (e.srcElement || e.target).files[0];
                $scope.getFile();
            })

        }

    }
})


