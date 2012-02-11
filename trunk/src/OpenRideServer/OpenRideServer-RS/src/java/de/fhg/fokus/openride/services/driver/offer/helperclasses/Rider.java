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

import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.util.Date;
import org.postgis.Point;

/**
 *
 * @author pab
 */
public class Rider {
    private Integer riderrouteId;
    private Integer givenrating;
    private String givenratingComment;
    private Date givenratingDate;
    private Date receivedratingDate;
    private Date starttimeLatest;
    private Point startpt;
    private Date timestamprealized;
    private Point endpt;
    private Integer receivedrating;
    private Double price;
    private Integer noPassengers;
    private Date timestampbooked;
    private String receivedratingComment;
    private Date starttimeEarliest;
    private int custId;
    private int rideId;
    private String startptAddress;
    private String endptAddress;
    private String comment;

    public Rider(int cust_id, Date starttimeEarliest, Date starttimeLatest, int noPassengers, Point startpt, Point endpt, double price) {
        this.custId = cust_id;
        this.starttimeEarliest = starttimeEarliest;
        this.starttimeLatest = starttimeLatest;
        this.startpt = startpt;
        this.endpt = endpt;
        this.price = price;
        this.noPassengers = noPassengers;
    }

    public Rider(RiderUndertakesRideEntity entity){
        this.comment = entity.getComment();
        this.custId = entity.getCustId().getCustId();
        this.endpt = entity.getEndpt();
        this.endptAddress = entity.getEndptAddress();
        this.givenrating = entity.getGivenrating();
        this.givenratingComment = entity.getGivenratingComment();
        this.givenratingDate = entity.getGivenratingDate();
        this.noPassengers = entity.getNoPassengers();
        this.price = entity.getPrice();
        this.receivedrating = entity.getReceivedrating();
        this.receivedratingComment = entity.getReceivedratingComment();
        this.receivedratingDate = entity.getReceivedratingDate();
        this.rideId = entity.getRideId().getRideId();
        this.riderrouteId = entity.getRiderrouteId();
        this.startpt = entity.getStartpt();
        this.startptAddress = entity.getStartptAddress();
        this.starttimeEarliest = entity.getStarttimeEarliest();
        this.starttimeLatest = entity.getStarttimeLatest();
        this.starttimeLatest = entity.getTimestampbooked();
        this.timestampbooked = entity.getTimestamprealized();
    }

    public void setEndptAddress(String endptAddress) {
        this.endptAddress = endptAddress;
    }

    public void setStartptAddress(String startptAddress) {
        this.startptAddress = startptAddress;
    }

    public String getEndptAddress() {
        return endptAddress;
    }

    public String getStartptAddress() {
        return startptAddress;
    }

    public Integer getRiderrouteId() {
        return riderrouteId;
    }

    public void setRiderrouteId(Integer riderrouteId) {
        this.riderrouteId = riderrouteId;
    }

    public Integer getGivenrating() {
        return givenrating;
    }

    public void setGivenrating(Integer givenrating) {
        this.givenrating = givenrating;
    }

    public String getGivenratingComment() {
        return givenratingComment;
    }

    public void setGivenratingComment(String givenratingComment) {
        this.givenratingComment = givenratingComment;
    }

    public Date getGivenratingDate() {
        return givenratingDate;
    }

    public void setGivenratingDate(Date givenratingDate) {
        this.givenratingDate = givenratingDate;
    }

    public Date getReceivedratingDate() {
        return receivedratingDate;
    }

    public void setReceivedratingDate(Date receivedratingDate) {
        this.receivedratingDate = receivedratingDate;
    }

    public Date getStarttimeLatest() {
        return starttimeLatest;
    }

    public void setStarttimeLatest(Date starttimeLatest) {
        this.starttimeLatest = starttimeLatest;
    }

    public Point getStartpt() {
        return startpt;
    }

    public void setStartpt(Point startpt) {
        this.startpt = startpt;
    }

    public Date getTimestamprealized() {
        return timestamprealized;
    }

    public void setTimestamprealized(Date timestamprealized) {
        this.timestamprealized = timestamprealized;
    }

    public Point getEndpt() {
        return endpt;
    }

    public void setEndpt(Point endpt) {
        this.endpt = endpt;
    }

    public Integer getReceivedrating() {
        return receivedrating;
    }

    public void setReceivedrating(Integer receivedrating) {
        this.receivedrating = receivedrating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNoPassengers() {
        return noPassengers;
    }

    public void setNoPassengers(Integer noPassengers) {
        this.noPassengers = noPassengers;
    }

    public Date getTimestampbooked() {
        return timestampbooked;
    }

    public void setTimestampbooked(Date timestampbooked) {
        this.timestampbooked = timestampbooked;
    }

    public String getReceivedratingComment() {
        return receivedratingComment;
    }

    public void setReceivedratingComment(String receivedratingComment) {
        this.receivedratingComment = receivedratingComment;
    }

    public Date getStarttimeEarliest() {
        return starttimeEarliest;
    }

    public void setStarttimeEarliest(Date starttimeEarliest) {
        this.starttimeEarliest = starttimeEarliest;
    }
/*
    public AccountHistoryEntity getAccountHistoryEntity() {
        return accountHistoryEntity;
    }

    public void setAccountHistoryEntity(AccountHistoryEntity accountHistoryEntity) {
        this.accountHistoryEntity = accountHistoryEntity;
    }
*/
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "de.fhg.fokus.openride.rides.driver.RiderUndertakesRideEntity[riderrouteId=" + riderrouteId + "]";
    }

}
