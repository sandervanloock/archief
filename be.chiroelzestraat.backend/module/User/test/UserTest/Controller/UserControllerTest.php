<?php

namespace UserTest\Controller;

use User\Controller\UserController;
use User\Model\User;
use Zend\Http\Request;
use Zend\Http\Response;
use Zend\Test\PHPUnit\Controller\AbstractHttpControllerTestCase;

class UserControllerTest extends AbstractHttpControllerTestCase
{
    protected $traceError = true;
    protected $user;
    protected $data;
    protected $request;
    protected $response;
    protected $controller;
    protected $event;
    protected $routeMatch;

    public function setUp()
    {
        $this->setApplicationConfig(
            include 'C:\Projects\archief\backend\config\application.config.php'
        );
        $this->data = array(
            'id' => 123,
            'first' => 'sander',
            'last' => 'van loock',
            'email' => 'svl@foreach.be');
//            'login' => 'admin',
//            'password' => 'test',
//            'live' => 1,
//            'deleted' => 0,
//            'created' => '2004-07-16 00:00:00',
//            'modified' => '2004-07-16 00:00:00');
        $this->user = new User();
        $this->user->exchangeArray($this->data);

        $this->controller = new UserController();
        $this->request = new Request();
        parent::setUp();
    }

    public function testIndexActionCanBeAccessed()
    {
        $this->dispatch('/user');
        $this->assertResponseStatusCode(200);

        $this->assertModuleName('User');
        $this->assertControllerName('User\Controller\User');
        $this->assertControllerClass('UserController');
        $this->assertMatchedRouteName('user');
    }

    public function testUserInitialState()
    {
        $user = new User();
        $this->assertNull(
            $user->id,
            '"id" should initially be null'
        );
    }

    public function testExchangeArraySetsPropertiesCorrectly()
    {
        $data = $this->data;
        $user = $this->user;
        $this->assertSame($data['id'], $user->id, '"id" was not set correctly');
    }

    public function testCreateCanBeAccessed()
    {
        $this->getRequest()->setMethod('post');
        $this->getRequest()->getPost()->set('first', 'sander');
        $this->getRequest()->getPost()->set('last', 'van loock');
        $this->getRequest()->getPost()->set('email', 'lierserulez@hotmail.com');

        $result = $this->dispatch('/user');
        $response = $this->getResponse();

        $this->assertEquals(200, $response->getStatusCode());
    }
}