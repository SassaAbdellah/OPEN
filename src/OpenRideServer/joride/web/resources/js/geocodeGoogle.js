function codeAddressWithGoogle(address) {
	
  
    var geocoder = new google.maps.Geocoder();

	console.log("searching for address : "+address);
  	geocoder.geocode( 
				{'address': address}, 
				function(results, status) {
    					if (status == google.maps.GeocoderStatus.OK) 
					{
    						
    				   var numberOfResults=results.length;	    
    				    // make a nice header 
    				   $('#geosearch_out').append('\<h3\>'+geocodeResultHeader+' '+numberOfResults+'\<\/h3\>');  	
    				
    				   
    				   var i=0;
    				   
    				   
						while( i<results.length){
							
					        var displayName=results[i].formatted_address;
					        var lat=results[i].geometry.location.lat();
					        var lon=results[i].geometry.location.lng();
							
							
							
					        // build the url for calling the mapper
					        // note that early webkit browsers want to have ampersant escaped as '&aml;
					        // which is not done by the encodeURI funtion
					        
					        var mapperURL=mapURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat+'&amp;'+displayP+'\='+displayName); 

					        mapperURL=encodeURI(mapperURL);
					        
					        // dropped as not to annoy M$ IIE
					        // 
					        //console.log('mapperURL : '+mapperURL);
					        
					           // build the URL for acceppting the place
					        var targetedURL=targetURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat+'&amp;'+displayP+'\='+displayName+'&amp;'+targetP+'\='+target); 
					  
					        // dropped as not to annoy M$ IIE
					        // 
					        // console.log('targetedURL : '+targetedURL);
					        
					        
					        // since webkit has trouble displaying divs, we had to add 
					        // tons of ugly br tags here.
					        // TODO: find out what's wrong with webkit on android 2.2'
					        
					        $('#geosearch_out').append('\<br\/\>')
					        $('#geosearch_out').append(displayName);  
					        $('#geosearch_out').append('\<br\/\>');
					        $('#geosearch_out').append(geocodeAcceptLabel.link(targetedURL));
					        $('#geosearch_out').append('........................');
					        $('#geosearch_out').append(geocodeShowInMapLabel.link(mapperURL));
					        $('#geosearch_out').append('\<br\/\>')
					        
                          i++;
						};	
     		 		    } else {
      						console.log('Geocode was not successful for the following reason: ' + status);
    					}
  				  }
				);
	} // function codeAddress