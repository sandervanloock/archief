<?php

// Change this with your Google calendar feed
$calendarURL = 'https://www.google.com/calendar/feeds/2aptde9r90v1dq3iglhh654sns%40group.calendar.google.com/public/full?orderby=starttime&sortorder=ascending&futureevents=true';
// Nothing else to edit
$feed = file_get_contents($calendarURL);
header('Content-type: text/xml'); 
echo $feed;

?>