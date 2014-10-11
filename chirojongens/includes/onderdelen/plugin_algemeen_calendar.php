<?php

session_start();

require_once "Google/Client.php";
require_once "Google/Service/Calendar.php";

$client_id = '717623609503-ed7csr850m8j3h9u55q9k1bl3j6hmkpm.apps.googleusercontent.com';
$client_secret = 'UlE6TxZTzyiUOz3y4663Ck4C';
$redirect_uri = 'https://www.example.com/oauth2callback';
$api_key = 'AIzaSyCU65Wf2cfMqIl0xxP0nbDgmBwFc_6CDz8';
$calendar_id = '2aptde9r90v1dq3iglhh654sns@group.calendar.google.com';

$client = new Google_Client();
$client->setApplicationName("Chiro Elzestraat Site");
$client->setDeveloperKey($api_key);
$client->setScopes(array('https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/calendar.readonly'));
$client->setClientId($client_id);
$client->setClientSecret($client_secret);
$client->setRedirectUri($redirect_uri);

$service = new Google_Service_Calendar($client);

$events = $service->events->listEvents($calendar_id);

while(true) {
    foreach ($events->getItems() as $event) {
        echo $event->getSummary();
    }
    $pageToken = $events->getNextPageToken();
    if ($pageToken) {
        $optParams = array('pageToken' => $pageToken);
        $events = $service->events->listEvents($calendar_id, $optParams);
    } else {
        break;
    }
}

//if (isset($_SESSION['oauth_access_token'])) {
//    $apiClient->setAccessToken($_SESSION['oauth_access_token']);
//} else {
//    $token = $apiClient->authenticate("bosville");
//    $_SESSION['oauth_access_token'] = $token;
//}

//$calendarURL = 'https://www.google.com/calendar/feeds/2aptde9r90v1dq3iglhh654sns%40group.calendar.google.com/public/full?orderby=starttime&sortorder=ascending&futureevents=true';
//// Nothing else to edit
//$feed = file_get_contents($calendarURL);
//header('Content-type: text/xml');
//echo $feed;

?>