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

package de.fhg.fokus.openride.services.driver.offer.helperclasses;

/**
 *
 * @author pab
 */
public class MatchResponse {

    private Integer driverState;
    private Integer riderState;
    private Double matchSharedDistanceMeters;
    private Double matchDetourMeters;
    private long matchExpectedStartTime;
    private Integer rideid;
    private Integer riderRouteId;
    private Integer riderCustId;
    private String riderNickname;
    private Integer riderRatingsRatioPercent;
    private String riderMobilePhoneNo;
    private Integer matchPriceCents;
    private String startPtAddress;
    private String endPtAddress;
    private Double startPtLat;
    private Double startPtLon;
    private Double endPtLat;
    private Double endPtLon;
    private int seatsAvailable;
    private Integer noOfPassengers;

    public MatchResponse(Integer driverState, Integer riderState, Double matchSharedDistanceMeters, Double matchDetourMeters, long matchExpectedStartTime, Integer rideid, Integer riderRouteId, Integer riderCustId, String riderNickname, Integer riderRatingsRatioPercent, Integer matchPriceCents, String startPtAddress, String endPtAddress) {
        this.driverState = driverState;
        this.riderState = riderState;
        this.matchSharedDistanceMeters = matchSharedDistanceMeters;
        this.matchDetourMeters = matchDetourMeters;
        this.matchExpectedStartTime = matchExpectedStartTime;
        this.rideid = rideid;
        this.riderRouteId = riderRouteId;
        this.riderCustId = riderCustId;
        this.riderNickname = riderNickname;
        this.riderRatingsRatioPercent = riderRatingsRatioPercent;
        this.matchPriceCents = matchPriceCents;
        this.startPtAddress = startPtAddress;
        this.endPtAddress = endPtAddress;
    }

    public MatchResponse(Integer driverState, Integer riderState, Double matchSharedDistanceMeters, Double matchDetourMeters, long matchExpectedStartTime, Integer rideid, Integer riderRouteId, Integer riderCustId, String riderNickname, Integer riderRatingsRatioPercent, Integer matchPriceCents, String startPtAddress, String endPtAddress, Double startPtLatitude, Double startPtLongitude, Double endPtLatitude, Double endPtLongitude, Integer noOfPassengers) {//, int seatsAvailable) {
        this.driverState = driverState;
        this.riderState = riderState;
        this.matchSharedDistanceMeters = matchSharedDistanceMeters;
        this.matchDetourMeters = matchDetourMeters;
        this.matchExpectedStartTime = matchExpectedStartTime;
        this.rideid = rideid;
        this.riderRouteId = riderRouteId;
        this.riderCustId = riderCustId;
        this.riderNickname = riderNickname;
        this.riderRatingsRatioPercent = riderRatingsRatioPercent;
        this.matchPriceCents = matchPriceCents;
        this.startPtAddress = startPtAddress;
        this.endPtAddress = endPtAddress;
        this.startPtLat = startPtLatitude;
        this.startPtLon = startPtLongitude;
        this.endPtLat = endPtLatitude;
        this.endPtLon = endPtLongitude;
        this.noOfPassengers = noOfPassengers;
//                this.seatsAvailable = seatsAvailable;
    }

    public Integer getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(Integer noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Integer getRideid() {
        return rideid;
    }

    public Integer getDriverState() {
        return driverState;
    }

    public void setDriverState(Integer driverState) {
        this.driverState = driverState;
    }

    public Double getMatchDetourMeters() {
        return matchDetourMeters;
    }

    public void setMatchDetourMeters(Double matchDetourMeters) {
        this.matchDetourMeters = matchDetourMeters;
    }

    public long getMatchExpectedStartTime() {
        return matchExpectedStartTime;
    }

    public void setMatchExpectedStartTime(long matchExpectedStartTime) {
        this.matchExpectedStartTime = matchExpectedStartTime;
    }

    public Double getMatchSharedDistancEmeters() {
        return matchSharedDistanceMeters;
    }

    public void setMatchSharedDistancEmeters(Double matchSharedDistancEmeters) {
        this.matchSharedDistanceMeters = matchSharedDistancEmeters;
    }

    public Integer getRiderState() {
        return riderState;
    }

    public void setRiderState(Integer riderState) {
        this.riderState = riderState;
    }

    public int getRiderrouteid() {
        return riderRouteId;
    }

    public void setRiderrouteid(int riderrouteid) {
        this.riderRouteId = riderrouteid;
    }

    public String getEndPtAddress() {
        return endPtAddress;
    }

    public Double getEndPtLat() {
        return endPtLat;
    }

    public Double getEndPtLon() {
        return endPtLon;
    }

    public void setEndPtLat(Double lat) {
        endPtLat = lat;
    }

    public void setEndPtLon(Double lon) {
        endPtLon = lon;
    }

    public Double getStartPtLat() {
        return startPtLat;
    }

    public Double getStartPtLon() {
        return startPtLon;
    }

    public void setStartPtLat(Double lat) {
        startPtLat = lat;
    }

    public void setStartPtLon(Double lon) {
        startPtLon = lon;
    }

    public void setEndPtAddress(String endPtAddress) {
        this.endPtAddress = endPtAddress;
    }

    public Integer getMatchPriceCents() {
        return matchPriceCents;
    }

    public void setMatchPriceCents(Integer matchPriceCents) {
        this.matchPriceCents = matchPriceCents;
    }

    public Double getMatchSharedDistanceMeters() {
        return matchSharedDistanceMeters;
    }

    public void setMatchSharedDistanceMeters(Double matchSharedDistanceMeters) {
        this.matchSharedDistanceMeters = matchSharedDistanceMeters;
    }

    public void setRideid(Integer rideid) {
        this.rideid = rideid;
    }

    public Integer getRiderCustId() {
        return riderCustId;
    }

    public void setRiderCustId(Integer riderCustId) {
        this.riderCustId = riderCustId;
    }

    public String getRiderNickname() {
        return riderNickname;
    }

    public void setRiderNickname(String riderNickname) {
        this.riderNickname = riderNickname;
    }

    public Integer getRiderRatingsRatioPercent() {
        return riderRatingsRatioPercent;
    }

    public void setRiderRatingsRatioPercent(Integer riderRatingsRatioPercent) {
        this.riderRatingsRatioPercent = riderRatingsRatioPercent;
    }

    public Integer getRiderRouteId() {
        return riderRouteId;
    }

    public void setRiderRouteId(Integer riderRouteId) {
        this.riderRouteId = riderRouteId;
    }

    public String getStartPtAddress() {
        return startPtAddress;
    }

    public void setStartPtAddress(String startPtAddress) {
        this.startPtAddress = startPtAddress;
    }

    public String getRiderMobilePhoneNo() {
        return riderMobilePhoneNo;
    }

    public void setRiderMobilePhoneNo(String riderMobilePhoneNo) {
        this.riderMobilePhoneNo = riderMobilePhoneNo;
    }
}
