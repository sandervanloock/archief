'use strict';

var app = angular.module('app', ['ngRoute'], function($locationProvider) {
    $locationProvider.html5Mode(true);
});

app.controller('NavController',function($scope){
    console.log('NavController init');
    $scope.currentPage = 'home';
});

app.controller('GroupNavController',function($scope){
    console.log('GroupNavController init');
    $scope.currentGroup = 'speelclub';
});

app.controller('GroupController',function($scope,$routeParams){
    console.log('GroupController init');
    console.log('found',$routeParams,'in URL');
});

app.controller('GeneralCalendarController',function($scope){
    console.log('GeneralCalendarController init');
})

app.controller('GroupCalendarController', function ($scope, $location, CalendarService) {
    console.log("CalendarController init");
    var urlGroup = $location.search()['groep'];
    console.log("found group", urlGroup);
    $scope.group = urlGroup;
    CalendarService.getFutureEventsFromGroup($scope);
});

app.factory('CalendarService', function (Constants) {
    function convertDates(data) {
        angular.forEach(data, function (event) {
            if (event.start.dateTime) {
                event.start.dateTime = moment(event.start.dateTime).toDate();
            }
            if (event.end.dateTime) {
                event.end.dateTime = moment(event.end.dateTime).toDate();
            }
        });
    };
    return {
        getFutureEventsFromGroup: function (scope) {
            gapi.client.calendar.events.list({
                calendarId: Constants.speelclubCalendarId,
                timeMin: moment().toJSON()
            }).then(function (data) {
                scope.calendar = {};
                scope.calendar.events = data.result.items;
                convertDates(scope.calendar.events);
                scope.$apply();
                $("#slider1").codaSlider();
            });
        }
    }
});

app.constant('Constants',
    {
        speelclubCalendarId: 'ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com'
    }
);


app.config(function($routeProvider){
    $routeProvider
        .when('/chirojongens/',{
            templateUrl: 'templates/home.tpl.html'
        })
        .when('/chirojongens/groepen/:groupname',{
            templateUrl: '/chirojongens/templates/groepen.tpl.html',
           controller: 'GroupController'
        })
        .otherwise({
            template: 'No route matches'
        })

});