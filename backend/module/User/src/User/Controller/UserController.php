<?php
namespace User\Controller;

use Framework\Template\Exception;
use User\Model\User;
use User\Model\Milestone;
use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\Validator\File\Size;
use Zend\View\Model\JsonModel;

class UserController extends AbstractRestfulController
{
    protected $userTable;
    protected $milestoneTable;

    public function getUserTable()
    {
        if (!$this->userTable) {
            $sm = $this->getServiceLocator();
            $this->userTable = $sm->get('User\Model\UserTable');
        }
        return $this->userTable;
    }

    public function getMilestoneTable()
    {
        if (!$this->milestoneTable) {
            $sm = $this->getServiceLocator();
            $this->milestoneTable = $sm->get('User\Model\MilestoneTable');
        }
        return $this->milestoneTable;
    }

    public function getList()
    {
        $users = $this->getUserTable()->fetchAll();
        $variables = array();
        $json = new JsonModel();
        foreach ($users as $user) {
            //TODO make this cleaner
            $user->presentOnReunion = (isset($user->presentOnReunion) and $user->presentOnReunion == "1") ? true : false;
            $user->isPhotoBookCandidate = (isset($user->isPhotoBookCandidate) and $user->isPhotoBookCandidate == "1") ? true : false;
            array_push($variables, $user);
        }
        $json->setVariables($variables);
        return $json;
    }

    public function get($id)
    {
        $user = $this->getUserTable()->getUser($id);
        $user->presentOnReunion = (isset($user->presentOnReunion) and $user->presentOnReunion == "1") ? true : false;
        $user->isPhotoBookCandidate = (isset($user->isPhotoBookCandidate) and $user->isPhotoBookCandidate == "1") ? true : false;
        $milestonesFromUser = $this->getMilestoneTable()->getMilestonesFromUser($id);
        $milestoneAsArray = array();
        foreach($milestonesFromUser as $milestone){
            array_push($milestoneAsArray,$milestone);
        }
        $user->setMilestones($milestoneAsArray);
        return new JsonModel(array("user" => $user));
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
        $user = $this->getUserTable()->getUser($id);
        //TODO validation
        if (true) {
            $user->exchangeArray($data);
            $this->updateUserMilestones($data['milestones'],$id);
            $this->getUserTable()->saveUser($user);
        }

        return new JsonModel(array(
            'data' => $this->get($id),
        ));
    }

    private function updateUserMilestones($milestones,$userid){
        foreach($milestones as $milestone){
            foreach($milestone['events'] as $event){
                $milestone = new Milestone();
                $milestone->userid =$userid;
                $milestone->eventid =$event['id'];
                $milestone->remarks =$event['description'];
                $this->getMilestoneTable()->saveMilestone($milestone);
            }
        }
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