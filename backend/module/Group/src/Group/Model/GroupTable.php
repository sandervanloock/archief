<?php
namespace Group\Model;

use Zend\Db\TableGateway\TableGateway;

class GroupTable
{
    protected $groupGateway;

    public function __construct(TableGateway $groupGateway)
    {
        $this->groupGateway = $groupGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->groupGateway->select();
        return $resultSet;
    }

    public function getGroup($id)
    {
        $id  = (int) $id;
        $rowset = $this->groupGateway->select(array('id' => $id));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row $id");
        }
        return $row;
    }

    public function deleteGroup($id)
    {
        $this->groupGateway->delete(array('id' => (int) $id));
    }
}