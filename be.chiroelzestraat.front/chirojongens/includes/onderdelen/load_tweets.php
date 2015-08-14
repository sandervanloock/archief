<?php

$cache = dirname(__FILE__) . '/temp/twitter-json.txt';

$data = file_get_contents('http://api.twitter.com/1.1/statuses/user_timeline/chiroelzestraat.json?count=4&include_rts=true&include_entities=true'); 

$cachefile = fopen($cache, 'wb');
fwrite($cachefile,utf8_encode($data));
fclose($cachefile);

?>
