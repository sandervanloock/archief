<?php

use Shared\Controller as Controller;
use Framework\Registry as Registry;
use Framework\RequestMethods as RequestMethods;
use Utils\DirectoryReader as DirectoryReader;

class Photos extends Controller
{
	public function synchronizePhotos(){
		$reader = new DirectoryReader("../../data");
		$events = Event::all(array(
				"live = ?" => true,
				"deleted = ?" => false,
		));
		foreach($reader->dirs as &$dir){
			//check existance of photo based on directory
			if(Photo::first(array(
					"live = ?" => true,
					"deleted = ?" => false,
					"directory = ?" => $dir
			)) == null){
				foreach($events as $event){
					//if the directory contains the name of an event,  add the photo to the database
					// with a reference to the event
					if(strpos($dir,$event->name) !== false){
						$photo = new Photo(array(
								"title" => substr($dir,strripos($dir,'/')+1),
								"directory" => $dir,
								"event" => $event->id
						));
						$photo->save();
					}
				}
			}
		}
	}
	
	public function getAllPhotos(){
		$result = Photo::all(array(
				"live = ?" => true,
				"deleted = ?" => false
		),array("title","directory"));
		
		$data = array();
		foreach ($result as $row) {
			$asset = array('media' => $row->directory);
			$row_array['text'] = $row->title;
			$row_array['startDate'] = "2012,1,26";
			$row_array['asset'] = $asset;
			array_push($data,$row_array);
		}
		
		$timeline = array('headline' => 'Archief Chiro Elzestraat', 'type' => 'default', 'text' => 'mlkqsjfml', 'startDate' => '2012,1,26', 'date' => $data);
		$result = array('timeline' => $timeline);
		
		
		$this->setLayoutView("empty");
		
		echo json_encode($result);
	}
	 
	public function register()
	{
		if (RequestMethods::post("register"))
		{
			$title = RequestMethods::post("title");
			$directory = RequestMethods::post("directory");
			 
			$view = $this->getActionView();
			$error = false;
			 
			if (empty($title))
			{
				$view->set("title_error", "Title not provided");
				$error = true;
			}
			 
			if (empty($directory))
			{
				$view->set("directory_error", "Directory not provided");
				$error = true;
			}
			 
			if (!$error)
			{
				$photo = new Photo(array(
						"title" => $title,
						"directory" => $directory
				));
				 
				$photo->save();
				$view->set("success", true);
			}
		}
	}
}
