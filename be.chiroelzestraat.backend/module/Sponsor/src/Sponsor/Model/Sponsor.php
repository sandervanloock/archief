<?php
namespace Sponsor\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class Sponsor implements InputFilterAwareInterface
{
    public $id;
    public $name;
    public $amount;
    public $dimension;
    public $object;
    public $logo;
    public $year;
    protected $inputFilter;

    public function exchangeArray($data)
    {
        $this->id     = (!empty($data['id'])) ? $data['id'] : null;
        $this->name  = (!empty($data['name'])) ? $data['name'] : null;
        $this->amount  = (!empty($data['amount'])) ? $data['amount'] : null;
        $this->dimension  = (!empty($data['dimension'])) ? $data['dimension'] : null;
        $this->object  = (!empty($data['object'])) ? $data['object'] : null;
        $this->logo  = (!empty($data['logo'])) ? $data['logo'] : null;
        $this->year = (!empty($data['year'])) ? $data['year'] : null;
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