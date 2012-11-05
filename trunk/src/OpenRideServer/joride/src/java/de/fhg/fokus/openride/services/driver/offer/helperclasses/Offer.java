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

import org.postgis.Point;

/**
 * This class contains all the information sent by a client generating a new offer.
 * @author pab
 */
public class Offer {

    private int rideId;
    private Double ridestartPtLat;
    private Double ridestartPtLon;
    private Double rideendPtLat;
    private Double rideendPtLon;
    private long ridestartTime;
    private double rideprice;
    private String rideComment;
    private int acceptableDetourInMin;
    private int acceptableDetourInKm;
    private int acceptableDetourInPercent;
    private int offeredSeatsNo;
    private String startptAddress;
    private String endptAddress;
    private Boolean updated;
    private Point[] intermediatePoints;


    public String getEndptAddress() {
        return endptAddress;
    }

    public String getStartptAddress() {
        return startptAddress;
    }
    public Offer() {
    }

    public Offer(
            int rideId,
            Double ridestartPtLat,
            Double ridestartPtLon,
            Double rideendPtLat,
            Double rideendPtLon,
            long ridestartTime,
            double rideprice,
            String rideComment,
            int acceptableDetourInMin,
            int acceptableDetourInKm,
            int acceptableDetourInPercent,
            int offeredSeatsNo,
            String startptAddress,
            String endptAddress,
            Point[] intermediatePoints) {
        this.intermediatePoints = intermediatePoints;
        this.ridestartPtLat = ridestartPtLat;
        this.ridestartPtLon = ridestartPtLon;
        this.rideendPtLat = rideendPtLat;
        this.rideendPtLon = rideendPtLon;
        this.ridestartTime = ridestartTime;
        this.rideprice = rideprice;
        this.rideComment = rideComment;
        this.acceptableDetourInMin = acceptableDetourInMin;
        this.acceptableDetourInKm = acceptableDetourInKm;
        this.acceptableDetourInPercent = acceptableDetourInPercent;
        this.offeredSeatsNo = offeredSeatsNo;
        this.startptAddress = startptAddress;
        this.endptAddress = endptAddress;
        this.rideId = rideId;
    }

    public Point[] getIntermediatePoints() {
        return intermediatePoints;
    }

    public void setIntermediatePoints(Point[] intermediatePoints) {
        this.intermediatePoints = intermediatePoints;
    }


    public Offer(
            Double ridestartPtLat,
            Double ridestartPtLon,
            Double rideendPtLat,
            Double rideendPtLon,
            long ridestartTime,
            double rideprice,
            String rideComment,
            int acceptableDetourInMin,
            int acceptableDetourInKm,
            int acceptableDetourInPercent,
            int offeredSeatsNo) {
        this.ridestartPtLat = ridestartPtLat;
        this.ridestartPtLon = ridestartPtLon;
        this.rideendPtLat = rideendPtLat;
        this.rideendPtLon = rideendPtLon;
        this.ridestartTime = ridestartTime;
        this.rideprice = rideprice;
        this.rideComment = rideComment;
        this.acceptableDetourInMin = acceptableDetourInMin;
        this.acceptableDetourInKm = acceptableDetourInKm;
        this.acceptableDetourInPercent = acceptableDetourInPercent;
        this.offeredSeatsNo = offeredSeatsNo;

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

    public int getAcceptableDetourInMin() {
        return acceptableDetourInMin;
    }

    public int getOfferedSeatsNo() {
        return offeredSeatsNo;
    }

    public String getRideComment() {
        return rideComment;
    }

    public double getRideprice() {
        return rideprice;
    }

    public long getRidestartTime() {
        return ridestartTime;
    }

    public int getAcceptableDetourInKm() {
        return acceptableDetourInKm;
    }

    public int getAcceptableDetourInPercent() {
        return acceptableDetourInPercent;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }


    public String toString(){
        return ""+
    ridestartPtLat+" "+
    ridestartPtLon+" "+
    rideendPtLat+" "+
    rideendPtLon+" "+
    ridestartTime+" "+
    rideprice+" "+
    rideComment+" "+
    acceptableDetourInMin+" "+
    offeredSeatsNo;
    }


    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

}
