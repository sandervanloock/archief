//////////////////////////
// main
//////////////////////////
if(self==top) {

    location = "../../index.html";
}

///////////////////////////
function SetupSmall() {
///////////////////////////

    // set keyboard handler
    document.onkeydown = ImageBrowsingKeydownHandlerSmall;

    // render the small images
    var vert = smallPos=='left' || smallPos=='right';
    var html = '';

    html += '<table>';

        if (!vert) { html += '<tr>'; }

            // control links
            if (vert) { html += '<tr>'; }
                html += '<td>';
                    if (slink_href!='' && slink_txt!='') {

                        html += '<a class="control" target="_parent" href="'+slink_href+'">'+slink_txt+'</a><br/>';
                    }
                    if (intro!='') { html += '<a name="top"></a><a class="control" href="javascript:parent.mf.ShowHome()">'+ls_home+'</a><br/>'; }
                    html += '<a class="control" href="javascript:parent.mf.ShowAll()">'+ls_showall+'</a><br/>';
                    html += '<a class="control" href="javascript:parent.DisplayLightbox()">'+ls_lightbox+'</a><br/>';
                html += '</td>';
            if (vert) { html += '</tr>'; }


            // images
            for (i=0; i<nbImages; i++) {

                if (vert) { html += '<tr>'; }
                    html += '<td style="vertical-align: middle; text-align: center;">';
                        html += '<a name='+i+' href="javascript:parent.mf.ShowImage('+i+',0)">';
                        html += '<img class="small" src="../../'+smallDir+'/'+ImageFilenameURL(i)+'"';
                        html += ' style="margin: auto;"';
                        html += ' width="'+smallWidth[i]+'"';
                        html += ' height="'+smallHeight[i]+'"';
                        html += ' galleryimg="no" />';
                        html += '</a>';
                    html += '</td>';
                if (vert) { html += '</tr>'; }
            }

        if (!vert) { html += '</tr>'; }

    html += '</table>';

    var bordersize = 1;
    if (vert) {

        document.getElementById('smallpaper').style.width = parseInt(maxSmallW+2*bordersize)+'px';
        document.getElementsByTagName('html').item(0).style.overflowX = 'hidden';
    } else {

        document.getElementById('smallpaper').style.height = parseInt(maxSmallH+2*bordersize)+'px';
        document.getElementById('smallpaper').style.whiteSpace = 'nowrap';
        document.getElementById('smallpaper').style.marginTop = '2px';
    }

    document.getElementById('smallcell').innerHTML = html;
}

//////////////////////////
function ImageBrowsingKeydownHandlerSmall( e ) {
//////////////////////////

    ImageBrowsingKeydownHandler( e, parent.mf );
}