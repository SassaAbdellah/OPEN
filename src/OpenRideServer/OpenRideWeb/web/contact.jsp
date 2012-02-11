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

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
    
    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="contact" /></jsp:include>

    <h1>Kontakt</h1>

    <p>OpenRide ist ein Projekt des Fraunhofer Instituts für Offene Kommunikationssysteme (FOKUS):</p>

<p>Fraunhofer-Institut für Offene Kommunikationssysteme (FOKUS)<br />

Kaiserin-Augusta-Allee 31<br />

10589 Berlin<br />

Tel: +49 30 3463-7000<br />

info [at] fokus [dot] fraunhofer [dot] de</p>



<p>OpenRide Support:</p>

<p>Besuchen Sie OpenRide auf <a href="http://sourceforge.net/projects/openride/" target="_blank">SourceForge</a> oder schreiben Sie an den OpenRide-Support: <br /> <a href="mailto:openride-support@lists.sourceforge.net">openride-support@lists.sourceforge.net</a></p>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
