<?php

use Shared\Controller as Controller;
use Framework\Registry as Registry;
use Framework\RequestMethods as RequestMethods;
use Utils\DirectoryReader as DirectoryReader;
use Framework\View as View;

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
                echo "insert photo " + $dir;
				$this->insertPhotoFromEvent($dir,$events);
		        echo "OK";
			}
		}
		$this->synchronizeThumbs();
	}

	public function synchronizeThumbs(){
		$reader = new DirectoryReader("../../data");
		foreach($reader->dirs as &$dir){
			//check existance of photo based on directory
			if (!file_exists("../../thumbs" . $dir)) {
				$dirNoTitle = substr($dir,0,strripos($dir,'/')+1);
				$imageName = substr($dir,strripos($dir,'/')+1);
				$this->createThumbnail('../../data'.$dirNoTitle,$imageName,'../../thumbs'.$dirNoTitle);
			}
		}
		echo "OK";
	}
	
	private function createThumbnail($destDir, $img, $upDir){
		$thumbnail_width = 100;
		$thumbnail_height = 100;
		$arr_image_details = getimagesize("$destDir" . "$img");
		$original_width = $arr_image_details[0];
		$original_height = $arr_image_details[1];
        if($original_width != 0 && $original_height != 0){
            if ($original_width > $original_height) {
                $new_width = $thumbnail_width;
                $new_height = intval($original_height * $new_width / $original_width);
            } else {
                $new_height = $thumbnail_height;
                $new_width = intval($original_width * $new_height / $original_height);
            }
            $dest_x = intval(($thumbnail_width - $new_width) / 2);
            $dest_y = intval(($thumbnail_height - $new_height) / 2);
            $imgt = null;
            if ($arr_image_details[2] == 1) {
                $imgt = "ImageGIF";
                $imgcreatefrom = "ImageCreateFromGIF";
            }
            if ($arr_image_details[2] == 2) {
                $imgt = "ImageJPEG";
                $imgcreatefrom = "ImageCreateFromJPEG";
            }
            if ($arr_image_details[2] == 3) {
                $imgt = "ImagePNG";
                $imgcreatefrom = "ImageCreateFromPNG";
            }
            if ($imgt) {
                $old_image = $imgcreatefrom("$destDir" . "$img");
                $new_image = imagecreatetruecolor($thumbnail_width, $thumbnail_height);
                imagecopyresized($new_image, $old_image, $dest_x, $dest_y, 0, 0, $new_width, $new_height, $original_width, $original_height);
                if (!file_exists("$upDir")) {
                    mkdir("$upDir", 0777, true);
                }
                $imgt($new_image, "$upDir" . "$img");
            }
        }
	}
	
	private function insertPhotoFromEvent($dir, $events){
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
	
	public function savePhoto(){
		$photo = json_decode(file_get_contents('php://input'))->{photo};
		$id = $photo->id;
		$photoDB = Photo::first(array("id = ?" => $id));
		$photoDB->title = $photo->title;
		$photoDB->directory = $photo->directory;
		$photoDB->live = $photo->active;
		$photoDB->save();
		echo 'OK';
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
	
	public function getEventPhotos(){
		if(!isset($_GET['eventId'])){
			echo "eventId not set";
		}
		$result = Photo::all(array(
				"deleted = ?" => false,
				"event = ?" => $_GET['eventId'] 
		),array("title","directory","live","id"));
		
		$data = array();
		foreach ($result as $row) {
			$entry = array();
			$entry['id'] = $row->id;
			$entry['title'] = $row->title;
			$entry['directory'] = $row->directory;
			$entry['active'] = $row->live;
			array_push($data,$entry);
		}
//		$view = new View(array(
//				"file" => APP_PATH."/{$defaultPath}/layouts/empty.{$defaultExtension}"
//				));
//		$this->setLayoutView($view);
		header("Content-Type: application/json");
		echo json_encode($data);
	}

}
