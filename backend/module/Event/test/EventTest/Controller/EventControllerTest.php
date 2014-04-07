<?php

namespace EventTest\Controller;

use Zend\Test\PHPUnit\Controller\AbstractHttpControllerTestCase;

class EventControllerTest extends AbstractHttpControllerTestCase
{
    protected $traceError = true;

    public function setUp()
    {
        $this->setApplicationConfig(
            include 'C:\Projects\archief\backend\config\application.config.php'
        );
        parent::setUp();
    }

    public function testIndexActionCanBeAccessed()
    {
        $this->dispatch('/event');
        $this->assertResponseStatusCode(200);

        $this->assertModuleName('Event');
        $this->assertControllerName('Event\Controller\Event');
        $this->assertControllerClass('EventController');
        $this->assertMatchedRouteName('event');
    }

}