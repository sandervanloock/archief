'use strict';

angular.module('myApp.home', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'
        });
    }])

    .controller('HomeCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.dt = new Date();
        $scope.dateOptions = {
            startingDay: 1,
            showWeeks: false
        };
        $scope.typeFilter = {
            name: 'Geen filter',
            value: ''
        }
        $http.get('/basket/ranking').
            success(function (data, status, headers, config) {
                $scope.rankings = data;
                updateGameWithDate(new Date());
            }).
            error(function (data, status, headers, config) {
                console.log("error");
            });
        var updateGameWithDate = function (date) {
            angular.forEach($scope.rankings, function (ranking) {
                $http({
                    method: 'GET',
                    url: '/basket/game',
                    params: {
                        date: moment(date).format("DD-MM-YYYY"),
                        type: ranking.name
                    }})
                    .success(function (data, status, headers, config) {
                        ranking.games = data
                    }).
                    error(function (data, status, headers, config) {
                        console.log("error");
                    });
            });
        }
        $scope.$watch('dt', function () {
            updateGameWithDate($scope.dt);
        })
        $scope.openDatepicker = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };
        $scope.setTypeFilter = function($event, value) {
            $scope.typeFilter = {};
            $scope.typeFilter.name = $event.currentTarget.innerHTML;
            $scope.typeFilter.value = value;
        };
        $scope.disableDate = function(date, mode) {
            return ( mode === 'day' && isDateAvailable(date) );
        };

        var isDateAvailable = function(date){
            angular.forEach($scope.rankings, function (ranking) {
                angular.forEach(ranking.games, function(game){
                    if(game.date === date){
                        return true;
                    }
                })
            });
            return false;
        }
    }]);