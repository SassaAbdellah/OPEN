/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.matching;

import de.avci.joride.jbeans.driverundertakesride.JDriverUndertakesRideEntity;
import java.io.Serializable;

import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntity;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.fhg.fokus.openride.matching.MatchEntity;

/**
 * Wrapper making MatchingEntity available as a CDI Bean for use in JSF
 * frontend.
 *
 * @author jochen
 *
 */
@Named
@SessionScoped
public class JMatchingEntity implements Serializable {

    /**
     * The Match Entity that this Object is build around. Initial property.
     */
    private MatchEntity matchEntity = null;

    public MatchEntity getMatchEntity() {
        return this.matchEntity;
    }
    /**
     * Representation of the matchEntities riderUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JRiderUndertakesRideEntity ride = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JRiderUndertakesRideEntity getRide() {

        if (ride == null) {
            ride = new JRiderUndertakesRideEntity();
            ride.updateFromRiderUndertakesRideEntity(matchEntity.getRiderUndertakesRideEntity());
        }

        return ride;
    }
    /**
     * Representation of the matchEntities driverUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JDriverUndertakesRideEntity drive = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JDriverUndertakesRideEntity getDrive() {

        if (drive == null) {
            drive = new JDriverUndertakesRideEntity();
            drive.updateFromDriverUndertakesRideEntity(matchEntity.getDriverUndertakesRideEntity());
        }

        return drive;
    }

    JMatchingEntity(MatchEntity arg) {
        this.matchEntity = arg;
    }
} // class 
