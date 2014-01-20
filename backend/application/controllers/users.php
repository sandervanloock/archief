<?php

use Shared\Controller as Controller; 
use Framework\Registry as Registry;
use Framework\RequestMethods as RequestMethods;

class Users extends Controller
{
    public function index()
    {
        $where =  array(
            "live = ?" => true,
            "deleted = ?" => false,
        );
        if(isset($_GET['id'])){
            $where['id = ?'] = $_GET['id'];
            $event = User::first($where);
            $data = $this->getUserEntry($event);
        } else{
            $events = User::all($where);
            $data = array();
            foreach ($events as $event){
                $entry = array();
                $entry = $this->getUserEntry($event);
                array_push($data,$entry);
            }
        }

        echo json_encode($data);
    }

    public function register()
    {
        if (RequestMethods::post("register"))
        {
            $first = RequestMethods::post("first");
            $last = RequestMethods::post("last");
            $email = RequestMethods::post("email");
            $password = RequestMethods::post("password");
            
            $view = $this->getActionView();
            $error = false;
            
            if (empty($first))
            {
                $view->set("first_error", "First name not provided");
                $error = true;
            }
            
            if (empty($last))
            {
                $view->set("last_error", "Last name not provided");
                $error = true;
            }
            
            if (empty($email))
            {
                $view->set("email_error", "Email not provided");
                $error = true;
            }
            
            if (empty($password))
            {
               $view->set("password_error", "Password not provided");
               $error = true;
            }
            
            if (!$error)
            {
                $user = new User(array(
                    "first" => $first,
                    "last" => $last,
                    "email" => $email,
                    "password" => $password
                ));
                
                $user->save();
                $view->set("success", true);
            }
        }
    }

    public function login()
    {
        $email = RequestMethods::post("email");
        $password = RequestMethods::post("password");
        $user = User::first(array(
        	        "email = ?" => $email,
                    "password = ?" => $password,
                    "live = ?" => true,
            		"deleted = ?" => false
        ));
        
        $jsonUser = array(
        		"firstName" => $user->first,
        		"lastName" => $user->last,
        		"email" => $user->email,
        		"admin" => true,
       	);
        $data = array(
        		"user" => $jsonUser
        );
        $_SESSION['currentUser'] = json_encode($data);
        header("Content-Type: application/json");
       	echo $_SESSION['currentUser'];
    }
    
    public function logout(){
    	if (isset($_SESSION['currentUser'])) {
    		unset($_SESSION['currentUser']);
    	}
    }
    
    public function currentUser(){
    	if (isset($_SESSION['currentUser'])){
    		echo $_SESSION['currentUser'];
    	}
    }

    /**
     * @param $event
     * @return array
     */
    private function getUserEntry($event)
    {
        $data = array();
        $data['id'] = $event->id;
        $data['firstName'] = $event->first;
        $data['lastName'] = $event->last;
        $data['email'] = $event->email;
        return $data;
    }
}
