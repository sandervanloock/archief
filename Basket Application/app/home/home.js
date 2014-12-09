'use strict';

angular.module('myApp.home', ['ngRoute'])

// Declared route 
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'
        });
    }])

    .controller('HomeCtrl', ['$scope', '$http',function ($scope,$http) {
        $http.get('/basket/ranking').
            success(function (data, status, headers, config) {
                $scope.rankings = data;
            }).
            error(function (data, status, headers, config) {
                console.log("error");
            });
    }]);