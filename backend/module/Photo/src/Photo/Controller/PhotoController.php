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

    public function create($data)
    {
        $data['id']=0;
        $file = $this->params()->fromFiles();
        $upload = new \Zend\File\Transfer\Adapter\Http();
        $uploadDirectory = 'data\\uploads\\' . date_create()->format("Y-m-d");
        if(!is_dir($uploadDirectory)){
            mkdir($uploadDirectory);
        }
        $upload->setDestination($uploadDirectory);
        if (!$upload->isValid()) {
            throw new Exception('Bad image data: ' . implode(',', $upload->getMessages()));
        }
        try {
            foreach($file as $oneFile){
                $upload->receive($oneFile['name']);
                $data['directory'] = $uploadDirectory.'\\'.$oneFile['name'];
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