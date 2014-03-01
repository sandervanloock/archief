<?php
namespace User\Controller;

use Framework\Template\Exception;
use User\Model\User;
use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\Validator\File\Size;
use Zend\View\Model\JsonModel;

class UserController extends AbstractRestfulController
{
    protected $userTable;

    public function getUserTable()
    {
        if (!$this->userTable) {
            $sm = $this->getServiceLocator();
            $this->userTable = $sm->get('User\Model\UserTable');
        }
        return $this->userTable;
    }

    public function getList()
    {
        $users = $this->getUserTable()->fetchAll();
        $variables = array();
        $json = new JsonModel();
        foreach ($users as $user) {
            //TODO make this cleaner
            array_push($variables, $user);
        }
        $json->setVariables($variables);
        return $json;
    }

    public function get($id)
    {
        $user = $this->getUserTable()->getUser($id);

        return new JsonModel($user);
    }

    public function create($data)
    {
        $variables = array();
        $errors = array();
        //TODO validation
        if (true) {
            $user = new User();
            $user->exchangeArray($data);
            $this->getUserTable()->saveUser($user);
            $variables["success"] = "true";
            $variables["message"] = "User toegevoegd";
        } else {
            $variables["success"] = "false";
            array_push($errors, "Request is not a POST request");
        }
        $variables["errors"] = $errors;
        $json = new JsonModel();
        $json->setVariables($variables);
        return $json;
    }

    public function update($id, $data)
    {
        $data['id'] = $id;
        $user = $this->getUserTable()->getUser($id);
        //TODO validation
        if (true) {
            $id = $this->getUserTable()->saveUser($data);
        }

        return new JsonModel($this->get($id));
    }

    public function delete($id)
    {
        $this->getUserTable()->deleteUser($id);

        return new JsonModel(array(
            'data' => 'deleted',
        ));
    }

    public function login(){
        echo("OK");
    }
}