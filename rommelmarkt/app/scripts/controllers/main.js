'use strict';

angular.module('rommelmarktApp')
    .controller('MainCtrl', [ '$scope', '$location','Sponsor',
        function($scope, $location, Sponsor) {
            $scope.sponsors = Sponsor.query({ });
        } ]);

angular.module('rommelmarktApp')
    .controller('SponsorCtrl', [ '$scope', '$location','Sponsor', 'fileReader',
        function($scope, $location, Sponsor, fileReader) {
            $scope.sponsor = {};
            $scope.getFile = function () {
                $scope.progress = 0;
                fileReader.readAsDataUrl($scope.file, $scope)
                    .then(function(result) {
                        $scope.imageSrc = result;
                    });
            };

            $scope.$on("fileProgress", function(e, progress) {
                $scope.progress = progress.loaded / progress.total;
            });
        } ]);

