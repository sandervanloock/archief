<?php
namespace Event\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\View\Model\JsonModel;

class EventController extends AbstractRestfulController
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

    public function getList()
    {
        $events = $this->getEventTable()->fetchAll();
        $variables = array();
        $json = new JsonModel();
        foreach ($events as $event) {
            $eventPhotos = $this->getEventTable()->fetchAllLiveEventPhotos($event->id, 1);
            $event->setPhotos($eventPhotos->toArray());
            if (count($event->photos) > 0) {
                array_push($variables, $event);
            }
        }
        $json->setVariables($variables);
        return $json;
    }

    public function get($id)
    {
        $event = $this->getEventTable()->getEvent($id);
        $event->setPhotos($this->getEventTable()->fetchAllEventPhotos($id)->toArray());
        return new JsonModel(array("event" => $event));
    }

    public function create()
    {
    }

    public function update()
    {
    }

    public function delete()
    {
    }

    public function getAllEventPhotos()
    {
        $events = $this->getEventTable()->fetchAll();
        $variables = array();
        $json = new JsonModel();
        foreach ($events as $event) {
            $event->setPhotos($this->getEventTable()->fetchAllLiveEventPhotos($event->id, 1)->toArray());
            array_push($variables, $event);
        }
        $json->setVariables($variables);
        return $json;
    }


}