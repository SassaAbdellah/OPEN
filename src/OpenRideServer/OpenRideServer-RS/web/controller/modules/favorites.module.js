/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


fokus.openride.mobclient.controller.modules.favorites = function(){

    /* ------ private variabeles and methods ------ */
    var favpt = {
        'FavoritePointRequest':[
        {
            'favptAddress':'Bitte Strasse eingben!',
            'favptGeoCoords':'0,0',
            'favptDisplayName':'Bitte Namen eingeben'
        }
        ]
    }

    /*{"list":[{"FavoritePointResponse":[{"favptId":9652,"favptAddress":"Brandenburg Gate, Pariser Platz 7, 10117 Berlin, Germany","favptDisplayName":"Brandenburg Gate"},
						{"favptId":9653,"favptAddress":"Kaiserin-Augusta-Allee 31, 10589 Berlin, Germany","favptDisplayName":"Kaiserin-Augusta-Allee 31"},
						{"favptId":9654,"favptAddress":"Technical University of Berlin","favptDisplayName":"Technical University of Berlin"}]}]}*/
                
    return {

        setAddress : function(addrstr){
            favpt.FavoritePointRequest[0].favptAddress = addrstr;
        },

        getAdress : function(){
            return favpt.FavoritePointRequest[0].favptAddress;
        },

        setGeoCoordStr : function(coordstr){
            favpt.FavoritePointRequest[0].favptGeoCoords = coordstr;
        },

        getGeoCoordStr : function(){
            return favpt.FavoritePointRequest[0].favptGeoCoords;
        },

        setGeoCoords : function(lat, ln){
            favpt.FavoritePointRequest[0].favptGeoCoords = lat + ',' + ln;
        },

        setDisplayName : function(name){
            favpt.FavoritePointRequest[0].favptDisplayName = name;
        },

        getDisplayName : function(){
            return favpt.FavoritePointRequest[0].favptDisplayName;
        },

        getFavPt : function(){
            return favpt;
        }
    };
}();
