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
    public $password;
    public $birthDate;
    public $city;
    public $postalCode;
    public $street;
    public $houseNumber;
    public $postalBox;
    public $phone;
    public $mobilePhone;
    public $presentOnReunion;
    public $isPhotoBookCandidate;

    public function exchangeArray($data)
    {
        $this->id     = (!empty($data['id'])) ? $data['id'] : null;
        $this->first  = (!empty($data['first'])) ? $data['first'] : null;
        $this->last  = (!empty($data['last'])) ? $data['last'] : null;
        $this->email  = (!empty($data['email'])) ? $data['email'] : null;
        $this->login= (!empty($data['email'])) ? $data['email'] : null;
        $this->birthDate= (!empty($data['birthDate'])) ? date_create($data['birthDate'])->format('Y-m-d\TH:i:sO') : null;
        $this->city= (!empty($data['city'])) ? $data['city'] : null;
        $this->postalCode= (!empty($data['postalCode'])) ? $data['postalCode'] : null;
        $this->street= (!empty($data['street'])) ? $data['street'] : null;
        $this->houseNumber= (!empty($data['houseNumber'])) ? $data['houseNumber'] : null;
        $this->postalBox= (!empty($data['postalBox'])) ? $data['postalBox'] : null;
        $this->phone= (!empty($data['phone'])) ? $data['phone'] : null;
        $this->mobilePhone= (!empty($data['mobilePhone'])) ? $data['mobilePhone'] : null;
        $this->presentOnReunion= isset($data['presentOnReunion']) ? $data['presentOnReunion'] : false;
        $this->isPhotoBookCandidate= isset($data['isPhotoBookCandidate']) ? $data['isPhotoBookCandidate'] : false;
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