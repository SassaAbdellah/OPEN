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

document.write('<div id="inprogress">&nbsp;</div><div id="inprogress_dialog"><div id="inprogress_dialog_text"></div></div>');

var progressDialogTimer;

function prepareProgressDialog() {

    clearTimeout(progressDialogTimer);
    progressDialogTimer = setTimeout('showProgressDialog();', 50);

}

function showProgressDialog() {

    if (document.getElementById) {

        var dialog_text = ' <img id="inprogress_gif" src="../img/inprogress.gif" alt="inprogress"/> ';

        document.getElementById("inprogress_dialog_text").innerHTML = dialog_text;


        hideSelects();

        var inprogressHeight = document.getElementById("content").offsetHeight + 88;
        if (inprogressHeight == 88) {
            // we don't have a visible content div, so use a default value
            inprogressHeight = window.innerHeight;
        }

        document.getElementById("inprogress").style.height = inprogressHeight + "px";
        document.getElementById("inprogress_dialog").style.top = getScrollHeight() + "px";

        document.getElementById("inprogress").style.display = 'block';
        document.getElementById("inprogress_dialog").style.display = 'block';

    }

}


function hideProgressDialog() {
    clearTimeout(progressDialogTimer);

    if (document.getElementById) {

        document.getElementById("inprogress").style.display = 'none';
        document.getElementById("inprogress_dialog").style.display = 'none';

    }

    showSelects();

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


function getScrollWidth()
{
    var w = window.pageXOffset ||
    document.body.scrollLeft ||
    document.documentElement.scrollLeft;

    return w ? w : 0;
}

function getScrollHeight()
{
    var h = window.pageYOffset ||
    document.body.scrollTop ||
    document.documentElement.scrollTop;

    return h ? h : 0;
}