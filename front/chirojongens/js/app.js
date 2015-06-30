'use strict';

angular.module('app', ['ui.bootstrap','ngRoute']);

angular.module('app').config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'templates/home.tpl.html',
                controller: 'HomeController'
            }).
            when('/groepen', {
                templateUrl: 'templates/groepen.tpl.html',
                controller: 'GroupController'
            }).
            when('/fotos', {
                templateUrl: 'templates/media.tpl.html',
                //controller: 'ShowOrdersController'
            }).
            when('/verhuur', {
                templateUrl: 'templates/verhuur.tpl.html',
                //controller: 'ShowOrdersController'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

angular.module('app').constant('Constants',
    {
        groups: [
            {name: 'speelclub'},
            {name: 'rakkers'},
            {name: 'toppers'},
            {name: 'kerels'},
            {name: 'aspiranten'},
        ]
    }
);