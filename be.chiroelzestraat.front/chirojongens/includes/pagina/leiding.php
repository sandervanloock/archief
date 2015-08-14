<?php
	// In a real application this would use a database, and not a session!
	session_start();
	
	require_once '../google-api-php-client/src/apiClient.php';
	require_once '../google-api-php-client/src/contrib/apiBuzzService.php';
	
	$client = new apiClient();
	// Visit https://code.google.com/apis/console to generate your
	// oauth2_client_id, oauth2_client_secret, and to register your oauth2_redirect_uri.
	$client->setClientId('717623609503.apps.googleusercontent.com');
	$client->setClientSecret('KXL1DQVQeLNEo8pkRfQHnuxr');
	$client->setRedirectUri('http://www.chiroelzestraat.be/chirojongens/v3/chiro/includes/pagina/leiding.php');
	$client->setApplicationName("Website Chirojongens Elzestraat");
	$buzz = new apiBuzzService($client);
	
	if (isset($_SESSION['access_token'])) {
	  $client->setAccessToken($_SESSION['access_token']);
	} else {
	  $client->setAccessToken($client->authenticate());
	}
	$_SESSION['access_token'] = $client->getAccessToken();
	
	if (isset($_GET['code'])) {
	  header('Location: http://' . $_SERVER['HTTP_HOST'] . $_SERVER['PHP_SELF']);
	}
	
	// Make an authenticated request to the Buzz API.
	if ($client->getAccessToken()) {
	  $me = $buzz->getPeople('@me');
	  $ident = '<img src="%s"> <a href="%s">%s</a>';
	  printf($ident, $me['thumbnailUrl'], $me['profileUrl'], $me['displayName']);
	}
?>