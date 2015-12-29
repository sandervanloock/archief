angular.module('app').controller('RentController',['$scope','CalendarService', 'Constants',function($scope,calendar,constants){
    $scope.rent = {
        minDate: new Date()
    }
    var availableDates = [];
    calendar.getFutureEventsFromGroupName("verhuur").then(function(events){
        $scope.dateModel =  new Date();
        availableDates = events;
    });

    $(".bxslider").bxSlider({
        'adaptiveHeight': true
    });

    $scope.getDayClass = function(date, mode){
        var result = "";
        var momentDate = moment(date);
        $(availableDates).filter(function(index,item){
            var unavailable = false;
            if(!availableDates){
                unavailable = true;
            }
            if(momentDate.isBefore(new moment())){
                result+= " disabled";
            }
            if(item.start.dateTime && item.end.dateTime &&
                (moment(item.start.dateTime.value).isBefore(momentDate,'day') ||
                moment(item.start.dateTime.value).isSame(momentDate,'day')) &&
                (moment(item.end.dateTime.value).isAfter(momentDate,'day') ||
                moment(item.end.dateTime.value).isSame(momentDate,'day'))){
                unavailable = true;
            }
            if(item.start.date && item.end.date &&
                (moment(item.start.date.value).isBefore(momentDate,'day') ||
                moment(item.start.date.value).isSame(momentDate,'day')) &&
                (moment(item.end.date.value).isAfter(momentDate,'day') ||
                moment(item.end.date.value).isSame(momentDate,'day'))){
                unavailable = true;
            }

            if(unavailable){
                result+= " unavailable";
            }
        });
        return result;
    };

}]);