<?php
namespace User\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class Milestone implements InputFilterAwareInterface
{
    public $userid;
    public $eventid;
    public $remarks;

    public function exchangeArray($data)
    {
        $this->userid     = (!empty($data['userid'])) ? $data['userid'] : null;
        $this->eventid  = (!empty($data['eventid'])) ? $data['eventid'] : null;
        $this->remarks  = (!empty($data['remarks'])) ? $data['remarks'] : null;
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