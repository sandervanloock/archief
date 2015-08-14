<?php
	if (isset($_GET["pagina"])){
		$pagina=$_GET["pagina"];
	}else{
		$pagina="sqdfsdfdsqf";
	}
	switch ($pagina){
		case "home":
			echo "<ul>";
		   		echo "<li class='first'><a href='index.php?pagina=home' class='active first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='last'>Contact</a></li>";
			echo "</ul>";
		break;
		case "overons":
			echo "<ul>";
			   	echo "<li class='first'><a href='index.php?pagina=home' class='first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons' class='active'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='last'>Contact</a></li>";
			echo "</ul>";
		break;
		case "media":
			echo "<ul>";
   				echo "<li class='first'><a href='index.php?pagina=home' class='first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media' class='active'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='last'>Contact</a></li>";
			echo "</ul>";
		break;
		case "verhuur":
			echo "<ul>";
			   	echo "<li class='first'><a href='index.php?pagina=home' class='first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur' class='active'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='last'>Contact</a></li>";
			echo "</ul>";
		break;
		case "contact":
			echo "<ul>";
   				echo "<li class='first'><a href='index.php?pagina=home' class='first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='active last'>Contact</a></li>";
			echo "</ul>";
		break;
		default:
			echo "<ul>";
		   		echo "<li class='first'><a href='index.php?pagina=home' class='first'>Home</a></li>";
			    echo "<li>				<a href='index.php?pagina=overons'>Over Ons</a></li>";
			    echo "<li>				<a href='index.php?pagina=media'>Media</a></li>";
			    echo "<li>				<a href='index.php?pagina=verhuur'>Verhuur</a></li>";
			    echo "<li>				<a href='index.php?pagina=contact' class='last'>Contact</a></li>";
			echo "</ul>";
		break;
	}
?>