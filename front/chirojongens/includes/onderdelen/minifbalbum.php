<?php 
	session_start();
	$pageID = "windowsphone";

	if($_POST["pageID"]){
		$pageID = $_POST["pageID"]; 
	}

	if(isset($_POST) && sizeof($_POST)>0){
		switch($_POST["action"]){
			case "getAlbums"	:	$albums = getCache(array($pageID,"albums"));
									if(!$albums){
										$albums = json_decode(file_get_contents('https://graph.facebook.com/'.$pageID.'/albums'),1);
										$albums = $albums["data"];
										foreach($albums as $key=>$album){	
											$cover = $album["cover_photo"];
											$cover =  json_decode(file_get_contents("http://graph.facebook.com/".$cover));
											$albums[$key]["cover_photo"] = $cover;
										}
										setCache(array($pageID,"albums"),$albums);
									}
									echo json_encode($albums);
									break;
			
			case "getImages"	:	$images = getCache(array($pageID,"images",$_POST["id"]));
									if(!$images){
										$images = json_decode(file_get_contents('https://graph.facebook.com/'.$_POST["id"].'/photos'),1);
										$images = $images["data"];
										setCache(array($pageID,"images",$_POST["id"]),$images);
									}
									echo json_encode($images);
									break;
									
		}
	}
	
	function getCache($args){
		$fname = "temp/".md5(implode("-",$args));
		if(file_exists($fname)){
			$content = file_get_contents($fname);
			return unserialize($content);
		}
		return false;
	}
	function setCache($args,$content){
		$content = serialize($content);
		$fname = "temp/".md5(implode("-",$args));
		$handle = fopen($fname, "w");
		fwrite($handle,$content,strlen($content));
		fclose($handle);
	}
?>
