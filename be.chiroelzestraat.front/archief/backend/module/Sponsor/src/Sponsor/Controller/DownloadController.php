<?php
namespace Sponsor\Controller;

use Framework\Template\Exception;
use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\Validator\File\Size;
use Zend\Mvc\Controller\AbstractActionController;

class DownloadController extends AbstractActionController
{
    protected $sponsorTable;

    public function getSponsorTable()
    {
        if (!$this->sponsorTable) {
            $sm = $this->getServiceLocator();
            $this->sponsorTable = $sm->get('Sponsor\Model\SponsorTable');
        }
        return $this->sponsorTable;
    }

    public function downloadZipAction()
    {
        $sponsors =  $this->getSponsorTable()->fetchAll();
        $files = array();
        foreach($sponsors as $sponsor){
            array_push($files,'uploads/'.$sponsor->logo);
        }
        $zipname = 'uploads/all_logos.zip';
        if($this->create_zip($files, $zipname,true)){
            header('Content-Type: application/zip');
            header('Content-disposition: attachment; filename=all_logos.zip');
            header('Content-Length: ' . filesize($zipname));
            readfile($zipname);
        }
    }

    function create_zip($files = array(), $destination = '', $overwrite = false)
    {
        //if the zip file already exists and overwrite is false, return false
        if (file_exists($destination) && !$overwrite) {
            return false;
        }
        //vars
        $valid_files = array();
        //if files were passed in...
        if (is_array($files)) {
            //cycle through each file
            foreach ($files as $file) {
                //make sure the file exists
                if (file_exists($file)) {
                    $valid_files[] = $file;
                }
            }
        }
        //if we have good files...
        if (count($valid_files)) {
            //create the archive
            $zip = new \ZipArchive();
            if ($zip->open($destination, $overwrite ? \ZIPARCHIVE::OVERWRITE : \ZIPARCHIVE::CREATE) !== true) {
                return false;
            }
            //add the files
            foreach ($valid_files as $file) {
                $zip->addFile($file, $file);
            }
            //debug
            //echo 'The zip archive contains ',$zip->numFiles,' files with a status of ',$zip->status;

            //close the zip -- done!
            $zip->close();

            //check to make sure the file exists
            return file_exists($destination);
        } else {
            return false;
        }
    }
}