angular.module("app").factory('CalendarService', ['$q', '$http', function ($q,$http) {
    this.getCalendarEvents = function (group) {
        return $http.get("/admin/api/event/"+group.name);
    };
    var _self = this;
    return {
        getFutureEventsFromGroups: function (groups) {
            var events = [];
            var deferred = $q.defer();
            var responses = 0;
            angular.forEach(groups, function (group) {
                _self.getCalendarEvents(group, $q).then(function (resp) {
                    responses++;
                    var newEvents = resp.data.items;
                    for (var i = 0; i < newEvents.length; i++) {
                        if (newEvents[i].start && newEvents[i].start.dateTime) {
                            newEvents[i].start.dateTime = moment(newEvents[i].start.dateTime).toDate();
                        }
                        if (newEvents[i].end && newEvents[i].end.dateTime) {
                            newEvents[i].end.dateTime = moment(newEvents[i].end.dateTime).toDate();
                        }
                        events.push(newEvents[i]);
                    };
                    if (responses == groups.length) {
                        return deferred.resolve(events);
                    }
                });
            });
            return deferred.promise;
        }
    }
}]);
