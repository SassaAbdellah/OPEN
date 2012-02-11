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

<jsp:include page="/WEB-INF/jspf/welcome_header.jsp"><jsp:param name="section" value="home" /></jsp:include>

<h1 style="text-align: right; margin: 15px 10px;"><img src="/OpenRideServer-RS/img/logo.png" alt="OpenRide" /></h1>

<h3>Fehler 500</h3>

<small>
    <p>Die Anfrage konnte durch den Server nicht verarbeitet werden.<br />Sollte dieses Problem weiterhin bestehen, wenden Sie sich bitte an unser Support-Team.</p>
    <p><a href="/OpenRideServer-RS/">&laquo; Zur Startseite</a></p>
</small>

<jsp:include page="/WEB-INF/jspf/welcome_footer.jsp"></jsp:include>
