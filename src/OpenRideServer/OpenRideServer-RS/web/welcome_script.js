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

window.onload = function(){

    document.getElementById("usermodelink").onclick = switchUserMode;
    restoreUserMode();

    window.onresize();

    // skip toolbar of mobile browsers:
    setTimeout(scrollTo, 0, 0, 1);

}

window.onresize = function(){
    adjustContentDimensions();
}

adjustContentDimensions = function() {

    // Adjusting only the height for now: innerHeight - top border (5px) - padding (38px)
    document.getElementById("content").style.minHeight = window.innerHeight - 5 - 38 + "px";

}

var usermode;

switchUserMode = function() {

    if (usermode == 1) { // Rider mode
        // switch to driver mode
        usermode = 0;
    }
    else { // Driver mode
        // Switch to rider mode
        usermode = 1;
    }

    updateUserModeControl();

}

updateUserModeControl = function() {

    var drivermodeimg = document.getElementById('drivermodeimg');
    var ridermodeimg = document.getElementById('ridermodeimg');
    if (usermode == 1) { // Rider mode
        drivermodeimg.src = "/OpenRideServer-RS/img/drivermodebtn_inactive.png";
        ridermodeimg.src = "/OpenRideServer-RS/img/ridermodebtn_active.png";
        document.getElementById('usermodeslider').style.marginLeft = '0px';
        document.getElementById('ridermodelabel').style.fontWeight = 'bold';
        document.getElementById('drivermodelabel').style.fontWeight = 'normal';
        document.getElementById('loginbutton').value = 'Einloggen';
    }
    else { // Driver mode (default)
        drivermodeimg.src = "/OpenRideServer-RS/img/drivermodebtn_active.png";
        ridermodeimg.src = "/OpenRideServer-RS/img/ridermodebtn_inactive.png";
        document.getElementById('usermodeslider').style.marginLeft = '41px';
        document.getElementById('ridermodelabel').style.fontWeight = 'normal';
        document.getElementById('drivermodelabel').style.fontWeight = 'bold';
        document.getElementById('loginbutton').value = 'Einloggen';
    }

}

restoreUserMode = function() {

    // Restore user mode from cookie
    usermode = readCookie('usermode');

    updateUserModeControl();

}

function createCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name,"",-1);
}