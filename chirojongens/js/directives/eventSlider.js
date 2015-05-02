angular.module('app').directive('eventSlider',['CalendarService',function(CalendarService){
    return {
        restrict: 'A',
        templateUrl: 'templates/event-slider.tpl.html',
        link: function(scope, element, attrs){
            console.log("EventSlider init");
            scope.calendar = {};
            CalendarService.getFutureEventsFromAllGroups().then(function(events) {
                scope.calendar.events = events;
            });
            scope.calendar.currentEvent=0;
            scope.prevEvent = function(){
                var length = scope.calendar.events.length;
                scope.calendar.currentEvent = ((scope.calendar.currentEvent-1)% length+length)%length;
            };
            scope.nextEvent = function(){
                scope.calendar.currentEvent = (scope.calendar.currentEvent+1)%scope.calendar.events.length;
            };
        }
    }
}]);