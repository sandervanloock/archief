angular.module("app").factory('CalendarService', ['$q', function ($q) {
    this.getCalendarEvents = function (calendarId) {
        return gapi.client.calendar.events.list({
            calendarId: calendarId,
            timeMin: moment().toJSON()
        })
    };
    var _self = this;
    return {
        getFutureEventsFromGroups: function (calendarIds) {
            var events = [];
            var deferred = $q.defer();
            var responses = 0;
            angular.forEach(calendarIds, function (calendarId) {
                _self.getCalendarEvents(calendarId, $q).then(function (data) {
                    responses++;
                    var newEvents = data.result.items;
                    for (var i = 0; i < newEvents.length; i++) {
                        if (newEvents[i].start && newEvents[i].start.dateTime) {
                            newEvents[i].start.dateTime = moment(newEvents[i].start.dateTime).toDate();
                        }
                        if (newEvents[i].end && newEvents[i].end.dateTime) {
                            newEvents[i].end.dateTime = moment(newEvents[i].end.dateTime).toDate();
                        }
                        events.push(newEvents[i]);
                    };
                    if (responses == calendarIds.length) {
                        return deferred.resolve(events);
                    }
                });
            });
            return deferred.promise;
        }
    }
}]);
