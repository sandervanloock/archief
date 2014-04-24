<?php
namespace Event\Controller;

use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\View\Model\JsonModel;
use Event\Model\Event;

class EventController extends AbstractRestfulController
{
    protected $eventTable;

    public function create($data)
    {
        $event = new Event();
        //TODO validation
        if (true) {
            $event->exchangeArray($data);
            $this->getEventTable()->saveEvent($event);
        }

        return new JsonModel(array(
            'data' => $event,
        ));
    }

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
        $from = $this->getRequest()->getQuery()->from;
        if(isset($from)){
            $from = date_create($from)->format('Y-m');
        }
        $to = $this->getRequest()->getQuery()->to;
        if(isset($from)){
            $to = date_create($to)->format('Y-m');
        }

        //event for admin from a specific type
        if(!empty($type)){
            $events = $this->getEventTable()->fetchAllFromType($type);
            foreach ($events as $event) {
                $eventPhotos = $this->getEventTable()->fetchAllEventPhotos($event->id);
                $event->setPhotos($eventPhotos->toArray());
                array_push($variables, $event);
            }
        }
        //event for users between tow given dates (format = mm-yyyy)
        else if(!empty($from) and !empty($to)){
            $events = $this->getEventTable()->fetchAllLiveEventsInRange($from,$to);
            foreach ($events as $event) {
                $eventPhotos = $this->getEventTable()->fetchAllLiveEventPhotos($event->id, 1);
                $event->setPhotos($eventPhotos->toArray());
                array_push($variables, $event);
            }
        }
        //event for timeline,  all events with live photos
        else{
            $events = $this->getEventTable()->fetchAll();
            foreach ($events as $event) {
                $eventPhotos = $this->getEventTable()->fetchAllLiveEventPhotos($event->id, 1);
                $event->setPhotos($eventPhotos->toArray());
                if (count($event->photos) > 0 || isset($event->description)) {
                    array_push($variables, $event);
                }
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

    public function delete($id){
        if($this->getEventTable()->deleteEvent($id) > 0){
            return new JsonModel(array(
                'data' => 'deleted',
            ));
        }
        return new JsonModel(array(
            'data' => 'error',
        ));
    }

}