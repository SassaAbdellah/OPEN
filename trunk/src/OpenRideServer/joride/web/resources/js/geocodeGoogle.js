








//
// Geomapping using Google Geocoding API v3 
//
// https://developers.google.com/maps/documentation/javascript/?hl=de
//
// See reference document here
// https://developers.google.com/maps/documentation/javascript/reference
//
// Note that bounds in GoogleGeocoding API are interpreted as *hints*
// (unlike Nominatim, which treats them as exclusive)
// 
// Hence, while passing the bounds to the query as a hint, we still
// have to check every result returned if it is contained inside
// the bounds 
//
//

function codeAddressWithGoogle(address, north, east, south, west) {
	
	
	
	
    var geocoder = new google.maps.Geocoder();

	
    // Southwest bound
	var sw=  new google.maps.LatLng( south,  west  );
    // northeast bound
	var ne=  new google.maps.LatLng( north , east  );
	// bounds object created from corners
	var bounds= new google.maps.LatLngBounds(sw, ne);
    
    
       
  	geocoder.geocode( 
				{'address': address , 'bounds' : bounds }, 
				function(results, status) {
    					if (status == google.maps.GeocoderStatus.OK) 
					{
    						
    				   var numberOfResults=results.length;	    
    				    // make a nice header 
    				 
    				   
    				   var i=0;
    				   var count=0;
    				   
						while( i<results.length && count < limit ){
						
						// bounds are interpreted as hint only, so we have to check
						// if result is indeed contained in bounding box
						//	
						if(bounds.contains( results[i].geometry.location))	{
					        var displayName=results[i].formatted_address;
					        var lat=results[i].geometry.location.lat();
					        var lon=results[i].geometry.location.lng();
								
					        // build the url for calling the mapper
					        // note that early webkit browsers want to have ampersant escaped as '&aml;
					        // which is not done by the encodeURI funtion
					        
					        var mapperURL=mapURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat+'&amp;'+displayP+'\='+displayName); 

					        mapperURL=encodeURI(mapperURL);
					      
					        
					           // build the URL for acceppting the place
					        var targetedURL=targetURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat+'&amp;'+displayP+'\='+displayName+'&amp;'+targetP+'\='+target); 
					  
					        
					        // since webkit has trouble displaying divs, we had to add 
					        // tons of ugly br tags here.
					        // TODO: find out what's wrong with webkit on android 2.2'
					        
					        $('#geosearch_out').append('\<br\/\>')
					        $('#geosearch_out').append(displayName);  
					        $('#geosearch_out').append('\<br\/\>');
					        $('#geosearch_out').append(geocodeAcceptLabel.link(targetedURL));
					        $('#geosearch_out').append('........................');
					        $('#geosearch_out').append(geocodeShowInMapLabel.link(mapperURL));
					        $('#geosearch_out').append('\<br\/\>');
					        
					        count++; // count number of results
						}
					        
                          i++;
						};	
     		 		    } else { // if (status == google.maps.GeocoderStatus.OK) 
      						console.log('Geocode was not successful for the following reason: ' + status);
    					}
    					// at the end of the list, print out the number of results,
    					// so user gets notified that there are more results
    					$('#geosearch_out').append('\<h4\>'+geocodeResultHeader+' '+count+'\<\/h4\>');  	
    	    				
  				  }
				);
	} // function codeAddress