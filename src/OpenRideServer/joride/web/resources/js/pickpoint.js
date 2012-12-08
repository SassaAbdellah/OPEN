                    //	/////////////////////////////////////////////////////////////
                    // create openlayers control object
                    // //////////////////////////////////////////////////////////////
    
                    OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
                        defaultHandlerOptions: {
                            'single': true, // enable zoom on single click
                            'double': true, // enable moving the marker on dblclick
                            'pixelTolerance': 0,
                            'stopSingle': false,
                            'stopDouble': false
                        },

                        initialize: function(options) {
                            this.handlerOptions = OpenLayers.Util.extend(
                            {}, this.defaultHandlerOptions
                        );
                            OpenLayers.Control.prototype.initialize.apply(
                            this, arguments
                        );
                            this.handler = new OpenLayers.Handler.Click(
                            this, {
                                // react to double mouse click only,
                                // ignore single mouseclic
                                'dblclick': this.moveMarker
                            }, this.handlerOptions
                        );
                        },
                
                        // move the marker to doubleclicked coordinates
                        moveMarker: function(e) {
                            var coord = map.getLonLatFromViewPortPx(e.xy);

                            // ///////////////////////////////////	
                            //  move the marker to current click position 
                            // ///////////////////////////////////
                            jorideMarkersLayer.clearMarkers();    
                            var nextMarker=new OpenLayers.Marker(coord); 
                            jorideMarkersLayer.addMarker(nextMarker);
                           
                            console.log("number of layers in map : "+map.getNumLayers());
                  
                            // /////////////////////////////////////////////////
                            // transform display friendly EPSG:90913 coordinate 
                            // back to latitude/Longitude EPSG:4236 
                            // /////////////////////////////////////////////////

                            var lonlat=coord.transform(
                            new OpenLayers.Projection("EPSG:900913"), 
                            new OpenLayers.Projection("EPSG:4326")
                        );

                            // set longitude and latitude in input controls	
                            // (i.e: input controls that have class lat or lon) 
                            $('#lat').val(lonlat.lat); 
                            $('#lon').val(lonlat.lon); 
                            
                            

                            $('#latSpan').html(lonlat.lat)
                            $('#lonSpan').html(lonlat.lon); 
                            
                         
                   
                            // no more actions on doubleclick (i.e: do not zoom in)
                            this.handler.stopDouble = true;
                        }

                    });

           
                    //	/////////////////////////////////////////////////////////////
                    // create openlayers control object
                    // //////////////////////////////////////////////////////////////
    
    





                    map = new OpenLayers.Map("mapdiv");
                    map.addLayer(new OpenLayers.Layer.OSM());

                    

                    var lonLat = new OpenLayers.LonLat( pLongitude , pLatitude).transform(
                    new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984 to Spherical Mercator Projection
                    map.getProjectionObject() //
                );
                    
                    



		       
                                     
                
                 var marker=new OpenLayers.Marker(lonLat);
                    jorideMarkersLayer.addMarker(marker);
                    map.setCenter (lonLat, pZoom );

                    // //////////////////////////////////////////
                    // add click handler control 
                    // //////////////////////////////////////////
		
                    var click = new OpenLayers.Control.Click();
                    map.addControl(click);
                    click.activate();



               





