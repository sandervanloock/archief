angular.module('app').directive('twitterFeed',['TwitterService',function(TwitterService){
    return {
        restrict: 'A',
        templateUrl: 'templates/twitter-feed.tpl.html',
        link: function(scope){
            TwitterService.searchTweets().then(function(data){
                scope.tweets = data.data;
                angular.forEach(scope.tweets,function(tweet){
                    tweet.created_at = moment(tweet.createdAt).toDate();
                })
            })
        }
    }
}]);