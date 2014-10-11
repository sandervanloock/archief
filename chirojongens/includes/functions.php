<?php
/*hierin schrijf je alle globale functies weg die verwijzen naar een aparte file.
je include op je indexpagina alleen functions.php die verwijst naar deze file. elke functie zal dan opzich een file eenmaal opvragen. 
zo blijft alles overzichtelijk
*/

function generate_main_inhoud(){
	if (isset($_GET["pagina"])){
		$pagina=$_GET["pagina"];
	}else{
		$pagina="home";
	}
	
	//VERWIJZING NAAR HIERONDER
	switch ($pagina){
		case "home":
			pagina_index();
		break;
		case "groepen":
			pagina_groepen();
		break;
		case "overons":
			pagina_overons();
		break;
		case "media":
			pagina_media();
		break;
		case "verhuur":
			pagina_verhuur();
		break;
		case "contact":
			pagina_contact();
		break;
	}
	
}

function main_menu(){
	//laad het hoofdmenu in zodanig dat je dit maar éénmaal moet schrijven
	require_once("onderdelen/main_menu.php");
}

function sub_menu(){
	//laad het menu in van de groepen
	require_once("onderdelen/sub_menu.php");
}
function sub_menu_over_ons(){
	//laad het menu in van de groepen
	require_once("onderdelen/sub_menu_over_ons.php");
}
function plugin_program(){
	require_once("onderdelen/plugin_program.php");
}
	
function plugin_google(){
	//laad de plugin van google
	require_once("onderdelen/plugin_google.php");
}
function plugin_facebook(){
	//laad de plugin van facebook
	require_once("onderdelen/plugin_facebook.php");
}
function plugin_login(){
	//laad de plugin voor in te loggen
	require_once("onderdelen/plugin_login.php");
}
function connect(){
	//laad het connect bestand in naar een eigen gemaakte database indien nodig
	require_once("onderdelen/connect.php");
}

function cache_tweets(){
	require_once("onderdelen/load_tweets.php");
}


//Hieronder worden de mainpaginas ingeladen voor weer te geven


function pagina_index(){
	require_once("pagina/index.php");
}
function pagina_overons(){
	require_once("pagina/overons.php");
}
function pagina_media(){
	require_once("pagina/media.php");
}
function pagina_groepen(){
	require_once("pagina/groepen.php");
}
function pagina_verhuur(){
	require_once("pagina/verhuur.php");
}
function pagina_contact(){
	require_once("pagina/contact.php");
}

?>