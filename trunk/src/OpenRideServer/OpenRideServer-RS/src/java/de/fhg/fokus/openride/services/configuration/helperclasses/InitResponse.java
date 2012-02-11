/*
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
*/

package de.fhg.fokus.openride.services.configuration.helperclasses;

/**
 *
 * @author pab, tku
 */
public class InitResponse {

    private String nickname;    
    private int openoffers;
    private int opensearches;
    private int updatedoffers;
    private int updatedsearches;
    private int openratings;
    private int newratings;
    private String profilpic;

    public InitResponse() {
    }

    public InitResponse(String nickname, int openoffers, int opensearches, int updatedoffers, int updatedsearches, int openratings, int newratings, String profilpic) {
        this.nickname = nickname;
        this.openoffers = openoffers;
        this.opensearches = opensearches;
        this.updatedoffers = updatedoffers;
        this.updatedsearches = updatedsearches;
        this.openratings = openratings;
        this.newratings = newratings;
        this.profilpic = profilpic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOpenoffers() {
        return openoffers;
    }

    public void setOpenoffers(int openoffers) {
        this.openoffers = openoffers;
    }

    public int getOpenratings() {
        return openratings;
    }

    public void setOpenratings(int openratings) {
        this.openratings = openratings;
    }

    public int getOpensearches() {
        return opensearches;
    }

    public void setOpensearches(int opensearches) {
        this.opensearches = opensearches;
    }

    public String getProfilpic() {
        return profilpic;
    }

    public void setProfilpic(String profilpic) {
        this.profilpic = profilpic;
    }

    public int getRatings() {
        return newratings;
    }

    public void setRatings(int newratings) {
        this.newratings = newratings;
    }

    public int getUpdatedoffers() {
        return updatedoffers;
    }

    public void setUpdatedoffers(int updatedoffers) {
        this.updatedoffers = updatedoffers;
    }

    public int getUpdatedsearches() {
        return updatedsearches;
    }

    public void setUpdatedsearches(int updatedsearches) {
        this.updatedsearches = updatedsearches;
    }

    
}
