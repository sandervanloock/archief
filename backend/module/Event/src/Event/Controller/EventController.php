<?php
namespace Event\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\View\Model\JsonModel;

class EventController extends AbstractRestfulController
{
    protected $eventTable;

    public function create($data){}

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
        $variables = array();
        $json = new JsonModel();
        $type = $this->getRequest()->getQuery()->type;

        //event for timeline,  all events with live photos
        if(empty($type)){
            $events = $this->getEventTable()->fetchAll();
            foreach ($events as $event) {
                $eventPhotos = $this->getEventTable()->fetchAllLiveEventPhotos($event->id, 1);
                $event->setPhotos($eventPhotos->toArray());
                if (count($event->photos) > 0) {
                    array_push($variables, $event);
                }
            }
        }
        //event for admin from a specific type
        else{
            $events = $this->getEventTable()->fetchAllFromType($type);
            foreach ($events as $event) {
                $eventPhotos = $this->getEventTable()->fetchAllEventPhotos($event->id);
                $event->setPhotos($eventPhotos->toArray());
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

    public function update($id,$data)
    {
        $event = $this->getEventTable()->getEvent($id);
        //TODO validation
        if (true) {
            $event->exchangeArray($data);
            $this->getEventTable()->saveEvent($event);
        }

        return new JsonModel(array(
            'data' => $this->get($id),
        ));
    }

    public function delete($id){}

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