<?php
$routes = array(
		array(
				"pattern" => "photos/register",
				"controller" => "photos",
				"action" => "register"
		),
		array(
				"pattern" => "photos/register?id=*",
				"controller" => "photos",
				"action" => "register"
		),
		array(
				"pattern" => "events/register",
				"controller" => "events",
				"action" => "register"
		),
		array(
				"pattern" => "users/register",
				"controller" => "users",
				"action" => "register"
		),
		array(
				"pattern" => "events/getAllEventPhotos",
				"controller" => "events",
				"action" => "getAllEventPhotos"
		),
		array(
				"pattern" => "photos/synchronizePhotos",
				"controller" => "photos",
				"action" => "synchronizePhotos"
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