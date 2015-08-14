angular.module("app").factory("GroupService", ["$http", function ($http) {
    return{
        getGroupInfo: function(groupName){
            return $http({
                method: "GET",
                url: "/admin/api/group/"+groupName
            });
        }
    }
}]);