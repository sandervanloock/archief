angular.module("app").factory("GroupService", ["$http", 'Properties', function ($http,properties) {
    return{
        getGroupInfo: function(groupName){
            return $http({
                method: "GET",
                url: properties.apiHost+"/admin/api/group/"+groupName
            });
        }
    }
}]);