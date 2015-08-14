<?php
namespace User\Model;

use Zend\Db\TableGateway\TableGateway;

class UserTable
{
    protected $userGateway;

    public function __construct(TableGateway $userGateway)
    {
        $this->userGateway = $userGateway;
    }

    public function fetchAll()
    {
        $resultSet = $this->userGateway->select();
        return $resultSet;
    }

    public function getUser($id)
    {
        $id  = (int) $id;
        $rowset = $this->userGateway->select(array('id' => $id));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row $id");
        }
        return $row;
    }

    public function getUserByLogin($login)
    {
        $rowset = $this->userGateway->select(array('login' => $login));
        $row = $rowset->current();
        if (!$row) {
            throw new \Exception("Could not find row with login: $login");
        }
        return $row;
    }

    public function saveUser(User $user)
    {
        //no id because it is inserted afterwards
        $data = array(
            'first'  => $user->first,
            'last'  => $user->last,
            'email'  => $user->email,
            'login'  => $user->login,
            'modified'  => date("Y-m-d H:i:s"),
            'live'  => 1,
            'deleted'  => 0,
        	'birthDate'  => $user->birthDate,
        	'city'  => $user->city,
        	'postalCode'  => $user->postalCode,
        	'street'  => $user->street,
        	'houseNumber' => $user->houseNumber,
        	'postalBox'  => $user->postalBox,
        	'phone'  => $user->phone,
        	'mobilePhone'  => $user->mobilePhone,
        	'presentOnReunion'  => $user->presentOnReunion,
        	'isPhotoBookCandidate'  => $user->isPhotoBookCandidate
        );

        $id = (int) $user->id;
        if ($id == 0) {
            $data["created"] = date("Y-m-d H:i:s");
            $this->userGateway->insert($data);
        } else {
            if ($this->getUser($id)) {
                $this->userGateway->update($data, array('id' => $id));
            } else {
                throw new \Exception('User id does not exist');
            }
        }
    }

    public function deleteUser($id)
    {
        $this->userGateway->delete(array('id' => (int) $id));
    }
}