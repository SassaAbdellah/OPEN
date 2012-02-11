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
public class MatchResponse { 
    private Integer driverState;
    private Integer riderState;
    private long matchExpectedStartTime;
    private Integer rideid;
    private Integer riderRouteId;
    private Integer driverCustId;
    private String driverNickname;
    private Integer driverRatingsRatioPercent;
    private String driverMobilePhoneNo;
    private String driverCarColour;
    private String driverCarBrand;
    private Short driverCarBuildYear;
    private String driverCarPlateNo;
    private Integer matchPriceCents;
    private String startPtAddress;
    private String endPtAddress;

    public MatchResponse(Integer driverState, Integer riderState, long matchExpectedStartTime, Integer rideid, Integer riderRouteId, Integer driverCustId, String driverNickname, Integer driverRatingsRatioPercent, Integer matchPriceCents, String startPtAddress, String endPtAddress) {
        this.driverState = driverState;
        this.riderState = riderState;
        this.matchExpectedStartTime = matchExpectedStartTime;
        this.rideid = rideid;
        this.riderRouteId = riderRouteId;
        this.driverCustId = driverCustId;
        this.driverNickname = driverNickname;
        this.driverRatingsRatioPercent = driverRatingsRatioPercent;        
        this.matchPriceCents = matchPriceCents;
        this.startPtAddress = startPtAddress;
        this.endPtAddress = endPtAddress;
    }

    public String getEndPtAddress() {
        return endPtAddress;
    }

    public void setEndPtAddress(String endPtAddress) {
        this.endPtAddress = endPtAddress;
    }

    public Integer getDriverState() {
        return driverState;
    }

    public void setDriverState(Integer driverState) {
        this.driverState = driverState;
    }

    public long getMatchExpectedStartTime() {
        return matchExpectedStartTime;
    }

    public void setMatchExpectedStartTime(long matchExpectedStartTime) {
        this.matchExpectedStartTime = matchExpectedStartTime;
    }

    public int getRideid() {
        return rideid;
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

    public Integer getDriverCustId() {
        return driverCustId;
    }

    public void setDriverCustId(Integer driverCustId) {
        this.driverCustId = driverCustId;
    }

    public String getDriverNickname() {
        return driverNickname;
    }

    public void setDriverNickname(String driverNickname) {
        this.driverNickname = driverNickname;
    }

    public Integer getDriverRatingsRatioPercent() {
        return driverRatingsRatioPercent;
    }

    public void setDriverRatingsRatioPercent(Integer driverRatingsRatioPercent) {
        this.driverRatingsRatioPercent = driverRatingsRatioPercent;
    }

    public Integer getMatchPriceCents() {
        return matchPriceCents;
    }

    public void setMatchPriceCents(Integer matchPriceCents) {
        this.matchPriceCents = matchPriceCents;
    }

    public void setRideid(Integer rideid) {
        this.rideid = rideid;
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

    public String getDriverMobilePhoneNo() {
        return driverMobilePhoneNo;
    }

    public void setDriverMobilePhoneNo(String driverMobilePhoneNo) {
        this.driverMobilePhoneNo = driverMobilePhoneNo;
    }

    public String getDriverCarBrand() {
        return driverCarBrand;
    }

    public void setDriverCarBrand(String driverCarBrand) {
        this.driverCarBrand = driverCarBrand;
    }

    public Short getDriverCarBuildYear() {
        return driverCarBuildYear;
    }

    public void setDriverCarBuildYear(Short driverCarBuildYear) {
        this.driverCarBuildYear = driverCarBuildYear;
    }

    public String getDriverCarColour() {
        return driverCarColour;
    }

    public void setDriverCarColour(String driverCarColour) {
        this.driverCarColour = driverCarColour;
    }

    public String getDriverCarPlateNo() {
        return driverCarPlateNo;
    }

    public void setDriverCarPlateNo(String driverCarPlateNo) {
        this.driverCarPlateNo = driverCarPlateNo;
    }

}
