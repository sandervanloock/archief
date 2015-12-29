angular.module("app").controller('GroupController', ['$scope','$compile','GroupService','Constants',
    function ($scope,$compile,GroupService,Constants) {

    $scope.groups = Constants.groups;

    $scope.openModal = function(groupName){
        console.log("Opening Modal: ",groupName);
        $('#'+groupName).modal();
        GroupService.getGroupInfo(groupName).success(function(data){
           $scope.activeGroup = data;
            var eventSliderHtml = $compile("<div class='lg-12' event-slider data-group='"+groupName+"'/>")($scope);
            $('#'+groupName).find(".chiro-groups__event-slider").html(eventSliderHtml)

        });
    }

}]);