<?php
namespace Photo\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Photo\Model\Photo;
use Zend\View\Model\JsonModel;

class PhotoController extends AbstractActionController
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

    public function indexAction()
    {
        $albums = $this->getPhotoTable()->fetchAll();
        $variables = array();
        $json = new JsonModel( );
        foreach($albums as $album){
             array_push($variables,$album);
        }
        $json->setVariables($variables);
        return $json;
    }

    public function addAction()
    {
    }

    public function editAction()
    {
    }

    public function deleteAction()
    {
    }
}