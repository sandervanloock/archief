<?php
namespace Event\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class Event implements InputFilterAwareInterface
{
    public $id;
    public $name;
    public $start;
    public $end;
    public $photos;
    public $eventtype;
    protected $inputFilter;

    public function exchangeArray($data)
    {
        $this->id     = (!empty($data['id'])) ? $data['id'] : null;
        $this->name  = (!empty($data['name'])) ? $data['name'] : null;
        $this->start = (!empty($data['start'])) ? $data['start'] : null;
        $this->end = (!empty($data['end'])) ? $data['end'] : null;
        $this->eventtype = (!empty($data['eventtype'])) ? $data['eventtype'] : null;
    }

    public function getArrayCopy()
    {
        return get_object_vars($this);
    }

    public function setPhotos($photos){
        $this->photos = $photos;
    }

    public function setInputFilter(InputFilterInterface $inputFilter)
    {
        throw new \Exception("Not used");
    }

    public function getInputFilter()
    {
        if (!$this->inputFilter) {
            $inputFilter = new InputFilter();
            $this->inputFilter = $inputFilter;
        }

        return $this->inputFilter;
    }

}