/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.matching;

import de.fhg.fokus.openride.matching.MatchEntity;
import de.fhg.fokus.openride.matching.RouteMatchingBeanLocal;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/** Service for delivering JMatchingEntities.
 * 
 *
 * @author jochen
 */
public class JMatchingEntityService {
    
    
     /**
     * Lookup MatchingBeanLocal that controls my requests.
     *
     * @return
     */
    protected RouteMatchingBeanLocal lookupRouteMatchingBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouteMatchingBeanLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RouteMatchingBean!de.fhg.fokus.openride.matching.RouteMatchingBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }

    }
    
    
     /**
     * Returns a list of Matches for a rideRequest
     *
     * @return
     */
    public List<JMatchingEntity> getMatchesForRide(int rideId) {
        
       List <MatchEntity> mel=this.lookupRouteMatchingBeanLocal().searchForDrivers(rideId);
       
       
       Iterator <MatchEntity> it = mel.iterator();
       
       
       List <JMatchingEntity> res = new LinkedList<JMatchingEntity> ();
       
       while(it.hasNext()){
           res.add(new JMatchingEntity(it.next()));
       }
       
       return res;
       
    }
    

    
}
