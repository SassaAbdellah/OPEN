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

package de.fhg.fokus.openride.services.rating.helperclasses;

import java.util.Date;

/**
 *
 * @author tku
 */
public class OpenRatingResponse implements Comparable<OpenRatingResponse> {

    private Integer riderRouteId;
    private Integer custId;
    private String custNickname;
    private Character custGender;
    private Character custRole;
    private Long timestamprealized;

    public Character getCustGender() {
        return custGender;
    }

    public void setCustGender(Character custGender) {
        this.custGender = custGender;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustNickname() {
        return custNickname;
    }

    public void setCustNickname(String custNickname) {
        this.custNickname = custNickname;
    }

    public Character getCustRole() {
        return custRole;
    }

    public void setCustRole(Character custRole) {
        this.custRole = custRole;
    }

    public Integer getRiderRouteId() {
        return riderRouteId;
    }

    public void setRiderRouteId(Integer riderRouteId) {
        this.riderRouteId = riderRouteId;
    }

    public Long getTimestamprealized() {
        return timestamprealized;
    }

    public void setTimestamprealized(Long timestamprealized) {
        this.timestamprealized = timestamprealized;
    }

    public int compareTo(OpenRatingResponse o) {
        return this.timestamprealized.compareTo(o.timestamprealized);
    }

}
