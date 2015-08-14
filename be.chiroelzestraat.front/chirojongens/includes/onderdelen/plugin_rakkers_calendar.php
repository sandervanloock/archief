<?php

// Change this with your Google calendar feed
$calendarURL = 'https://www.google.com/calendar/feeds/80k5sp0pj8973vrdj5lafga33g%40group.calendar.google.com/public/full?orderby=starttime&sortorder=ascending&futureevents=true';
// Nothing else to edit
$feed = file_get_contents($calendarURL);
header('Content-type: text/xml'); 
echo $feed;

?>