'use strict';

var app = angular.module('app', ['ngRoute','ui.bootstrap']);

app.controller('NavController', function ($scope, $location) {
    console.log('NavController init');
    $scope.isActivePage = function (page) {
        return page == $location.url().substring(13);
    }
});

app.controller('GroupNavController', function ($scope, $routeParams) {
    console.log('GroupNavController init');
    $scope.isActivePage = function (page) {
        return page == $routeParams.groupname;
    }
});

app.constant('Constants',
    {
        calendarIds: {
            speelclub: 'ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com',
            rakkers: '80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com',
            toppers: 'vtuadhst93b1h7879gogidqepk@group.calendar.google.com',
            kerels: '25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com',
            aspiranten: '9aomc83dom4mbdq10m5t8vnkpo@group.calendar.google.com',
            general: '2aptde9r90v1dq3iglhh654sns@group.calendar.google.com',
            verhuur: 'bms3ile8seb4hobrk4vq31nafo@group.calendar.google.com'
        }
    }
);


app.config(function ($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl: 'templates/home.tpl.html',
        controller: 'HomeController'
    })
    .when('/overons', {
        templateUrl: 'templates/overons.tpl.html'
    })
    //.when('/chirojongens/media', {
    //    templateUrl: '/chirojongens/templates/media.tpl.html'
    //})
    //.when('/chirojongens/verhuur', {
    //    templateUrl: '/chirojongens/templates/verhuur.tpl.html'
    //})
    //.when('/chirojongens/contact', {
    //    templateUrl: '/chirojongens/templates/contact.tpl.html'
    //})
    //.when('/chirojongens/groepen/:groupname', {
    //    templateUrl: '/chirojongens/templates/groepen.tpl.html',
    //    controller: 'GroupController'
    //})
    .otherwise({
        templateUrl: 'templates/home.tpl.html',
        controller: 'HomeController'
    })

});