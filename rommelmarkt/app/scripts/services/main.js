var sponsorService = angular.module('sponsorService', ['ngResource']);

sponsorService.factory('Sponsor', ['$resource', 'BACKEND_SERVER_CONFIG',
    function ($resource,BACKEND_SERVER_CONFIG) {
        return $resource(BACKEND_SERVER_CONFIG + 'sponsor', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);