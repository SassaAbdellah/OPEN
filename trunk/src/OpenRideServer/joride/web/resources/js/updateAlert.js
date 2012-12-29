

/*  showUpdate is the callback function that updates the header to 
 *  display update alerts if a request or offer is updated.
 *  This method is called periodically from within the header.xhtml section
 *        
 *  To work properly, it needs the following global variables to be defined  
 *  within the calling script:
 *
 *  var updatedImageURL    = URL where the "update" image is located 
 *  var rideUpdateMessage  = Message to be displayed when there are updated requests   
 *  var driveUpdateMessage = Message to be displayed when there are updated offers  
 *                   
 *                   
 *  See also /sections/header.xml JSF Template to see how this works.                 
 *                   
 */

function showUpdates(xxx){
    
    updatedOffers   = xxx.UpdateResponse.NoOfUpdatedOffers;
    updatedRequests = xxx.UpdateResponse.NoOfUpdatedRequests;

   
    if(0!=updatedRequests   )   {
        $('#updatedRequestsImg').html('<img src=\"'+updatedImageURL+'\" width=\"20\" height=\"20\" alt=\"!\" />');
        $('#updatedRequestsMsg').html(rideUpdateMessage); 
    } else {
        $('#updatedRequestsImg').html(' ');
        $('#updatedRequestsMsg').html(' '); 
    }


    if(0!=updatedOffers )   { 
        $('#updatedOffersImg').html('<img src=\"'+updatedImageURL+'\"  width=\"20\" height=\"20\" alt=\"!\" />');
        $('#updatedOffersMsg').html(driveUpdateMessage);
    } else {
        $('#updatedOffersImg').html(' ');
        $('#updatedOffersMsg').html(' '); 
    }

}


  
