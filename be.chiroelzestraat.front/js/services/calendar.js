angular.module("app").factory('CalendarService', ['$q', '$http', function ($q,$http) {
    function convertDates(newEvents) {
        if(newEvents){
            for (var i = 0; i < newEvents.length; i++) {
                if (newEvents[i].start && newEvents[i].start.dateTime) {
                    newEvents[i].start.dateTime.value = moment(newEvents[i].start.dateTime.value).toDate();
                }
                if (newEvents[i].end && newEvents[i].end.dateTime) {
                    newEvents[i].end.dateTime.value = moment(newEvents[i].end.dateTime.value).toDate();
                }
            };
        }
    }

    this.getCalendarEvents = function () {
        var def = $q.defer();
        $http.get("/admin/api/event").success(function(data){
            var newEvents = data.items;
            convertDates(newEvents);
            def.resolve(newEvents);
        });
        return def.promise;
    };
    this.getCalendarEventsFromGroupName = function (group) {
        var def = $q.defer();
        $http.get("/admin/api/event/"+group).success(function(data){
            var newEvents = data.items;
            convertDates(newEvents);
            def.resolve(newEvents);
        });
        return def.promise
    };
    var _self = this;
    return {
        getFutureEvents: function () {
            var deferred = $q.defer();
            deferred.resolve(_self.getCalendarEvents());
            return deferred.promise;
        },

        getFutureEventsFromGroupName: function (group) {
            var deferred = $q.defer();
            deferred.resolve(_self.getCalendarEventsFromGroupName(group));
            return deferred.promise;
        }
    }
}]);
