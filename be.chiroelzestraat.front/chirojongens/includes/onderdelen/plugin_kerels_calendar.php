<?php

// Change this with your Google calendar feed
$calendarURL = 'https://www.google.com/calendar/feeds/25bpqoqdk4j0a6p4m68uqea3ig%40group.calendar.google.com/public/full?orderby=starttime&sortorder=ascending&futureevents=true';
// Nothing else to edit
$feed = file_get_contents($calendarURL);
header('Content-type: text/xml'); 
echo $feed;

?>