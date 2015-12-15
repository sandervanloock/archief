<?php
return array(
    'controllers' => array(
        'invokables' => array(
            'Event\Controller\Event' => 'Event\Controller\EventController',
        ),
    ),

    'router' => array(
        'routes' => array(
            'event' => array(
                'type'    => 'segment',
                'options' => array(
                    'route'    => '/event[/:id]',
                    'constraints' => array(
                        'id'     => '[0-9]+',
                    ),
                    'defaults' => array(
                        'controller' => 'Event\Controller\Event',
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