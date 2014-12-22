'use strict';

angular.module('myApp.home', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'
        });
    }])

    .controller('HomeCtrl', ['$scope', '$http', 'cookieService', function ($scope, $http, cookieService) {
        $scope.init = function () {
            var cookieDate = cookieService.getCookie('datePicker');
            if (cookieDate) {
                $scope.dt = new Date(cookieDate);
            } else {
                $scope.dt = new Date();
            }
            var cookieFilter = cookieService.getCookie('typeFilter');
            if (cookieFilter) {
                $scope.typeFilter = cookieFilter;
            } else {
                $scope.typeFilter = {
                    name: 'Geen filter',
                    value: ''
                }
            }
            $scope.dateOptions = {
                startingDay: 1,
                showWeeks: false
            };
        };
        $http.get('/basket/ranking').
            success(function (data, status, headers, config) {
                $scope.rankings = data;
                $scope.updateGameWithDate($scope.dt);
            }).
            error(function (data, status, headers, config) {
                console.log("error");
            });
        $scope.updateGameWithDate = function (date) {
            angular.forEach($scope.rankings, function (ranking) {
                $http({
                    method: 'GET',
                    url: '/basket/game',
                    params: {
                        date: moment(date).add(-2).format("DD-MM-YYYY"),
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
            $scope.updateGameWithDate($scope.dt);
            cookieService.storeCookie('datePicker', $scope.dt);
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
            cookieService.storeCookie('typeFilter', $scope.typeFilter);
        };
        $scope.disableDate = function (date, mode) {
            return ( mode === 'day' && isDateAvailable(date) );
        };
        var isDateAvailable = function (date) {
            angular.forEach($scope.rankings, function (ranking) {
                angular.forEach(ranking.games, function (game) {
                    if (game.date === date) {
                        return true;
                    }
                })
            });
            return false;
        }
        $scope.init();
    }]);