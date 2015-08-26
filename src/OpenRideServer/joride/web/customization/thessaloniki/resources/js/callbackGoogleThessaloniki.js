

/**
 * 
 */

var addressField;

/**
 * 
 */

var longitudeField;

/**
 * 
 */

var latitudeField;


/**
 * 
 * 
 */
function codeAddressWithGoogleStartPt() {
		

	addressField   ='f1:addressStart';
    longitudeField ='f1:longitudeStart';
	latitudeField  ='f1:latitudeStart';
	
	codeAddressWithGoogleSR();
}


/**
 * 
 * 
 */
function codeAddressWithGoogleEndPt() {
		
	addressField   ='f1:addressEnd';
    longitudeField ='f1:longitudeEnd';
	latitudeField  ='f1:latitudeEnd';
	
	codeAddressWithGoogleSR();
}




/**
 * 
 * 
 * 
 * 
 */	
function codeAddressWithGoogleSR() {
	
	
  	var address = document.getElementById(addressField).value;
    var geocoder = new google.maps.Geocoder();
    
	function handleGMResult(results, status) {
		
					// blank out longitude and latitude not to have wrong values if we had no results
					document.getElementById(longitudeField).value='';
					document.getElementById(latitudeField).value='';
					
					
    				if (status == google.maps.GeocoderStatus.OK ) {	
    					
					   if(bounds.contains( results[0].geometry.location)){
						
					 	document.getElementById(addressField).value=(results[0].formatted_address);
                      	document.getElementById(longitudeField).value=(results[0].geometry.location.lng());
    					document.getElementById(latitudeField).value=(results[0].geometry.location.lat());
               				
						}              
     		 		        } else {
     		 		     

      						// console.log('Geocode was not successful for the following reason: ' + status);
    					}
    				
			} // function handleGMResult
	

 	geocoder.geocode( 
 				{'address' : address, 'bounds' : bounds }, 
				function(results, status) { handleGMResult(results,status);}
				);
 
 	
	} // function codeAddress
