/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


fokus.openride.mobclient.controller.modules.offer = function(){

    /* ------ private variabeles and methods ------ */

    /* ------ public variabeles and methods ------ */

    var newoffer = {
        'Offer':[
        {
            'rideId'                    : -1,
            'ridestartPtLat'            : 52.525798,
            'ridestartPtLon'            : 13.314266,
            'rideendPtLat'              : 52.5225,
            'rideendPtLon'              : 13.4123,
            'ridestartTime'             : new Date().getMilliseconds(),
            'rideprice'                 : 8.2,
            'rideComment'               :'Das Fahrtkommentar.',
            'acceptableDetourInMin'     : 10,
            'acceptableDetourInKm'      : 10,
            'acceptableDetourInPercent' : 10,
            'offeredSeatsNo'            : 4,
            'startptAddress'            : 'Weg 1, 10001 Berlin',
            'endptAddress'              : 'Weg 900, 10002 Berlin'
        }
        ]
    }


    return {
        validateOfferRequest : function(){
            return newoffer;
        },

        setStartLat : function(latitude){
            newoffer.Offer[0].ridestartPtLat = latitude;
        },

        setStartLon : function(longitude){
            newoffer.Offer[0].ridestartPtLon = longitude;
        },

        setStartLatLn : function(latlnstr){
            if(latlnstr.indexOf(',') != -1){
                newoffer.Offer[0].ridestartPtLat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                newoffer.Offer[0].ridestartPtLon = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
            }else{
                newoffer.Offer[0].ridestartPtLat = 52.525798;
                newoffer.Offer[0].ridestartPtLon = 13.314266;
            }
        },

        getStartLat : function(){
            return newoffer.Offer[0].ridestartPtLat;
        },

        getStartLon : function(){
            return newoffer.Offer[0].ridestartPtLon;
        },

        setStartAddr : function(startaddr){
            newoffer.Offer[0].startptAddress = startaddr;
        },

        getStartAddr : function(){
            return newoffer.Offer[0].startptAddress;
        },

        setDestLat : function(latitude){
            newoffer.Offer[0].rideendPtLat = latitude;
        },

        setDestLon : function(longitude){
            newoffer.Offer[0].rideendPtLon = longitude;
        },

        setDestLatLn : function(latlnstr){
            if(latlnstr.indexOf(',') != -1){
                newoffer.Offer[0].rideendPtLat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                newoffer.Offer[0].rideendPtLon = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
            }else{
                newoffer.Offer[0].rideendPtLat = 52.525798;
                newoffer.Offer[0].rideendPtLon = 13.314266;
            }
        },

        getDestLat : function(){
            return newoffer.Offer[0].rideendPtLat;
        },

        getDestLon : function(){
            return newoffer.Offer[0].rideendPtLon;
        },

        setDestAddr : function(destaddr){
            newoffer.Offer[0].endptAddress = destaddr;
        },

        getDestAddr : function(){
            return newoffer.Offer[0].endptAddress;
        },

        setStartTime : function(starttime){
            newoffer.Offer[0].ridestartTime = starttime.getTime();
        },

        getStartTime : function(){
            return newoffer.Offer[0].ridestartTime;
        },

        setPrice : function(price){
            newoffer.Offer[0].rideprice = price;
        },

        getPrice : function(){
            return newoffer.Offer[0].rideprice;
        },

        setComment : function(comment){
            newoffer.Offer[0].rideComment = comment;
        },

        getComment : function(){
            return newoffer.Offer[0].rideComment;
        },

        setDetour : function(km){
            newoffer.Offer[0].acceptableDetourInKm = km;
        },

        getDetour : function(){
            return newoffer.Offer[0].acceptableDetourInMin;
        },

        setOfferedSeatsNo : function(offeredseats){
            newoffer.Offer[0].offeredSeatsNo = offeredseats;
        },

        getOfferedSeatsNo : function(){
            return newoffer.Offer[0].offeredSeatsNo;
        },

        setSaveTemplate : function(savetmpl){
            newoffer.Offer[0].savetemplate = savetmpl;
        },

        getSaveTemplate : function(){
            return newoffer.Offer[0].savetemplate;
        }

    };
}();