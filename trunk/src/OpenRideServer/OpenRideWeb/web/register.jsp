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

<f:view>

    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="home" /></jsp:include>

    <h1>Registrieren</h1>

    <!--p>Sie wollen an der OpenRide-Pilotierung für die Studenten und Mitarbeiter der Zeppelin Universität teilnehmen? Das freut uns! Nehmen Sie sich bitte kurz Zeit und registrieren Sie sich. </p>
    <p>Bitte tragen Sie Ihre tatsächlichen und keine fiktiven Daten ein. Fahrer und Mitfahrer wissen so, mit wem sie es zu tun haben und können sich bei Problemen (z.B. Verspätung) schneller verständigen. Sie können jederzeit die Löschung/Änderung Ihrer Nutzerdaten veranlassen. Ach übrigens: Die Nutzung von OpenRide ist natürlich kostenlos.</p-->

    <p><strong>Im Rahmen der Softwaretests findet keine Vermittlung von realen Fahrten statt. Alle Daten sind fiktiv. Authentische persönliche Daten bei der Registrierung sind für den Softwaretest nicht erforderlich.</strong></p>

    <p>Das Passwort für Ihren ersten Login wird Ihnen direkt im Anschluss an die Registrierung angezeigt.</p>

    <fieldset>
        <legend>Erstellen Sie Ihr Benutzerkonto bei OpenRide:</legend>

        <div style="width: 80%;">

            <h:form id="registrationForm">

                <h:messages globalOnly="true" styleClass="error" />

                <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                    <h:panelGroup>
                        <h:outputLabel for="username" value="Benutzername:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <t:inputText id="username" binding="#{Register_Backing.username}" validator="#{Register_Backing.validateUsername}" required="true" styleClass="wide">
                            <t:validateRegExpr pattern="\w{3,18}" message="Benutzernamen können nur aus den Buchstaben A-Z, Zahlen und Unterstrichen bestehen (Länge: 3-18 Zeichen)." />
                        </t:inputText>
                        <h:message for="username" styleClass="error"/>
                    </h:panelGroup>

                    <%--h:panelGroup>
                        <h:outputLabel for="password" value="Passwort:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputSecret id="password" binding="#{Register_Backing.password}" required="true" styleClass="wide" />
                        <h:message for="password" styleClass="error"/>
                    </h:panelGroup>

                    <h:panelGroup>
                        <h:outputLabel for="passwordCheck" value="Passwort (wiederholen):" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputSecret id="passwordCheck" binding="#{Register_Backing.passwordCheck}" required="true" validator="#{Register_Backing.validatePassword}" styleClass="wide" />
                        <h:message for="passwordCheck" styleClass="error"/>
                    </h:panelGroup--%>

                    <h:panelGroup>
                        <h:outputLabel for="firstName" value="Vorname:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputText id="firstName" binding="#{Register_Backing.firstName}" required="true" styleClass="wide" />
                        <h:message for="firstName" styleClass="error"/>
                    </h:panelGroup>

                    <h:panelGroup>
                        <h:outputLabel for="lastName" value="Nachname:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputText id="lastName" binding="#{Register_Backing.lastName}" required="true" styleClass="wide" />
                        <h:message for="lastName" styleClass="error"/>
                    </h:panelGroup>

                    <h:panelGroup>
                        <h:outputLabel for="gender" value="Geschlecht:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:selectOneRadio id="gender" binding="#{Register_Backing.gender}" required="true">
                            <f:selectItem itemLabel="männlich" itemValue="m" />
                            <f:selectItem itemLabel="weiblich" itemValue="f" />
                        </h:selectOneRadio>
                        <h:message for="gender" styleClass="error"/>
                    </h:panelGroup>

                    <h:panelGroup>
                        <h:outputLabel for="email" value="E-Mail-Adresse:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputText id="email" binding="#{Register_Backing.email}" required="true" validator="#{Register_Backing.validateEmail}" styleClass="wide">
                            <t:validateEmail summaryMessage="Ungültige E-Mail-Adresse."/>                            
                        </h:inputText>
                        <h:message for="email" styleClass="error" />
                    </h:panelGroup>

                    <h:panelGroup>
                        <h:outputLabel for="mobilePhoneNumber" value="Handynummer:" styleClass="requiredField" />
                        <span class="requiredField">(*)</span>
                    </h:panelGroup>
                    <h:panelGroup>
                        <h:inputText id="mobilePhoneNumber" binding="#{Register_Backing.mobilePhoneNumber}" required="true" styleClass="wide">
                            <t:validateRegExpr pattern="^[(]?0[ ]?1[5-7][0-9][ ]?[-/)]?[ ]?[1-9][-0-9 ]{6,16}$" message ="Bitte tragen Sie eine gültige deutsche Handynummer ein." />
                        </h:inputText>
                        <h:message for="mobilePhoneNumber" styleClass="error"/>
                    </h:panelGroup>
                        
                    <f:verbatim>&nbsp;</f:verbatim>
                    <h:panelGroup>
                        <h:selectBooleanCheckbox id="acceptedTerms" binding="#{Register_Backing.acceptedTerms}" required="true" validator="#{Register_Backing.validateAcceptedTerms}" style="float: left;" />
                        <div style="padding-top: 1px;">Ich habe <a href="terms.jsf" target="_blank">den Haftungsausschluss und die Datenschutzerklärung</a> gelesen und akzeptiere diese. <span class="requiredField">(*)</span></div>
                        <h:message for="acceptedTerms" styleClass="error" />
                    </h:panelGroup>

                    <f:verbatim>&nbsp;</f:verbatim>
                    <h:panelGroup>
                        <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                        <h:commandButton value="Weiter" action="#{Register_Backing.register}" />
                    </h:panelGroup>

                </h:panelGrid>


            </h:form>

        </div>

    </fieldset>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
