<?php
namespace User\Controller;


use Framework\Template\Exception;
use Zend\Authentication\Adapter\DbTable as AuthAdapter;
use Zend\Authentication\AuthenticationService;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\Mvc\Controller\AbstractRestfulController;
use Zend\View\Model\JsonModel;

class AuthController extends AbstractActionController
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

    public function loginAction()
    {
        $request = $this->getRequest();
        $postData = $request->getPost();
        $sm = $this->getServiceLocator();
        $authAdapter = new AuthAdapter($sm->get('Zend\Db\Adapter\Adapter'));
        $authAdapter
            ->setTableName('user')
            ->setIdentityColumn('email')
            ->setCredentialColumn('password')
        ;
        $authAdapter
            ->setIdentity($postData->login)
            ->setCredential($postData->password);
        $auth = new AuthenticationService();
        $result = $auth->authenticate($authAdapter);

        if (!$result->isValid()) {
            // Authentication failed; print the reasons why
            return new JsonModel(array(
                "errors" => $result->getMessages()
            ));
        } else {
            $user = $this->getUserTable()->getUserByLogin($result->getIdentity());
            return new JsonModel(array("user" => $user));
        };
    }

    public function logoutAction()
    {
        $auth = new AuthenticationService();
        $auth->clearIdentity();
        return new JsonModel(array("result" => "OK"));
    }

    public function currentUserAction()
    {
        $auth = new AuthenticationService();

        if ($auth->hasIdentity()) {
            $user = $this->getUserTable()->getUserByLogin($auth->getIdentity());
            return new JsonModel(array("user" => $user));
        }
        return new JsonModel(array("user" => ""));
    }
}