'use strict';

angular.module('rommelmarktApp')
    .controller('MainCtrl', [ '$scope', '$location','Sponsor',
        function($scope, $location, Sponsor) {
            $scope.sponsors = Sponsor.query({ });
        } ]);

angular.module('rommelmarktApp')
    .controller('SponsorCtrl', [ '$scope', '$location','$http', 'fileReader', 'BACKEND_SERVER_CONFIG',
        function($scope, $location, $http, fileReader, BACKEND_SERVER_CONFIG) {
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
            $scope.submitSponsor = function(){
                var sponsor = $scope.sponsor;
                if(sponsor.amount!=0){
                    sponsor.object = "CASH";
                    switch(sponsor.amount){
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
                $http({
                    method  : 'POST',
                    url     : BACKEND_SERVER_CONFIG + 'sponsor/add',
                    data    : $.param(sponsor),  // pass in data as strings
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
                }).success(function(data) {
                        console.log(data);
                        if (!data.success) {
                            // if not successful, bind errors to error variables
                            $scope.errorName = data.errors.name;
                            $scope.errorSuperhero = data.errors.superheroAlias;
                        } else {
                            // if successful, bind success message to message
                            $scope.message = data.message;
                        }
                    });
            }
        } ]);

