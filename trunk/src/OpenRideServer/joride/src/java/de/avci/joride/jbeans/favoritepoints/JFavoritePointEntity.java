/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.favoritepoints;

import de.avci.joride.jbeans.cardetails.JCardetailsEntityService;
import de.avci.joride.utils.CRUDConstants;
import de.avci.joride.utils.HTTPRequestUtil;
import de.fhg.fokus.openride.customerprofile.CarDetailsEntity;
import de.fhg.fokus.openride.customerprofile.FavoritePointEntity;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 * JSF Bean Wrapper for FavouritePointEntity
 *
 * @author jochen
 */
@Named
@RequestScoped
public class JFavoritePointEntity extends FavoritePointEntity {

    /**
     * Returns the list of favourite points for the current customer given.
     * Current customer is determined savely from HTTPRequest's Auth Principal
     *
     *
     * @return list
     */
    public List<FavoritePointEntity> getFavoritePointList() {

        return (new JFavoritePointsService()).getFavoritePointList();
    }

    public void doCrudAction(ActionEvent evt) {

        HTTPRequestUtil hru = new HTTPRequestUtil();

        System.out.println("doCrudAction Event : " + evt.toString());

        String action = hru.getParameterSingleValue((new CRUDConstants()).getParamNameCrudAction());
        System.out.println("Param Action : " + action);

        String id = hru.getParameterSingleValue((new CRUDConstants()).getParamNameCrudId());
        System.out.println("Param ID     : " + id);



        if (CRUDConstants.PARAM_VALUE_CRUD_DELETE.equals(action)) {
            this.delete(new Integer(id).intValue());
        }



        if (CRUDConstants.PARAM_VALUE_CRUD_CREATE.equals(action)) {
            this.create();
        }

    }

    
    
    /** Create a new Favoritepoint for this user
     * 
     */
    public void create() {
    
        (new JFavoritePointsService()).addFavoritePoint(this);
    
    }
    
    

    public void delete(int favpointId){
    
        (new JFavoritePointsService()).deleteFavoritePointSavely(favpointId);
    }
    
    
    
        /** Load the FavoritePointsEntity from database with the id given
       *  as in http request parameter.
       *  Will do extensive checking on the backside to 
       *  ensure that the current User (identified by http Auth principal)
       *  has permissions to do so.
       * 
       */
      public void updateFromDB(){
                
          int favpointId=0;
          
          String favpointIdStr=(new HTTPRequestUtil()).getParameterSingleValue(CRUDConstants.PARAM_NAME_CRUD_ID);
          favpointId=(new Integer(favpointIdStr)).intValue();
          
          System.err.println("============ Loading Favpoint :"+favpointId+" ");
            
            FavoritePointEntity fpe=(new JFavoritePointsService()).getFavoritePointEntitySafely(favpointId);
                    
            
            this.setFavptId(fpe.getFavptId());
            this.setFavptDisplayname(fpe.getFavptDisplayname());
            this.setFavptAddress(fpe.getFavptAddress());   
            this.setFavptPoint(fpe.getFavptPoint());
                                    
      } // updateFromDB
    
   
   
} // class
