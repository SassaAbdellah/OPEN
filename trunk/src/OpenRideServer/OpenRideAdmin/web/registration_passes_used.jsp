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

    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="regpasses_used" /></jsp:include>

    <h:form id="logoutForm">
        <div style="float: right; margin-top: 4px;"><h:commandLink action="#{Welcome_Backing.logout}" immediate="true"><h:outputText value="Ausloggen" /></h:commandLink></div>
        <h1>Vergebene Passcodes</h1>
    </h:form>

    <h:form id="form">
        <f:verbatim><fieldset>
                <legend>Zuletzt registrierte Benutzer mit initialem Passwort:</legend></f:verbatim>

                <div  style="padding: 10px 15px;">

                <h:dataTable id="dt1" value="#{RegistrationPasses_Backing.invalidPasses}" var="pass">

                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Datum" style="font-weight: bold;" />
                        </f:facet>
                        <h:outputText value="#{pass.usageDate}"><f:convertDateTime pattern="dd.MM.yyyy" /></h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Nachname" style="font-weight: bold;" />
                        </f:facet>
                        <h:outputText value="#{pass.custId.custLastname}"></h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Vorname" style="font-weight: bold;" />
                        </f:facet>
                        <h:outputText value="#{pass.custId.custFirstname}"></h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="E-Mail-Adresse" style="font-weight: bold;" />
                        </f:facet>
                        <h:outputText value="#{pass.custId.custEmail}"></h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Benutzername" style="font-weight: bold;" />
                        </f:facet>
                        <h:outputText value="#{pass.custId.custNickname}"></h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Passwort" style="font-weight: bold;" />
                        </f:facet>
                        <a href="mailto:<h:outputText value="#{pass.custId.custEmail}"></h:outputText>?subject=Dein Passwort für OpenRide&body=Lieber OpenRide-Nutzer, liebe OpenRide-Nutzerin,%0A%0Aes freut uns, dass Du an der OpenRide-Pilotierung der Zeppelin Universität teilnimmst!%0A%0ADein Benutzername ist:%0A%0A<h:outputText value="#{pass.custId.custNickname}"></h:outputText>%0A%0ADein initiales Passwort für OpenRide lautet:%0A%0A<h:outputText value="#{pass.passcode}"></h:outputText>%0A%0AWir empfehlen, das Passwort nach dem ersten Einloggen zu ändern!%0A%0AUnd hier der Link zur Anwendung: https://www.open-ride.mobi/%0A%0AEine Anleitung findest Du nach dem Einloggen über die Desktop-Anwendung in Deinem persönlichen Bereich:%0A%0Ahttps://www.open-ride.mobi/OpenRideWeb/%0A%0AMit freundlichen Grüßen,%0ADein OpenRide-Team"><h:outputText value="#{pass.passcode}"></h:outputText></a>
                    </h:column>

                </h:dataTable>

            </div>

            <f:verbatim></fieldset></f:verbatim>

    </h:form>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
