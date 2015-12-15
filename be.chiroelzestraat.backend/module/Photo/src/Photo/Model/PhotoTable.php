<?php
namespace Photo\Model;

use Zend\Db\TableGateway\TableGateway;

class PhotoTable
{
    protected $photoGateway;

    public function __construct(TableGateway $photoGateway)
    {
        $this->photoGateway = $photoGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->photoGateway->select();
        return $resultSet;
    }

    public function getPhoto($id)
    {
        $id  = (int) $id;
        $rowset = $this->photoGateway->select(array('id' => $id));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row $id");
        }
        return $row;
    }

    public function savePhoto(Photo $photo)
    {
        $data = array(
            'title'  => $photo->title,
            'directory'  => $photo->directory,
            'live'  => $photo->live,
            'deleted'  => $photo->deleted,
            'modified'  => date("Y-m-d H:i:s"),
            'event' => $photo->event
        );

        $id = (int) $photo->id;
        if ($id == 0) {
            $data["created"] = date("Y-m-d H:i:s");
            $this->photoGateway->insert($data);
        } else {
            if ($this->getPhoto($id)) {
                $this->photoGateway->update($data, array('id' => $id));
            } else {
                throw new \Exception('Photo id does not exist');
            }
        }
        return $id;
    }

    public function deletePhoto($id)
    {
        return $this->photoGateway->delete(array('id' => (int) $id));
    }
}