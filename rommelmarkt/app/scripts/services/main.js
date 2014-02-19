var sponsorService = angular.module('sponsorModule', ['ngResource']);

sponsorService.service('sponsorService', [function(){
    this.selectedSponsor = {};
    this.setSelectedSponsor = function(sponsor){
        this.selectedSponsor = sponsor;
    }
}]);
sponsorService.factory('Sponsor', ['$resource', 'BACKEND_SERVER_CONFIG',
    function ($resource,BACKEND_SERVER_CONFIG) {
        return $resource(BACKEND_SERVER_CONFIG + 'sponsor', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);