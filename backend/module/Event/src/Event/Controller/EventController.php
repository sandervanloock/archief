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

    public function getAllEventPhotosAction(){
        $events = $this->getEventTable()->fetchAll  ();
        $variables = array();
        $json = new JsonModel( );
        foreach($events as $event){
            $event->setPhotos($this->getEventTable()->fetchAllLiveEventPhotos($event->id)->toArray());
            array_push($variables,$event);
        }
        $json->setVariables($variables);
        return $json;
//        $data = array();
//        foreach ($events as $event){
//            $entry = array();
//            $entry['name'] = $event->name;
//            $entry['start'] = $event->start;
//            $entry['end'] = $event->end;
//            $photos = Photo::all(array(
//                "live = ?" => true,
//                "deleted = ?" => false,
//                "event = ?" => $event->id
//            ),array("title","directory"));
//            $photoEntries = array();
//            foreach ($photos as $photo){
//                $photoEntry = array();
//                $photoEntry['title'] = $photo->title;
//                $photoEntry['directory'] = $photo->directory;
//                array_push($photoEntries,$photoEntry);
//            }
//            $entry['photos'] = $photoEntries;
//            if(count($photos)>0){
//                array_push($data,$entry);
//            }
//        }
//
////		$view = new View(array(
////				"file" => APP_PATH."/{$defaultPath}/layouts/empty.{$defaultExtension}"
////				));
////		$this->setLayoutView($view);
//
//        echo json_encode($data);
    }
}