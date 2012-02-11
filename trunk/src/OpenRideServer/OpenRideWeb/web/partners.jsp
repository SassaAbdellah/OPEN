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

    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="partners" /></jsp:include>

    <h1>Partner</h1>

    <p>OpenRide wird unterstützt durch:</p>

    <br>
    <a href="http://www.fokus.fraunhofer.de"><img src="img/FraunhoferLogo_team-seite.png" height="47" width="154" alt="Fraunhofer FOKUS Logo"></a>
    <p>
        Das <a href="http://www.fokus.fraunhofer.de/de/fokus/index.html">Fraunhofer-Institut für Offene Kommunikationssysteme FOKUS</a> erforscht und entwickelt seit mehr als 20 Jahren am Standort Berlin Kommunikations- und Integrationslösungen für Partner aus Industrie, Forschungsförderung und Öffentlicher Verwaltung in den Branchen Telekommunikation, Automotive, eGovernment und Softwareentwicklung.
    </p>
    <br>


    <a href="http://www.bmwi.de/BMWi/Navigation/root.html"><img src="img/bmwi.png" height="92" width="169" alt="BMWI Logo"></a>  <a href="http://www.exist.de/"><img align="top" src="img/exist.png" height="47" width="103" alt="exist Logo"></a>
    <p>OpenRide wird gefördert durch die Bundesrepublik Deutschland im Rahmen des <a href="http://www.exist.de/">EXIST Förderprogramms</a>; Zuwendungsgeber:
        <a href="http://www.bmwi.de/BMWi/Navigation/root.html">Bundesministerium für Wirtschaft und Technologie</a> aufgrund eines
        Beschlusses des Deutschen Bundestages.
    </p>
    <p>
        Das EXIST Förderprogramm unterstützt herausragende forschungsbasierte Gründungsvorhaben,
        die mit aufwändigen und risikoreichen Entwicklungsarbeiten verbunden sind.
    </p>



    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
