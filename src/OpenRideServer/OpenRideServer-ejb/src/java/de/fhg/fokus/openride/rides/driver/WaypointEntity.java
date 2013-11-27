/**
 * @author jochen
 *
 *
 */
package de.fhg.fokus.openride.rides.driver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Waypoint models driver-defined intermediate points on a route.
 *
 *
 * @author jochen
 */
@NamedQueries({
    @NamedQuery(name = "WaypointEntity.findByRideId", query = "SELECT wp FROM WaypointEntity wp WHERE wp.rideId = :rideId ORDER BY wp.routeIdx"),})
@Entity
@Table(name = "way_point")
public class WaypointEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Unique ID
     *
     */
    @Id
    @Column(name = "waypoint_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * References the driverundertakesride Object that this waypoint belongs to
     *
     */
    @Column(name = "ride_id", nullable = false)
    private Integer rideId;
    /**
     * Gives the order in which waypoints should be called on the tour.
     */
    @Column(name = "route_idx", nullable = false)
    private Integer routeIdx;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "description")
    private String description;

    public WaypointEntity() {
    }

    public WaypointEntity(Integer rideId, Integer routeIdx, Double longitude, Double latitude, Integer riderrouteId, boolean isRequired) {
        this.rideId = rideId;
        this.routeIdx = routeIdx;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return this.id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getRideId() {
        return rideId;
    }

    public Integer getRouteIdx() {
        return routeIdx;
    }

    public String getDescription() {
        return this.description;
    }
    
    

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public void setRouteIdx(Integer routeIdx) {
        this.routeIdx = routeIdx;
    }

    public void setDescription(String arg) {
        this.description = arg;
    }
}
