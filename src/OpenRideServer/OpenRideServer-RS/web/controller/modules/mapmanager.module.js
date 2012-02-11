/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

fokus.openride.mobclient.controller.modules.mapmanager = function(){
    
    // --- --- private variables
    var srvconn = fokus.openride.mobclient.controller.serverconnector;
	
    // --- main map entities
    var map;
    var geocoder;
    var marker;
    var startmarker;
    var destmarker;
    var mapCenter;
    var usedAdressInput;
    var maximalZoom = 17;
    
    // --- icon resources
	
    //default icon vars
    var defIconPath = "../img/defaultpinicon.png";
    var defIconSizeX = 40;
    var defIconSizeY = 40;
    var defShadowPath = "../img/thumbshadow.png";
	
    //route-start icon var
    var mainstartIconPath = "../img/startred.png";
    var mainstartIconSizeX = 40;
    var mainstartIconSizeY = 40;
    
    //route destination point vars
    var maindstIconPath = "../img/zielred.png";
    var maindstIconSizeX = 40;
    var maindstIconSizeY = 40;
    
    //viapt start point vars
    var mainviaptstartIconPath = "../img/mapicon2.png";
    var mainviaptstartIconSizeX = 21;
    var mainviaptstartIconSizeY = 30;
    
    //via destination point vars
    var mainviaptdstIconPath = "../img/flagrdicon.png";
    var mainviaptdstIconSizeX = 20;
    var mainviaptdstIconSizeY = 15;
	
    //via destination point vars
    var wayPtIconPath = "../img/waypoint.png";
    var wayPtIconSizeX = 28;
    var wayPtIconSizeY = 40;
	
    var riderStartIconSizeX = 21;
    var riderStartIconSizeY = 30;
	
    var riderDestIconSizeX = 23;
    var riderDestIconSizeY = 17;
    
    var thumbiconpaths = ["../img/thumbcol1.png", "../img/thumbcol2.png", "../img/thumbcol3.png", "../img/thumbcol4.png", "../img/thumbcol5.png", "../img/thumbcol6.png"];
    var flagiconpaths = ["../img/flagcol1.png", "../img/flagcol2.png", "../img/flagcol3.png", "../img/flagcol4.png", "../img/flagcol5.png", "../img/flagcol6.png"];
	
    var ridermarkers = new Array();
	
    // --- state vars
	
    var mapMode = 0;//default
    var PICK_LOC_MODE = 0;
    var SIMPLE_ROUTE_MODE = 1;
    var VIAPT_ROUTE_MODE = 2;
	
    var routepathlatlns;
    var route;//PolyLine
    var routeopts//PolylineOptions
    var viastartptlatlns;
    var viadestptlatlns;
	
    var combinedlatlns;
    var mappolyline = '';
    var routecorrectionptlatlns = '';
    var partialroutes = '';
    var partialroutesloaded = false;
    var markerposindex = 1;

    var DUMMYPOSITION = new google.maps.LatLng(47.66029,9.432982);
	
    /*** define inner class DivMarker, which extends google.maps.OverlayView()
	 * and allows adding text-or image based markers as overlay onto our map*/
	
    /*function DivMarker(divPosLatLng, overlayMap){

    	google.maps.OverlayView.call(this);

    	this.latlng_ = divPosLatLng;

    	// trigger a call to panes_changed -> calls draw().

    	//this.setMap(overlayMap);
  	}

  	DivMarker.prototype = new google.maps.OverlayView();

  	DivMarker.prototype.draw = function() { 

    	var me = this;

	    // Check if the div has been created.
	
	    var div = this.div_;

	    if (!div) {
			// Create a overlay text DIV
      		div = this.div_ = document.createElement('DIV');

      		// Create the DIV representing our DivMarker

      		div.style.border = "0px solid none";
			div.style.position = "absolute";
			div.style.paddingLeft = "0px";
			div.style.cursor = 'pointer';
	        google.maps.event.addDomListener(div, "click", function(event) {
        		google.maps.event.trigger(me, "click");
      		});

			// Then add the overlay to the DOM
			var panes = this.get_panes();
			panes.overlayLayer.appendChild(div);
    	}

	    // Position the overlay 
    	var point = this.get_projection().fromLatLngToDivPixel(this.latlng_);

    	if(point){
			div.style.left = point.x + 'px';
			div.style.top = point.y + 'px';
		}
  	};


	DivMarker.prototype.remove = function() {

	    // Check if the overlay was on the map and needs to be removed.
	    if (this.div_){
			this.div_.parentNode.removeChild(this.div_);
			this.div_ = null;
	    }
	};
		
	DivMarker.setText = function(text){
		
		// Check if the div has been created.
	    var div = this.div_;
	    if (!div) {
			// Create a overlay text DIV
			div = this.div_ = document.createElement('DIV');
		}
		div.innerHTML = this.text_ = text;
	}
		
	DivMarker.setImage = function(imgPath){		
		// Check if the div has been created.
	    var div = this.div_;
	    if (!div) {
			// Create a overlay text DIV
			div = this.div_ = document.createElement('DIV');
		}
		var img = document.createElement("img");
		img.src = imgPath;
		div.appendChild(img);
	}*/
	
    /** end of DivMarker code*/

    // --- --- public variables / methods

    return {
		
        username : 'user',

        currentFormattedAddress: "1",
        revgeocodedAddr : "",

        updateAddressInfo: function (addressdivid){

            //avoid collision with "this" from google maps namespace"
            var mapmanagerTHIS = this;

            var addr = document.getElementById(addressdivid);
            /*if(!geocoder)geocoder = new google.maps.Geocoder();*/
            if (geocoder) {
                geocoder.geocode({
                    'latLng': mapCenter
                }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK){
                        if (results[0]){
                            addr.value = results[0].formatted_address;
                            mapmanagerTHIS.currentFormattedAddress = results[0].formatted_address;
                        }else {
                            addr.value = "keine Adresse gefunden!   ";
                        }
                    }
                    else {
                        addr.value = "keine Adresse gefunden!   ";
                    /*addr.value = "keine Adresse gefunden, status: " + status;*/
                    }

                });
            }
        },
    
        //initialize map with draggable icon etc.
        initialize: function (mapdivid, addressinputid, userlocation){
			
            usedAdressInput = addressinputid;

            //avoid collision with "this" from google maps namespace"
            var mapmanagerTHIS = this;

            geocoder = new google.maps.Geocoder();
            //mapCenter = new google.maps.LatLng(52.525798, 13.314266);
            mapCenter = userlocation;

            //parametrize map
            var mapOptions = {
                zoom:13,
                center: mapCenter,
                disableDefaultUI: true,
                mapTypeControl: false,
                navigationControl: false,
                /*navigationControlOptions: {
                    style: google.maps.NavigationControlStyle.DEFAULT,*/
                /*position: google.maps.ControlPosition.TOP_LEFT
                },*/
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                disableDoubleClickZoom: true
            }

            //create map
            map = new google.maps.Map(document.getElementById(mapdivid), mapOptions);
			
            //configure map ui corresponding to current state
            if(mapMode==PICK_LOC_MODE){
                mapmanagerTHIS.updateAddressInfo(addressinputid);
                createMarker('default', mapCenter);

                google.maps.event.addListener(marker, "dragstart", function() {
                    });

                google.maps.event.addListener(marker, "dragend", function() {
                    setCenter(marker.getPosition());
                    mapmanagerTHIS.updateAddressInfo(addressinputid);
                });

                google.maps.event.addListener(marker, "click", function() {
                    map.setZoom(15);
					
                });

                google.maps.event.addListener(map, "click", function(event) {
                    setCenter(event.latLng);
                    mapmanagerTHIS.updateAddressInfo(addressinputid);
                });
            }
			
            if(mapMode==SIMPLE_ROUTE_MODE){
                this.drawSimpleRoute();
            }else if(mapMode==VIAPT_ROUTE_MODE){
                this.drawRouteWithViaPts();
            }
        },
		
        setMapMode : function(mode){
            if(mode>=0)//caller takes care of upper bound, to allow adding more states
                mapMode = mode;
            else{
        //handle error
        }
        },
		
        setRoutePath : function(routeArray){
            routepathlatlns = routeArray;
        },
		
        setViaStartPoints : function(viaptArray){
            viastartptlatlns = viaptArray;
        },
		
        resetViaStartPoints : function(){
            viastartptlatlns = '';
        },
		
        setViaDestPoints : function(viaptArray){
            viadestptlatlns = viaptArray;
        },
		
        resetViaDestPoints : function(){
            viadestptlatlns = '';
        },
		
        getRoutePath : function(){
            return routepathlatlns;
        },
		
        getViaStartPoints : function(){
            return viaptstartlatlns;
        },
		
        getViaDestPoints : function(){
            return viadestptlatlns;
        },
		
        fitToBounds : function(bounds){
            map.fitBounds(bounds);
        },
		
        parsesimpleroutecoords : function(routexml){
            var routearr = new Array();
            var routeExists = $(routexml).find('hasroute').text();
            if(routeExists!= null && routeExists != 'undefined' && typeof routeExists != 'undefined'){
                if(routeExists == 'true'){
                    $(routexml).find('coordinates').each(function(){
                        var latlnstr = $(this).text();
                        var latlnstrarr = latlnstr.split(' ');
                        for(var i=0;i<latlnstrarr.length-1;i++){
                            var coordstr = latlnstrarr[i];

                            var separatorindex = coordstr.indexOf(',');
                            var latstr = coordstr.substr(0, separatorindex);
                            var lnstr = coordstr.substr(separatorindex+1, coordstr.length-separatorindex+1);
	
                            var lat = parseFloat(latstr);
                            var ln = parseFloat(lnstr);
	
                            var latlnObj = new google.maps.LatLng(lat, ln);
                            routearr.push(latlnObj);
                        }
                    });
                    return routearr;
                }else{//no route
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Es wurde keine Route gefunden.');
                    return false;
                }
            }
        },
		
        getPartialRoutes : function(){

            partialroutes = new Array();

            partialroutesloaded = true;
	
            if(routecorrectionptlatlns.length && routecorrectionptlatlns.length>0){
                for(var i= 0; i<routecorrectionptlatlns.length; i++ ){
                    var startlat = '';
                    var startln = '';
                    var dstlat = '';
                    var dstln = '';
                    var corrpt	= '';
	
                    if(i==0){
                        corrpt = routecorrectionptlatlns[i].getPosition();
                        startlat = routepathlatlns[0].lat();
                        startln = routepathlatlns[0].lng();
                        dstlat = corrpt.lat();
                        dstln = corrpt.lng();
                    }
                    else if(i != 0 && i == routecorrectionptlatlns.length-1){
                        corrpt = routecorrectionptlatlns[i].getPosition();
                        startlat = corrpt.lat();
                        startln = corrpt.lng();
                        dstlat = routepathlatlns[routepathlatlns.length-1].lat();
                        dstln = routepathlatlns[routepathlatlns.length-1].lng();
                    }else{
                        var lastcorrpt = routecorrectionptlatlns[i-1].getPosition();
                        startlat = lastcorrpt.lat();
                        startln = lastcorrpt.lng();
                        corrpt = routecorrectionptlatlns[i].getPosition();
                        dstlat = corrpt.lat();
                        dstln = corrpt.lng();
                    }
					

                    var mapmanagerTHIS = this;
                    srvconn.GET('/OpenRideServer-RS/resources/users/'+this.username+'/routes/new,'+startlat+','+startln+','+dstlat+','+dstln, false, function(routexml){
                        var route = mapmanagerTHIS.parsesimpleroutecoords(routexml);
                        if(route != false)
                            partialroutes.push(route);
                    }, function(){
                        partialroutesloaded = false;
                        alert('Die Route konnte nicht mit diesem Wegpunkt geladen werden!');
                    });
                }

				
                if(routecorrectionptlatlns.length==1){
                    corrpt = routecorrectionptlatlns[0].getPosition();
                    startlat = corrpt.lat();
                    startln = corrpt.lng();
                    dstlat = routepathlatlns[routepathlatlns.length-1].lat();
                    dstln = routepathlatlns[routepathlatlns.length-1].lng();
                }
				
                var mapmanagerTHIS = this;
                srvconn.GET('/OpenRideServer-RS/resources/users/'+this.username+'/routes/new,'+startlat+','+startln+','+dstlat+','+dstln, false, function(routexml){
                    var route = mapmanagerTHIS.parsesimpleroutecoords(routexml);
                    if(route != false)
                        partialroutes.push(route);
                }, function(){
                    partialroutesloaded = false;
                    alert('Die Route konnte nicht mit diesem Wegpunkt geladen werden!');
                });
            }
        },


        drawPartialRoutes : function(partialroutearr){
			
            if(partialroutesloaded){
                combinedlatlns = new Array();
				
                for(var j=0;j<partialroutearr.length;j++){
                    var partialroute = partialroutearr[j];
					
                    for(var k=0; k<partialroute.length; k++){

                        combinedlatlns.push(partialroute[k]);
                    //	                	if(!(j>0 && k==0)){
                    //	                    }
                    }
                }
				
                //configure and draw polyline from route points
                mappolyline.setMap(null);
                mappolyline.setPath(combinedlatlns);
                //draw polyline onto map
                mappolyline.setMap(map);
					
                //create bounds object
                var latlngbounds = new google.maps.LatLngBounds();
                //add all route points to the bounds, to allow zooming to route span
                var len = combinedlatlns.length;
                for(var l=0; l<len;l++){
                    latlngbounds.extend(combinedlatlns[l]);
                }
					
                //adjust map zoom and center
                map.fitBounds(latlngbounds);
            //map.setCenter(latlngbounds.getCenter());
            }
            else{
        //draw something, if partial routes cannot be loaded - currently route stays unchanged + infomsg
        }
        },
		
        addCorrectionPoint : function(){
	
            var pos = '';
			
            /*if(routecorrectionptlatlns!='' && routecorrectionptlatlns.length > 0)
			
			if(routecorrectionptlatlns.length && routecorrectionptlatlns.length>2){
				markerposindex+=1;
				if(markerposindex<routepathlatlns.length-1);
					pos = routepathlatlns[1];
			}*/
            pos = routepathlatlns[routepathlatlns.length-2];
	            
            var ptimage = new google.maps.MarkerImage(wayPtIconPath,
                new google.maps.Size(wayPtIconSizeX, wayPtIconSizeY),
                new google.maps.Point(0,0),
                new google.maps.Point(wayPtIconSizeX/2+2, (wayPtIconSizeY)));
            
            var ptshadow = new google.maps.MarkerImage(defShadowPath,
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(-6, 35));
            
            var ptmarker = new google.maps.Marker({
                position: pos,
                map: map,
                shadow: ptshadow,
                icon: ptimage,
                //shape: shape,
                draggable: true,
                clickable: false
            });
            
            var lastpos = ptmarker.getPosition();

            google.maps.event.addListener(ptmarker, "dragstart", function() {
                lastpos = ptmarker.getPosition();
            });
			
            var mapmanagerTHIS = this;
            google.maps.event.addListener(ptmarker, "dragend", function() {
                mapmanagerTHIS.getPartialRoutes();
                if(partialroutesloaded){
                    setCenter(ptmarker.getPosition());
                    mapmanagerTHIS.drawPartialRoutes(partialroutes);
                }else ptmarker.setPosition(lastpos);
            });


            if(routecorrectionptlatlns == ''){
                routecorrectionptlatlns = new Array();
            }

            routecorrectionptlatlns.push(ptmarker);

        //            mapmanagerTHIS.getPartialRoutes();
        //            mapmanagerTHIS.drawPartialRoutes(partialroutes);
        },
		
        drawSimpleRoute : function(){
            //check, if route has been fetched from OR-Server
            if(routepathlatlns != 'undefined' && typeof routepathlatlns != 'undefined'){
                if(routepathlatlns.length>=2){
					
                    //configure and draw polyline from route points
                    mappolyline = new google.maps.Polyline({
                        path : routepathlatlns,
                        strokeColor: "#96bd0d",
                        strokeOpacity: 1.0,
                        strokeWeight: 2
                    });
					
                    //create and add start and destination marker
                    createMarker('mainstart', routepathlatlns[0]);
                    createMarker('maindst', routepathlatlns[routepathlatlns.length-1]);
					
                    //create bounds object
                    var bounds = new google.maps.LatLngBounds();
					
                    //add all route points to the bounds, to allow zooming to route span
                    var len = routepathlatlns.length;
                    for(var i=0; i<len;i++)
                    {
                        bounds.extend(routepathlatlns[i]);
                    }
					
                    //adjust map zoom and center
                    map.fitBounds(bounds);
                    //map.setCenter(bounds.getCenter());
					
                    //draw polyline onto map
                    mappolyline.setMap(map);
                }
            }else{// no route
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'no route (in draw)');
            }
			
        //overlay debug test
        /*var textMarker = DivMarker(map.getCenter(), map);
			textMarker.setText("test");*/
        },
		
        drawRouteWithViaPts : function(){
            //draw route of drive
            this.drawSimpleRoute();
			
            //add marker at rider fetch positions
            if (viastartptlatlns != 'undefined' && typeof viastartptlatlns != 'undefined') {
                for(var index=0;index<viastartptlatlns.length;index++){
                    
                    var mystartimage = new google.maps.MarkerImage(thumbiconpaths[(index%thumbiconpaths.length)],
                        new google.maps.Size(riderStartIconSizeX, riderStartIconSizeY),
                        new google.maps.Point(0,0),
                        new google.maps.Point(5, riderStartIconSizeY));
                        
                    var mystartshadow = new google.maps.MarkerImage(defShadowPath,
                        new google.maps.Size(29, 34),
                        new google.maps.Point(0,0),
                        new google.maps.Point(-6, 35));
                        
                    var mystartmarker = new google.maps.Marker({
                        position: viastartptlatlns[index],
                        map: map,
                        shadow: mystartshadow,
                        icon: mystartimage,
                        //shape: shape,
                        draggable: false,
                        clickable: false,
                        zIndex: index
                    });
                    ridermarkers.push(mystartmarker);
                }
            }
            else{
            //no via start points
            }
            if (viadestptlatlns != 'undefined' && typeof viadestptlatlns != 'undefined') {
                for(var index=0;index<viadestptlatlns.length;index++){
					
                    var mydestimage = new google.maps.MarkerImage(flagiconpaths[(index%flagiconpaths.length)],
                        new google.maps.Size(riderDestIconSizeX, riderDestIconSizeY),
                        new google.maps.Point(0,0),
                        new google.maps.Point(5, riderDestIconSizeY));
                        
                    var mydestshadow = new google.maps.MarkerImage(defShadowPath,
                        new google.maps.Size(29, 34),
                        new google.maps.Point(0,0),
                        new google.maps.Point(-6, 35));
                        
                    var mydestmarker = new google.maps.Marker({
                        position: viadestptlatlns[index],
                        map: map,
                        shadow: mydestshadow,
                        icon: mydestimage,
                        //shape: shape,
                        draggable: false,
                        clickable: false,
                        zIndex: index
                    });
                        
                    ridermarkers.push(mydestmarker);
                }
            }
            else{
        //no via dest points
        }
        },
		
        resetRiderMarkers : function(){
            for(var i=0;i<ridermarkers.length;i++){
                ridermarkers[i].setMap(null);
            }
            this.resetViaStartPoints();
            this.resetViaDestPoints();
            ridermarkers.length=0;
        },

        geocodeAddressFromInput: function (addressInputDivId){

            //avoid collision with "this" from google maps namespace"
            var mapmanagerTHIS = this;

            var address = document.getElementById(addressInputDivId).value;
            if(!geocoder)geocoder = new google.maps.Geocoder();
            if (geocoder) {
                geocoder.geocode( {
                    'address': address
                }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        mapCenter = results[0].geometry.location;
                        setCenter(mapCenter);
                        mapmanagerTHIS.updateAddressInfo(addressInputDivId);
                    } else {
                        address = "Geocode was not successful for the following reason: " + status;
                    }
                });
            }
        },

        insertRevGeocodedAddr : function(latLn, htmlelemid){
        	
            var latLng = correctPosition(new google.maps.LatLng(latLn.coords.latitude, latLn.coords.longitude));
            
            geocoder = new google.maps.Geocoder();
            if (geocoder) {
                geocoder.geocode({
                    'latLng': latLng
                }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[0]){
                            document.getElementById(htmlelemid).innerHTML = "Standort:" + results[0].formatted_address;
                            document.getElementById(htmlelemid).latln = results[0].geometry.location.lat() + ',' + results[0].geometry.location.lng();
                        }
                    } else {
                        document.getElementById(htmlelemid).innerHTML = "Standort: nicht ermittelbar!"
                        document.getElementById(htmlelemid).latln = 'none';
                    }
                });
            }
            document.getElementById(htmlelemid).innerHTML = "Standort: nicht ermittelbar!"
            document.getElementById(htmlelemid).latln = 'none';
        },

        setRevGeocodedAddr : function(lat, ln){

            //avoid collision with "this" from google maps namespace"
            var mapmanagerTHIS = this;

            var latLng = new google.maps.LatLng(lat, ln);
            if(!geocoder)geocoder = new google.maps.Geocoder();
            if (geocoder) {
                geocoder.geocode({
                    'latLng': latLng
                }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[0]){
                            mapmanagerTHIS.revgeocodedAddr = results[0].formatted_address;
                        }
                    } else {
                        mapmanagerTHIS.revgeocodedAddr = "Standort nicht ermittelbar!"
                    }
                });
            }
            mapmanagerTHIS.revgeocodedAddr = "Standort nicht ermittelbar!"
        },



        getLocationFromString : function(locationAsString){
            if(!geocoder)geocoder = new google.maps.Geocoder();
            if (geocoder) {
                geocoder.geocode( {
                    'address': locationAsString
                }, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        return results[0].geometry.location;
                    } else {
                        return DUMMYPOSITION;
                    }
                });
            }
        },

        getCenterPosition : function(){
            return mapCenter;
        },

        getLocationLatFromString : function(locationAsString){
            this.getLocationFromString(locationAsString).lat();
        },

        getLocationLonFromString : function(locationAsString){
            this.getLocationFromString(locationAsString).lng();
        },
		
        setMapToLocation : function(centerLatLon){
            setCenter(centerLatLon);
            this.updateAddressInfo(usedAdressInput);
        },
		
        zoomIn : function(){
            var currentZoom = map.getZoom();
            if(currentZoom < maximalZoom)
                map.setZoom(currentZoom + 1);
        },
		
        zoomOut : function(){
            var currentZoom = map.getZoom();
            if(currentZoom > 0)
                map.setZoom(currentZoom - 1);
        }

    };

    /**
     * Checks wether the given position is inside the used map material or not.
     * If so, the function returns the positions.
     * If not, a new valid position near to the given position is returned.
     */
    function correctPosition (latLng) {
        var correctedLatLng = DUMMYPOSITION;

        try{
            srvconn.GET('/OpenRideServer-RS/resources/users/'+fokus.openride.mobclient.controller.modules.mapmanager.username+'/routes/validate/'+latLng.lat()+','+latLng.lng(), false, function(coords) {
                var lat = coords.split(",")[0];
                var lng = coords.split(",")[1].split(";")[0];

                correctedLatLng = new google.maps.LatLng(lat,lng);

            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Die Koordinaten konnten zum Validieren leider nicht &uuml;bermittelt werden. Fehler: '+s)
            });
        }catch(e){
	
        }finally{
            return correctedLatLng;
        }
    }

    function createMarker(type, position){
        if(type == "default"){
            // Marker sizes are expressed as a Size of X,Y
            // where the origin of the image (0,0) is located
            // in the top left of the image.

            // Origins, anchor positions and coordinates of the marker
            // increase in the X direction to the right and in
            // the Y direction down.
            var image = new google.maps.MarkerImage(defIconPath,
                // This marker is 20 pixels wide by 32 pixels tall.
                new google.maps.Size(defIconSizeX, defIconSizeY),
                // The origin for this image is 0,0.
                new google.maps.Point(0,0),
                // The anchor for this image
                new google.maps.Point(0, (defIconSizeY)));
            var shadow = new google.maps.MarkerImage(defShadowPath,
                // The shadow image is larger in the horizontal dimension
                // while the position and offset are the same as for the main image.
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(-6, 35));
            // Shapes define the clickable region of the icon.
            // The type defines an HTML <area> element 'poly' which
            // traces out a polygon as a series of X,Y points. The final
            // coordinate closes the poly by connecting to the first
            // coordinate.
            /*var shape = {
        coord: [1, 1, 1, 20, 18, 20, 18 , 1],
        type: 'poly'
        };*/
            marker = new google.maps.Marker({
                position: position,
                map: map,
                shadow: shadow,
                icon: image,
                //shape: shape,
                draggable: true,
                clickable: true,
                zIndex: 5
            });
        }
        
        else if(type == 'mainstart'){
            
            var image = new google.maps.MarkerImage(mainstartIconPath,
                new google.maps.Size(mainstartIconSizeX, mainstartIconSizeY),
                new google.maps.Point(0,0),
                new google.maps.Point(mainstartIconSizeX/2, mainstartIconSizeY));
			
            var shadow = new google.maps.MarkerImage(defShadowPath,
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(5, 35));
            
            startmarker = new google.maps.Marker({
                position: position,
                map: map,
                shadow: shadow,
                icon: image,
                //shape: shape,
                draggable: false,
                clickable: false,
                zIndex: 100
            });
            marker = startmarker;
        }
        
        else if(type == 'maindst'){
            
            var image = new google.maps.MarkerImage(maindstIconPath,
                new google.maps.Size(maindstIconSizeX, maindstIconSizeY),
                new google.maps.Point(0,0),
                new google.maps.Point((maindstIconSizeX/2), maindstIconSizeY));
            
            var shadow = new google.maps.MarkerImage(defShadowPath,
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(-6, 35));
            
            destmarker = new google.maps.Marker({
                position: position,
                map: map,
                shadow: shadow,
                icon: image,
                //shape: shape,
                draggable: false,
                clickable: false,
                zIndex: 100
            });
        }
        
        else if(type == 'viastart'){
            
            var image = new google.maps.MarkerImage(mainviaptstartIconPath,
                new google.maps.Size(mainviaptstartIconSizeX, mainviaptstartIconSizeY),
                new google.maps.Point(0,0),
                new google.maps.Point(0, (mainviaptstartIconSizeY)));
            
            var shadow = new google.maps.MarkerImage(defShadowPath,
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(-6, 35));
            
            startmarker = new google.maps.Marker({
                position: position,
                map: map,
                shadow: shadow,
                icon: image,
                //shape: shape,
                title: 'test_username',
                draggable: false,
                clickable: false,
                zIndex: 10
            });
            
            marker = startmarker;
        }
        
        else if(type == 'viadst'){
            
            var image = new google.maps.MarkerImage(mainviaptdstIconPath,
                new google.maps.Size(mainviaptdstIconSizeX, mainviaptdstIconSizeY),
                new google.maps.Point(0,0),
                new google.maps.Point(0, (mainviaptdstIconSizeY)));
            
            var shadow = new google.maps.MarkerImage(defShadowPath,
                new google.maps.Size(29, 34),
                new google.maps.Point(0,0),
                new google.maps.Point(-6, 35));
            
            destmarker = new google.maps.Marker({
                position: position,
                map: map,
                shadow: shadow,
                icon: image,
                //shape: shape,
                draggable: false,
                clickable: false,
                zIndex: 10
            });
        }
    }
    
    function createViaPt(position){
    	
        var newMarker;
    	
        var image = new google.maps.MarkerImage(mainviaptstartIconPath,
            new google.maps.Size(mainviaptstartIconSizeX, mainviaptstartIconSizeY),
            new google.maps.Point(0,0),
            new google.maps.Point(0, (mainviaptstartIconSizeY)));
            
        var shadow = new google.maps.MarkerImage(defShadowPath,
            new google.maps.Size(29, 34),
            new google.maps.Point(0,0),
            new google.maps.Point(-6, 35));
            
        newMarker = new google.maps.Marker({
            position: position,
            map: map,
            shadow: shadow,
            icon: image,
            //shape: shape,
            draggable: true,
            clickable: false
        });
        return newMarker;
    }
	
    function setCenter (centerLatLon){
        mapCenter = correctPosition(centerLatLon);
        map.setCenter(mapCenter);
        marker.setPosition(mapCenter);
    }

}();
