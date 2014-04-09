<?php

namespace PhotoTest\Controller;

use Photo\Model\Photo;
use Zend\Test\PHPUnit\Controller\AbstractHttpControllerTestCase;

class PhotoControllerTest extends AbstractHttpControllerTestCase
{
    protected $traceError = true;
    protected $photo;
    protected $data;

    public function setUp()
    {
        $this->setApplicationConfig(
            include 'C:\Projects\archief\backend\config\application.config.php'
        );
        $this->data = array(
            'id' => 123,
            'name' => 'some photo',
            'end' => '2004-07-16 00:00:00',
            'start' => '2004-07-15 00:00:00',
            'phototype' => 1);
        $this->photo = new Photo();
        $this->photo->exchangeArray($this->data);
        parent::setUp();
    }

    public function testIndexActionCanBeAccessed()
    {
        $this->dispatch('/photo');
        $this->assertResponseStatusCode(200);

        $this->assertModuleName('Photo');
        $this->assertControllerName('Photo\Controller\Photo');
        $this->assertControllerClass('PhotoController');
        $this->assertMatchedRouteName('photo');
    }

    public function testPhotoInitialState()
    {
        $photo = new Photo();
        $this->assertNull(
            $photo->id,
            '"id" should initially be null'
        );
    }

    public function testExchangeArraySetsPropertiesCorrectly()
    {
        $data = $this->data;
        $photo = $this->photo;
        $this->assertSame($data['id'],$photo->id,'"id" was not set correctly');
    }
}