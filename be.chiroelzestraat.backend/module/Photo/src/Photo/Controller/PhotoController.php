<?php
namespace Photo\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Photo\Model\Photo;
use Zend\View\Model\JsonModel;
use Zend\Validator\File\Size;
use Framework\Template\Exception;

class PhotoController extends AbstractRestfulController
{
    protected $photoTable;

    public function getPhotoTable()
    {
        if (!$this->photoTable) {
            $sm = $this->getServiceLocator();
            $this->photoTable = $sm->get('Photo\Model\PhotoTable');
        }
        return $this->photoTable;
    }

    public function getList()
    {
        $photos = $this->getPhotoTable()->fetchAll();
        $variables = array();
        $json = new JsonModel( );
        foreach($photos as $album){
            array_push($variables,$album);
        }
        $json->setVariables($variables);
        return $json;
    }

    public function get($id)
    {
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

    public function create($data)
    {
        $data['id']=0;
        $file = $this->params()->fromFiles();
        $upload = new \Zend\File\Transfer\Adapter\Http();
        $uploadDirectory = 'data/uploads/' . date_create()->format("Y-m-d");
        $thumbDirectory = 'thumbs/uploads/' . date_create()->format("Y-m-d");
        if(!is_dir($uploadDirectory)){
            mkdir($uploadDirectory);
        }
        if(!is_dir($thumbDirectory)){
            mkdir($thumbDirectory);
        }
        $upload->setDestination($uploadDirectory);
        if (!$upload->isValid()) {
            throw new Exception('Bad image data: ' . implode(',', $upload->getMessages()));
        }
        try {
            foreach($file as $oneFile){
                $upload->receive($oneFile['name']);
                $this->createThumbnail($uploadDirectory.'/',$oneFile['name'],$thumbDirectory.'/');
                $directory = substr($uploadDirectory,strpos($uploadDirectory,'/'),strlen($uploadDirectory));
                $data['directory'] = $directory.'/'.$oneFile['name'];
                $data['title'] = $oneFile['name'];
                $data['live'] = 1;
                $data['deleted'] = 0;
                $data['event'] = $data['eventid'];
                $photo = new Photo();
                $photo->exchangeArray($data);
                $this->getPhotoTable()->savePhoto($photo);
            }
        } catch (\Zend\File\Transfer\Exception $e) {
            throw new Exception('Bad image data: ' . $e->getMessage());
        }
        return new JsonModel(array(
            'photo' => 'ok',
        ));
    }

    public function update($id, $data)
    {
        $data['id'] = $id;
        $newPhoto = new Photo();
        //TODO validation
        $newPhoto->exchangeArray($data);
        $id = $this->getPhotoTable()->savePhoto($newPhoto);

        return new JsonModel(array(
            'photo' => $id,
        ));
    }

    public function delete($id)
    {
        if($this->getPhotoTable()->deletePhoto($id) > 0){
            return new JsonModel(array(
                'data' => 'deleted',
            ));
        }
        return new JsonModel(array(
            'data' => 'error',
        ));
    }
}