<?php
return array(
    'controllers' => array(
        'invokables' => array(
            'Group\Controller\Group' => 'Group\Controller\GroupController',
        ),
    ),

    'router' => array(
        'routes' => array(
            'group' => array(
                'type'    => 'segment',
                'options' => array(
                    'route'    => '/group[/:id]',
                    'constraints' => array(
                        'id'     => '[0-9]+',
                    ),
                    'defaults' => array(
                        'controller' => 'Group\Controller\Group',
                    ),
                ),
            ),
        ),
    ),

    'view_manager' => array(
        'strategies' => array(
            'ViewJsonStrategy',
        ),
    ),
);