<?php
namespace User\Model;

use Zend\Db\TableGateway\TableGateway;

class MilestoneTable
{
    protected $milestoneGateway;

    public function __construct(TableGateway $milestoneGateway)
    {
        $this->milestoneGateway = $milestoneGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->milestoneGateway->select();
        return $resultSet;
    }

    public function getMilestonesFromUser($userid){
        return $this->milestoneGateway->select(array('userid'=>(int)$userid));
    }

    public function existingMilestone($userid, $eventid)
    {
        $userid  = (int) $userid;
        $eventid  = (int) $eventid;
        $rowset = $this->milestoneGateway->select(array('userid' => $userid, 'eventid' => $eventid));
        $row = $rowset->current();
        if (!$row) {
            return false;
        }
        return true;
    }

    public function saveMilestone(Milestone $milestone)
    {
        $data = array(
            'remarks'  => $milestone->remarks,
            'userid'  => $milestone->userid,
            'eventid'  => $milestone->eventid,
        );

        $userid = (int)$milestone->userid;
        $eventid = (int)$milestone->eventid;

        if ($this->existingMilestone($userid,$eventid)) {
            $this->milestoneGateway->update($data, array('userid' => $userid, 'eventid' => $eventid));
        } else {
            $this->milestoneGateway->insert($data);
        }
    }

    public function deleteMilestone($userid,$eventid)
    {
        $this->milestoneGateway->delete(array('userid' => (int) $userid, 'eventid' => $eventid));
    }
}