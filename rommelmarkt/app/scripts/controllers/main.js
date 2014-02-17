'use strict';

angular.module('rommelmarktApp')
    .controller('MainCtrl', [ '$scope', '$location','Sponsor',
        function($scope, $location, Sponsor) {
            $scope.sponsors = Sponsor.query({ });
        } ]);

angular.module('rommelmarktApp')
    .controller('SponsorCtrl', [ '$scope', '$location','Sponsor',
        function($scope, $location, Sponsor) {
            $scope.sponsor = {};
        } ]);

