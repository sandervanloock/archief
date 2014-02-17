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


