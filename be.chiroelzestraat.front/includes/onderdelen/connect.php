<?php
$server = "chiroelzestraat.be.mysql"; 
$gebruiker = "chiroelzestraat"; 
$wachtwoord = "JxUTQagh"; 
$db = "chiroelzestraat"; 

$connectie = mysql_connect($server,$gebruiker,$wachtwoord) 
or die ("Kon niet connecteren met de server"); 
mysql_select_db($db,$connectie) 
or die ("Kon de database niet selecteren"); 

?>