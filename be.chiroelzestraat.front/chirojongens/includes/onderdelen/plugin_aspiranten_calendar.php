<?php

// Change this with your Google calendar feed
$calendarURL = 'https://www.google.com/calendar/feeds/9aomc83dom4mbdq10m5t8vnkpo%40group.calendar.google.com/public/full?orderby=starttime&sortorder=ascending&futureevents=true';
// Nothing else to edit
$feed = file_get_contents($calendarURL);
header('Content-type: text/xml'); 
echo $feed;

?>