var items = '';
var sp, ra, to, ke, as = false;

function    fillGeneralSlider(obj, groep) {
    items = obj.getElementsByTagName('entry');
    try {
        var desc = items[0].getElementsByTagName('content').item(0).firstChild.data;
    } catch (e) {
        desc = 'geen verdere beschrijving';
    }
    var panel = '<div class="panel"><div class="wrapper"><h2>' + groep.substr(0, 1).toUpperCase() + groep.substr(1) + '</h2>';
    panel += '<img class="left png" src="images/' + groep + '.png" alt="logo"/>';
    panel += '<p class="description">' + desc + '</p><br/>';
    panel += '<span style="top:130px;" id="start" class="date">Van: ' + returnStartdate(items, 0).format("dd/mm/yyyy H:MM");
    panel += '</span><br><span style="top:145px;" id="end" class="date">Tot: ' + returnEnddate(items, 0).format("dd/mm/yyyy H:MM");
    panel += '</span><p class="slider_links"><a href="index.php?pagina=groepen&groep=' + groep + '">';
    panel += groep + 'pagina </a>';
    $('.panelContainer').html($('.panelContainer').html() + panel);
    switch (groep) {
        case "speelclub" :
            sp = true;
            break;
        case "rakkers" :
            ra = true;
            break;
        case "toppers" :
            to = true;
            break;
        case "kerels" :
            ke = true;
            break;
        case "aspiranten" :
            as = true;
            break;
    }
}

function fillSlider(obj, groep) {
    items = obj.getElementsByTagName('entry');
    if (items.length == 0)
        $('#activiteiten').remove();
    for (var i = 0; i < items.length; i++) {
        try {
            var desc = items[i].getElementsByTagName('content').item(0).firstChild.data;
        } catch (e) {
            desc = 'geen verdere beschrijving';
        }
        var panel = '<div class="panel"><div class="wrapper"><h2>' + items[i].getElementsByTagName('title')[0].childNodes[0].nodeValue + '</h2>';
        panel += '<img class="left png" src="images/' + groep + '.png" alt="logo"/>';
        panel += '<p class="description">' + desc + '</p><br/>';
        panel += '<span style="top:130px;" id="start" class="date">Van: ' + returnStartdate(items, i).format("dd/mm/yyyy H:MM");
        panel += '</span><br><span style="top:145px;" id="end" class="date">Tot: ' + returnEnddate(items, i).format("dd/mm/yyyy H:MM");
        panel += '</span><p class="slider_links">';
        $('.panelContainer').html($('.panelContainer').html() + panel);
    }
}

function TimeStampToDate(xmlDate) {
    var dt = new Date();
    var dtS = xmlDate.slice(xmlDate.indexOf('T') + 1, xmlDate.indexOf('.'))
    var TimeArray = dtS.split(":");
    try {
        dt.setUTCHours(TimeArray[0] - 2, TimeArray[1], TimeArray[2]);
    }
    catch (e) {
    }
    ;
    dtS = xmlDate.slice(0, xmlDate.indexOf('T'))
    TimeArray = dtS.split("-");
    dt.setUTCFullYear(TimeArray[0], TimeArray[1] - 1, TimeArray[2]);
    return dt;
}

function returnStartdate(entries, index) {
    var cdate = entries[index].getElementsByTagName("when")[0];
    if (!cdate || cdate == null)
        cdate = entries[index].getElementsByTagName("gd:when")[0];
    if (!cdate || cdate == null)
        cdate = entries[index].getElementsByTagNameNS('http://schemas.google.com/g/2005/gd', "when")[0];
    return TimeStampToDate(cdate.getAttribute("startTime"));
}

function returnEnddate(entries, index) {
    var cdate = entries[index].getElementsByTagName("when")[0];
    if (!cdate || cdate == null)
        cdate = entries[index].getElementsByTagName("gd:when")[0];
    if (!cdate || cdate == null)
        cdate = entries[index].getElementsByTagNameNS('http://schemas.google.com/g/2005/gd', "when")[0];
    return TimeStampToDate(cdate.getAttribute("endTime"));
}

/**
 Groep is de groep waarvoor een slider moet worden gecreeerd
 gen is een boolean die aangeeft of het om een algemene slider gaat (verschillende calendars) of slechts één enkele
 */
function initActivities(groep, gen) {
    $("#activiteiten").addClass("loading");
    var url = 'includes/onderdelen/plugin_' + groep + '_calendar.php';
    var exec = false;
    $.ajax({
        url: url
    }).done(function (data) {
        if (gen) {
            fillGeneralSlider(data, groep);
        }
        else {
            fillSlider(data, groep);
        }
        if (!exec && ((sp && ra && to && ke && as) || !gen)) {
            $("#slider1").codaSlider()
        $("#activiteiten").removeClass("loading");
            exec = true;
        }
    });
}

function initGeneralSlider() {
    initActivities('speelclub', true);
    initActivities('rakkers', true);
    initActivities('toppers', true);
    initActivities('kerels', true);
    initActivities('aspiranten', true);
}
