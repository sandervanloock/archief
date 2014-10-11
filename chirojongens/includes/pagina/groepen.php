<?php 
if (($handle = fopen("uploads/groepeninfo.csv", "r")) !== FALSE) {
    if(fgetcsv($handle, 1000, ";") !== FALSE){
	     $speelclub_data = fgetcsv($handle, 1000, ";",'"');
	     $rakker_data = fgetcsv($handle, 1000, ";",'"');
	     $topper_data = fgetcsv($handle, 1000, ";",'"');
	     $kerel_data = fgetcsv($handle, 1000, ";",'"');
	     $aspi_data = fgetcsv($handle, 1000, ";",'"');
        $groep = $_GET["groep"];
        switch($groep){
			case "speelclub" :
				fillPage($groep,$speelclub_data);
				break;
			case "rakkers" : 
				fillPage($groep,$rakker_data);
				break;
			case "toppers" : 
				fillPage($groep,$topper_data);
				break;
			case "kerels" : 
				fillPage($groep,$kerel_data);
				break;
			case "aspiranten" : 
				fillPage($groep,$aspi_data);
				break;
			default : 
				echo '<iframe src="http://docs.google.com/gview?url=http://www.chiroelzestraat.be/chirojongens/uploads/ProgramMaartMei2012.pdf&embedded=true" style="width:930px; height:600px;" frameborder="0"></iframe>';
				break;			
			}
    }
    fclose($handle);
}
	
function fillPage($groep,$data){
	echo '<h1>'.$data[0].'</h1>';
	fillInfo($groep,$data);
	fillLeiding($groep,$data);
	echo '<script type="text/javascript">';
	echo '$(document).ready(function() {'; 
	echo '	initActivities("'.$groep.'", false);';
	echo '});';
	echo '</script>';
}
	
function fillInfo($groep,$data){
	echo '<div >';
		echo '<p>Informatie</p>';
		echo '<table>';
			echo '<tr>';
				echo '<td>Leeftijd</td>';
				echo '<td>'.$data[1].' jaar t.e.m. '.$data[2].' jaar</td>';
			echo '</tr>';
			echo '<tr>';
				echo '<td>Chiro van</td>';
				echo '<td>14u t.e.m. '.$data[3].'</td>';	
			echo '</tr>';
		echo '</table>';
		echo'</div>';
}

echo '<div id="activiteiten" class="gridbox left">
	  <div class="slider-wrap">
	  <div id="slider1" class="csw">
	  <div class="panelContainer">
	  </div>
	  </div>
	  </div>
      </div>';

function fillLeiding($groep,$data){
	$nb_leiding = $data[4];
	$leiding_data = readLeiding();
	$leiding_id = explode(',',$data[5]);
	echo '<div class="gridbox">';
	echo '<h2>Leiding</h2>';
	echo '<table id="leiders" width="100%" cellspacing="15">';
	echo '<tr>';
	for($i = 0 ; $i < $nb_leiding ; $i++){
		echo '		<th>'.$leiding_data[$leiding_id[$i]][3].' '.$leiding_data[$leiding_id[$i]][2].'</th>';
	}
	echo '	</tr>';
	echo '	<tr>';
	for($i = 0 ; $i < $nb_leiding ; $i++){
		echo '		<td class="leider">';
		echo '			<img src="'.$leiding_data[$leiding_id[$i]][1].' " alt="Leider" />';
		echo ' 			Geboortedatum: '.$leiding_data[$leiding_id[$i]][4].'<br />';
		$now = new DateTime();
		$ref = DateTime::createFromFormat('d/m/Y',$leiding_data[$leiding_id[$i]][4]);
		$diff = $now->diff($ref);
		echo '			Leeftijd: '.$diff->y.' <br />';
		echo '			<h3>Contactgegevens</h3>';
		echo '			'.$leiding_data[$leiding_id[$i]][7].' '.$leiding_data[$leiding_id[$i]][8].'<br />';
		echo '			'.$leiding_data[$leiding_id[$i]][9].' '.$leiding_data[$leiding_id[$i]][10].'<br />';
		echo '			gsm: 0'.$leiding_data[$leiding_id[$i]][11].'<br />';
		echo '			'.$leiding_data[$leiding_id[$i]][12].'<br />';
		echo '		</td>';
	}
	echo '  </tr>';
	echo '</table>';
	echo '</div>';
}

	
	
function readLeiding(){
$leiding[] = array();
	if (($handle = fopen("uploads/leidinginfo.csv", "r")) !== FALSE) {
		$row=0;
	    while(!feof($handle)){
	    		$leiding[$row] = fgetcsv($handle, 1000, ";", '"');
	    		$row++;
		}
		fclose($handle);
	}
return $leiding;
}

?>

