/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

/**
 * A set of constants describing the State of Negotiations for a drive.
 *
 * These constants are defined in a seperate class instead of
 * JDriverUndertakesRideEntity as CDIBeans and enums do
 * not go well with each other.
 *
 *
 * @author jochen
 */
public enum DriveNegotiationConstants {

    /**
     * Initial State of an offer. STATE_NEW meanst that the offer is newly
     * created, and that there are no requests from potential riders yet.
     *
     * From STATE_NEW, the ride offer may get into state STATE_RIDER_REQUESTED
     * (if a rider requests a ride) or STATE_DRIVER_ACCEPTED (if Driver accepts
     * a matching request ) or STATE_COUNTERMANDED (if Driver needs to
     * invalidate offer for some reason)
     *
     * see also: null null null null null null null null     {@link STATE_NEW} 
     *  {@link  STATE_RIDER_REQUESTED}
     *  {@link  STATE_DRIVER_ACCEPTED}
     *  {@link  STATE_CONFIRMED}
     *  {@link  STATE_COUNTERMANDED}
     *  {@link  STATE_UNCLEAR}
     *
     */
    STATE_NEW,
    /**
     * STATE_RIDER_REQUESTED is a state into which an offer gets if one (or
     * more) riders have requested to be picked up, but the driver has not (yet)
     * acceppted any one of those requests.
     *
     * From STATE_RIDER_REQUESTED the ride offer may get into state
     *
     * STATE_CONFIRMED (if Driver accepts a matching request ) or
     * STATE_COUNTERMANDED (if Driver needs to invalidate offer for some reason)
     *
     * see also: null null null null null null null null     {@link STATE_NEW} 
     *  {@link  STATE_RIDER_REQUESTED}
     *  {@link  STATE_DRIVER_ACCEPTED}
     *  {@link  STATE_CONFIRMED}
     *  {@link  STATE_COUNTERMANDED}
     *  {@link  STATE_UNCLEAR}
     *
     */
    STATE_RIDER_REQUESTED,
    /**
     * STATE_DRIVER_ACCEPTED is a state into which an offer gets if one (or
     * more) matchings exists and driver has "prematurely" accepted to pick up
     * riders, while riders have not (yet) requested to be picked up.
     *
     * From STATE_DRIVER_ACCEPTED the ride offer may get into state
     *
     * STATE_CONFIRMED (if one or maore accepted riders acceppt this ride too)
     * STATE_COUNTERMANDED (if Driver needs to invalidate offer for some reason)
     *
     * see also: null null null null null null null null     {@link STATE_NEW} 
     *  {@link  STATE_RIDER_REQUESTED}
     *  {@link  STATE_DRIVER_ACCEPTED}
     *  {@link  STATE_CONFIRMED}
     *  {@link  STATE_COUNTERMANDED}
     *  {@link  STATE_UNCLEAR}
     *
     */
    STATE_DRIVER_ACCEPTED,
    /**
     * STATE_CONFIRMED is a state into which an offer gets if one (or more)
     * matchings exists and driver both, driver and rider have agreed to take
     * the lift, rsp pick up the rider.
     *
     * From STATE_CONFIRMED, the ride offer may get into state
     *
     * STATE_COUNTERMANDED (if Driver needs to invalidate offer for some reason)
     *
     * see also: null null null null null null null null     {@link STATE_NEW} 
     *  {@link  STATE_RIDER_REQUESTED}
     *  {@link  STATE_DRIVER_ACCEPTED}
     *  {@link  STATE_CONFIRMED}
     *  {@link  STATE_COUNTERMANDED}
     *  {@link  STATE_UNCLEAR}
     *
     */
    STATE_CONFIRMED,
    /**
     * STATE_COUTERMANDED is a state into which an offer gets if Driver has to
     * cancel the ride for whatever reason (Blizzards, Earthquake, ...etc...)
     *
     * From STATE_COUNTERMANDED the ride offer may not get into any other
     * stated.
     *
     * STATE_CONFIRMED (if one or maore accepted riders acceppt this ride too)
     * STATE_COUNTERMANDED (if Driver needs to invalidate offer for some reason)
     *
     * see also: null null null null null null null null     {@link STATE_NEW} 
     *  {@link  STATE_RIDER_REQUESTED}
     *  {@link  STATE_DRIVER_ACCEPTED}
     *  {@link  STATE_CONFIRMED}
     *  {@link  STATE_COUNTERMANDED}
     *  {@link  STATE_UNCLEAR}
     *
     */
    STATE_COUNTERMANDED,
    /**
     * STATE_UNCLEAR is a state that gets returned if the negotiations of a
     * drive cannot be determined.
     */
    STATE_UNCLEAR,  

    /** Drive is no more availlable, typically in the past
     */
    STATE_UNAVAILLABLE

}
