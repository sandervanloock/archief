<?php
ini_set('display_errors', 1);
require_once('TwitterAPIExchange.php');

/** Set access tokens here - see: https://dev.twitter.com/apps/ **/
$settings = array(
    'oauth_access_token' => "520891161-yUnoGAjmxJRrlDqVfkF8JJFsecWxsvuKmJiFbg2A",
    'oauth_access_token_secret' => "IIZCvbRuFjKpJdON8zXM7PY0buZnH3sKzffpkTqrM",
    'consumer_key' => "KCTMiEDzQnJdvGdlIETXQ",
    'consumer_secret' => "zxHEFXAvhKtrrfuzwuw8z6GaXBYNRgc9OTvykOUNmZo"
);


/** Perform a GET request and echo the response **/
/** Note: Set the GET field BEFORE calling buildOauth(); **/
$url = 'https://api.twitter.com/1.1/statuses/user_timeline.json';
$getfield = '?screen_name=chiroelzestraat&count=5';
$requestMethod = 'GET';
$twitter = new TwitterAPIExchange($settings);
header('Content-Type: application/json');
echo $twitter->setGetfield($getfield)
             ->buildOauth($url, $requestMethod)
             ->performRequest();
?>