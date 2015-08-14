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
        );
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

    public function testDeleteCanBeAccessed()
    {
        $this->getRequest()->setMethod('delete');
        $this->dispatch('/photo/10');

        $this->assertModuleName('Photo');
        $this->assertControllerName('Photo\Controller\Photo');
        $this->assertControllerClass('PhotoController');
        $response = $this->getResponse();
        $this->assertEquals(200, $response->getStatusCode());
    }
}