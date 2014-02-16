<?php

class Event extends Shared\Model
{
	
	/**
	 * @column
	 * @readwrite
	 * @type text
	 * @length 100
	 */
	protected $_name;
	
	/**
    * @column
    * @readwrite
    * @type datetime
    */
    protected $_start;
    
    /**
    * @column
    * @readwrite
    * @type datetime
    */
    protected $_end;

    /**
     * @column
     * @readwrite
     * @type integer
     */
    protected $_eventtype;
    
}
