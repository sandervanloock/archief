<?php
include("includes/functions.php");
?>
<!DOCTYPE html>
<html>


<head>
    <meta charset="utf-8">

    <title>Chirojongens Elzestraat</title>
    <!--[if lte IE 9]>
    <link href="css/stylesheetIE9.css" rel="stylesheet" type="text/css"><![endif]-->
    <!--[if lte IE 8]>
    <link href="css/stylesheetIE8.css" rel="stylesheet" type="text/css"><![endif]-->
    <!--[if lte IE 7]>
    <link href="css/stylesheetIE7.css" rel="stylesheet" type="text/css"><![endif]-->

    <!--[if lte IE 9]>
    <link href="css/jquery.minifbalbum.IE9.css" rel="stylesheet" type="text/css" media="all"/><![endif]-->
    <!--[if lte IE 8]>
    <link href="css/jquery.minifbalbum.IE8.css" rel="stylesheet" type="text/css" media="all"/><![endif]-->
    <!--[if lte IE 7]>
    <link href="css/jquery.minifbalbum.IE7.css" rel="stylesheet" type="text/css" media="all"/><![endif]-->

    <!--[if !IE]>-->
    <link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="all"/>
    <!--<![endif]-->
    <!--[if !IE]>-->
    <link href="css/jquery.minifbalbum.css" rel="stylesheet" type="text/css" media="all"/>
    <!--<![endif]-->

    <link href="css/sliderstyle.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Layout verhuur-->
    <link href="css/groepenstyle.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Layout groepen-->
    <link href="css/videocss.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Layout Video-->
    <link href="css/jquery.tweet.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Layout Tweets-->
    <link href="css/images/favicon.ico" rel="shortcut icon"/>
    <link href="css/slider.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="css/universalStylesheet.css" rel="stylesheet" type="text/css" media="all"/>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/SpryTabbedPannels.js"></script>
    <script type="text/javascript" src="js/jquery.minifbalbum.js"></script>
    <script type="text/javascript" src="js/jquery.miniloader.js"></script>
    <script type="text/javascript" src="js/date.format.js"></script>
    <script type="text/javascript" src="js/reedcalendar.js"></script>
    <script type="text/javascript" src="js/videochannel.js"></script>
    <script type="text/javascript" src="js/jquery.tweet.js"></script>
    <script type="text/javascript" src="js/slider.js"></script>
    <script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="js/jquery.easing.compatibility.js"></script>


    <script type="text/javascript">
        $("#tweets").addClass("loading");
        jQuery(function ($) {
            $("#tweets").tweet({
                username: "chiroelzestraat",
                avatar_size: 48,
                count: 5,
                loading_text: "nieuws ophalen..."
            });
        });
    </script>

    <script type="text/javascript">

        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-30791822-1']);
        _gaq.push(['_trackPageview']);

        (function () {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();

    </script>


</head>
<body>
<div id="contentwrapper">
    <div id="header">
        <h1>Chirojongens Elzestraat</h1>

        <h1>Chirojongens Elzestraat Verhuur Lokalen</h1>

        <h1>Jeugdbeweging</h1>

        <h1>ElzestraaTD</h1>
    </div>
    <div id="menu">
        <?php main_menu() ?>
    </div>
    <div id="main">
        <div id="left" class="borderradius">
            <?php
            generate_main_inhoud();
            ?>
        </div>
        <div id="right">
            <?php
            echo "<div id='groepen' class='grid orangeul'>";
            echo "<h1>Groepen</h1>";
            sub_menu();
            ?></div>

        <div id="program" class="grid">
            <h1>'t Program</h1>
            <?php plugin_program(); ?>
        </div>

        <div id="contactinfo" class="grid">
            <h1>Contacteer ons</h1>
            <ul class="social-icons">
                <li><a target="blank"
                       href="https://www.facebook.com/pages/Chirojongens-Elzestraat/234251636629703?bookmark_t=page"><img
                            src="images/facebook_32.png" alt="Like ons op Facebook"/></a></li>
                <li><a target="blank" href="https://twitter.com/ChiroElzestraat"><img src="images/twitter_32.png"
                                                                                      alt="Volg ons op Twitter"/></a>
                </li>
                <li><a href"" target="blank"><img src="images/youtube_icon.png" alt="Youtube kanaal"></a></li>
                </li>
                <li><a href="http://www.chiroelzestraat.be/chirojongens/index.php?pagina=contact" alt="Mail ons"><img
                            src="images/email_32.png" alt="Email Us"/></a></li>
            </ul>
        </div>

        <!--                <div id="login" class="grid">-->
        <!--                	<h1>Leiding Login</h1>-->
        <!--    					--><?php //plugin_login(); ?>
        <!--                </div>-->

        <div id="chiropromo" class="grid">
            <h1>Wat is Chiro?!</h1>
            <iframe src="http://player.vimeo.com/video/9671922" width="280" height="150" frameborder="0"
                    webkitAllowFullScreen mozallowfullscreen allowFullScreen>
            </iframe>
        </div>

    </div>


</div>
<div id="footer">
    <p>
        Chirojongens Elzestraat |
        Contact: info@chiroelzestraat.be |
        Clemenceaustraat 111 (achter de kerk)
        2860 Sint-Katelijne-Waver |
        Copyright &copy;  <?php echo date('Y'); ?>
    </p>
</div>
</div>

</body>
</html>