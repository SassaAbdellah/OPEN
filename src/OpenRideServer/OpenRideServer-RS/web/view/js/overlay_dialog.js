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

document.write('<div id="overlay">&nbsp;</div><div id="overlay_dialog"><div id="dialog_text"></div></div>');	

function showOverlayDialog(title, content, btn1_label, btn1_action, btn2_label, btn2_action) {

	if (document.getElementById) {
	
		var dialog_text = '<p><strong>'+title+'</strong></p><p>'+content+'</p><div id="buttons"><input type="button" value="'+btn1_label+'" class="rounded" id="dialog_btn_ok" onclick="'+btn1_action+'; hideOverlayDialog();" />';
	
		if (btn2_label!='')
			dialog_text = dialog_text + '<input type="button" value="'+btn2_label+'" class="rounded" onclick="'+btn2_action+'; hideOverlayDialog();"/>';
			
		dialog_text = dialog_text + '</div>';
	
		document.getElementById("dialog_text").innerHTML = dialog_text;
	
	
		hideSelects()

                var overlayHeight = document.getElementById("content").offsetHeight + 88;
                if (overlayHeight == 88) {
                    // we don't have a visible content div, so use a default value
                    overlayHeight = window.innerHeight;
                }

                document.getElementById("overlay").style.height = overlayHeight + "px";
                document.getElementById("overlay_dialog").style.top = getScrollHeight() + "px";

		document.getElementById("overlay").style.display = 'block';
		document.getElementById("overlay_dialog").style.display = 'block';

	}

}


function hideOverlayDialog() {
	
	document.getElementById("overlay").style.display = 'none';
	document.getElementById("overlay_dialog").style.display = 'none';
	
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