<?php
namespace Sponsor\Model;

use Zend\Db\TableGateway\TableGateway;

class SponsorTable
{
    protected $sponsorGateway;

    public function __construct(TableGateway $sponsorGateway)
    {
        $this->sponsorGateway = $sponsorGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->sponsorGateway->select();
        return $resultSet;
    }

    public function getSponsor($id)
    {
        $id  = (int) $id;
        $rowset = $this->sponsorGateway->select(array('id' => $id));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row $id");
        }
        return $row;
    }

    public function saveSponsor(Sponsor $sponsor)
    {
        $data = array(
            'name'  => $sponsor->name,
            'amount'  => $sponsor->amount,
            'dimension'  => $sponsor->dimension,
            'object'  => $sponsor->object,
            'logo'  => $sponsor->logo,
        );

        $id = (int) $sponsor->id;
        if ($id == 0) {
            $this->sponsorGateway->insert($data);
        } else {
            if ($this->getSponsor($id)) {
                $this->sponsorGateway->update($data, array('id' => $id));
            } else {
                throw new \Exception('Sponsor id does not exist');
            }
        }
    }

    public function deleteSponsor($id)
    {
        $this->sponsorGateway->delete(array('id' => (int) $id));
    }
}