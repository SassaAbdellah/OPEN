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
<%@taglib prefix="t" uri="http://myfaces.apache.org/tomahawk" %>

<h1>Herzlich willkommen bei OpenRide!</h1>

    <t:div rendered="#{Welcome_Backing.renderRegistrationSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
        <p><strong>Vielen Dank für Ihre Registrierung!</strong><br />Ihr Benutzerkonto wurde erfolgreich erstellt. </p><p style="color: red;">Sie können sich als &bdquo;<h:outputText value="#{Welcome_Backing.registeredUsername}" />&ldquo; mit dem Passwort &bdquo;<h:outputText value="#{Welcome_Backing.registeredPassword}" />&ldquo; einloggen.</p>
        <script type="text/javascript">
            window.onload = function() { document.getElementById("j_password").focus(); }
        </script>
    </t:div>

    <fieldset style="float: left; margin: 10px 18px 0 0; width: 325px; min-height: 50px;">
        <legend>Sie haben noch kein Konto?</legend>

        <p>Hier geht es zur Registrierung.</p>

        <h:form id="registrationForm">
            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                <%--h:panelGroup>
                    <h:outputLabel for="passCode" value="Registrierungs- code:" styleClass="requiredField" />
                    <span class="requiredField">(*)</span>
                </h:panelGroup>
                <h:panelGroup>
                    <h:inputText id="passCode" binding="#{Welcome_Backing.passCode}" value="#{sessionScope['passCode']}" required="true" validator="#{Welcome_Backing.validatePassCode}" styleClass="wide" />
                    <h:message for="passCode" styleClass="error" />
                </h:panelGroup--%>

                <f:verbatim>&nbsp;</f:verbatim>
                <h:commandButton value="Registrieren" action="#{Welcome_Backing.register}" />

            </h:panelGrid>
        </h:form>

        <%--p><strong>Die freie Testphase hat begonnen!</strong><br />Bitte nutzt als nächsten Registrierungscode: <strong style="margin-left: 33%; padding-left: 5px; line-height: 145%;"><a href="javascript:void(0);" onclick="document.getElementById('registrationForm:passCode').value='<h:outputText value="#{Welcome_Backing.nextPassCode}" />'; return false;" style="font-family: monospace;"><h:outputText value="#{Welcome_Backing.nextPassCode}" /></a></strong></p--%>

    </fieldset>
