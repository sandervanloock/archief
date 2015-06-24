'use strict';

angular.module('app', ['ui.bootstrap']);

angular.module('app').constant('Constants',
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