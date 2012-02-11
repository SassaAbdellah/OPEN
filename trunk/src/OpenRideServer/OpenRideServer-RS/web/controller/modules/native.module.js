/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

fokus.openride.mobclient.controller.modules.nativemodule = function(){

    /* ------ private variabeles and methods ------ */
    var userlocation;

    function processlocation(location){
        userlocation = location;
    }

    function processgearslocation(location){
        userlocation = new Object();
        userlocation.coords = {};
        userlocation.coords.latitude = location.latitude;
        userlocation.coords.longitude = location.longitude;
    }

    function processerror(){
        return null;
    }

    /* ------ public variabeles and methods ------ */
    return {

        getUserLocation: function(){

            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition(processlocation, processerror);
            }else if(window.google && google.gears){
                try {
                    var geolocation = google.gears.factory.create('beta.geolocation');
                    geolocation.getCurrentPosition(processgearslocation, processerror,
                        {
                            enableHighAccuracy: true,
                            gearsRequestAddress: true
                        });
                } catch (e) {
                    return null;
                }
            } else{
                return null;
            }
            return userlocation;
        }
    };
}();
