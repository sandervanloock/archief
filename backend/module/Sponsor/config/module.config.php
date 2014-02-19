<?php
return array(
    'controllers' => array(
        'invokables' => array(
            'Sponsor\Controller\Sponsor' => 'Sponsor\Controller\SponsorController',
        ),
    ),

    'router' => array(
        'routes' => array(
            'sponsor' => array(
                'type'    => 'segment',
                'options' => array(
                    'route'    => '/sponsor[/:id]',
                    'constraints' => array(
                        'id'     => '[0-9]+',
                    ),
                    'defaults' => array(
                        'controller' => 'Sponsor\Controller\Sponsor',
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