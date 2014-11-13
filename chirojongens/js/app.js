'use strict';

var app = angular.module('app', ['ngRoute'], function ($locationProvider) {
    $locationProvider.html5Mode(true);
});

app.controller('NavController', function ($scope, $location) {
    console.log('NavController init');
    $scope.isActivePage = function(page){
        return page == $location.url().substring(13);
    }
});

app.controller('GroupNavController', function ($scope, $routeParams) {
    console.log('GroupNavController init');
    $scope.isActivePage = function(page){
        return page == $routeParams.groupname;
    }
});

app.controller('GroupController', function ($scope, $routeParams) {
    console.log('GroupController init');
    console.log('found', $routeParams, 'in URL');
});

app.constant('Constants',
    {
        speelclubCalendarId: 'ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com'
    }
);


app.config(function ($routeProvider) {
    $routeProvider
        .when('/chirojongens/', {
            templateUrl: 'templates/home.tpl.html'
        })
        .when('/chirojongens/overons', {
            templateUrl: '/chirojongens/templates/overons.tpl.html'
        })
        .when('/chirojongens/media', {
            templateUrl: '/chirojongens/templates/media.tpl.html'
        })
        .when('/chirojongens/verhuur', {
            templateUrl: '/chirojongens/templates/verhuur.tpl.html'
        })
        .when('/chirojongens/contact', {
            templateUrl: '/chirojongens/templates/contact.tpl.html'
        })
        .when('/chirojongens/groepen/:groupname', {
            templateUrl: '/chirojongens/templates/groepen.tpl.html',
            controller: 'GroupController'
        })
        .otherwise({
            template: 'No route matches'
        })

});