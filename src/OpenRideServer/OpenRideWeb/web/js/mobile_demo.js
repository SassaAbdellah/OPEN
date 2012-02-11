/*
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

document.write('<div id="overlay">&nbsp;</div><div id="mobile_demo"></div>');

var mobileDemoLoginTimeout;

function showMobileDemo(username) {

    if (document.getElementById) {

        hideSelects()

        document.getElementById("mobile_demo").innerHTML = '<a href="javascript:hideMobileDemo();" style="position: absolute; top: 82px; right: 56px;">Schließen - X</a><iframe id="mobile_demo_iframe" src="/OpenRideServer-RS/view/" style="height: 480px; width: 335px; overflow: hidden; overflow-y: scroll;" frameborder="no"></iframe>';

        document.getElementById("overlay").style.display = 'block';
        document.getElementById("mobile_demo").style.display = 'block';

        mobileDemoLoginTimeout = setInterval("autoSetUsername('"+username+"')", 500);        
        //setInterval("enableDesktopStyles()", 1000); //handled by the client

    }

}


function hideMobileDemo() {

    document.getElementById("overlay").style.display = 'none';
    document.getElementById("mobile_demo").style.display = 'none';

    showSelects();

    // Refresh page to get updated user data:
    location.reload();

}

function enableDesktopStyles() {
    try {
        var iFrame =  document.getElementById('mobile_demo_iframe');
        var iFrameBody;
        if ( iFrame.contentDocument )
        { // FF
            iFrameBody = iFrame.contentDocument.getElementsByTagName('body')[0];
        }
        else if ( iFrame.contentWindow )
        { // IE
            iFrameBody = iFrame.contentWindow.document.getElementsByTagName('body')[0];
        }
        iFrameBody.className = "desktop";
    }
    catch(err) {
       
    }
}

function autoSetUsername(username) {
    try {
        var iFrame =  document.getElementById('mobile_demo_iframe');
        var usernameField;
        if ( iFrame.contentDocument )
        { // FF
            usernameField = iFrame.contentDocument.getElementById('j_username');
        }
        else if ( iFrame.contentWindow )
        { // IE
            usernameField = iFrame.contentWindow.document.getElementById('j_username');
        }
        usernameField.value = username;
        clearInterval(mobileDemoLoginTimeout);
    }
    catch(err) {

    }
}

function hideSelects() {
    IE6 = false /*@cc_on || @_jscript_version < 5.7 @*/;
    if (IE6) {
        // looping through all forms on the page
        for (f = 0; f < document.forms.length; f++) {
            var elements = document.forms[f].elements;
            // looping through all elements on certain form
            for (e = 0; e < elements.length; e++) {
                if (elements[e].type == "select-one") {
                    elements[e].style.visibility = 'hidden';
                }
            }
        }
    }
}

function showSelects() {
    IE6 = false /*@cc_on || @_jscript_version < 5.7 @*/;
    if (IE6) {
        // looping through all forms on the page
        for (f = 0; f < document.forms.length; f++) {
            var elements = document.forms[f].elements;
            // looping through all elements on certain form
            for (e = 0; e < elements.length; e++) {
                if (elements[e].type == "select-one") {
                    elements[e].style.visibility = 'visible';
                }
            }
        }
    }
}

function TrimString(sInString) {
    sInString = sInString.replace( /^\s+/g, "" );// strip leading
    return sInString.replace( /\s+$/g, "" );// strip trailing
}

function nl2br(text){
    text = escape(text);
    if(text.indexOf('%0D%0A') > -1){
        re_nlchar = /%0D%0A/g ;
    }
    else if(text.indexOf('%0A') > -1){
        re_nlchar = /%0A/g ;
    }
    else if(text.indexOf('%0D') > -1){
        re_nlchar = /%0D/g ;
    }
    return unescape( text.replace(re_nlchar,'<br />') );
}