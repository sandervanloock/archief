<?php

// 1. define the default path for includes
define("APP_PATH", dirname(dirname(__FILE__))); 

// 2. load the Core class that includes an autoloader
require("../framework/core.php");
Framework\Core::initialize();

// 3. load and initialize the Configuration class
$configuration = new Framework\Configuration(array(
    "type" => "ini"
));
Framework\Registry::set("configuration", $configuration->initialize());

// 4. load and initialize the Database class – does not connect
$database = new Framework\Database();
Framework\Registry::set("database", $database->initialize());


// 5. load and initialize the Cache class – does not connect
$cache = new Framework\Cache();
Framework\Registry::set("cache", $cache->initialize());

// 6. load and initialize the Session class
session_start();

// 7. load the Router class and provide the url + extension
$router = new Framework\Router(array(
    "url" => isset($_GET["url"]) ? $_GET["url"] : "home/index",
    "extension" => isset($_GET["url"]) ? $_GET["url"] : "html"
));
include("routes.php");
Framework\Registry::set("router", $router);

$database = $database->initialize()->connect();
/*$user = new User(array(
    "connector" => $database
));
$database->sync($user);
$user = new Photo(array(
		"connector" => $database
));
$database->sync($user); */
//$user = new Event(array(
//		"connector" => $database
//));
//$database->sync($user);
//$user = new Event_type(array(
//		"connector" => $database
//));
//$database->sync($user);

// 8. dispatch the current request
$router->dispatch();

// 9. unset global variables
unset($configuration);
unset($database);
unset($cache);
unset($session);
unset($router);
