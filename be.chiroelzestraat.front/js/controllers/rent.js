angular.module('app').controller('RentController',['$scope','CalendarService', 'Constants',function($scope,calendar,constants){
    $scope.rent = {
        minDate: new Date()
    }
    var availableDates = [];
    calendar.getFutureEventsFromGroup(constants.groups[5]).then(function(events){
        $scope.dateModel =  new Date();
        availableDates = events;
    });

    $scope.getDayClass = function(date, mode){
        var result = "";
        var momentDate = moment(date);
        $(availableDates).filter(function(index,item){
            if((moment(item.start.dateTime.value).isBefore(momentDate,'day') ||
                moment(item.start.dateTime.value).isSame(momentDate,'day')) &&
                (moment(item.end.dateTime.value).isAfter(momentDate,'day') ||
                moment(item.end.dateTime.value).isSame(momentDate,'day'))){
                result+= " unavailable";
            }
        });
        return result;
    };

}]);