<?php
namespace Photo\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Photo\Model\Photo;
use Zend\View\Model\JsonModel;

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
        $album = new Photo();
        //TODO validation
        $album->exchangeArray($data);
        $id = $this->getPhotoTable()->savePhoto($album);
        return new JsonModel(array(
            'photo' => $id,
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

    public function delete()
    {
    }
}