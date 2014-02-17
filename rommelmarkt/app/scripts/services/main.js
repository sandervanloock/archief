var sponsorService = angular.module('sponsorService', ['ngResource']);

sponsorService.factory('Sponsor', ['$resource', 'BACKEND_SERVER_CONFIG',
    function ($resource,BACKEND_SERVER_CONFIG) {
        var xsrf = $.param({fkey: "key"});
        return $resource(BACKEND_SERVER_CONFIG + 'sponsor', {}, {
            query: {method: 'GET', isArray: true},
            save: {
                method: 'POST',
                url: BACKEND_SERVER_CONFIG + 'sponsor',
                data: xsrf,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
        });
    }]);