<?php
namespace User\Model;

use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

class User implements InputFilterAwareInterface
{
    public $id;
    public $first;
    public $last;
    public $email;
    public $login;
    public $salt;
    public $password;
    public $live;
    public $deleted;
    public $created;
    public $modified;

    public function exchangeArray($data)
    {
        $this->id     = (!empty($data['id'])) ? $data['id'] : null;
        $this->first  = (!empty($data['first'])) ? $data['first'] : null;
        $this->last  = (!empty($data['last'])) ? $data['last'] : null;
        $this->email  = (!empty($data['email'])) ? $data['email'] : null;
        $this->login= (!empty($data['login'])) ? $data['login'] : null;
        $this->salt  = (!empty($data['salt'])) ? $data['salt'] : null;
        $this->password  = (!empty($data['password'])) ? $data['password'] : null;
        $this->live  = (!empty($data['live'])) ? $data['live'] : null;
        $this->deleted  = (!empty($data['deleted'])) ? $data['deleted'] : null;
        $this->created  = (!empty($data['created'])) ? $data['created'] : null;
        $this->modified  = (!empty($data['modified'])) ? $data['modified'] : null;
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