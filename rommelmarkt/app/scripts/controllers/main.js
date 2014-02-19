'use strict';


angular.module('rommelmarktApp')
    .controller('MainCtrl', [ '$scope', '$location', 'sponsorService', 'Sponsor', 'STATIC_SERVER_CONFIG',
        function ($scope, $location, SponsorService, Sponsor, STATIC_SERVER_CONFIG) {
            $scope.staticServer = STATIC_SERVER_CONFIG;
            $scope.sponsors = Sponsor.query({});
            $scope.showSponsor = function (sponsorId) {
                window.location = '#/sponsor/' + sponsorId;
            }
        } ]);

angular.module('rommelmarktApp')
    .controller('SponsorCtrl', [ '$scope', '$upload', '$http', '$routeParams', 'fileReader', 'Sponsor', 'BACKEND_SERVER_CONFIG', 'STATIC_SERVER_CONFIG',
        function ($scope, $upload, $http, $routeParams, fileReader, Sponsor, BACKEND_SERVER_CONFIG,STATIC_SERVER_CONFIG) {
            $scope.staticServer = STATIC_SERVER_CONFIG;
            $scope.sponsor = {};
            if ($routeParams.sponsorId != -1) {
                Sponsor.query({},function(sponsors)
                {
                    angular.forEach(sponsors,function(sponsor) {
                        if (sponsor.id == $routeParams.sponsorId) {
                            $scope.sponsor = sponsor;
                        }
                    })
                });
            }
            $scope.getFile = function () {
                $scope.progress = 0;
                fileReader.readAsDataUrl($scope.file, $scope)
                    .then(function (result) {
                        $scope.imageSrc = result;
                    });
            };
            $scope.$on("fileProgress", function (e, progress) {
                $scope.progress = progress.loaded / progress.total;
            });

            $scope.submitSponsor = function () {
                //TODO validaition
                var sponsor = $scope.sponsor;
                if (sponsor.amount != 0) {
                    sponsor.object = "CASH";
                    switch (sponsor.amount) {
                        case "50":
                            sponsor.dimension = 1;
                            break;
                        case "100":
                            sponsor.dimension = 2;
                            break;
                            otherwise:
                                sponsor.dimension = -1;
                    }
                }
                $scope.upload = $upload.upload({
                    url: BACKEND_SERVER_CONFIG + 'sponsor/add',
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },  // set the headers so angular passing info as form data (not request payload)
                    data: sponsor,  // pass in data as strings
                    file: $scope.file
                }).success(function (data) {
                        console.log(data);
                        if (!data.success) {
                            //TODO error handling
                            console.log("error");
                            $scope.message = "ERROR";
                        } else {
                            $scope.message = data.message;
                        }
                    });
            }
        } ]);

