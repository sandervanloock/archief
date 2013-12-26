<?php
$routes = array(		
		array(
				"pattern" => "users/register",
				"controller" => "users",
				"action" => "register"
		),
		array(
				"pattern" => "events/index?q=*",
				"controller" => "events",
				"action" => "index"
		),
		array(
				"pattern" => "events/getAllEventPhotos",
				"controller" => "events",
				"action" => "getAllEventPhotos"
		),
		array(
				"pattern" => "photos",
				"controller" => "photos",
				"action" => "getEventPhotos"
		),
		array(
				"pattern" => "photos/synchronizePhotos",
				"controller" => "photos",
				"action" => "synchronizePhotos"
		),
		array(
				"pattern" => "photos/save",
				"controller" => "photos",
				"action" => "savePhoto"
		)
);

// add defined routes

foreach ($routes as $route)
{
	$router->addRoute(new Framework\Router\Route\Simple($route));
}

// unset globals

unset($routes);
?>