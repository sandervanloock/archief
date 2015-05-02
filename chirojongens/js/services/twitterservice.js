angular.module('app').factory('TwitterService', ['$http', function ($http) {
    return {
        searchTweets: function (hashtag) {
            return $http(
                {
                    //method: 'JSONP',
                    url: '/includes/onderdelen/plugin_tweets.php',
                    params: {
                        'q': encodeURIComponent('#' + hashtag),
                        //'since_id': lastID || '',
                        'rpp': 5,
                        'result_type': 'recent',
                        'callback': 'JSON_CALLBACK'
                    }
                }
            )
        }
    }
}]);