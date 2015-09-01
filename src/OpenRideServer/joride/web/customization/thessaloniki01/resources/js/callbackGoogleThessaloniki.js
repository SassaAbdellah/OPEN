

/** Global variable containing the name of the "address" field to update.
 *  This should typically be one of "f1:startAddress" or "f1:endAddress"
 *  depending on whether start or destination is to be updated 
 */

var addressField;

/** Global variable containing the name of the "longitude" field to update.
 *  This should typically be one of "f1:startLongitude" or "f1:endLongitude"
 *  depending on whether start or destination is to be updated 
 */

var longitudeField;

/** Global variable containing the name of the "longitude" field to update.
 *  This should typically be one of "f1:startLongitude" or "f1:endLongitude"
 *  depending on whether start or destination is to be updated 
 */


var latitudeField;


/** Call google geocoder to update the startpoint.
 *  Set global variables for fieldnames to be updated,
 *  And then call codeAddressWithGoogle
 * 
 */
function codeAddressWithGoogleStartPt() {
		
	addressField   ='f1:addressStart';
    longitudeField ='f1:longitudeStart';
	latitudeField  ='f1:latitudeStart';
	errorField     ='divErrorStart';
		
	codeAddressWithGoogleSR();
}


/** Call google geocoder to update the endpoint.
 *  Set global variables for fieldnames to be updated,
 *  And then call codeAddressWithGoogle
 * 
 */
function codeAddressWithGoogleEndPt() {
		
	addressField   ='f1:addressEnd';
    longitudeField ='f1:longitudeEnd';
	latitudeField  ='f1:latitudeEnd';
	 errorField     ='divErrorEnd';
	
	codeAddressWithGoogleSR();
}




/** Get input from field named "addressField", 
 *  Then call google geomapper to find places for which 
 *  the input of address field matches.
 *  If results are returned, update fields address/longitude/latitude
 *  with the respective data.
 *  
 * 
 */	
function codeAddressWithGoogleSR() {
	
	
	
  	var address = document.getElementById(addressField).value;
    var geocoder = new google.maps.Geocoder();
    
	function handleGMResult(results, status) {
		
					// remove all previous error messages
					$('.formError').html('');
		
					// blank out longitude and latitude  not to have wrong values if we had no results
					document.getElementById(longitudeField).value='';
					document.getElementById(latitudeField).value='';
				
						
    				if ( 
    						(status == google.maps.GeocoderStatus.OK ) 	
    						&&
    						(bounds.contains( results[0].geometry.location))
    				 ){
					 	document.getElementById(addressField).value=(results[0].formatted_address);
                      	document.getElementById(longitudeField).value=(results[0].geometry.location.lng());
    					document.getElementById(latitudeField).value=(results[0].geometry.location.lat());
    					
               				
    				} else {
    					// set single errormessage for failed search
     		 		    $("#"+errorField).html(noResults);
    				}
    				
			} // function handleGMResult
	

 	geocoder.geocode( 
 				{'address' : address, 'bounds' : bounds }, 
				function(results, status) { handleGMResult(results,status);}
				);
 
 	
	} // function codeAddress
