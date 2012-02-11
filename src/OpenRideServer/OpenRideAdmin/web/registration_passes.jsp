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

    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="regpasses" /></jsp:include>

    <h:form id="logoutForm">
        <div style="float: right; margin-top: 4px;"><h:commandLink action="#{Welcome_Backing.logout}" immediate="true"><h:outputText value="Ausloggen" /></h:commandLink></div>
        <h1><h1>RegistrationPass-Generator</h1></h1>
    </h:form>

    <h:form id="form">

        <f:verbatim><fieldset>
                <legend>1. Schritt: Parameter festlegen</legend></f:verbatim>

                <div  style="padding: 10px 15px;">

                    <table>
                        <tr>
                            <td>
                            <h:outputLabel value="Anzahl: *" for="passCount" />
                        </td>
                        <td>
                            <h:inputText id="passCount" value="#{RegistrationPasses_Backing.passCount}" required="true" requiredMessage="Bitte trage etwas in dieses Feld ein.">
                                <f:validateDoubleRange minimum="1"  />
                            </h:inputText>
                        </td>
                        <td style="color: red;">
                            <h:message for="passCount" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h:outputLabel value="Länge: *" for="passLength" />
                        </td>
                        <td>
                            <h:inputText id="passLength" value="#{RegistrationPasses_Backing.passLength}" required="true" requiredMessage="Bitte trage etwas in dieses Feld ein.">
                                <f:validateDoubleRange minimum="1" />
                            </h:inputText>
                        </td>
                        <td style="color: red;">
                            <h:message for="passLength" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h:outputLabel value="Prefix:" for="passPrefix" />
                        </td>
                        <td>
                            <h:inputText id="passPrefix" value="#{RegistrationPasses_Backing.passPrefix}"></h:inputText>
                        </td>
                        <td style="color: red;">
                            <h:message for="passPrefix" />
                        </td>
                    </tr>
                </table>

                <h:commandButton value="Beispiel-Codes generieren" action="#{RegistrationPasses_Backing.generate}" />

                <h:dataTable id="dt1" value="#{RegistrationPasses_Backing.passes}" var="pass">

                    <h:column>
                        <span style="font-family: monospace;"><h:outputText value="#{pass}"></h:outputText></span>
                    </h:column>

                </h:dataTable>

            </div>

            <f:verbatim></fieldset></f:verbatim>

        <f:verbatim><fieldset>
                <legend>2. Schritt: Persistierung</legend></f:verbatim>

                <div  style="padding: 10px 15px;">

                <h:commandButton value="Codes nach diesem Schema persistieren" action="#{RegistrationPasses_Backing.persist}" />

                <h:dataTable id="dt2" value="#{RegistrationPasses_Backing.persistedPasses}" var="pass">

                    <h:column>
                        <span style="font-family: monospace;"><h:outputText value="#{pass}"></h:outputText></span>
                    </h:column>

                </h:dataTable>

            </div>

            <f:verbatim></fieldset></f:verbatim>

    </h:form>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
