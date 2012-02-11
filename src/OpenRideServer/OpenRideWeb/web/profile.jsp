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
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="t" uri="http://myfaces.apache.org/tomahawk" %>

<f:view> 

    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="home" /></jsp:include>

    <h:form id="logoutForm">
        <div style="float: right; margin-top: 4px;">Eingeloggt als <a href="profile.jsf"><h:outputText value="#{UserBean.username}" /></a>
            | <h:commandLink action="#{Welcome_Backing.logout}" immediate="true"><h:outputText value="Ausloggen" /></h:commandLink></div>
        <h1>Hallo, <h:outputText value="#{UserBean.firstName} #{UserBean.lastName}" />!</h1>
        <p>Neu hier? Hier findest Du <a href="files/AnleitungOpenRide.pdf">eine ausf&uuml;hrliche Anleitung zu OpenRide</a> als pdf.</p>
    </h:form>

    <h:form id="membersArea" enctype="multipart/form-data" onsubmit="if ((document.getElementById('membersArea:profileTab:profilePersonalDataTab:profilePersonalDataForm:dateOfBirth.year').value != '' && document.getElementById('membersArea:profileTab:profilePersonalDataTab:profilePersonalDataForm:dateOfBirth.year').value<=1900)) { alert('Bitte geben Sie eine vierstellige Jahreszahl an.'); return false; }">

        <t:panelTabbedPane serverSideTabSwitch="false" width="100%" activeTabStyleClass="activeOuterTab" activeSubStyleClass="activeOuterTabSub" disabledTabStyleClass="disabledOuterTab" inactiveTabStyleClass="inactiveOuterTab" inactiveSubStyleClass="inactiveOuterTabSub" tabContentStyleClass="outerTabContent">

            <!-- profile tab -->
            <t:panelTab id="profileTab" label="Mein Profil">

                <jsp:include page="/WEB-INF/jspf/profile_info.jsp" />

                <t:panelTabbedPane serverSideTabSwitch="false" style="width: 80%;" activeTabStyleClass="activeInnerTab" activeSubStyleClass="activeInnerTabSub" disabledTabStyleClass="disabledInnerTab" inactiveTabStyleClass="inactiveInnerTab" inactiveSubStyleClass="inactiveInnerTabSub" tabContentStyleClass="innerTabContent">

                    <t:panelTab id="profilePersonalDataTab" label="Persönliche Daten">

                        <t:div rendered="#{Profile_Backing.renderPersonalDataSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                            <p>Änderungen erfolgreich gespeichert.</p>
                        </t:div>

                        <h3>Hier erweitern und überprüfen Sie Ihre Angaben.</h3>
                        <p>Bitte laden Sie im Reiter <strong>&quot;Mein Bild&quot;</strong> unbedingt ein Bild von Ihnen hoch, damit Fahrer bzw. Mitfahrer Sie erkennen können. Wenn Sie Autofahrer(in) sind, dann geben Sie unten bitte die Informationen zu Ihrem Fahrzeug an. So erleichtern Sie dem Mitfahrer das Auffinden Ihres Fahrzeugs. Vielen Dank!</p>

                        <t:subform id="profilePersonalDataForm">
                            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:outputText value="Vorname:" />
                                <h:outputText value="#{Profile_Backing.firstName}" />

                                <h:outputText value="Nachname:" />
                                <h:outputText value="#{Profile_Backing.lastName}" />

                                <h:outputText value="Geschlecht:" />
                                <h:outputText value="#{Profile_Backing.gender}" />

                                <h:outputLabel for="dateOfBirth" value="Geburtsdatum:" />
                                <h:panelGroup>
                                    <t:inputDate id="dateOfBirth" binding="#{Profile_Backing.dateOfBirth}" popupCalendar="false" type="date" onblur="if (document.getElementById('membersArea:profileTab:profilePersonalDataTab:profilePersonalDataForm:dateOfBirth.year').value != '' && document.getElementById('membersArea:profileTab:profilePersonalDataTab:profilePersonalDataForm:dateOfBirth.year').value<=1900) { alert('Bitte geben Sie eine vierstellige Jahreszahl an.'); return false; }" />
                                    <t:message for="dateOfBirth" styleClass="error"/>
                                </h:panelGroup>

                                <h:panelGroup>
                                    <h:outputLabel for="email" value="E-Mail-Adresse:" styleClass="requiredField" />
                                    <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                    <h:inputText id="email" binding="#{Profile_Backing.email}" required="true" validator="#{Profile_Backing.validateEmail}" styleClass="wide" >
                                        <t:validateEmail summaryMessage="Bitte tragen Sie eine gültige E-Mail-Adresse ein."/>
                                    </h:inputText>
                                    <h:message for="email" styleClass="error" />
                                </h:panelGroup>

                                <h:panelGroup>
                                    <h:outputLabel for="mobilePhoneNumber" value="Handynummer:" styleClass="requiredField" />
                                    <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                    <h:inputText id="mobilePhoneNumber" binding="#{Profile_Backing.mobilePhoneNumber}" required="true" styleClass="wide">
                                        <t:validateRegExpr pattern="^[(]?0[ ]?1[5-7][0-9][ ]?[-/)]?[ ]?[1-9][-0-9 ]{6,16}$" message ="Bitte tragen Sie eine gültige deutsche Handynummer ein." />
                                    </h:inputText>
                                    <h:message for="mobilePhoneNumber" styleClass="error"/>
                                </h:panelGroup>

                                <%--h:outputLabel for="fixedPhoneNumber" value="Festnetznummer:" />
                                <h:panelGroup>
                                    <h:inputText id="fixedPhoneNumber" binding="#{Profile_Backing.fixedPhoneNumber}" styleClass="wide" />
                                    <h:message for="fixedPhoneNumber" styleClass="error"/>
                                </h:panelGroup--%>

                                <h:outputLabel for="streetAddress" value="Straße, Hausnummer:" />
                                <h:panelGroup>
                                    <h:inputText id="streetAddress" binding="#{Profile_Backing.streetAddress}" styleClass="wide" />
                                    <h:message for="streetAddress" styleClass="error"/>
                                </h:panelGroup>

                                <h:outputLabel for="zipCode" value="PLZ:" />
                                <h:panelGroup>
                                    <h:inputText id="zipCode" binding="#{Profile_Backing.zipCode}" styleClass="wide" required="false">
                                        <t:validateRegExpr pattern="^$|^\d{5}$" message ="Ungültige Postleitzahl." />
                                    </h:inputText>
                                    <h:message for="zipCode" styleClass="error"/>
                                </h:panelGroup>

                                <h:outputLabel for="city" value="Ort:" />
                                <h:panelGroup>
                                    <h:inputText id="city" binding="#{Profile_Backing.city}" styleClass="wide" />
                                    <h:message for="city" styleClass="error"/>
                                </h:panelGroup>

                                <h:outputLabel for="isSmoker" value="Ich bin ...:" />
                                <h:panelGroup>
                                    <h:selectOneRadio id="isSmoker" binding="#{Profile_Backing.isSmoker}" required="true">
                                        <f:selectItem itemLabel="Nichtraucher/in" itemValue="n" />
                                        <f:selectItem itemLabel="Raucher/in" itemValue="y" />
                                        <f:selectItem itemLabel="keine Angabe" itemValue="-" />
                                    </h:selectOneRadio>
                                    <h:message for="isSmoker" styleClass="error"/>
                                </h:panelGroup>

                                <f:verbatim><strong>Fahrerangaben</strong></f:verbatim>
                                <f:verbatim>&nbsp;</f:verbatim>

                            </h:panelGrid>

                            <p>Zur Teilnahme als Fahrer ist mindestens die Angabe von Autofarbe, -marke und -kennzeichen erforderlich.</p>

                            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:outputLabel for="carColour" value="Autofarbe:" />
                                <h:panelGroup>
                                    <h:inputText id="carColour" binding="#{Profile_Backing.carColour}" styleClass="wide" />
                                    <h:message for="carColour" styleClass="error"/>
                                </h:panelGroup>

                                <h:outputLabel for="carBrand" value="Automarke:" />
                                <h:panelGroup>
                                    <h:inputText id="carBrand" binding="#{Profile_Backing.carBrand}" styleClass="wide" />
                                    <h:message for="carBrand" styleClass="error"/>
                                </h:panelGroup>

                                <%--h:outputLabel for="carBuildYear" value="Autobaujahr:" />
                                <h:panelGroup>
                                    <h:inputText id="carBuildYear" binding="#{Profile_Backing.carBuildYear}" validator="#{Profile_Backing.validateYear}" styleClass="wide" />
                                    <h:message for="carBuildYear" styleClass="error"/>
                                </h:panelGroup--%>

                                <h:outputLabel for="carPlateNo" value="Autokennzeichen:" />
                                <h:panelGroup>
                                    <h:inputText id="carPlateNo" binding="#{Profile_Backing.carPlateNo}" styleClass="wide" autocomplete="off" />
                                    <h:message for="carPlateNo" styleClass="error"/>
                                </h:panelGroup>

                                <%--h:outputLabel for="licenseDate" value="Führerschein seit (Jahr):" />
                                <h:panelGroup>
                                    <h:inputText id="licenseDate" binding="#{Profile_Backing.licenseDate}" validator="#{Profile_Backing.validateYear}" styleClass="wide" />
                                    <h:message for="licenseDate" styleClass="error"/>
                                </h:panelGroup--%>

                                <f:verbatim>&nbsp;</f:verbatim>
                                <h:panelGroup>
                                    <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                                    <t:commandButton value="Speichern" action="#{Profile_Backing.editPersonalData}" actionFor="profilePersonalDataForm" />
                                </h:panelGroup>

                            </h:panelGrid>
                        </t:subform>

                    </t:panelTab>

                    <t:panelTab id="profilePictureTab" label="Mein Bild">

                        <t:div rendered="#{Profile_Backing.renderPictureSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                            <p>Änderungen erfolgreich gespeichert.</p>
                        </t:div>                        

                        <h3>Speichern Sie ein Foto von Ihnen als Profilbild:</h3>

                        <t:subform id="profilePictureForm">
                            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:outputLabel for="picture" value="Neues Bild:" />
                                <h:panelGroup>
                                    <t:inputFileUpload id="picture" storage="file" accept="image/*" binding="#{Profile_Backing.picture}" styleClass="wide" required="true">
                                    </t:inputFileUpload>
                                    <h:message for="picture" styleClass="error"/>
                                    <t:div rendered="#{Profile_Backing.renderPictureErrorMessage}" style="color: red;">
                                        Das Profilbild konnte nicht gespeichert werden. Bitte wählen Sie eine gültige Bilddatei aus.
                                    </t:div>
                                </h:panelGroup>

                                <f:verbatim>&nbsp;</f:verbatim>
                                <h:panelGroup>
                                    <div style="float: right; margin-top: 2px; color: #999;">Dateigröße maximal 10 MB</div>
                                    <t:commandButton value="Speichern" action="#{Profile_Backing.editPicture}" actionFor="profilePictureForm" />
                                </h:panelGroup>

                            </h:panelGrid>
                        </t:subform>

                    </t:panelTab>

                    <t:panelTab id="profileDriverRiderPrefsTab" label="Präferenzen">

                        <t:div rendered="#{Profile_Backing.renderDriverRiderPrefsSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                            <p>Änderungen erfolgreich gespeichert.</p>
                        </t:div>

                        <t:subform id="profilePrefsForm">

                            <h3>Allgemeine Präferenzen:</h3>

                            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:outputLabel for="prefIsSmoker" value="Rauchen während der Fahrt erwünscht:" />
                                <h:panelGroup>
                                    <h:selectOneRadio id="prefIsSmoker" binding="#{Profile_Backing.prefIsSmoker}" required="true">
                                        <f:selectItem itemLabel="ja" itemValue="y" />
                                        <f:selectItem itemLabel="nein" itemValue="n" />
                                        <f:selectItem itemLabel="egal" itemValue="-" />
                                    </h:selectOneRadio>
                                    <h:message for="prefIsSmoker" styleClass="error"/>
                                </h:panelGroup>

                                <%--h:outputLabel for="prefGender" value="Geschlecht meiner Fahrer und Mitfahrer:" />
                                <h:panelGroup>
                                    <h:selectOneRadio id="prefGender" binding="#{Profile_Backing.prefGender}" required="true">
                                        <%/*f:selectItem itemLabel="männlich" itemValue="m" /*/%>
                                        <f:selectItem itemLabel="weiblich" itemValue="f" />
                                        <f:selectItem itemLabel="egal" itemValue="-" />
                                    </h:selectOneRadio>
                                    <h:message for="prefGender" styleClass="error"/>
                                </h:panelGroup--%>


                                <%/*h:outputLabel for="driverPrefAge" value="Alter:" />
                                            <h:panelGroup>
                                            <h:inputText id="driverPrefAge" binding="#{Profile_Backing.driverPrefAge}" styleClass="wide">
                                            <t:validateRegExpr pattern="\d*" message ="Bitte eine Zahl eintragen oder das Feld leer lassen." />
                                            </h:inputText>
                                            <h:message for="driverPrefAge" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <%/*h:outputLabel for="driverPrefGender" value="Geschlecht:" />
                                            <h:panelGroup>
                                            <h:selectOneRadio id="driverPrefGender" binding="#{Profile_Backing.driverPrefGender}" required="true">
                                            <f:selectItem itemLabel="weiblich" itemValue="f" />
                                            <f:selectItem itemLabel="egal" itemValue="-" />
                                            </h:selectOneRadio>
                                            <h:message for="driverPrefGender" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <%/*h:outputLabel for="driverPrefIsSmoker" value="Nichtraucher:" />
                                            <h:panelGroup>
                                            <h:selectOneRadio id="driverPrefIsSmoker" binding="#{Profile_Backing.driverPrefIsSmoker}" required="true">
                                            <f:selectItem itemLabel="ja, bitte nur!" itemValue="n" />
                                            <f:selectItem itemLabel="egal" itemValue="-" />
                                            </h:selectOneRadio>
                                            <h:message for="driverPrefIsSmoker" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <%/*h:outputLabel for="riderPrefAge" value="Alter:" />
                                            <h:panelGroup>
                                            <h:inputText id="riderPrefAge" binding="#{Profile_Backing.riderPrefAge}" styleClass="wide">
                                            <t:validateRegExpr pattern="\d*" message ="Bitte eine Zahl eintragen oder das Feld leer lassen." />
                                            </h:inputText>
                                            <h:message for="riderPrefAge" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <%/*h:outputLabel for="riderPrefGender" value="Geschlecht:" />
                                            <h:panelGroup>
                                            <h:selectOneRadio id="riderPrefGender" binding="#{Profile_Backing.riderPrefGender}" required="true">
                                            <f:selectItem itemLabel="weiblich" itemValue="f" />
                                            <f:selectItem itemLabel="egal" itemValue="-" />
                                            </h:selectOneRadio>
                                            <h:message for="riderPrefGender" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <%/*h:outputLabel for="riderPrefIsSmoker" value="Nichtraucher:" />
                                            <h:panelGroup>
                                            <h:selectOneRadio id="riderPrefIsSmoker" binding="#{Profile_Backing.riderPrefIsSmoker}" required="true">
                                            <f:selectItem itemLabel="ja, bitte nur!" itemValue="n" />
                                            <f:selectItem itemLabel="egal" itemValue="-" />
                                            </h:selectOneRadio>
                                            <h:message for="riderPrefIsSmoker" styleClass="error"/>
                                            </h:panelGroup*/%>

                                <f:verbatim>&nbsp;</f:verbatim>
                                <t:commandButton value="Speichern" action="#{Profile_Backing.editPreferences}" actionFor="profilePrefsForm" />

                            </h:panelGrid>
                        </t:subform>

                    </t:panelTab>

                    <t:panelTab id="profilePasswordTab" label="Passwort">

                        <t:div rendered="#{Profile_Backing.renderPasswordSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                            <p>Änderungen erfolgreich gespeichert.</p>
                        </t:div>

                        <h3>Ändern Sie Ihr Passwort für den Zugang zu OpenRide:</h3>

                        <t:subform id="profilePasswordForm">
                            <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:panelGroup>
                                    <h:outputLabel for="passwordOld" value="Altes Passwort:" styleClass="requiredField" />
                                    <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                    <h:inputSecret id="passwordOld" binding="#{Profile_Backing.passwordOld}" required="true" validator="#{Profile_Backing.validatePasswordOld}" styleClass="wide" />
                                    <h:message for="passwordOld" styleClass="error"/>
                                </h:panelGroup>

                                <h:panelGroup>
                                    <h:outputLabel for="password" value="Neues Passwort:" styleClass="requiredField" />
                                    <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                    <h:inputSecret id="password" binding="#{Profile_Backing.password}" required="true" styleClass="wide" />
                                    <h:message for="password" styleClass="error"/>
                                </h:panelGroup>

                                <h:panelGroup>
                                    <h:outputLabel for="passwordCheck" value="Neues Passwort (wiederholen):" styleClass="requiredField" />
                                    <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                    <h:inputSecret id="passwordCheck" binding="#{Profile_Backing.passwordCheck}" required="true" styleClass="wide">
                                        <t:validateEqual for="password" message="Die Passwörter stimmen nicht überein." />
                                    </h:inputSecret>
                                    <h:message for="passwordCheck" styleClass="error"/>
                                </h:panelGroup>

                                <f:verbatim>&nbsp;</f:verbatim>
                                <h:panelGroup>
                                    <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                                    <t:commandButton value="Speichern" action="#{Profile_Backing.editPassword}" actionFor="profilePasswordForm" />
                                </h:panelGroup>

                            </h:panelGrid>
                        </t:subform>

                    </t:panelTab>

                </t:panelTabbedPane>

            </t:panelTab>

            <!-- ratings tab -->
            <t:panelTab id="ratingsTab" label="Bewertungen">

                <jsp:include page="/WEB-INF/jspf/profile_info.jsp" />

                <t:panelTabbedPane serverSideTabSwitch="false" style="width: 80%;" activeTabStyleClass="activeInnerTab" activeSubStyleClass="activeInnerTabSub" disabledTabStyleClass="disabledInnerTab" inactiveTabStyleClass="inactiveInnerTab" inactiveSubStyleClass="inactiveInnerTabSub" tabContentStyleClass="innerTabContent">                    

                    <t:panelTab id="ratingsSubmitTab" label="Bewertungen abgeben">

                        <t:subform id="ratingsSubmitForm">                            

                            <t:div rendered="#{Ratings_Backing.renderSubmitSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                                <p>Bewertung(en) erfolgreich gespeichert.</p>
                            </t:div>

                            <t:div rendered="#{Ratings_Backing.renderSubmitErrorMessage}" style="border: 1px solid red; padding: 0 8px; margin: 0 0 10px 0;">
                                <p>Zu negativen Bewertungen bitte immer auch einen Kommentar hinterlassen.</p>
                            </t:div>

                            <h3>Offene Bewertungen:</h3>

                            <p>An dieser Stelle können Sie für Ihre letzten Fahrten Bewertungen an Ihre Fahrer und Mitfahrer vergeben.</p>

                            <t:panelTabbedPane serverSideTabSwitch="false" style="width: 100%;" activeTabStyleClass="activeInnerTab" activeSubStyleClass="activeInnerTabSub" disabledTabStyleClass="disabledInnerTab" inactiveTabStyleClass="inactiveInnerTab" inactiveSubStyleClass="inactiveInnerTabSub" tabContentStyleClass="innerTabContent borderless" >

                                <t:panelTab id="ratingsSubmitAsRiderTab" label="Bewertungen für meine Fahrer (#{Ratings_Backing.unratedRidesAsRiderListSize})">
                                    <t:div rendered="#{!Ratings_Backing.renderUnratedRidesAsRider}">
                                        <p>Sie haben alle Ihre bisherigen Fahrer beẃertet.</p>
                                    </t:div>
                                    <t:dataTable id="unratedRidesAsRiderTable" value="#{Ratings_Backing.unratedRidesAsRiderInfo}" var="ratingInfo" rows="5"  rendered="#{Ratings_Backing.renderUnratedRidesAsRider}">
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Fahrer" style="font-weight: bold;" /></f:facet>
                                            <h:graphicImage url="pictures/profile/#{ratingInfo.custNickname}_#{ratingInfo.custId}_thumb.jpg" alt="#{ratingInfo.custNickname}" width="60" /> <br />
                                            <t:outputText value="#{ratingInfo.custNickname}" />
                                        </t:column>
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Fahrt am" styleClass="nowrap" style="font-weight: bold;" /></f:facet>
                                            <t:outputText value="#{ratingInfo.timestamp}">
                                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                            </t:outputText>
                                        </t:column>
                                        <t:column>
                                            <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                            <h:selectOneRadio value="#{ratingInfo.rating}" validator="#{Ratings_Backing.validateRating}">
                                                <f:selectItem itemLabel="Positiv" itemValue="1" />
                                                <f:selectItem itemLabel="Neutral" itemValue="0" />
                                                <f:selectItem itemLabel="Negativ" itemValue="-1" />
                                                <f:selectItem itemLabel="Später abgeben" itemValue="-2" />
                                            </h:selectOneRadio>
                                            Kommentar:<br />
                                            <h:inputText value="#{ratingInfo.ratingComment}" validator="#{Ratings_Backing.validateRatingComment}" styleClass="wide" maxlength="80" />
                                        </t:column>
                                    </t:dataTable>

                                    <t:dataScroller for="unratedRidesAsRiderTable" paginator="true" paginatorMaxPages="10" paginatorRenderLinkForActive="false" renderFacetsIfSinglePage="false" paginatorColumnStyle="text-align: center;" paginatorActiveColumnStyle="text-align: center; font-weight: bold;" paginatorTableStyle="" immediate="true" style="width: auto; margin: 0 auto;">
                                        <f:facet name="previous">
                                            <t:graphicImage url="img/arrow_left.png" border="1" />
                                        </f:facet>
                                        <f:facet name="next">
                                            <t:graphicImage url="img/arrow_right.png" border="1" />
                                        </f:facet>
                                    </t:dataScroller>
                                </t:panelTab>

                                <t:panelTab id="ratingsSubmitAsDriverTab" label="Bewertungen für meine Mitfahrer (#{Ratings_Backing.unratedRidesAsDriverListSize})">
                                    <t:div rendered="#{!Ratings_Backing.renderUnratedRidesAsDriver}">
                                        <p>Sie haben alle Ihre bisherigen Mitfahrer beẃertet.</p>
                                    </t:div>
                                    <t:dataTable id="unratedRidesAsDriverTable" value="#{Ratings_Backing.unratedRidesAsDriverInfo}" var="ratingInfo" rows="5" rendered="#{Ratings_Backing.renderUnratedRidesAsDriver}">
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Mitfahrer" style="font-weight: bold;" /></f:facet>
                                            <h:graphicImage url="pictures/profile/#{ratingInfo.custNickname}_#{ratingInfo.custId}_thumb.jpg" alt="#{ratingInfo.custNickname}" width="60" /> <br />
                                            <t:outputText value="#{ratingInfo.custNickname}" />
                                        </t:column>
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Fahrt am" style="font-weight: bold;" /></f:facet>
                                            <t:outputText value="#{ratingInfo.timestamp}">
                                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                            </t:outputText>
                                        </t:column>
                                        <t:column>
                                            <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                            <h:selectOneRadio value="#{ratingInfo.rating}" validator="#{Ratings_Backing.validateRating}">
                                                <f:selectItem itemLabel="Positiv" itemValue="1" />
                                                <f:selectItem itemLabel="Neutral" itemValue="0" />
                                                <f:selectItem itemLabel="Negativ" itemValue="-1" />
                                                <f:selectItem itemLabel="Später abgeben" itemValue="-2" />
                                            </h:selectOneRadio>
                                            Kommentar:<br />
                                            <h:inputText value="#{ratingInfo.ratingComment}" validator="#{Ratings_Backing.validateRatingComment}" styleClass="wide" maxlength="80" />
                                        </t:column>
                                    </t:dataTable>

                                    <t:dataScroller for="unratedRidesAsDriverTable" paginator="true" paginatorMaxPages="10" paginatorRenderLinkForActive="false" renderFacetsIfSinglePage="false" paginatorColumnStyle="text-align: center;" paginatorActiveColumnStyle="text-align: center; font-weight: bold;" paginatorTableStyle="" immediate="true" style="width: auto; margin: 0 auto;">
                                        <f:facet name="previous">
                                            <t:graphicImage url="img/arrow_left.png" border="1" />
                                        </f:facet>
                                        <f:facet name="next">
                                            <t:graphicImage url="img/arrow_right.png" border="1" />
                                        </f:facet>
                                    </t:dataScroller>

                                </t:panelTab>
                            </t:panelTabbedPane>

                            <t:div rendered="#{Ratings_Backing.renderUnratedRidesAsRider || Ratings_Backing.renderUnratedRidesAsDriver}">
                                <t:commandButton value="Speichern" action="#{Ratings_Backing.submitRatings}" actionFor="ratingsSubmitForm" />
                            </t:div>

                        </t:subform>

                    </t:panelTab>

                    <t:panelTab id="ratingsViewTab" label="Meine Bewertungen">

                        <h3>Für mich abgegebene Bewertungen:</h3>

                        <p>Bewertungspunkte insgesamt: <h:outputText value="#{Ratings_Backing.ratingsTotal}" /></p>

                        <p>Davon in den letzten 12 Monaten: </p>

                        <table style="width: 70%; margin-left: 10px;">
                            <tr><td width="33%"><img src="img/rated_1.png" /> Positiv:</td><td><h:outputText value="#{Ratings_Backing.ratingsLatestPositive}" /> </td><td><h:outputText value="#{Ratings_Backing.ratingsRatioPercent}" />%</td></tr>
                            <tr><td width="33%"><img src="img/rated_0.png" /> Neutral:</td><td colspan="2"><h:outputText value="#{Ratings_Backing.ratingsLatestNeutral}" /> </td></tr>
                            <tr><td width="33%"><img src="img/rated_-1.png" /> Negativ:</td><td colspan="2"><h:outputText value="#{Ratings_Backing.ratingsLatestNegative}" /> </td></tr>
                        </table>

                        <br />

                        <t:panelTabbedPane serverSideTabSwitch="false" style="width: 100%;" activeTabStyleClass="activeInnerTab" activeSubStyleClass="activeInnerTabSub" disabledTabStyleClass="disabledInnerTab" inactiveTabStyleClass="inactiveInnerTab" inactiveSubStyleClass="inactiveInnerTabSub" tabContentStyleClass="innerTabContent borderless" >

                            <t:panelTab id="ratingsViewReceivedAsRiderTab" label="Bewertungen meiner Fahrer">
                                <t:div rendered="#{!Ratings_Backing.renderReceivedRatingsAsRider}">
                                    <p>Bisher hat kein Fahrer eine Bewertung für Sie abgegeben.</p>
                                </t:div>
                                <t:dataTable id="receivedRatingsAsRider" value="#{Ratings_Backing.receivedRatingsAsRider}" var="ride" rows="5" rendered="#{Ratings_Backing.renderReceivedRatingsAsRider}">
                                    <t:column style="width: 15%;">
                                        <f:facet name="header"><h:outputText value="Fahrer" style="font-weight: bold;" /></f:facet>
                                        <h:graphicImage url="pictures/profile/#{ride.rideId.custId.custNickname}_#{ride.rideId.custId.custId}_thumb.jpg" alt="#{ride.rideId.custId.custNickname}" width="60" /> <br />
                                        <t:outputText value="#{ride.rideId.custId.custNickname}" />
                                    </t:column>
                                    <t:column>
                                        <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                        <img src="img/rated_<t:outputText value="#{ride.receivedrating}" />.png" style="float: left; margin: 6px 6px 6px 0;" />
                                        <%-- // TODO: This should be replaced with Timestamprealized once this is set! --%>
                                        <br /><span style="color: #999;">Fahrt am <t:outputText value="#{ride.starttimeEarliest}">
                                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                            </t:outputText></span>
                                        </t:column>
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Abgegeben am" styleClass="nowrap" style="font-weight: bold;" /></f:facet>
                                            <t:outputText value="#{ride.receivedratingDate}"><f:convertDateTime pattern="dd.MM.yyyy" /></t:outputText>
                                        </t:column>
                                    </t:dataTable>

                                <t:dataScroller for="receivedRatingsAsRider" pageCountVar="pcRRAR" pageIndexVar="piRRAR" paginator="true" paginatorMaxPages="10" paginatorRenderLinkForActive="false" renderFacetsIfSinglePage="false" paginatorColumnStyle="text-align: center;" paginatorActiveColumnStyle="text-align: center; font-weight: bold;" paginatorTableStyle="" immediate="true" style="width: auto; margin: 0 auto;">
                                    <f:facet name="previous">
                                        <t:graphicImage url="img/arrow_left.png" border="1" />
                                    </f:facet>
                                    <f:facet name="next">
                                        <t:graphicImage url="img/arrow_right.png" border="1" />
                                    </f:facet>
                                </t:dataScroller>
                            </t:panelTab>

                            <t:panelTab id="ratingsViewReceivedAsDriverTab" label="Bewertungen meiner Mitfahrer">
                                <t:div rendered="#{!Ratings_Backing.renderReceivedRatingsAsDriver}">
                                    <p>Bisher hat kein Mitfahrer eine Bewertung für Sie abgegeben.</p>
                                </t:div>
                                <t:dataTable id="receivedRatingsAsDriver" value="#{Ratings_Backing.receivedRatingsAsDriver}" var="ride" rows="5" rendered="#{Ratings_Backing.renderReceivedRatingsAsDriver}">
                                    <t:column style="width: 15%;">
                                        <f:facet name="header"><h:outputText value="Mitfahrer" style="font-weight: bold;" /></f:facet>
                                        <h:graphicImage url="pictures/profile/#{ride.custId.custNickname}_#{ride.custId.custId}_thumb.jpg" alt="#{ride.custId.custNickname}" width="60" /> <br />
                                        <t:outputText value="#{ride.custId.custNickname}" />
                                    </t:column>
                                    <t:column>
                                        <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                        <img src="img/rated_<t:outputText value="#{ride.givenrating}" />.png" style="float: left; margin: 6px 6px 6px 0;" />
                                        <t:outputText value="#{ride.givenratingComment}" />
                                        <%-- // TODO: This should be replaced with Timestamprealized once this is set! --%>
                                        <br /><span style="color: #999;">Fahrt am <t:outputText value="#{ride.starttimeEarliest}">
                                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                            </t:outputText></span>
                                        </t:column>
                                        <t:column style="width: 15%;">
                                            <f:facet name="header"><h:outputText value="Abgegeben am" styleClass="nowrap" style="font-weight: bold;" /></f:facet>
                                            <t:outputText value="#{ride.givenratingDate}"><f:convertDateTime pattern="dd.MM.yyyy" /></t:outputText>
                                        </t:column>
                                    </t:dataTable>

                                <t:dataScroller for="receivedRatingsAsDriver" pageCountVar="pcRRAD" pageIndexVar="piRRAD" paginator="true" paginatorMaxPages="10" paginatorRenderLinkForActive="false" renderFacetsIfSinglePage="false" paginatorColumnStyle="text-align: center;" paginatorActiveColumnStyle="text-align: center; font-weight: bold;" paginatorTableStyle="" immediate="true" style="width: auto; margin: 0 auto;">
                                    <f:facet name="previous">
                                        <t:graphicImage url="img/arrow_left.png" border="1" />
                                    </f:facet>
                                    <f:facet name="next">
                                        <t:graphicImage url="img/arrow_right.png" border="1" />
                                    </f:facet>
                                </t:dataScroller>
                            </t:panelTab>

                        </t:panelTabbedPane>

                    </t:panelTab>

                    <%/*
                                <t:panelTab id="ratingsFindOthersTab" label="Andere Personen">

                                <h3>Bewertungen für andere Personen</h3>

                                <p>Hier können Sie die Bewertungen einsehen, die andere OpenRide-Benutzer von ihren Fahrern und Mitfahren erhalten haben.</p>
                                <p>Geben Sie dazu einfach einen Benutzernamen ein und klicken Sie auf "Anzeigen".</p>

                                <t:subform id="ratingsFindOthersForm">

                                <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                <h:panelGroup>
                                <h:outputLabel for="otherUsername" value="Benutzername:" styleClass="requiredField" />
                                <span class="requiredField">(*)</span>
                                </h:panelGroup>
                                <h:panelGroup>
                                <h:inputText id="otherUsername" binding="#{Ratings_Backing.otherUsername}" required="true" styleClass="wide" validator="#{Ratings_Backing.validateUsername}" />
                                <h:message for="otherUsername" styleClass="error" />
                                </h:panelGroup>

                                <f:verbatim>&nbsp;</f:verbatim>
                                <h:panelGroup>
                                <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                                <t:commandButton value="Anzeigen" action="#{Ratings_Backing.findOthersRatings}" actionFor="ratingsFindOthersForm" />
                                </h:panelGroup>

                                </h:panelGrid>

                                <t:div rendered="#{Ratings_Backing.renderOthersRatingsAsRider || Ratings_Backing.renderOthersRatingsAsRider}">
                                <hr />
                                </t:div>

                                <t:div rendered="#{Ratings_Backing.renderOthersRatingsAsRider}">

                                <h3>Bewertungen von Fahrern:</h3>

                                <t:dataTable value="#{Ratings_Backing.othersRatingsAsRider}" var="ride" >
                                <t:column style="width: 15%;">
                                <f:facet name="header"><h:outputText value="Fahrer" style="font-weight: bold;" /></f:facet>
                                <h:graphicImage url="pictures/upload/#{ride.rideId.custId.custNickname}_#{ride.rideId.custId.custId}_thumb.jpg" alt="#{ride.rideId.custId.custNickname}" width="60" /> <br />
                                <t:outputText value="#{ride.rideId.custId.custNickname}" />
                                </t:column>
                                <t:column>
                                <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                <img src="img/rated_<t:outputText value="#{ride.receivedrating}" />.png" style="float: left; margin: 6px 6px 6px 0;" />
                                <t:outputText value="#{ride.receivedratingComment}" />
                                <br /><span style="color: #999;">Fahrt am <t:outputText value="#{ride.timestamprealized}">
                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                </t:outputText></span>
                                </t:column>
                                <t:column style="width: 15%;">
                                <f:facet name="header"><h:outputText value="Abgegeben am" styleClass="nowrap" style="font-weight: bold;" /></f:facet>
                                <t:outputText value="#{ride.receivedratingDate}"><f:convertDateTime pattern="dd.MM.yyyy, HH:mm 'Uhr'" /></t:outputText>
                                </t:column>
                                </t:dataTable>

                                </t:div>

                                <t:div rendered="#{Ratings_Backing.renderOthersRatingsAsDriver}">

                                <h3>Bewertungen von Mitfahrern:</h3>

                                <t:dataTable value="#{Ratings_Backing.othersRatingsAsDriver}" var="ride" >

                                <t:column style="width: 15%;">
                                <f:facet name="header"><h:outputText value="Mitfahrer" style="font-weight: bold;" /></f:facet>
                                <h:graphicImage url="pictures/upload/#{ride.custId.custNickname}_#{ride.custId.custId}_thumb.jpg" alt="#{ride.custId.custNickname}" width="60" /> <br />
                                <t:outputText value="#{ride.custId.custNickname}" />
                                </t:column>
                                <t:column>
                                <f:facet name="header"><h:outputText value="Bewertung" style="font-weight: bold;" /></f:facet>
                                <img src="img/rated_<t:outputText value="#{ride.givenrating}" />.png" style="float: left; margin: 6px 6px 6px 0;" />
                                <t:outputText value="#{ride.givenratingComment}" />
                                <br /><span style="color: #999;">Fahrt am <t:outputText value="#{ride.timestamprealized}">
                                <f:convertDateTime pattern="dd.MM.yyyy" />
                                </t:outputText></span>
                                </t:column>
                                <t:column style="width: 15%;">
                                <f:facet name="header"><h:outputText value="Abgegeben am" styleClass="nowrap" style="font-weight: bold;" /></f:facet>
                                <t:outputText value="#{ride.givenratingDate}"><f:convertDateTime pattern="dd.MM.yyyy, HH:mm 'Uhr'" /></t:outputText>
                                </t:column>
                                </t:dataTable>

                                </t:div>

                                </t:subform>

                                </t:panelTab>
                                 */%>

                </t:panelTabbedPane>

            </t:panelTab>

            <!-- favorites tab -->
            <t:panelTab id="favsTab" label="Favoriten">

                <jsp:include page="/WEB-INF/jspf/profile_info.jsp" />

                <t:panelTabbedPane serverSideTabSwitch="false" style="width: 80%;" activeTabStyleClass="activeInnerTab" activeSubStyleClass="activeInnerTabSub" disabledTabStyleClass="disabledInnerTab" inactiveTabStyleClass="inactiveInnerTab" inactiveSubStyleClass="inactiveInnerTabSub" tabContentStyleClass="innerTabContent">

                    <t:panelTab id="favPointsTab" label="Favorisierte Orte">

                        <t:div rendered="#{FavoritePoints_Backing.renderSuccessMessage}" style="border: 1px solid #c7db86; padding: 0 8px; margin: 0 0 10px 0;">
                            <p>Änderungen erfolgreich gespeichert.</p>
                        </t:div>

                        <script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAAdbsOt58RO04BJLOwuybQyhTCT0k0d1rf0afV5cFJy3ix6t_ZGRQzwAPC1ri6AFQ7AO5hVtlRs8llOQ"></script>
                        <script type="text/javascript">
                            google.load("maps", "2",{"other_params":"sensor=false"});
                            var map = null;
                            var geocoder = null;

                            var initialized = false;                            

                            function initialize() {
                                if (GBrowserIsCompatible()) {
                                    document.getElementById('favPointsAddForm_part2').style.display='block';
                                    document.getElementById('map_canvas').style.display='block';
                                    map = new google.maps.Map2(document.getElementById("map_canvas"));
                                    geocoder = new google.maps.ClientGeocoder();
                                    initialized = true;
                                }
                            }

                            function searchAddress() {
                                
                                if (!initialized)
                                    initialize();

                                if (geocoder) {
                                    if (document.getElementById('favPointsSearchAddress')) {
                                        geocoder.getLocations(document.getElementById('favPointsSearchAddress').value, showAddress);
                                    }
                                    else {
                                        alert ("keine adresse")
                                    }
                                }
                            }

                            function showAddress(response) {
                                map.clearOverlays();
                                if (!response || response.Status.code != 200) {

                                    document.getElementById('favPointsAddForm_part2').style.display='none';

                                    document.getElementById('favPointsAddDisplayName').value = '';
                                    document.getElementById('favPointsAddFullAddress').value = '';
                                    document.getElementById('favPointsAddGeoCoords').value = '';

                                    alert("Die angegebene Adresse konnte leider nicht gefunden werden.");

                                } else {

                                    document.getElementById('favPointsAddForm_part2').style.display='block';
                                    //document.getElementById('map_canvas').style.display='block';

                                    place = response.Placemark[0];
                                    point = new google.maps.LatLng(place.Point.coordinates[1],
                                    place.Point.coordinates[0]);

                                    displayName = (place.address.indexOf(',') > 1) ? place.address.split(',')[0] : place.address;
                                    details = (place.address.indexOf(',') > 1) ? place.address.split(displayName + ', ')[1] : '';

                                    map.setCenter(point, 14);
                                    marker = new google.maps.Marker(point);
                                    map.addOverlay(marker);
                                    marker.openInfoWindowHtml('<b>'+displayName+'</b><br />'+details.replace(',', '<br />'));

                                    document.getElementById('favPointsAddDisplayName').value = displayName;
                                    document.getElementById('favPointsAddFullAddress').value = place.address;
                                    document.getElementById('favPointsAddGeoCoords').value = place.Point.coordinates[1] + "," + place.Point.coordinates[0];

                                }
                            }
                        </script>

                        <h3>Meine gespeicherten Orte:</h3>

                        <t:dataList value="#{FavoritePoints_Backing.favPoints}" var="point" layout="unorderedList">
                            <t:outputText value="#{point.favptDisplayname}" />
                            (<t:commandLink action="#{FavoritePoints_Backing.remove}" immediate="true" value="Löschen?" onclick="return confirm('Diesen Ort wirklich löschen?');">
                                <f:param name="favptId" value="#{point.favptId}" />
                            </t:commandLink>)
                            <br />
                            <span style="color: #999;">
                                <t:outputText value="#{point.favptAddress}" />
                            </span>
                        </t:dataList>


                        <h3>Neuen Ort hinzufügen:</h3>

                        <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                            <h:panelGroup>
                                Adresse suchen:
                                <span class="requiredField">(*)</span>
                            </h:panelGroup>
                            <h:panelGroup>
                                <input type="text" id="favPointsSearchAddress" class="wide" onkeypress="if (event && event.which) /*non-ie*/ { if (event.which==13) { searchAddress(); return false; } } else /*ie*/ { if (event.keyCode==13) { searchAddress(); return false; } }; " />

                            </h:panelGroup>

                            <f:verbatim>&nbsp;</f:verbatim>
                            <h:panelGroup>
                                <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                                <input type="button" value="Suchen" onclick="searchAddress();" />
                            </h:panelGroup>

                        </h:panelGrid>

                        <t:subform id="favPointsAddForm">
                            <div id="favPointsAddForm_part2" style="display: none;">
                                <p>Kartenansicht:</p>

                                <div id="map_canvas" style="height: 250px; margin: 10px 0; border: 1px solid #ddd; display: none;"></div>

                                <h:panelGrid width="100%" columns="2" columnClasses="column33,column66" border="0">

                                    <h:panelGroup>
                                        <t:outputLabel for="favPointsAddDisplayName" value="Diesen Ort speichern als:" styleClass="requiredField" />
                                        <span class="requiredField">(*)</span>
                                    </h:panelGroup>
                                    <h:panelGroup>
                                        <t:inputText id="favPointsAddDisplayName" binding="#{FavoritePoints_Backing.displayName}" styleClass="wide" forceId="true" required="true" validator="#{FavoritePoints_Backing.validateDisplayName}" />
                                        <t:message for="favPointsAddDisplayName" styleClass="error"/>
                                    </h:panelGroup>

                                    <f:verbatim>&nbsp;</f:verbatim>
                                    <h:panelGroup>
                                        <div style="float: right; margin-top: 2px; color: #999;"><span class="requiredField">(*)</span> = Angabe erforderlich</div>
                                        <t:commandButton value="Speichern" action="#{FavoritePoints_Backing.add}" actionFor="favPointsAddForm" />
                                    </h:panelGroup>

                                </h:panelGrid>

                            </div>

                            <t:inputHidden id="favPointsAddFullAddress" binding="#{FavoritePoints_Backing.address}" forceId="true" />

                            <t:inputHidden id="favPointsAddGeoCoords" binding="#{FavoritePoints_Backing.geoCoords}" forceId="true" />

                        </t:subform>

                    </t:panelTab>

                    <%/*
                                <t:panelTab id="favPeopleTab" label="Personen">
                                ...
                                </t:panelTab>
                                 */%>

                </t:panelTabbedPane>

            </t:panelTab>

        </t:panelTabbedPane>

    </h:form>



    <script type="text/javascript">
        document.getElementById("homelink").className = 'active';
    </script>

    <script type="text/javascript" src="js/mobile_demo.js"></script>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
