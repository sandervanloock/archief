//////////////////////////
// main
//////////////////////////
if(self==top) {

    location = "../../index.html";
}

///////////////////////////
function SetupMedium() {
///////////////////////////

    // set keyboard handler
    document.onkeydown = ImageBrowsingKeydownHandlerMedium;

    // set main image cell size
    var bordersize = 2;
    document.getElementById('imagecell').style.width = parseInt(maxMediumW+2*bordersize)+'px';

    // render title
    document.getElementById('title').innerHTML = title;

    // render footer
    document.getElementById('footer').innerHTML = PortaMagicFooter(footer);

    // render links
    RenderCtrlLinks();

    // render image
    ShowImage( currentImage, 0 );

    // cache the next image
    CacheNextImage();
}

//////////////////////////
function ImageBrowsingKeydownHandlerMedium( e ) {
//////////////////////////

    ImageBrowsingKeydownHandler( e, parent.mf );
}

///////////////////////////
function ShowingFirstImage() {
///////////////////////////

    if (intro!='') {

        return currentImage==-1;
    }

    return currentImage==0;
}

///////////////////////////
function ShowingLastImage() {
///////////////////////////

    return currentImage==(nbImages-1);
}

///////////////////////////
function ShowHome() {
///////////////////////////

    ShowImage( -1, 1 );
}

///////////////////////////
function Next() {
///////////////////////////

    if ( !ShowingLastImage() ) {

        ShowImage( currentImage+1, 1 );
    }
}

///////////////////////////
function Previous() {
///////////////////////////

    if ( !ShowingFirstImage() ) {

        ShowImage( currentImage-1, 1 );
    }
}

//////////////////////////
var cacheImage = new Image();
//////////////////////////
function CacheNextImage() {
//////////////////////////

    // cache the next image
    if ( !ShowingLastImage() ) {

        cacheImage.src = '../../' + mediumDir+'/'+ImageFilenameURL(currentImage+1);
    }
}

//////////////////////////
function GenerateImageAndCaptionHTML( imageIdx, onloadhandler ) {
//////////////////////////

    var mac = majorCaption[imageIdx];
    var mic = minorCaption[imageIdx].replace( /"/g, '&quot;'    );

    var mihtml = '<img name="imagepane" onload="'+onloadhandler+'" class="medium" src="../../' + mediumDir + '/'+ImageFilenameURL(imageIdx)+'" ';
    mihtml    += ' title="'+mic+'" alt="'+mic+'" />';
    if (largeImages) {

        mihtml = '<a href="javascript:OpenLarge('+imageIdx+')">'+mihtml+'</a>';
    }
    var bordersize = 1;
    cap_width = parseInt(mediumWidth[imageIdx]+2*bordersize);
    mihtml += '<div style="width:'+cap_width+'px;" class="caption">'+mac+'<br/></div>';

    return mihtml;
}

//////////////////////////
function ShowImage( imgNb, scrollsmall ) {
// changes 'currentImage'
//////////////////////////

    // set the current image number
    currentImage = imgNb;

    // load image, captions, et cetera

    // hide/show next, previous links
    document.getElementById('previous').style.visibility = ShowingFirstImage() ? 'hidden' : 'visible';
    document.getElementById('next').style.visibility = ShowingLastImage() ? 'hidden' : 'visible';;

    // render main item
    if (currentImage==-1) {

        // an intro page text is existing and requested
        var mihtml = '';
        mihtml += '<br/>';
        var bordersize = 1;
        mihtml += '<div id="intro" style="text-align:left;width:'+(maxMediumW+2*bordersize)+'px;min-height:'+mediumHeight[0]+'px;">'+intro+'</div>';
        document.getElementById('mainimage').innerHTML = mihtml;

        // cache next image
        CacheNextImage();
    } else {

        // render image and caption html
        document.getElementById('mainimage').innerHTML = GenerateImageAndCaptionHTML( currentImage, 'CacheNextImage()' );

        // nasty opera work-around
        var isOpera = (navigator.userAgent.toLowerCase().indexOf("opera") != -1);
        if (isOpera) {

            // image onload is not supported, hence call it directly
            CacheNextImage();
        }
    }

    // scroll to show the corresponding small image
    var sif = parent.document.getElementById('smalliframe');
    if (scrollsmall && sif) {

        anchor = imgNb>=0 ? imgNb : 'top';
        sif.src='ext/html/small.html#'+anchor;
    }
}

///////////////////////////
function OpenLarge( imgNb ) {
// changes 'currentImage'
///////////////////////////

    // set the current image number
    currentImage = imgNb;

    var w = largeWidth[imgNb];
    var h = largeHeight[imgNb];
    var sw = screen ? screen.width : w;
    var sh = screen ? screen.height : h;
    var scrollbars = 'no';
    var gutter = 100;

    if (sw-gutter<w || sh-gutter<h) {

        scrollbars = 'yes';
        w = sw-gutter;
        h = sh-gutter;
    }

    window.open('large.html',
                '',
                'width='+w+
                ',height='+h+
                ',resizable=yes,menubar=no,toolbar=no,status=no,location=no'+
                ',scrollbars='+scrollbars+
                ',left='+( (sw-w)/2 )+
                ',top='+( (sh-h)/2 )+
                ',screenX='+( (sw-w)/2 )+
                ',screenY='+( (sh-h)/2 ) );
}

///////////////////////////
function ShowAll() {
///////////////////////////

    var html = '';
    for (i=0;i<nbImages;i++) {

        html += GenerateImageAndCaptionHTML( i, '' );
    }
    document.getElementById('mainimage').innerHTML = html;
}

///////////////////////////
function RenderCtrlLinks() {
///////////////////////////

    var ctrllinks = '';
    for(i=0;i<clinks_href.length;i++) {

        ctrllinks += '&nbsp;&nbsp;<a class="control" href="' + clinks_href[i] + '">' + clinks_txt[i] + '</a>';
    }
    ctrllinks += '<span id="previous">&nbsp;&nbsp;<a class=control href="javascript:Previous()">'+ls_previous+'</a></span>';
    ctrllinks += '<span id="next">&nbsp;&nbsp;<a class=control href="javascript:Next()">'+ls_next+'</a></span>';

    document.getElementById('ctrllinks').innerHTML = ctrllinks;
}