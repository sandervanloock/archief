<?php
namespace Event\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\JsonModel;

class EventController extends AbstractActionController
{
    protected $eventTable;

    public function getEventTable()
    {
        if (!$this->eventTable) {
            $sm = $this->getServiceLocator();
            $this->eventTable = $sm->get('Event\Model\EventTable');
        }
        return $this->eventTable;
    }

    public function indexAction()
    {
        $events = $this->getEventTable()->fetchAll();
        $variables = array();
        $json = new JsonModel( );
        foreach($events as $album){
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