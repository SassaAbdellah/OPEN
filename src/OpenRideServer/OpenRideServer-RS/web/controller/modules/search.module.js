/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


fokus.openride.mobclient.controller.modules.search = function(){

    /* ------ private variabeles and methods ------ */
    var newsearch = {
        'Search':[
        {
            'ridestartPtLat':52.525798,
            'ridestartPtLon':13.314266,
            'rideendPtLat':52.525798,
            'rideendPtLon':13.314266,
            'ridestartTimeLatest':new Date().getTime(),
            'rideComment':'My dear Comment!',
            'maxwaitingtime':20,
            'searchedSeatsNo':1,
            'savetemplate':false,
            'ridestartTimeEarliest':new Date().getTime(),
            'price':5.12,
            'startptAddress'            : 'Weg 1, 10001 Berlin',
            'endptAddress'              : 'Weg 900, 10002 Berlin'
        }
        ]
    }
                
    return {
        validateSearchRequest : function(){
            return newsearch;
        },

        setTripName : function(ridename){
            newsearch.Search[0].rideName = ridename;
        },

        getTripName : function(){
            return newsearch.Search[0].rideName;
        },

        setStartLat : function(latitude){
            newsearch.Search[0].ridestartPtLat = latitude;
        },

        setStartLon : function(longitude){
            newsearch.Search[0].ridestartPtLon = longitude;
        },

        setStartLatLn : function(latlnstr){
            if(latlnstr.indexOf(',') != -1){
                newsearch.Search[0].ridestartPtLat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                newsearch.Search[0].ridestartPtLon = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
            }else{
                newsearch.Search[0].ridestartPtLat = 52.525798;
                newsearch.Search[0].ridestartPtLon = 13.314266;
            }
        },

        getStartLat : function(){
            return newsearch.Search[0].ridestartPtLat;
        },

        getStartLon : function(){
            return newsearch.Search[0].ridestartPtLon;
        },
		
        setStartAddr : function(startaddr){
            newsearch.Search[0].startptAddress = startaddr;
        },

        getStartAddr : function(){
            return newsearch.Search[0].startptAddress;
        },

        setDestLat : function(latitude){
            newsearch.Search[0].rideendPtLat = latitude;
        },

        setDestLon : function(longitude){
            newsearch.Search[0].rideendPtLon = longitude;
        },

        setDestLatLn : function(latlnstr){
            if(latlnstr.indexOf(',') != -1){
                newsearch.Search[0].rideendPtLat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                newsearch.Search[0].rideendPtLon = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
            }else{
                newsearch.Search[0].rideendPtLat = 52.525798;
                newsearch.Search[0].rideendPtLon = 13.314266;
            }
        },

        getDestLat : function(){
            return newsearch.Search[0].rideendPtLat;
        },

        getDestLon : function(){
            return newsearch.Search[0].rideendPtLon;
        },
		
        setDestAddr : function(destaddr){
            newsearch.Search[0].endptAddress = destaddr;
        },

        getDestAddr : function(){
            return newsearch.Search[0].endptAddress;
        },

        setStartTime : function(starttime){
            newsearch.Search[0].ridestartTimeEarliest = starttime.getTime();
        },

        getStartTime : function(){
            return newsearch.Search[0].ridestartTimeEarliest;
        },

        setPrice : function(price){
            newsearch.Search[0].rideprice = price;
        },

        getPrice : function(){
            return newsearch.Search[0].rideprice;
        },

        setMaxWaitingTime : function(min){
            newsearch.Search[0].maxwaitingtime = min;
            var waitingmillsec = min * 60 * 1000;
            newsearch.Search[0].ridestartTimeLatest = newsearch.Search[0].ridestartTimeEarliest + waitingmillsec;
        },

        getMaxWaitingTime : function(){
            return newsearch.Search[0].maxwaitingtime;
        },

        setComment : function(comment){
            newsearch.Search[0].rideComment = comment;
        },

        getComment : function(){
            return newsearch.Search[0].rideComment;
        },

        setSearchedSeatsNo : function(searchedseats){
            newsearch.Search[0].searchedSeatsNo = searchedseats;
        },

        getSearchedSeatsNo : function(){
            return newsearch.Search[0].searchedSeatsNo;
        },

        setSaveTemplate : function(savetmpl){
            newsearch.Search[0].savetemplate = savetmpl;
        },

        getSaveTemplate : function(){
            return newsearch.Search[0].savetemplate;
        }
    };
}();