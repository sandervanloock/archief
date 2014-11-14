app.controller('GeneralCalendarController', function ($scope, $routeParams, CalendarService) {
    console.log("CalendarController init");
    var urlGroup = $routeParams.groupname;
    console.log("found group", urlGroup);
    $scope.group = urlGroup;
    CalendarService.getFutureEventsFromGroup($scope, urlGroup);
})

app.controller('GroupCalendarController', function ($scope, $routeParams, CalendarService) {
    console.log("CalendarController init");
    var urlGroup = $routeParams.groupname;
    console.log("found group", urlGroup);
    $scope.group = urlGroup;
    CalendarService.getFutureEventsFromGroup($scope, urlGroup);
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
        getFutureEventsFromGroup: function (scope, group) {
            gapi.client.calendar.events.list({
                calendarId: Constants.calendarIds[group],
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
})

app.constant('Constants',
    {
        calendarIds: {
            speelclub: 'ofnonpgnb4affnd6t51e48fsdo@group.calendar.google.com',
            rakkers: '80k5sp0pj8973vrdj5lafga33g@group.calendar.google.com',
            toppers: 'vtuadhst93b1h7879gogidqepk@group.calendar.google.com',
            kerels: '25bpqoqdk4j0a6p4m68uqea3ig@group.calendar.google.com',
            aspiranten: '9aomc83dom4mbdq10m5t8vnkpo@group.calendar.google.com',
            general: '2aptde9r90v1dq3iglhh654sns@group.calendar.google.com'
        }
    }
);
