'use strict';

angular.module('app', ['ui.bootstrap','ngRoute']);

angular.module('app').config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'templates/home.tpl.html'
            }).
            when('/groepen', {
                templateUrl: 'templates/groepen.tpl.html',
                controller: 'GroupController'
            }).
            when('/fotos', {
                templateUrl: 'templates/media.tpl.html'
            }).
            when('/verhuur', {
                templateUrl: 'templates/verhuur.tpl.html',
                controller: 'RentController'
            }).
            when('/program', {
                templateUrl: 'templates/program.tpl.html',
                controller:'ProgramController'
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
            {name: 'verhuur'}
        ]
    }
);

angular.module('app').constant('Properties',
    {
        //TODO make this as property
        apiHost: "http://localhost:8080/"
    }
);