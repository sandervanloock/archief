<?php

namespace Utils
{
	class DirectoryReader
	{
		public $dirs;// = directoryScan('../../data',true,false);
		
		public function __construct($options = array()){
			if($options != null)
				$this->dirs=$this->directoryScan($options,true,false);
		}
		
		function getAllPhotosAsJson(){
			$result = array();
			foreach($dirs as &$dir){
				$entry = array();
				$entry['title'] = substr($dir,strripos($dir,'/')+1);
				$entry['directory'] = $dir;
				array_push($result,$entry);
			}
			echo json_encode($result);
		}
		
		function directoryScan($dir, $onlyfiles = false, $fullpath = false) {
			if (isset($dir) && is_readable($dir)) {
				$dlist = Array();
				$dir = realpath($dir);
				if ($onlyfiles) {
					$objects = new \RecursiveIteratorIterator(new \RecursiveDirectoryIterator($dir));
				} else {
					$objects = new \RecursiveIteratorIterator(new \RecursiveDirectoryIterator($dir), RecursiveIteratorIterator::SELF_FIRST);
				}
		
				foreach($objects as $entry => $object){
					if (!$fullpath) {
						$entry = str_replace($dir, '', $entry);
					}
						
					$dlist[] = $entry;
				}
		
				return $dlist;
			}
		}
	}
}