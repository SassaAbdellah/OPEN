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


<!-- TODO: include here a simplistic, unprotected, project ignorant header for CSS and the like -->

<h1 style="text-align: right; margin: 15px 10px;"><img src="/OpenRideServer-RS/img/logo.png" alt="OpenRide" /></h1>

<h3>Haftungsausschluss und Datenschutzerklärung</h3>

<h3>Haftungsausschluss</h3>
    <p>Die Betreiber dieser Webpräsenz übernehmen keine Haftung für die Inhalte externer Links. 
       Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich. 
    </p>

    <p> 
     Geschützte Marken und Namen, Bilder und Texte werden auf unseren Seiten in der Regel nicht als solche kenntlich gemacht. 
     Das Fehlen einer solchen Kennzeichnung bedeutet jedoch nicht, dass es sich um einen freien Namen, 
     ein freies Bild oder einen freien Text im Sinne des Markenzeichenrechts handelt. 
    </p>

    <p>
     Wir weisen darauf hin, dass wir das über diese Website angebotene Webangebot für die Vermittlung von Fahrten nur im Rahmen eines Softwaretests 
     anbieten – der Softwaretest ist nicht für das Vermitteln von echten Fahrten gedacht
      – deswegen können wir für zustande kommende Fahrten keine Haftung übernehmen.
    </p>
    <p>
      Alle Informationen, Daten, Bilder, Nachrichten und andere Arten von Materialien, im Folgenden als „Inhalte“ bezeichnet, 
      die durch OpenRide-Nutzer auf dem OpenRide-Webangebot eingestellt werden, unterliegen der alleinigen Verantwortung des Nutzers, 
      von welchem diese Inhalte stammen. Die Betreiber dieser Website übernehmen keine Haftung für die Aktualität, 
      die inhaltliche Richtigkeit sowie für die Vollständigkeit der in ihrem Webangebot eingestellten Informationen. 
      Dies bezieht sich auf eventuelle Schäden materieller oder ideeller Art Dritter, 
      die durch die Nutzung dieses Webangebotes verursacht wurden. 
      Die gesetzliche Haftung bei Vorsatz und grober Fahrlässigkeit ist davon nicht betroffen. 
   </p>


   <h3>Datenschutzerklärung</h3>
    <p>
    <strong>Im Rahmen der Softwaretests findet keine Vermittlung von realen Fahrten statt. Alle Daten sind fiktiv. 
    Authentische persönliche Daten bei der Registrierung sind für den Softwaretest nicht erforderlich. </strong>
    </p>
    <p>
     Wir behalten uns vor, Testnutzer regelmäßig und gegebenenfalls ohne Vorwarnung aus der OpenRide-Datenbank zu entfernen. 
     Alle erhobenen Daten werden entsprechend den Vorschriften des Bundesdatenschutzgesetzes (BDSG) und des Informations- 
     und Telekommunikationsdienstegesetzes (IuTDG) behandelt. 
    </p>
    <p>
     Eine Weitergabe Ihrer persönlichen Daten an Dritte erfolgt nicht. 
     Auf Wunsch erteilen wir Ihnen unentgeltlich Auskunft über Ihre bei uns gespeicherten personenbezogenen Daten. 
     Sie können bei uns jederzeit die Berichtigung, Löschung und Sperrung Ihrer bei uns gespeicherten personenbezogenen Daten einfordern. 
     Die Server werden im Rechenzentrum der xeneris.media an verschiedenen Standorten in Deutschland betrieben. 
     Die Webseiten werden über die Domain openride.xeneris.net bereitgestellt. 
    </p>
    <p>
     Im Folgenden wird dargestellt, welche personenbezogenen Daten erhoben bzw. gespeichert und weiterverarbeitet werden. 
     Dies erfolgt ausschließlich bei der Verwendung der Applikation, zum einen serverseitig, und zum anderen (in geringerem Umfang) clientseitig in Cookies. 
     Grundsätzlich werden zwei Bereiche unterschieden: 
     </p>
     <ol>
        <li>Informationsseite inkl. Registrierung</li>
        <li>Webbrowserapplikation für Desktop und mobile Endgeräte </li>
     </ol>
    <p>
      <strong>Bei Registrierung bzw. Profilpflege: </strong> 
        Benutzername, 
        Passwort, 
        Vorname, 
        Nachname, 
        Geschlecht, 
        Präferenzen Raucher/Nichtraucher, 
        Geburtsdatum (optional), 
        Straße (optional), 
        Hausnummer (optional), 
        PLZ (optional), 
        Ort (optional), 
        E-Mail-Adresse (nur ZU-Emailadressen werden zugelassen), 
        Mobilfunknummer, 
        KFZ-Kennzeichen, 
        KFZ-Farbe, 
        KFZ-Modellbeschreibung, 
        Profilbild. 
      </p>
      <p> 
        Diese Informationen sind nur für den Nutzer selbst sichtbar. Die im Falle einer Fahrtsuche, 
        eines Fahrtangebots oder einer Fahrtvermittlung an die andere Seite 
        (d.h. an den Fahrer bzw. Mitfahrer) übermittelten Daten sind unten angeführt. 
      </p>
      <p>
        <strong>Bei einem Fahrtgesuch: </strong>
        Startpunkt (autom. Lokalisiert bzw. manuell eingegeben), 
        Zielpunkt, 
        Abfahrtsdatum, 
        Abfahrtszeit, 
        max. Wartebereitschaft, 
        Anzahl der Personen. 
       Diese Informationen sind nur für den Nutzer selbst sichtbar. 
       Die bei einem passenden Angebot an den potentiellen Fahrer übermittelten Daten sind unter „Bei einer Fahrtvermittlung“ aufgeführt. 
      </p>

      <p>
       <strong>Bei einem Fahrtangebot: </strong>
       Startpunkt (autom. Lokalisiert bzw. manuell eingegeben),
       Zielpunkt,  
       Abfahrtsdatum, 
       Abfahrtszeit, 
       max. Umwegbereitschaft, 
       Anzahl der freien Plätze. 
       Diese Informationen sind nur für den Nutzer selbst sichtbar. 
       Die bei einem passenden Gesuch an den potentiellen Mitfahrer übermittelten Daten sind unter „Bei einer Fahrtvermittlung“ aufgeführt. 
      </p>
      <p>
        <strong>Bei einer Fahrtvermittlung: </strong>
        Zuordnung Fahrtangebot/Fahrtgesuch inkl. Zuordnung zu Fahrer und Mitfahrer, 
        für Fahrer und Mitfahrer jeweils 3-Punkte-Bewertung inkl. Freitextkommentar. 
        Diese Informationen werden innerhalb des Systems zugeordnet und werden nur intern weiterverarbeitet. 
        Für den Fahrer sind folgende Mitfahrerinformationen sichtbar: 
        Benutzername, Profilfoto, Mobilfunknummer (erst nach erfolgter Vermittlung mit Bestätigung durch beide Seiten, d.h. sowohl Fahrer als auch Mitfahrer), 
        Start- und Zielpunkt und bisherige Bewertungen des Mitfahrers. 
        Für den Mitfahrer sind folgende Fahrerinformationen sichtbar: 
        Benutzername, Profilfoto, Handynummer (erst nach erfolgter Vermittlung mit Bestätigung durch beide Seiten, d.h. sowohl Fahrer als auch Mitfahrer), 
        bisherige Bewertungen des Fahrers, KFZ-Farbe, KFZ-Kennzeichen, KFZ-Modellbeschreibung 
        (alle KFZ-Angaben erst nach erfolgter Vermittlung mit Bestätigung durch beide Seiten, d.h. sowohl Fahrer als auch Mitfahrer). 
      </p>

      <p>
        Wir behalten uns vor, die übermittelten Nutzerinformationen intern in anonymisierter Form auszuwerten. 
      </p>



    <p>
     <a href="./">&laquo; Zurück zur Startseite</a>
    </p>

    

<!-- TODO: include here a simplistic, unprotected, project ignorant footer (powered by openride or the like) -->

