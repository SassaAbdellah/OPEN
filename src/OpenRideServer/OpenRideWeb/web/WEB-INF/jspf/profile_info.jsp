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

<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<div style="float: left; width: 131px;">
    <t:subform id="profileInfoForm">
        <div style="text-align: center; margin-right: 6px; padding-bottom: 0px; border-bottom: 0px solid #ddd;"><a href="profile.jsf"><h:graphicImage url="#{Profile_Backing.pictureBasename}.jpg" alt="#{UserBean.username}" width="125" height="125" /></a>
                <br /><a href="profile.jsf"><strong><h:outputText value="#{UserBean.username}" /></strong></a> (<h:outputText value="#{Ratings_Backing.ratingsTotal}" />)</div>
        
        <p><h:outputText value="#{Ratings_Backing.ratingsRatioPercent}" />% positiv bewertet<br />(letzte 12 Monate)</p>
    </t:subform>
    <p style="background-color: #fff; padding-top: 1em; border-top: 1px solid #ddd; width: 125px; text-align: center;">Nutzen Sie OpenRide auch ohne passendes Smartphone mit dem
        <br /><a href="javascript:showMobileDemo('<h:outputText value="#{UserBean.username}" />');">Smartphone-Simulator<br /><img src="img/htc_hero_icon.jpg" /></a></p>
</div>