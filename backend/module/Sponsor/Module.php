<?php
namespace Sponsor;

use Sponsor\Model\Sponsor;
use Sponsor\Model\SponsorTable;
use Zend\Db\ResultSet\ResultSet;
use Zend\Db\TableGateway\TableGateway;

class Module
{
    public function getAutoloaderConfig()
    {
        return array(
            'Zend\Loader\ClassMapAutoloader' => array(
                __DIR__ . '\autoload_classmap.php',
            ),
            'Zend\Loader\StandardAutoloader' => array(
                'namespaces' => array(
                    __NAMESPACE__ => __DIR__ . '\src\\' . __NAMESPACE__,
                ),
            ),
        );
    }

    public function getConfig()
    {
        return include __DIR__ . '\config\module.config.php';
    }

    public function getServiceConfig()
    {
        return array(
            'factories' => array(
                'Sponsor\Model\SponsorTable' =>  function($sm) {
                    $sponsorGateway = $sm->get('SponsorTableGateway');
                    $table = new SponsorTable($sponsorGateway);
                    return $table;
                },
                'SponsorTableGateway' => function ($sm) {
                    $dbAdapter = $sm->get('Zend\Db\Adapter\Adapter');
                    $resultSetPrototype = new ResultSet();
                    $resultSetPrototype->setArrayObjectPrototype(new Sponsor    ());
                    return new TableGateway('sponsor', $dbAdapter, null, $resultSetPrototype);
                },
            ),
        );
    }
}