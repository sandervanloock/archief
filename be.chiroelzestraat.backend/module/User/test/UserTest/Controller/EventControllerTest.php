<?php

namespace EventTest\Controller;

use Event\Model\Event;
use Zend\Test\PHPUnit\Controller\AbstractHttpControllerTestCase;

class EventControllerTest extends AbstractHttpControllerTestCase
{
    protected $traceError = true;
    protected $event;
    protected $data;

    public function setUp()
    {
        $this->setApplicationConfig(
            include 'C:\Projects\archief\backend\config\application.config.php'
        );
        $this->data = array(
            'id' => 123,
            'name' => 'some event',
            'end' => '2004-07-16 00:00:00',
            'start' => '2004-07-15 00:00:00',
            'eventtype' => 1);
        $this->event = new Event();
        $this->event->exchangeArray($this->data);
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

    public function testEventInitialState()
    {
        $event = new Event();
        $this->assertNull(
            $event->id,
            '"id" should initially be null'
        );
        $this->assertNull(
            $event->name,
            '"name" should initially be null'
        );
        $this->assertNull(
            $event->start,
            '"start" should initially be null'
        );
        $this->assertNull(
            $event->end,
            '"end" should initially be null'
        );
        $this->assertNull(
            $event->eventtype,
            '"eventtype" should initially be null'
        );
    }

    public function testExchangeArraySetsPropertiesCorrectly()
    {
        $data = $this->data;
        $event = $this->event;
        $this->assertSame($data['id'],$event->id,'"id" was not set correctly');
        $this->assertSame($data['name'],$event->name,'"name" was not set correctly');
        $this->assertSame($data['start'],$event->start,'"start" was not set correctly');
        $this->assertSame($data['end'],$event->end,'"end" was not set correctly');
        $this->assertSame($data['eventtype'],$event->eventtype,'"eventtype" was not set correctly');
    }
}