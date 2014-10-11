<?php
namespace User\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class Membership implements InputFilterAwareInterface
{
    public $userid;
    public $groupid;
    public $from;
    public $to;
    public $milestones;

    public function exchangeArray($data)
    {
        $this->userid     = (!empty($data['userid'])) ? $data['userid'] : null;
        $this->groupid  = (!empty($data['groupid'])) ? $data['groupid'] : null;
        $this->from  = (!empty($data['from'])) ? $data['from'] : null;
        $this->to  = (!empty($data['to'])) ? $data['to'] : null;
    }

    public function setMilestones($milestones){
        $this->milestones = $milestones;
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