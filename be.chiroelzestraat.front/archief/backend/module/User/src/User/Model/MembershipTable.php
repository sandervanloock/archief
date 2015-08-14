<?php
namespace User\Model;

use Zend\Db\TableGateway\TableGateway;

class MembershipTable
{
    protected $membershipGateway;

    public function __construct(TableGateway $membershipGateway)
    {
        $this->membershipGateway = $membershipGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->membershipGateway->select();
        return $resultSet;
    }

    public function getMembershipsFromUser($userid){
        return $this->membershipGateway->select(array('userid'=>(int)$userid));
    }

    public function existingMembership($userid, $groupid)
    {
        $userid  = (int) $userid;
        $groupid  = (int) $groupid;
        $rowset = $this->membershipGateway->select(array('userid' => $userid, 'groupid' => $groupid));
        $row = $rowset->current();
        if (!$row) {
            return false;
        }
        return true;
    }

    public function saveMembership(Membership $membership)
    {
        $data = array(
            'userid'  => $membership->userid,
            'groupid'  => $membership->groupid,
            'from'  => $membership->from,
            'to'  => $membership->to,
        );

        $userid = (int)$membership->userid;
        $groupid = (int)$membership->groupid;

        if ($this->existingMembership($userid,$groupid)) {
            $this->membershipGateway->update($data, array('userid' => $userid, 'groupid' => $groupid));
        } else {
            $this->membershipGateway->insert($data);
        }
    }

    public function deleteMembership($userid,$groupid)
    {
        $this->membershipGateway->delete(array('userid' => (int) $userid, 'groupid' => (int) $groupid));
    }
}