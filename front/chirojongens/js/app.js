'use strict';

var app = angular.module('app', ['ngRoute','ui.bootstrap']);

//app.controller('NavController', function ($scope, $location) {
//    console.log('NavController init');
//    $scope.isActivePage = function (page) {
//        return page == $location.url().substring(13);
//    }
//});
//
//app.controller('GroupNavController', function ($scope, $routeParams) {
//    console.log('GroupNavController init');
//    $scope.isActivePage = function (page) {
//        return page == $routeParams.groupname;
//    }
//});

app.constant('Constants',
    {
        groups: [
            {name: 'Speelclub'},
            {name: 'Rakkers'},
            {name: 'Toppers'},
            {name: 'Kerels'},
            {name: 'Aspiranten'},
        ]
    }
);