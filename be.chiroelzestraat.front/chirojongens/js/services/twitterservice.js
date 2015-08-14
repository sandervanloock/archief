angular.module('app').factory('TwitterService', ['$http', function ($http) {
    return {
        searchTweets: function () {
            return $http(
                {
                    url: '/admin/api/tweets?amount=5'
                }
            )
        }
    }
}]);