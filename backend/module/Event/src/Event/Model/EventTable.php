<?php
namespace Event\Model;

use Zend\Db\TableGateway\TableGateway;
use Zend\Db\Sql\Sql;

class EventTable
{
    protected $eventGateway;

    public function __construct(TableGateway $eventGateway)
    {
        $this->eventGateway = $eventGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->eventGateway->select();
        return $resultSet;
    }

    public function fetchAllFromType($type)
    {
        return $this->eventGateway->select(array('eventtype'=>$type));
    }

    public function fetchAllEventPhotos($id)
    {
        $adapter = $this->eventGateway->getAdapter();
        $sql = new Sql($adapter);
        $select = $sql->select();
        $select->from(array('p'=>'photo'));
        $select->where(array('p.event = '.$id));

        $selectString = $sql->getSqlStringForSqlObject($select);
        $photos = $adapter->query($selectString, $adapter::QUERY_MODE_EXECUTE);
        return $photos;
    }

    public function fetchAllLiveEventPhotos($id, $isLive)
    {
        $adapter = $this->eventGateway->getAdapter();
        $sql = new Sql($adapter);
        $select = $sql->select();
        $select->from(array('p'=>'photo'));
        $select->where(array('p.live = '.$isLive,'p.event = '.$id));

        $selectString = $sql->getSqlStringForSqlObject($select);
        $photos = $adapter->query($selectString, $adapter::QUERY_MODE_EXECUTE);
        return $photos;
    }

    public function getEvent($id)
    {
        $id  = (int) $id;
        $rowset = $this->eventGateway->select(array('id' => $id));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row $id");
        }
        return $row;
    }

    public function saveEvent(Event $event)
    {
        $data = array(
            'name'  => $event->name,
            'start'  => $event->start,
            'end'  => $event->end,
            'eventtype'  => $event->eventtype,
            'description'  => $event->description,
        );

        $id = (int) $event->id;
        if ($id == 0) {
            $this->eventGateway->insert($data);
        } else {
            if ($this->getEvent($id)) {
                $this->eventGateway->update($data, array('id' => $id));
            } else {
                throw new \Exception('Event id does not exist');
            }
        }
    }

    public function deleteEvent($id)
    {
        $this->eventGateway->delete(array('id' => (int) $id));
    }
}