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

package de.fhg.fokus.openride.services.favoritepoint.helperclasses;

/**
 *
 * @author tku
 */
public class FavoritePointResponse {

    private int favptId;
    private String favptAddress;
    private String favptGeoCoords;
    private String favptDisplayName;

    public String getFavptAddress() {
        return favptAddress;
    }

    public void setFavptAddress(String favptAddress) {
        this.favptAddress = favptAddress;
    }

    public int getFavptId() {
        return favptId;
    }

    public void setFavptId(int favptId) {
        this.favptId = favptId;
    }

    public String getFavptDisplayName() {
        return favptDisplayName;
    }

    public void setFavptDisplayName(String favptDisplayName) {
        this.favptDisplayName = favptDisplayName;
    }

    public String getFavptGeoCoords() {
        return favptGeoCoords;
    }

    public void setFavptGeoCoords(String favptGeoCoords) {
        this.favptGeoCoords = favptGeoCoords;
    }
    
    

}
