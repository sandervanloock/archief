<?php
return array(
    'controllers' => array(
        'invokables' => array(
            'Sponsor\Controller\Sponsor' => 'Sponsor\Controller\SponsorController',
            'Downloader' => 'Sponsor\Controller\DownloadController',
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
                'may_terminate' => true,
                'child_routes' => array(
                    'downloadZip' => array(
                        'type' => 'segment',
                        'options' => array(
                            'route' => '/downloadZip',
                            'defaults' => array(
                                'controller' => 'Downloader',
                                'action' => 'downloadZip'
                            ),
                        ),
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