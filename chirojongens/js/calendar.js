app.controller('GeneralCalendarController', function ($scope, $location, CalendarService) {
    console.log('GeneralCalendarController init');
    var urlGroup = $location.search()['groep'];
    console.log("found group", urlGroup);
    $scope.group = urlGroup;
    CalendarService.getFutureEventsFromGroup($scope);
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
