'use strict';

var app = angular.module('rommelmarktApp', [
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ngRoute',
        'sponsorModule',
        'angularFileUpload',
        'services.config'
    ]);

app.config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/sponsor/:sponsorId', {
                templateUrl: 'views/sponsor-detail.html',
                controller: 'SponsorCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

app.directive("ngFileSelect",function(){

    return {
        link: function($scope,el){

            el.bind("change", function(e){

                $scope.file = (e.srcElement || e.target).files[0];
                $scope.getFile();
            })

        }

    }
})


