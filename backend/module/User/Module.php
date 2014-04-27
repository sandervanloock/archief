<?php
namespace User;

use User\Model\MilestoneTable;
use User\Model\User;
use User\Model\Milestone;
use User\Model\UserTable;
use User\Model\Membership;
use User\Model\MembershipTable;
use Zend\Db\ResultSet\ResultSet;
use Zend\Db\TableGateway\TableGateway;

class Module
{
    public function getAutoloaderConfig()
    {
        return array(
            'Zend\Loader\ClassMapAutoloader' => array(
                __DIR__ . '/autoload_classmap.php',
            ),
            'Zend\Loader\StandardAutoloader' => array(
                'namespaces' => array(
                    __NAMESPACE__ => __DIR__ . '/src/' . __NAMESPACE__,
                ),
            ),
        );
    }

    public function getConfig()
    {
        return include __DIR__ . '/config/module.config.php';
    }

    public function getServiceConfig()
    {
        return array(
            'factories' => array(
                'User\Model\UserTable' =>  function($sm) {
                    $userGateway = $sm->get('UserTableGateway');
                    $table = new UserTable($userGateway);
                    return $table;
                },
                'UserTableGateway' => function ($sm) {
                    $dbAdapter = $sm->get('Zend\Db\Adapter\Adapter');
                    $resultSetPrototype = new ResultSet();
                    $resultSetPrototype->setArrayObjectPrototype(new User());
                    return new TableGateway('user', $dbAdapter, null, $resultSetPrototype);
                },
                'User\Model\MilestoneTable' =>  function($sm) {
                    $milestoneGateway = $sm->get('MilestoneTableGateway');
                    $table = new MilestoneTable($milestoneGateway);
                    return $table;
                },
                'MilestoneTableGateway' => function ($sm) {
                    $dbAdapter = $sm->get('Zend\Db\Adapter\Adapter');
                    $resultSetPrototype = new ResultSet();
                    $resultSetPrototype->setArrayObjectPrototype(new Milestone());
                    return new TableGateway('milestone', $dbAdapter, null, $resultSetPrototype);
                },
                'User\Model\MembershipTable' =>  function($sm) {
                    $membershipGateway = $sm->get('MembershipTableGateway');
                    $table = new MembershipTable($membershipGateway);
                    return $table;
                },
                'MembershipTableGateway' => function ($sm) {
                    $dbAdapter = $sm->get('Zend\Db\Adapter\Adapter');
                    $resultSetPrototype = new ResultSet();
                    $resultSetPrototype->setArrayObjectPrototype(new Membership());
                    return new TableGateway('membership', $dbAdapter, null, $resultSetPrototype);
                },
            ),
        );
    }
}