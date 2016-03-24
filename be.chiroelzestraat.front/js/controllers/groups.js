angular.module("app").controller('GroupController', ['$scope','$compile','GroupService','Constants',
    function ($scope,$compile,GroupService,Constants) {

    $scope.groups = Constants.groups;
    //hide overlay when backbutton
    //see http://www.competa.com/blog/2013/11/how-to-stop-twitter-bootstrap-modal-dialogs-breaking-on-browser-history-navigation-in-angularjs/
    window.onhashchange = function() {
        $('.modal').modal('hide');
        $('.modal-backdrop').remove();
    };
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