/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;




/** Defines a time interval  in terms of 
 *  length in days and enddate.
 *
 * @author jochen
 */




@Named("timeinterval")
@RequestScoped
public class TimeInterval {
    
    /** Date ending the period for which 
     *  rides/drive should be displayed
     * 
     */
   protected Date endDate;
    
   public void setEndDate(Date date){
       this.endDate=date;
   }
   
   public Date getEndDate(){
       return this.endDate;
   }
    
    
      
    /** Number of Days before endDate for 
     *  which drives/ride should be listed.
     * 
     */
   protected int days;
    
   public void setDays(int args){
       this.days=args;
   }    
   
   public int getDays(){
       return this.days;
   }
    
   
   // number of miliseconds in a day
   long millisInDay=(1000*60*60*24);
   
   
   /** 
    * 
    * @return endDate- number of days before endDate
    */
   public Date getStartDate(){
       
      long endTime=this.getEndDate().getTime();
      long startTime=endTime-(millisInDay*getDays());
      return new Date(startTime);
   }
   
   
   
   
   
} // class
