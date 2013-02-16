/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jochen
 */

@Named("counter")
@SessionScoped


/** Provides a "count" property that is incremented 
 *  whenever it is called.
 *  This can be used as a source of sessionwide unique IDs.
 * 
 */
public class Counter implements Serializable {
    
    long count=0;
    
    public Long getCount(){
        count++;
        return count;
    }
}
