<?php

class Photo extends Shared\Model
{
    /**
    * @column
    * @readwrite
    * @type text
    * @length 100
    */
    protected $_title;
    
    /**
    * @column
    * @readwrite
    * @type text
    * @length 500
    */
    protected $_directory;
    
    /**
     * @column
     * @readwrite
     * @type integer
     */
    protected $_event;
}
