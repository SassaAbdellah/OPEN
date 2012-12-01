

// HTML 5 geolocation functions


function getLocation() {
      
     console.log('running getLocation()'); 
  
  if (navigator.geolocation)
    {
        
    console.log("geocoding is supported! good!");
    navigator.geolocation.getCurrentPosition(showPosition,handleError);
    
    console.log("done with getting position");
    }
    
    
    else{alert("Geolocation is not supported by this browser.");}
  }
function showPosition(position)
  {
      console.log("called showposition");
      
      var lat=position.coords.latitude;
      var lon=position.coords.longitude;
      
      
      console.log("lat : "+lat);
      console.log("lon : "+lon);
  
      alert("lat : "+lat+" lon : "+lon);
      
     
         var lat=position.coords.latitude;
         var lon=position.coords.longitude;
        
        
        // build the url for calling the mapper
        // note that early webkit browsers want to have ampersant escaped as '&amp;
        // which is not done by the encodeURI funtion
        
        var mapperURL=mapURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat); 

        // build the URL for acceppting the place
        var targetedURL=targetURL+('\?'+lonP+'\='+lon+'&amp;'+latP+'\='+lat); 



        mapperURL=encodeURI(mapperURL);
        console.log('mapperURL : '+mapperURL);
        
        // since webkit has trouble displaying divs, we had to add 
        // tons of ugly br tags here.
        // TODO: find out what's wrong with webkit on android 2.2'
        

        $('#geosearch_out').append('\<br\/\>');
        $('#geosearch_out').append(geocodeAcceptLabel.link(targetedURL));
        $('#geosearch_out').append('........................');
        $('#geosearch_out').append(geocodeShowInMapLabel.link(mapperURL));
        $('#geosearch_out').append('\<br\/\>')
      
      
      
      
      
      
      
  }
  
  function handleError(err){
      
    var message="unknown error";
    
    if (err.code == 1) { message=geolocationNotAllowedError;}
    if (err.code == 2) { message=geolocationUnavaillableError;}
    if (err.code == 3) { message=geolocationTimedOutError;}

    alert(message);
  }
  
  
  