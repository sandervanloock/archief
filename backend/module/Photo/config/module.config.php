<?php
return array(
    'controllers' => array(
        'invokables' => array(
            'Photo\Controller\Photo' => 'Photo\Controller\PhotoController',
        ),
    ),

    'router' => array(
        'routes' => array(
            'photo' => array(
                'type'    => 'segment',
                'options' => array(
                    'route'    => '/photo[/:id]',
                    'constraints' => array(
                        'id'     => '[0-9]+',
                    ),
                    'defaults' => array(
                        'controller' => 'Photo\Controller\Photo',
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