<?php

use Shared\Controller as Controller;
use Framework\Registry as Registry;
use Framework\RequestMethods as RequestMethods;
use Framework\View as View;

class Events extends Controller
{
	public function register()
	{
		if (RequestMethods::post("register"))
		{
			$name = RequestMethods::post("name");
			$start = RequestMethods::post("start");
			$end = RequestMethods::post("end");
			
			$view = $this->getActionView();
			$error = false;
			 
			if (empty($name))
			{
				$view->set("name_error", "name not provided");
				$error = true;
			}
			 
			if (empty($start))
			{
				$view->set("start_error", "start not provided");
				$error = true;
			}
			if (empty($end))
			{
				$view->set("end_error", "end not provided");
				$error = true;
			}
			 
			if (!$error)
			{
				$event = new Event(array(
						"name" => $name,
						"start" => $start,
						"end" => $end
				));
				 
				$event->save();
				$view->set("success", true);
			}
		}
		$defaultPath = $this->getDefaultPath();
		$defaultLayout = $this->getDefaultLayout();
		$defaultExtension = $this->getDefaultExtension();
			
		$view = new View(array(
				"file" => APP_PATH."/{$defaultPath}/layouts/page.{$defaultExtension}"
				));
		$this->setLayoutView($view);
	}

	
	public function index()
	{
		$where =  array(
				"live = ?" => true,
				"deleted = ?" => false,
		);
		if(isset($_GET['id'])){
			$where['id = ?'] = $_GET['id'];
			$event = Event::first($where);
			$data = array();
			$data['id'] = $event->id;
			$data['name'] = $event->name;
			$data['start'] = date_create($event->start)->format('Y-m-d\TH:i:sO');
			$data['end'] = date_create($event->end)->format('Y-m-d\TH:i:sO');
		} else{
			$events = Event::all($where);
			$data = array();
			foreach ($events as $event){
				$entry = array();
				$entry['id'] = $event->id;
				$entry['name'] = $event->name;
				$entry['start'] = date_create($event->start)->format('Y-m-d\TH:i:sO');
				$entry['end'] = date_create($event->end)->format('Y-m-d\TH:i:sO');
				array_push($data,$entry);
			}
		}
		
		$view = new View(array(
				"file" => APP_PATH."/{$defaultPath}/layouts/empty.{$defaultExtension}"
				));
		$this->setLayoutView($view);
		
		//$view->set("result", $data);
		
		echo json_encode($data);
	}
	
	public function getAllEventPhotos()
	{		
		$events = Event::all(array(
				"live = ?" => true,
				"deleted = ?" => false,
		));
		
		
		$data = array();
		foreach ($events as $event){
			$entry = array();
			$entry['name'] = $event->name;
			$entry['start'] = $event->start;
			$entry['end'] = $event->end;
			$photos = Photo::all(array(
					"live = ?" => true,
					"deleted = ?" => false,
					"event = ?" => $event->id
			),array("title","directory"));
			$photoEntries = array();
			foreach ($photos as $photo){
				$photoEntry = array();
				$photoEntry['title'] = $photo->title;
				$photoEntry['directory'] = $photo->directory;
				array_push($photoEntries,$photoEntry);
			}
			$entry['photos'] = $photoEntries;
			array_push($data,$entry);
		}
		
		$view = new View(array(
				"file" => APP_PATH."/{$defaultPath}/layouts/empty.{$defaultExtension}"
				));
		$this->setLayoutView($view);
		
		echo json_encode($data);
	}
}