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

package de.fhg.fokus.openride.services.rider.search.helperclasses;

/**
 *
 * @author pab
 */
public class Search {
    private int riderRouteId;
    private int rideId;
    private Double ridestartPtLat;
    private Double ridestartPtLon;
    private Double rideendPtLat;
    private Double rideendPtLon;
    private long ridestartTimeLatest;
    private String rideComment;
    private int maxwaitingtime;
    private int searchedSeatsNo;
    private boolean savetemplate;
    private long ridestartTimeEarliest;
    private Double price;
    private String startptAddress;
    private String endptAddress;
    private Boolean updated;

    public Search() {
    }
    @Deprecated
    public Search(
            Double ridestartPtLat,
            Double ridestartPtLon,
            Double rideendPtLat,
            Double rideendPtLon,
            long ridestartTimeLatest,
            String rideComment,
            int maxwaitingtime,
            int searchedSeatsNo,
            boolean savetemplate,
            long ridestartTimeEarliest,
            Double price,
            String startptAddress,
            String endptAddress) {
        this.ridestartPtLat = ridestartPtLat;
        this.ridestartPtLon = ridestartPtLon;
        this.rideendPtLat = rideendPtLat;
        this.rideendPtLon = rideendPtLon;
        this.ridestartTimeLatest = ridestartTimeLatest;
        this.rideComment = rideComment;
        this.maxwaitingtime = maxwaitingtime;
        this.searchedSeatsNo = searchedSeatsNo;
        this.savetemplate = savetemplate;
        this.ridestartTimeEarliest = ridestartTimeEarliest;
        this.price = price;
        this.startptAddress = startptAddress;
        this.endptAddress = endptAddress;
    }

    public String getEndptAddress() {
        return endptAddress;
    }

    public String getStartptAddress() {
        return startptAddress;
    }

    public Search(
            int riderRouteId,
            int rideId,
            Double ridestartPtLat,
            Double ridestartPtLon,
            Double rideendPtLat,
            Double rideendPtLon,
            long ridestartTimeLatest,
            String rideComment,
            int maxwaitingtime,
            int searchedSeatsNo,
            boolean savetemplate,
            long ridestartTimeEarliest,
            Double price,
            String startptAddress,
            String endptAddress) {
        this.riderRouteId = riderRouteId;
        this.rideId = rideId;
        this.ridestartPtLat = ridestartPtLat;
        this.ridestartPtLon = ridestartPtLon;
        this.rideendPtLat = rideendPtLat;
        this.rideendPtLon = rideendPtLon;
        this.ridestartTimeLatest = ridestartTimeLatest;
        this.rideComment = rideComment;
        this.maxwaitingtime = maxwaitingtime;
        this.searchedSeatsNo = searchedSeatsNo;
        this.savetemplate = savetemplate;
        this.ridestartTimeEarliest = ridestartTimeEarliest;
        this.price = price;
        this.startptAddress = startptAddress;
        this.endptAddress = endptAddress;
    }


    public Double getPrice() {
        return price;
    }

    public long getRidestartTimeEarliest() {
        return ridestartTimeEarliest;
    }

    public int getMaxwaitingtime() {
        return maxwaitingtime;
    }

    public String getRideComment() {
        return rideComment;
    }

    public Double getRideendPtLat() {
        return rideendPtLat;
    }

    public Double getRideendPtLon() {
        return rideendPtLon;
    }

    public Double getRidestartPtLat() {
        return ridestartPtLat;
    }

    public Double getRidestartPtLon() {
        return ridestartPtLon;
    }

    public long getRidestartTimeLatest() {
        return ridestartTimeLatest;
    }

    public boolean isSavetemplate() {
        return savetemplate;
    }

    public int getSearchedSeatsNo() {
        return searchedSeatsNo;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getRiderRouteId() {
        return riderRouteId;
    }

    public void setRiderRouteId(int riderRouteId) {
        this.riderRouteId = riderRouteId;
    }

    public void setEndptAddress(String endptAddress) {
        this.endptAddress = endptAddress;
    }

    public void setMaxwaitingtime(int maxwaitingtime) {
        this.maxwaitingtime = maxwaitingtime;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRideComment(String rideComment) {
        this.rideComment = rideComment;
    }

    public void setRideendPtLat(Double rideendPtLat) {
        this.rideendPtLat = rideendPtLat;
    }

    public void setRideendPtLon(Double rideendPtLon) {
        this.rideendPtLon = rideendPtLon;
    }

    public void setRidestartPtLat(Double ridestartPtLat) {
        this.ridestartPtLat = ridestartPtLat;
    }

    public void setRidestartPtLon(Double ridestartPtLon) {
        this.ridestartPtLon = ridestartPtLon;
    }

    public void setRidestartTimeEarliest(long ridestartTimeEarliest) {
        this.ridestartTimeEarliest = ridestartTimeEarliest;
    }

    public void setRidestartTimeLatest(long ridestartTimeLatest) {
        this.ridestartTimeLatest = ridestartTimeLatest;
    }

    public void setSavetemplate(boolean savetemplate) {
        this.savetemplate = savetemplate;
    }

    public void setSearchedSeatsNo(int searchedSeatsNo) {
        this.searchedSeatsNo = searchedSeatsNo;
    }

    public void setStartptAddress(String startptAddress) {
        this.startptAddress = startptAddress;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }


}
