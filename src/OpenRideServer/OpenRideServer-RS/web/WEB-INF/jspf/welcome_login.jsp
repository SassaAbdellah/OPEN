<%--
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
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<img src="/OpenRideServer-RS/img/logo.png" alt="OpenRide" style="float: right; margin: 13px 10px 14px 0;" />
<h1>Herzlich willkommen!</h1>

<form id="loginForm" action="/OpenRideServer-RS/j_security_check" method="post" class="login">
    <h3>Jetzt einloggen und starten:</h3>

    <c:if test="${param.login_error=='true'}"><p><span class="error">Ungültige Zugangsdaten.</span></p></c:if>

    <div>
        <label for="j_username" class="requiredField">Benutzername:</label>
        <input id="j_username" type="text" name="j_username" /><br />
    </div>
    <div>
        <label for="j_password" class="requiredField">Passwort:</label>
        <input id="j_password" type="text" name="j_password" value="" /><br />
    </div>

    <div id="usermodelink">
        <img id="ridermodeimg" src="/OpenRideServer-RS/img/ridermodebtn_inactive.png" alt="" />
        <div id="usermodeslider_bg">
            <img src="/OpenRideServer-RS/img/switch_btn.png" alt="" id="usermodeslider" />
        </div><img id="drivermodeimg" src="/OpenRideServer-RS/img/drivermodebtn_active.png" alt="" />
        <div id="ridermodelabel" style="position: absolute; text-align: center; font-weight: normal; font-size: 12px; width: 67px; left: 0; top: 5px;">Als<br />Mitfahrer<br />starten</div>
        <div id="drivermodelabel" style="position: absolute; text-align: center; font-weight: normal; font-size: 12px; width: 57px; right: 0; top: 5px;">Als<br />Fahrer<br />starten</div>
    </div>

    <input type="submit" name="submit" id="loginbutton" value="Einloggen" onclick="createCookie('usermode', usermode, 365);" />

    <p class="hint" style="color: #999; font-size: 12px;">Passwort vergessen? <a href="mailto:openride-support@lists.sourceforge.net">&raquo; E-Mail an den Support</a></p>
</form>

<script type="text/javascript">
    document.getElementById('loginForm').style.display = 'block';
</script>
<noscript>
    <h3>Bitte aktivieren Sie JavaScript in Ihrem Browser, bevor Sie sich bei OpenRide einloggen.</h3>
    <p>Bei Fragen hierzu senden Sie bitte eine <a href="mailto:support-zu@open-ride.com">E-Mail an den Support</a>.</p>
    <meta http-equiv="refresh" content="3" />
</noscript>
