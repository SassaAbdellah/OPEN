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
    
    <jsp:include page="/WEB-INF/jspf/header.jsp"><jsp:param name="section" value="faq" /></jsp:include>

    <h1>FAQ - H&auml;ufig gestellte Fragen</h1>

    <p>
    An dieser Stelle geben wir Antworten auf h&auml;ufig gestellte Fragen zur OpenRide-Pilotierung an der Zeppelin University. 
    </p>
    
    <p>
    <a href="#1">Wie funktioniert OpenRide?</a><br /><br />
    <a href="#5">Auf welchen mobilen Endgr&auml;ten funktioniert OpenRide? </a><br /><br />
    <a href="#7">Wo finde ich eine detaillierte Anleitung?</a><br /><br />
    <a href="#10">In welchem Verh&auml;ltnis stehen &ouml;ffentlicher Personennahverkehr und OpenRide zueinander?</a><br /><br />
    <a href="#11">Wer haftet bei einem Unfall?</a><br /><br />
    </p>
    
    <h2><a name="1">Wie funktioniert OpenRide?</a></h2>
    
    <p>
    Im Unterschied zu etablierten L&ouml;sungen f&uuml;r Mitfahrgelegenheiten vermittelt OpenRide Fahrten spontan von unterwegs und auch f&uuml;r kurze Fahrstrecken, etwa f&uuml;r die morgendliche Fahrt zur Universit&auml;t oder zwischen verschiedenen Universit&auml;tsgeb&auml;uden. 
    </p>
    
    <p>
    Autofahrer und Mitfahrer &uuml;bermitteln ihr Fahrtangebot oder ihren Mitfahrwunsch direkt &uuml;ber das Mobiltelefon. In Sekundenbruchteilen findet die OpenRide-Suchmaschine passende Fahrer und Mitfahrer. Dabei vergleicht OpenRide Wegstrecken, Zeiten und pers&ouml;nliche Vorlieben. Das funktioniert &uuml;brigens auch spontan von unterwegs und f&uuml;r bereits begonnene Fahrten.
    </p>
    
    <p>
    Fahrer und Mitfahrer erhalten sofort Informationen zu ihrem Gegen&uuml;ber. Stimmen beide
    zu, erh&auml;lt der Fahrer die angepasste Route mit dem Standort des Mitfahrers.
    Ihr entscheidet, wen Ihr mitnehmt oder bei wem Ihr mitfahren wollt: Noch nie waren Mitfahrgelegenheiten schneller, einfacher und dynamischer!
    </p>
    
    <h2><a name="5">Auf welchen mobilen Endgr&auml;ten funktioniert OpenRide? </a></h2>
    
    <p>
    Auf dem Mobiltelefon ist die Anwendung &uuml;ber den mobilen Webbrowser direkt unter www.open-ride.mobi erreichbar. 
    </p>
    
    <p>
    F&uuml;r die vollfunktionale Nutzung von OpenRide empfehlen wir Mobiltelefone mit einem
    neueren Webbrowser. Dazu geh&ouml;ren z.B. das IPhone 3G, Android-Mobiltelefone
    ab Android 2.1. und Windows Mobile ab Version 6.5. Auf Nokia-Smartphones kann man OpenRide nutzen, wenn man den Opera Mobile-Webbrowser installiert. 
    </p>
    
    <p>
    Wer kein modernes Smartphone hat, kann OpenRide mit einem normalen Computer
    im OpenRide-Smartphone-Simulator nutzen. Dazu geht Ihr mit dem Firefox-Webbrowser auf
    www.open-ride.mobi/OpenRideWeb, loggt Euch ein und nutzt den Link Smartphone-
    Simulator. 
    </p>

    <h2><a name="7">Wo finde ich eine detaillierte Anleitung?</a></h2>
    
    <p>
    Eine ausf&uuml;hrliche Anleitung findet Ihr nach dem <a href="https://www.open-ride.mobi/OpenRideWeb/">Login</a> 
    in Eurem pers&ouml;nlichen Bereich.
    </p>
    
    <h2><a name="10">In welchem Verh&auml;ltnis stehen &ouml;ffentlicher Personennahverkehr und OpenRide zueinander?</a></h2>
    <p>
    Wir sehen den &ouml;ffentlichen Personennahverkehr (&ouml;PNV) und OpenRide als komplement&auml;r an. Mitfahrgelegenheiten im Nahverkehr sind eher eine Erg&auml;nzung des Mobilit&auml;tsangebots, die gerade dort entstehen werden, wo der &ouml;PNV L&uuml;cken in der Versorgung aufweist. Gerade im l&auml;ndlichen Raum, wo es schwer ist, eine fl&auml;chendeckende Versorgung aufrecht zu erhalten, kann OpenRide helfen, Mobilit&auml;tsprobleme zu lindern. Ein Beispiel w&auml;ren gemeinsame Fahrten von Pendlern zu den Park&Ride Stationen, also in Kombination mit der Bahn. 
    </p>
    
    <h2><a name="11">Wer haftet bei einem Unfall?</a></h2>
    
    <p>
    Die Bedenken vieler Autofahrer bei der Mitnahme Fremder im eigenen Fahrzeug bez&uuml;glich der haftungsrechtlichen Folgen im Falle eines Unfalls sind weitgehend unbegr&uuml;ndet. Bei unverschuldeten Unf&auml;llen greifen die Kfz-Haftpflichtversicherung sowie die gesetzliche Unfallversicherung. Sachsch&auml;den und Schmerzensgeld werden &uuml;ber die Kfz-Haftplichtversicherung abgewickelt, bei Personensch&auml;den, insbesondere wenn sie auf direktem Weg zum oder vom Arbeitsplatz erfolgen, greift die gesetzliche Unfallversicherung. 
    </p>
    
    <p>
    Liegt die Schuld beim Unfallgegener, so muss dessen Kfz-Haftplichtversicherung auch f&uuml;r Sch&auml;den der Mitfahrer aufkommen. Dies gilt auch dann, wenn der Fahrer unverschuldet einen Unfall verursacht hat (z.B. nach einem Reifenplatzer oder Herzinfarkt). Gegen Anspr&uuml;che der Mitfahrer bei Sch&auml;den, die die eigene Haftplichtversicherung des Fahrers nicht oder nur zum Teil abdeckt, kann sich der Fahrer mit einer Erkl&auml;rung zur Haftungsbeschr&auml;nkung absichern. Derartige Haftungsbeschr&auml;nkungen bieten z.B. die Automobilclubs wie der ADAC zum Download an.  
    </p>

    <jsp:include page="/WEB-INF/jspf/footer.jsp"></jsp:include>
</f:view>
