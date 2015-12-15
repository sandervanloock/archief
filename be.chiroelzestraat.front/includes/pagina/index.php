<h1>Welkom op de site van Chirojongens Elzestraat</h1>

<div id="tweet_box" class="gridbox left" style="min-height:303px;">
    <h2>Nieuws</h2>

    <div id="tweets"></div>
</div>


<div id="in_de_kijker" class="gridbox right">
    <h2>In de Kijker</h2>
    <!-- Zet hier altijd dezelfde foto van grootte: 518 breed 250 hoog, in jpg formaat-->
    <strong style="width:518px; height:250px; padding-top:5px; padding-left:6px;"><img
            src="uploads/banner kamp 2014.jpg" alt="In de kijker"
            style="width: 100%;margin: 0 auto;display: block"/></strong>
</div>

<div id="activiteiten" class="gridbox right" ng-controller="GroupCalendarController" style="float:right;">
    <h2>Opkomende activiteiten</h2>

    <div class="slider-wrap">
        <div id="slider1" class="csw">
            <div class="panelContainer">
                <div class="panel" ng-repeat="event in calendar.events">
                    <div class="wrapper">
                        <h2>{{event.summary}}</h2>
                        <img class="left png" ng-src="{{event.image}}" alt="logo"/>
                        <p class="description">{{event.description}}</p><br/>
                        <span style="top:130px;" id="start" class="date">Van: {{entry.start.dateTime | date : 'dd/M/yyyy H:mm' }}</span><br>
                        <span style="top:145px;" id="end"
                              class="date">Tot: {{entry.end.dateTime | date : 'dd/M/yyyy H:mm' }}</span>
                        <p class="slider_links"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

