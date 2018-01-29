'use strict';


angular.module('rommelmarktApp')
    .controller('MainCtrl', [ '$scope', '$location', 'sponsorService', 'Sponsor', 'configuration',
        function ($scope, $location, SponsorService, Sponsor, configuration) {
            $scope.staticServer = configuration.UPLOAD_SERVER_CONFIG;
            $scope.totalAmount = 0;
            $scope.totalNumberSponsors = 0;
            $scope.showLoading= true;
            $scope.sponsors = Sponsor.query({},function(sponsors){
                $scope.showLoading = false;
                sponsors = $(sponsors).filter(function(sponsor){return sponsor.year == '2018'});
                $scope.totalNumberSponsors = sponsors.length;
                angular.forEach(sponsors, function(sponsor){
                    $scope.totalAmount += eval(sponsor.amount);
                })
            });
            $scope.showSponsor = function (sponsorId) {
                window.location = '#/sponsor/' + sponsorId;
            };
            $scope.downloadAllLogos = function(){
                var url = configuration.BACKEND_SERVER_CONFIG+'sponsor/downloadZip';
                console.log(url);
                window.open(url);
            };
        } ]);

angular.module('rommelmarktApp')
    .controller('SponsorCtrl', [ '$scope', 'Upload', '$http', '$routeParams', 'fileReader', 'Sponsor', 'configuration',
        function ($scope, $upload, $http, $routeParams, fileReader, Sponsor, configuration) {
            $scope.staticServer = configuration.UPLOAD_SERVER_CONFIG;
            $scope.sponsor = {
                id: -1
            };
            if ($routeParams.sponsorId != -1) {
                Sponsor.query({}, function (sponsors) {
                    angular.forEach(sponsors, function (sponsor) {
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
                //TODO validation
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
                        default:
                            sponsor.dimension = -1;
                    }
                }
                delete sponsor.id;
                $scope.upload = $upload.upload({
                    url: configuration.BACKEND_SERVER_CONFIG + 'sponsor',
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
            $scope.removeSponsor = function () {
                Sponsor.delete({id: $scope.sponsor.id});
                window.location = '#/';
            }
        } ]);

