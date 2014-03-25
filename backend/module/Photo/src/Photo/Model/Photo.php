<?php
namespace Photo\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class Photo implements InputFilterAwareInterface
{
    public $id;
    public $title;
    public $directory;
    public $live;
    public $deleted;
    public $created;
    public $modified;
    protected $inputFilter;

    public function exchangeArray($data)
    {
        $this->id     = (!empty($data['id'])) ? $data['id'] : null;
        $this->title  = (!empty($data['title'])) ? $data['title'] : null;
        $this->directory = (!empty($data['directory'])) ? $data['directory'] : null;
        $this->live = $data['live'];
        $this->deleted = $data['deleted'];
        $this->created = (!empty($data['created'])) ? $data['created'] : null;
        $this->modified = (!empty($data['modified'])) ? $data['modified'] : null;
    }

    public function getArrayCopy()
    {
        return get_object_vars($this);
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