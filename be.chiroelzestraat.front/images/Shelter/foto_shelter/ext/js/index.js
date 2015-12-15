//////////////////////////
// main
//////////////////////////
var mf;	// handle to the medium iframe (initialised onload())

///////////////////////////
function SetupIndex() {
///////////////////////////

    // setup medium iframe
    var m_iframe = '<iframe src="ext/html/medium.html" id="mediumiframe" name="mediumiframe" frameborder="no" scrolling="auto"></iframe>';
    document.getElementById('mediumiframe_span').innerHTML = m_iframe;

    // show thumbnails
    SetupIFrames( smallPos );

    // set handle to the medium iframe
    mf = this.frames['mediumiframe'];

    // set keyboard handler
    document.onkeydown = ImageBrowsingKeydownHandlerIndex;

    // set document title
    document.title = title.replace(/(<([^>]+)>)/ig,"");

    // write status message
    window.status = 'This is a Porta photo album. Use the left and right arrow keys to navigate.';
    
    // go to light box if specified    
    //var url = window.location.href;
    //var show_lightbox = (url.indexOf("?lightbox")!=-1);
    //if (show_lightbox) {
    	
    //	DisplayLightbox();
    //}    
}

//////////////////////////
function ImageBrowsingKeydownHandlerIndex( e ) {
//////////////////////////

    ImageBrowsingKeydownHandler( e, mf );
}


///////////////////////////
function EnlargeImageInLightbox( i ) {
///////////////////////////

    // delete selection to avoid display artefacts
    if (self.getSelection)  {
        self.getSelection().removeAllRanges();
    } else if (document.selection) {
        document.selection.clear();
    }

    mf.ShowImage( i, 0  );
    document.getElementById('mainlayer').style.visibility = 'visible';
    document.getElementById('lightboxlayer').style.visibility = 'hidden';
}

///////////////////////////
function DisplayLightbox() {
///////////////////////////

    // hide the main layer and show the lightbox
    document.getElementById('mainlayer').style.visibility = 'hidden';
    document.getElementById('lightboxlayer').style.visibility = 'visible';

    // unload small images elements in the mainlayer
    SetupIFrames( 'none' );

    // register and render light box links
    mf.clinks_href  = new Array();
    mf.clinks_txt   = new Array();
    mf.clinks_href.push('javascript:parent.DisplayLightbox()');
    mf.clinks_txt.push( ls_lightbox );
    mf.clinks_href.push('javascript:parent.HideLightbox()');
    mf.clinks_txt.push( ls_albumview );
    mf.RenderCtrlLinks();

    // write title and link
    document.getElementById('title_lb').innerHTML = title;
    document.getElementById('ctrllinks_lb').innerHTML = '<a class="control" href="javascript:HideLightbox()">'+ls_albumview+'</a>';

    // write thumbnails
    var border = 14;
    var html = '';
    for (i=0;i<nbImages;i++) {

        var hfill    = maxSmallW-smallWidth[i];
        var vfill    = maxSmallH-smallHeight[i];
        var m_left   = border + Math.floor( hfill/2 );
        var m_right  = border + Math.ceil ( hfill/2 );
        var m_top    = border + Math.floor( vfill/2 );
        var m_bottom = border + Math.ceil ( vfill/2 );

        var mac = majorCaption[i].replace( /"/g,    '&quot;' );
        var re  = /<\S[^>]*>/g;
        mac = mac.replace(re,""); // strip html

        html += '<a href="javascript:EnlargeImageInLightbox('+i+')">';
        html += '<img style="display: inline; padding: 0px; border: 0px solid black; ';
        html += 'margin-left: '+m_left+'px; margin-right:'+m_right+'px; ';
        html += 'margin-top:'+m_top+'px; margin-bottom:'+m_bottom+'px;" ';
        html += 'src="'+smallDir+'/'+ImageFilenameURL(i)+'" ';
        html += 'width="'+smallWidth[i]+'" ' ;
        html += 'height="'+smallHeight[i]+'" ' ;
        html += 'galleryimg="no" title="'+mac+'" alt="'+mac+'" />';
        html += '</a> ';
    }
    document.getElementById('images_lb').innerHTML = html;

    // write footer
    document.getElementById('footer_lb').innerHTML = PortaMagicFooter(footer);
}

///////////////////////////
function HideLightbox() {
///////////////////////////

    // unload lightbox images to conserve memory
    document.getElementById('images_lb').innerHTML = '';

    // turn on small images in the main layer
    SetupIFrames( smallPos );

    // unregister links
    mf.clinks_href  = new Array();
    mf.clinks_txt   = new Array();
    mf.RenderCtrlLinks();

    // hide lightbox and show the main layer
    document.getElementById('mainlayer').style.visibility = 'visible';
    document.getElementById('lightboxlayer').style.visibility = 'hidden';
}

//////////////////////////
function SetupIFrames( smallPosSetting ) {
//////////////////////////

    // access medium iframe style
    var stym      = document.getElementById('mediumiframe').style;
    var stym_span = document.getElementById('mediumiframe_span').style;

    // stuff required for the nasty ie workaround....
    // (which to make matters worse requires to use the xml prolog stunt 
    //  to switch ie into quirks mode in order to use the faulty box model)
    var isIEnoFixed = (navigator.userAgent.toLowerCase().indexOf("msie 3")!=-1) || 
    				  (navigator.userAgent.toLowerCase().indexOf("msie 4")!=-1) ||
    				  (navigator.userAgent.toLowerCase().indexOf("msie 5")!=-1) ||
    				  (navigator.userAgent.toLowerCase().indexOf("msie 6")!=-1);        
    
    if (smallPosSetting=='none') {

        // empty iframe
        document.getElementById('smalliframe_span').innerHTML = '';
                     	
        // set the main content to fill the browser
        stym.left   = '0px';
        stym.top    = '0px';
        stym.padding = '0px 0px 0px 0px';
        if (isIEnoFixed) {
			
            stym.height = '100%';
            stym.width  = '100%';
        } else {

            stym.height = '100%';
            stym.width  = '100%';
			stym_span.position = 'fixed';
			stym_span.top      = '0px';
	    	stym_span.left     = '0px';
	        stym_span.bottom   = '0px';
	        stym_span.right    = '0px';
	        stym_span.width    = 'auto';
	        stym_span.height   = 'auto';
        }
        stym.visibility = 'visible';
        return;
    }

	// write the small iframe
    var s_iframe = '<iframe src="ext/html/small.html" id="smalliframe" name="smalliframe" frameborder="no" scrolling="auto"></iframe>';
    document.getElementById('smalliframe_span').innerHTML = s_iframe;

	// size and position iframes
    var sty  = document.getElementById('smalliframe').style;
    var scrollbarsize = 16;		 // ugly pragmatic stuff
    var marginsize = 2*1+2*3+10; // ugly pragmatic stuff
    var space = isIEnoFixed ? 15 : 10;  // ugly pragmatic stuff
    var w = parseInt(maxSmallW+scrollbarsize+marginsize+space)+'px';
    var h = parseInt(maxSmallH+scrollbarsize+marginsize)+'px';
    stym.top      = '0px';
    stym.left     = '0px';    
    stym.height   = '100%';
    stym.width    = '100%';
    if (isIEnoFixed==false) {
		    
		sty.position 	   = 'fixed';
		stym_span.position = 'fixed';
		stym_span.top      = '0px';
    	stym_span.left     = '0px';
        stym_span.bottom   = '0px';
        stym_span.right    = '0px';
        stym_span.width    = 'auto';
        stym_span.height   = 'auto';
    }
    switch (smallPosSetting) {

        case 'top':     sty.left    = '0px';
                        sty.top     = '0px';
                        sty.width   = '100%';
                        sty.height  = h;

                        if (isIEnoFixed) {

                            stym.paddingTop = h;
                        } else {

                            stym_span.top   = h;
                        }
                        break;

        case 'bottom':  sty.left    = '0px';
                        sty.bottom  = '0px';
                        sty.width   = '100%';
                        sty.height  = h;

                        if (isIEnoFixed) {

                            stym.paddingBottom = h;
                        } else {

                            stym_span.bottom = h;
                        }
                        break;

        case 'right':   sty.right   = '0px';
                        sty.top     = '0px';
                        sty.width   = w;
                        sty.height  = '100%';

                        if (isIEnoFixed) {

                            stym.paddingRight = w;
                        } else {

                            stym_span.right  = w;
                        }

                        break;

        case 'left':
        default:        sty.left    = '0px';
                        sty.top     = '0px';
                        sty.width   = w;
                        sty.height  = '100%';

                        if (isIEnoFixed) {

                            stym.paddingLeft = w;                            
                        } else {
							
                            stym_span.left = w;
                        }
                        break;
     }
     sty.visibility  = 'visible';
     stym.visibility = 'visible';
}