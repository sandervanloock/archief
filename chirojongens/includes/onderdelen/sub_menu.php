<?php	
  echo '<ul class="orangeul">';           				
	if (isset($_GET["groep"])){
		$groep=$_GET["groep"];
	}else{
		$groep="";
	}
	
	switch ($groep){
		case "speelclub":
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub" class="active">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers">Rakkers</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers">Toppers</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels">Kerels</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten">Aspiranten</a></li>';
		break;
		case "rakkers":
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers" class="active">Rakkers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers">Toppers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels">Kerels</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten">Aspiranten</a></li>';
		break;
		case "toppers":
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers">Rakkers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers" class="active">Toppers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels">Kerels</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten">Aspiranten</a></li>';
		break;
		case "kerels":
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers">Rakkers</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers">Toppers</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels" class="active">Kerels</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten">Aspiranten</a></li>';
		break;
	    case "aspiranten":
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers">Rakkers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers">Toppers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels">Kerels</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten" class="active">Aspiranten</a></li>';
		break;
		default:
			echo '<li><a href="index.php?pagina=groepen&amp;groep=speelclub">Speelclub</a></li>';
			echo '<li><a href="index.php?pagina=groepen&amp;groep=rakkers">Rakkers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=toppers">Toppers</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=kerels">Kerels</a></li>';
		   	echo '<li><a href="index.php?pagina=groepen&amp;groep=aspiranten">Aspiranten</a></li>';
		break;
		}
  echo "</ul>";
?>
                