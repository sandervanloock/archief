//////////////////////////
function ImageFilenameURL( i ) {
//////////////////////////

    return escape( imageFilename[i] );
}

//////////////////////////
function ImageBrowsingKeydownHandler( e, mfHandle ) {
//////////////////////////

    if (!e) { e = window.event; }
    if (e.type!='keydown') { return; }

    switch (e.keyCode) {

        case 37:    // left_arrow
                    mfHandle.Previous();
                    break;

        case 39:    // right_arrow
                    mfHandle.Next();
                    break;
        default:
    }
}

///////////////////////////
function PortaMagicFooter( footerStr ) {
///////////////////////////

    var p_link = ' <a href=\"http://www.stegmann.dk/mikkel/porta/\" class="control">Porta</a>';
    return footerStr.replace( / Porta/g, p_link );
}