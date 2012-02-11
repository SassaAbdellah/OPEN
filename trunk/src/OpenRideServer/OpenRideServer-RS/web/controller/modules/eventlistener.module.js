/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

fokus.openride.mobclient.controller.modules.uievents = function(){

    /* ------ private variabeles and methods ------ */

    //references to modules
    var mapmod              = fokus.openride.mobclient.controller.modules.mapmanager;
    var modulemanagermod    = fokus.openride.mobclient.controller.modules.modulemanager;
    var calendarpicker      = fokus.openride.mobclient.controller.modules.calendar;
    var offermod            = fokus.openride.mobclient.controller.modules.offer;
    var searchmod           = fokus.openride.mobclient.controller.modules.search;
    var nativemod           = fokus.openride.mobclient.controller.modules.nativemodule;
    var srvconn             = fokus.openride.mobclient.controller.serverconnector;
    var favmod              = fokus.openride.mobclient.controller.modules.favorites;

    var username = '';
    var profilepic = '';

    var timer = true;
	
    var showofferrouteUI = 'showofferrouteUI';

    var offerstartdropdownid = 'offerstartdropd';
    var offerdestdropdownid = 'offerdestdropd';
    var searchstartdropdownid = 'searchstartdropd';
    var searchdestdropdownid = 'searchdestdropd';

    //option elemt id's for setting user position address, when screen gets set to offer/search ui
    var offerstartselectcurrpos = 'offerstartselectcurrpos';
    var offerdestselectcurrpos = 'offerdestselectcurrpos';
    var searchstartselectcurrpos = 'searchstartselectcurrpos';
    var searchdestselectcurrpos = 'searchdestselectcurrpos';

    var daylabel = 'dayLabel';
    var monthlabel = 'monthLabel';
    var yearlabel = 'yearLabel';

    var minutelabel = 'minuteLabel';
    var hourlabel = 'hourLabel';

    var dateLabels = new Array(daylabel, monthlabel, yearlabel);
    var timeLabels = new Array(hourlabel, minutelabel);

    var searchdaylabel = 'searchdayLabel';
    var searchmonthlabel = 'searchmonthLabel';
    var searchyearlabel = 'searchyearLabel';

    var searchminutelabel = 'searchminuteLabel';
    var searchhourlabel = 'searchhourLabel';

    var searchdateLabels = new Array(searchdaylabel, searchmonthlabel, searchyearlabel);
    var searchtimeLabels = new Array(searchhourlabel, searchminutelabel);

    var focusdatelabelid = daylabel;
    var focustimelabelid = hourlabel;

    /*var offersavetempllink = document.getElementById('offersavetempllink');
    var offersavetemplimg = document.getElementById('offersavetemplimg');*/

    var offertempl = false;
    var offeractive = false;

    var searchtempl = false;
    var searchactive = false;

    /*var searchfocusdatelabelid = searchdaylabel;
    var searchfocustimelabelid = searchhourlabel;*/

    function setLabelFocus(labels, id){
        if(labels.length>2){
            focusdatelabelid = id;
        }else focustimelabelid = id;
        
        for(var i=0; i< labels.length; i++){
            if(labels[i] == id){
                document.getElementById(id).className = 'labelStyleOnFocus';
            }else document.getElementById(labels[i]).className = 'labelStyle';
        }
    }

    function refreshSimpleCalendarPickerLabels(dlabels, tlabels){

        var days = calendarpicker.getDay();
        if(days < 10){
            var daysstr = '0' + days;
            document.getElementById(dlabels[0]).innerHTML = daysstr;
        }else document.getElementById(dlabels[0]).innerHTML = days;

        var months = calendarpicker.getMonth();
        if(months < 10){
            var monthstr = '0' + months;
            document.getElementById(dlabels[1]).innerHTML = monthstr;
        }else document.getElementById(dlabels[1]).innerHTML = months;

        document.getElementById(dlabels[2]).innerHTML = calendarpicker.getYear();

        var hours = calendarpicker.getHour();
        if(hours < 10){
            var hourstr = '0' + hours;
            document.getElementById(tlabels[0]).innerHTML = hourstr;
        }else document.getElementById(tlabels[0]).innerHTML = hours;

        var minutes = calendarpicker.getMin();
        if(minutes < 10){
            var minutestr = '0' + minutes;
            document.getElementById(tlabels[1]).innerHTML = minutestr;
        }else document.getElementById(tlabels[1]).innerHTML = minutes;
    }

    function confirmMapAddr(dropdownid){
        modulemanagermod.setTabContent(1, 0);
        var selectdiv = document.getElementById(dropdownid);
        var newoption = document.createElement('option');
        newoption.innerHTML = mapmod.currentFormattedAddress;
        //newoption.innerHTML = mapmod.currentFormattedAddress.substring(0, 24);
        
        newoption.latln = mapmod.getCenterPosition().lat()+','+mapmod.getCenterPosition().lng();
        newoption.mod = true;

        /*var lastopt = selectdiv.options[selectdiv.length-1];

        selectdiv.options[selectdiv.length] = lastopt;
        selectdiv.options[selectdiv.length-2] = newoption;*/

        try
        {
            selectdiv.add(newoption,null); // standards compliant
            selectdiv.selectedIndex = selectdiv.length-1;
        }
        catch(ex)
        {
        /*selectdiv.add(newoption); // IE only*/
        }
    /*document.getElementById(dropdownid).options[0].innerHTML = mapmod.currentFormattedAddress.substring(0, 24); */
    }
    
    /* ------ public variabeles and methods ------ */
    return {
		
        ajaxsuccess : false,

        validationerror : false,

        newfavaddrstring : "",

        parseInitData : function(initData){
            profilepic = initData.InitResponse.profilpic;
            username = initData.InitResponse.nickname;
            modulemanagermod.username = username;
            mapmod.username = username;
            document.getElementById('usernametag').innerHTML = username;
            var profileimg = document.getElementById('profilepicimg');
            var now = new Date();
            profileimg.src = "../.." + profilepic + "?" + now.getTime(); // time in search string forces reload

            // Home tab statistics
            document.getElementById("homeinfoopenoffers").innerHTML = initData.InitResponse.openoffers;
            if (initData.InitResponse.openoffers==1) {
                document.getElementById("homeinfoopenoffers-singular").style.display = 'inline';
                document.getElementById("homeinfoopenoffers-plural").style.display = 'none';
            }
            else {
                document.getElementById("homeinfoopenoffers-singular").style.display = 'none';
                document.getElementById("homeinfoopenoffers-plural").style.display = 'inline';
            }

            document.getElementById("homeinfoopensearches").innerHTML = initData.InitResponse.opensearches;
            if (initData.InitResponse.opensearches==1) {
                document.getElementById("homeinfoopensearches-singular").style.display = 'inline';
                document.getElementById("homeinfoopensearches-plural").style.display = 'none';
            }
            else {
                document.getElementById("homeinfoopensearches-singular").style.display = 'none';
                document.getElementById("homeinfoopensearches-plural").style.display = 'inline';
            }

            document.getElementById("homeinfoopenratings").innerHTML = initData.InitResponse.openratings;
            if (initData.InitResponse.openratings==1) {
                document.getElementById("homeinfoopenratings-plural").style.display = 'none';
            }
            else {
                document.getElementById("homeinfoopenratings-plural").style.display = 'inline';
            }

            // Update notifications
            modulemanagermod.setriderupdatecount(initData.InitResponse.updatedsearches);
            modulemanagermod.setdriverupdatecount(initData.InitResponse.updatedoffers);            

            // Profile data - upload form action
            document.getElementById("profilepictureform").action = "../resources/users/"+username+"/profile/picture";

            return true;
        },

        hideUnusedTabs : function(tabArray) {
            var id;

            for (id in tabArray) {
                document.getElementById(tabArray[id]).setAttribute("class", "inactiveTab");;
            }
        },

        unhideAllTabs : function() {
            var id;
            var tabArray = new Array("tabimg11", "tabimg12", "tabimg13", "tabimg14");

            for (id in tabArray) {
                document.getElementById(tabArray[id]).setAttribute("class", "tablevel1 img");
            }
        },

        timerStart : function() {
            timer = true;
        },

        refreshTimer : function() {
            if (timer) {
                fokus.openride.mobclient.controller.modules.calendar.reset();
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
            }
            setTimeout("fokus.openride.mobclient.controller.modules.uievents.refreshTimer()", 10000);
        },

        timerStop : function() {
            timer = false;
        },
        
        //init setup
        start: function(){
            modulemanagermod.setupTabs();
            this.setUpListeners();
            refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);

            // Restore previous mode from cookie
            if (readCookie('usermode') == 1) // Rider mode -> need to switch from default
                modulemanagermod.changemode();
            
            // Enable homeUI
            document.getElementById("homeUI_loading").style.display = "none";
            document.getElementById("homeUI_live").style.display = "block";

        },

        setUpListeners: function (){

            /* ------ home ui link elements ------ */
            var homeActiveOffers = document.getElementById("homeActiveOffers");

            homeActiveOffers.onclick = function () {
                modulemanagermod.changeViewAndUserMode('offers');
            };

            var homeActiveSearches = document.getElementById("homeActiveSearches");

            homeActiveSearches.onclick = function () {
                modulemanagermod.changeViewAndUserMode('searches');
            };


            var homeOpenRatings = document.getElementById("homeOpenRatings");

            homeOpenRatings.onclick = function () {
                modulemanagermod.changeViewAndUserMode('ratings');
            };


            /* ------ configure Date picker layout elements ------ */
            var dayLbl = document.getElementById(daylabel);
            var monthLbl = document.getElementById(monthlabel);
            var yearLbl = document.getElementById(yearlabel);

            var hourLbl = document.getElementById(hourlabel);
            var minuteLbl = document.getElementById(minutelabel);

            dayLbl.onclick = function(){
                setLabelFocus(dateLabels, daylabel);
            };

            monthLbl.onclick = function(){
                setLabelFocus(dateLabels, monthlabel);
            };

            yearLbl.onclick = function(){
                setLabelFocus(dateLabels, yearlabel);
            };

            hourLbl.onclick = function(){
                setLabelFocus(timeLabels, hourlabel);
            };

            minuteLbl.onclick = function(){
                setLabelFocus(timeLabels, minutelabel);
            };

            var dateuparrow = document.getElementById("dateuparrow");

            var dateuparrowlink = document.getElementById("dateuparrowlink");
            dateuparrowlink.href = "javascript:void(0);";

            dateuparrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focusdatelabelid == daylabel)
                    calendarpicker.increaseDay();
                else if(focusdatelabelid == monthlabel)
                    calendarpicker.increaseMonth();
                else if(focusdatelabelid == yearlabel)
                    calendarpicker.increaseYear();
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
            };

            var datedownarrow = document.getElementById("datedownarrow");

            var datedownarrowlink = document.getElementById("datedownarrowlink");
            datedownarrowlink.href = "javascript:void(0);";

            datedownarrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focusdatelabelid == daylabel)
                    calendarpicker.decreaseDay();
                else if(focusdatelabelid == monthlabel)
                    calendarpicker.decreaseMonth();
                else if(focusdatelabelid == yearlabel)
                    calendarpicker.decreaseYear();
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
            };
            
            var timeuparrow = document.getElementById("timeuparrow");

            var timeuparrowlink = document.getElementById("timeuparrowlink");
            timeuparrowlink.href = "javascript:void(0);";

            timeuparrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focustimelabelid == hourlabel)
                    calendarpicker.increaseHour();
                else if(focustimelabelid == minutelabel)
                    calendarpicker.increaseMin(5);
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
            };
            
            var timedownarrow = document.getElementById("timedownarrow");

            var timedownarrowlink = document.getElementById("timedownarrowlink");
            timedownarrowlink.href = "javascript:void(0);";

            timedownarrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focustimelabelid == hourlabel)
                    calendarpicker.decreaseHour();
                else if(focustimelabelid == minutelabel)
                    calendarpicker.decreaseMin(5);
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
            };

            /* ------ configure Date picker layout elements SEARCH ------ */
            var searchdayLbl = document.getElementById(searchdaylabel);
            var searchmonthLbl = document.getElementById(searchmonthlabel);
            var searchyearLbl = document.getElementById(searchyearlabel);

            var searchhourLbl = document.getElementById(searchhourlabel);
            var searchminuteLbl = document.getElementById(searchminutelabel);

            searchdayLbl.onclick = function(){
                setLabelFocus(searchdateLabels, searchdaylabel);
            };

            searchmonthLbl.onclick = function(){
                setLabelFocus(searchdateLabels, searchmonthlabel);
            };

            searchyearLbl.onclick = function(){
                setLabelFocus(searchdateLabels, searchyearlabel);
            };

            searchhourLbl.onclick = function(){
                setLabelFocus(searchtimeLabels, searchhourlabel);
            };

            searchminuteLbl.onclick = function(){
                setLabelFocus(searchtimeLabels, searchminutelabel);
            };

            var searchdateuparrow = document.getElementById("searchdateuparrow");

            var searchdateuparrowlink = document.getElementById("searchdateuparrowlink");
            searchdateuparrowlink.href = "javascript:void(0);";

            searchdateuparrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focusdatelabelid == searchdaylabel)
                    calendarpicker.increaseDay();
                else if(focusdatelabelid == searchmonthlabel)
                    calendarpicker.increaseMonth();
                else if(focusdatelabelid == searchyearlabel)
                    calendarpicker.increaseYear();
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
            };

            var searchdatedownarrow = document.getElementById("searchdatedownarrow");

            var searchdatedownarrowlink = document.getElementById("searchdatedownarrowlink");
            searchdatedownarrowlink.href = "javascript:void(0);";

            searchdatedownarrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focusdatelabelid == searchdaylabel)
                    calendarpicker.decreaseDay();
                else if(focusdatelabelid == searchmonthlabel)
                    calendarpicker.decreaseMonth();
                else if(focusdatelabelid == searchyearlabel)
                    calendarpicker.decreaseYear();
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
            };

            var searchtimeuparrow = document.getElementById("searchtimeuparrow");

            var searchtimeuparrowlink = document.getElementById("searchtimeuparrowlink");
            searchtimeuparrowlink.href = "javascript:void(0);";

            searchtimeuparrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focustimelabelid == searchhourlabel)
                    calendarpicker.increaseHour();
                else if(focustimelabelid == searchminutelabel)
                    calendarpicker.increaseMin(5);
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
            };

            var searchtimedownarrow = document.getElementById("searchtimedownarrow");

            var searchtimedownarrowlink = document.getElementById("searchtimedownarrowlink");
            searchtimedownarrowlink.href = "javascript:void(0);";

            searchtimedownarrow.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStop();
                if(focustimelabelid == searchhourlabel)
                    calendarpicker.decreaseHour();
                else if(focustimelabelid == searchminutelabel)
                    calendarpicker.decreaseMin(5);
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
            };

            setLabelFocus(dateLabels, daylabel);
            setLabelFocus(timeLabels, minutelabel);
            setLabelFocus(searchdateLabels, searchdaylabel);
            setLabelFocus(searchtimeLabels, searchminutelabel);


            /* ------ new offer related elements ------ */

            var newOfferDdArrow = document.getElementById('newOfferDdArrow');
            
            if(newOfferDdArrow) {
                newOfferDdArrow.onclick = function() {
                    var adrInput = document.getElementById('newOfferFrom');
                    adrInput.focus();
                }
            }

            var newOfferDestDdArrow = document.getElementById('newOfferDestDdArrow');

            if(newOfferDestDdArrow) {
                newOfferDestDdArrow.onclick = function() {
                    var adrInput = document.getElementById('newOfferDest');
                    adrInput.focus();
                }
            }

            var newofferdetaillink = document.getElementById('newofferdetaillink');
            newofferdetaillink.href = "javascript:void(0);";

            newofferdetaillink.onclick = function(){
                modulemanagermod.setView('newofferdetailsUI');
                return false;
            }

            var newoffersubmit = document.getElementById('newoffersubmit');
            newoffersubmit.href = "javascript:void(0);";

            newoffersubmit.onclick = function(){
                //set start time

                var minute = 1000*60;


                // Validation
                // Time & date
                if ((calendarpicker.getDate().getTime() + minute - new Date().getTime()) < 0) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Die Abfahrtszeit liegt in der Vergangenheit.');
                    return;
                }



                // OK, continue submitting:

                offermod.setStartTime(calendarpicker.getDate());

                //set start location
                //                var test = document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex]; // just for debugging...
                offermod.setStartLatLn(document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].latln);

                //set destination location
                offermod.setDestLatLn(document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].latln);

                /*currently no geocoding here, address-string from dropd's is used instead. it is actually cropped, but will hold the full adress text in substitution of selecvt with inputs
                 *
                //rev geocoding to get adress
                var startlatitude = offermod.getStartLat();
                var startlongititude = offermod.getStartLon();

                var destlatitude = offermod.getDestLat();
                var destlongititude = offermod.getDestLon();

                var startLatLn = new Object();
                var destLatLn = new Object();

                startLatLn.coords.latitude = startlatitude;
                startLatLn.coords.longitude = startlongititude;

                destLatLn.coords.latitude = destlatitude;
                destLatLn.coords.longitude = destlongititude;

                var startAddrContainer = document.createElement('div');
                startAddrContainer.id = 'startAddrCont';
                mapmod.insertRevGeocodedAddr(startLatLn, 'startAddrCont');

                var startAdressStr = startAddrContainer.innerHTML;

                var destAddrContainer = document.createElement('div');
                destAddrContainer.id = 'destAddrCont';
                mapmod.insertRevGeocodedAddr(destLatLn, 'destAddrCont');

                var destAdressStr = startAddrContainer.innerHTML;

                alert('Startadresse: ' + startAdressStr + 'Zieladresse: ' + destAdressStr);

                 */
                
                var determiningLocation = 'Standort: wird ermittelt...';

                if ((document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].text == determiningLocation) ||
                    (document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].text == determiningLocation)) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Ihr Standort konnte noch nicht ermittelt werden.');
                    return;
                }

                //                alert(document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].text);
                offermod.setStartAddr(document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].text);

                //                alert(document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].text);
                offermod.setDestAddr(document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].text);

                //set detour
                switch(document.getElementById('offerdetourselect').selectedIndex){
                    case 0:
                        offermod.setDetour(1);
                        break;
                    case 1:
                        offermod.setDetour(2);
                        break;
                    case 2:
                        offermod.setDetour(5);
                        break;
                    case 3:
                        offermod.setDetour(10);
                        break;
                    case 4:
                        offermod.setDetour(20);
                        break;
                    case 5:
                        offermod.setDetour(30);
                        break;
                }
                //set offered seats number
                offermod.setOfferedSeatsNo(document.getElementById('offerseatsselect').selectedIndex + 1);
                //set price
                switch(document.getElementById('offerpriceselect').selectedIndex){
                    case 0:
                        offermod.setPrice(1.8);
                        break;
                    case 1:
                        offermod.setPrice(1.9);
                        break;
                    case 2:
                        offermod.setPrice(2.0);
                        break;
                    case 3:
                        offermod.setPrice(2.1);
                        break;
                    case 4:
                        offermod.setPrice(2.2);
                        break;
                    case 5:
                        offermod.setPrice(2.3);
                        break;
                    default: // Für FOKUS-DAY
                        offermod.setPrice(1.8);
                }
                //set comment
                //                offermod.setComment(document.getElementById('offercommentta').value);
                offermod.setComment("F&uuml;r FOKUS-DAY aus der HTML Seite genommen!");

                //(validate and) commit new offer to server
                var eventlistenerTHIS = this;
                var serviceType = modulemanagermod.getServiceType();
                if ((offermod.getStartLat() != offermod.getDestLat()) || (offermod.getStartLon() != offermod.getDestLon())) {
                    if (serviceType == 'new') {
                        srvconn.POST('/OpenRideServer-RS/resources/users/'+username+'/rides/offers', false, offermod.validateOfferRequest(), function(data) {
                            eventlistenerTHIS.ajaxsuccess = true;
                            if(eventlistenerTHIS.ajaxsuccess) {
                                showOverlayDialog('Ihr Fahrtangebot wurde erfolgreich gespeichert.', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);', '', '');
                                slidingUITabListClick(document.getElementById('r'+data.PostOfferResponse.rideId));
                            }
                        }, function(x,s,e) {
                            fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihr neues Angebot konnte leider nicht gespeichert werden.');
                        });
                    } else {
                        srvconn.PUT('/OpenRideServer-RS/resources/users/'+username+'/rides/offers/'+modulemanagermod.getRideId(), false, offermod.validateOfferRequest(), function(data) {
                            eventlistenerTHIS.ajaxsuccess = true;
                            if(eventlistenerTHIS.ajaxsuccess) {
                                showOverlayDialog('Ihr Fahrtangebot wurde erfolgreich ge&auml;ndert.', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);', '', '');
                                slidingUITabListClick(document.getElementById('r'+data.PostOfferResponse.rideId));
                            }
                        }, function(x,s,e) {
                            fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihr ge&auml;ndertes Angebot konnte leider nicht gespeichert werden.');
                        });
                    }
                } else {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Bitte geben Sie unterschiedliche Addressen für Start und Ziel an.');
                }


            }

            var offershowroutepickerlink = document.getElementById('offershowroutepickerlink');
            offershowroutepickerlink.href = "javascript:void(0);";
			
            offershowroutepickerlink.onclick = function(){
				
                var startlatlnstr = document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].latln;
				
                var startseparatorindex = startlatlnstr.indexOf(',');
                var startlatstr = startlatlnstr.substr(0, startseparatorindex);
                var startlnstr = startlatlnstr.substr(startseparatorindex+1, startlatlnstr.length-startseparatorindex+1);
				
                var startlat = parseFloat(startlatstr);
                var startln = parseFloat(startlnstr);
				
                var dstlatlnstr = document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].latln;
				
                var dstseparatorindex = dstlatlnstr.indexOf(',');
                var dstlatstr = dstlatlnstr.substr(0, dstseparatorindex);
                var dstlnstr = dstlatlnstr.substr(dstseparatorindex+1, dstlatlnstr.length-dstseparatorindex+1);
				
                var dstlat = parseFloat(dstlatstr);
                var dstln = parseFloat(dstlnstr);
				
                //stop changing screens and give user info, if start equals destination
                if(startlat==dstlat && startln==dstln){				                   
                    showOverlayDialog('Start und Ziel d' +  unescape("%FC") + 'rfen nicht identisch sein, bitte neu ausw' +  unescape("%E4") + 'hlen!', '', 'OK', '', '', '');
                    return false;
                }

                srvconn.GET('/OpenRideServer-RS/resources/users/'+username+'/routes/new,'+startlat+','+startln+','+dstlat+','+dstln, false, function(routexml){
                    var routeFound = modulemanagermod.parsesimpleroute(routexml);
                    if(routeFound){
                        //modulemanagermod.setView('showofferrouteUI');
                        modulemanagermod.setFullScreenMapView('offerroutegmapscreencontainer');
                    }
                }, function(){
                    showOverlayDialog('Die Route konnte nicht geladen werden! Bitte versuche es sp' +  unescape("%E4") + 'ter erneut.', '', 'OK', '', '', '');
                });
            }
			
            var searchroutepickerlink = document.getElementById('searchroutepickerlink');
            searchroutepickerlink.href = "javascript:void(0);";
			
            searchroutepickerlink.onclick = function(){
				
                var startlatlnstr = document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex].latln;
				
                var startseparatorindex = startlatlnstr.indexOf(',');
                var startlatstr = startlatlnstr.substr(0, startseparatorindex);
                var startlnstr = startlatlnstr.substr(startseparatorindex+1, startlatlnstr.length-startseparatorindex+1);
				
                var startlat = parseFloat(startlatstr);
                var startln = parseFloat(startlnstr);
				
                var dstlatlnstr = document.getElementById(searchdestdropdownid)[document.getElementById(searchdestdropdownid).selectedIndex].latln;
				
                var dstseparatorindex = dstlatlnstr.indexOf(',');
                var dstlatstr = dstlatlnstr.substr(0, dstseparatorindex);
                var dstlnstr = dstlatlnstr.substr(dstseparatorindex+1, dstlatlnstr.length-dstseparatorindex+1);
				
                var dstlat = parseFloat(dstlatstr);
                var dstln = parseFloat(dstlnstr);
				
                //stop changing screens and give user info, if start equals destination
                if(startlat==dstlat && startln==dstln){
                    showOverlayDialog('Start und Ziel d' +  unescape("%FC") + 'rfen nicht identisch sein, bitte neu ausw' +  unescape("%E4") + 'hlen!', '', 'OK', '', '', '');                    
                    return false;
                }
					

                srvconn.GET('/OpenRideServer-RS/resources/users/'+username+'/routes/new,'+startlat+','+startln+','+dstlat+','+dstln, false, function(routexml){
                    var routeFound = modulemanagermod.parsesimpleroute(routexml);
                    if(routeFound){
                        modulemanagermod.setFullScreenMapView('searchroutegmapscreencontainer');
                    }
                //modulemanagermod.setView('showsearchrouteUI');
                }, function(){
                    showOverlayDialog('Die Route konnte nicht geladen werden! Bitte versuche es sp' +  unescape("%E4") + 'ter erneut.', '', 'OK', '', '', '');                   
                });
            }
			
            var offerroutebackbtnlink = document.getElementById('offerroutebackbtnlink');
            offerroutebackbtnlink.href = "javascript:void(0);";
			
            offerroutebackbtnlink.onclick = function(){
                modulemanagermod.setView('newofferUI');
            }
			
            var searchroutebackbtnlink = document.getElementById('searchroutebackbtnlink');
            searchroutebackbtnlink.href = "javascript:void(0);";
			
            searchroutebackbtnlink.onclick = function(){
                modulemanagermod.setView('newsearchUI');
            }

            var offerstartdropdown = document.getElementById(offerstartdropdownid);

            offerstartdropdown.onchange = function(){
                if(offerstartdropdown.selectedIndex == 0){
                    var userlocation = nativemod.getUserLocation();
                    mapmod.insertRevGeocodedAddr(userlocation, offerstartselectcurrpos);
                }
                else if(offerstartdropdown.selectedIndex == 1){
                    modulemanagermod.setFullScreenMapView('offerstartgmapscreencontainer');
                }
                if(offerstartdropdown.selectedIndex != 1){
                    offermod.setStartLatLn(offerstartdropdown[offerstartdropdown.selectedIndex].latln);
                }
            }

            var offerdestdropdown = document.getElementById(offerdestdropdownid);

            offerdestdropdown.onchange = function(){
                if(offerdestdropdown.selectedIndex == 0){
                    var userlocation = nativemod.getUserLocation();
                    mapmod.insertRevGeocodedAddr(userlocation, offerdestselectcurrpos);
                }
                else if(offerdestdropdown.selectedIndex == 1){
                    modulemanagermod.setFullScreenMapView('offerdestgmapscreencontainer');
                }
                if(offerdestdropdown.selectedIndex != 1){
                    offermod.setDestLatLn(offerdestdropdown[offerdestdropdown.selectedIndex].latln);
                }
            }

            var searchstartdropdown = document.getElementById(searchstartdropdownid);

            searchstartdropdown.onchange = function(){
                if(searchstartdropdown.selectedIndex == 0){
                    var userlocation = nativemod.getUserLocation();
                    mapmod.insertRevGeocodedAddr(userlocation, searchstartselectcurrpos);
                }
                else if(searchstartdropdown.selectedIndex == 1){
                    modulemanagermod.setFullScreenMapView('searchstartgmapscreencontainer');
                }
                if(searchstartdropdown.selectedIndex != 1){
                    searchmod.setStartLatLn(searchstartdropdown[searchstartdropdown.selectedIndex].latln);
                }
            }

            var searchdestdropdown = document.getElementById(searchdestdropdownid);

            searchdestdropdown.onchange = function(){
                if(searchdestdropdown.selectedIndex == 0){
                    var userlocation = nativemod.getUserLocation();
                    mapmod.insertRevGeocodedAddr(userlocation, searchdestselectcurrpos);
                }
                else if(searchdestdropdown.selectedIndex == 1){
                    modulemanagermod.setFullScreenMapView('searchdestgmapscreencontainer');
                }
                if(searchdestdropdown.selectedIndex != 1){
                    searchmod.setDestLatLn(searchdestdropdown[searchdestdropdown.selectedIndex].latln);
                }
            }

            var newsearchdetaillink = document.getElementById('newsearchdetaillink');
            newsearchdetaillink.href = "javascript:void(0);";

            newsearchdetaillink.onclick = function(){
                modulemanagermod.setView('newsearchdetailsUI');
                return false;
            }

            var newSearchFromDdArrow = document.getElementById('newSearchFromDdArrow');

            if(newSearchFromDdArrow) {
                newSearchFromDdArrow.onclick = function() {
                    var adrInput = document.getElementById('newSearchFrom');
                    adrInput.focus();
                }
            }

            var newSearchDestDdArrow = document.getElementById('newSearchDestDdArrow');

            if(newSearchDestDdArrow) {
                newSearchDestDdArrow.onclick = function() {
                    var adrInput = document.getElementById('newSearchDest');
                    adrInput.focus();
                }
            }

            var newsearchsubmit = document.getElementById('newsearchsubmit');
            newsearchsubmit.href = "javascript:void(0);";

            newsearchsubmit.onclick = function(){

                var minute = 1000*60;

                if ((calendarpicker.getDate().getTime() + minute - new Date().getTime()) < 0) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Die Abfahrtszeit liegt in der Vergangenheit.');
                    return;
                }

                //set start time
                searchmod.setStartTime(calendarpicker.getDate());

                var determiningLocation = 'Standort: wird ermittelt...';

                if ((document.getElementById(searchdestdropdownid)[document.getElementById(searchdestdropdownid).selectedIndex].text == determiningLocation) ||
                    (document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex].text == determiningLocation)) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Ihr Standort konnte noch nicht ermittelt werden.');
                    return;
                }


                //set start location
                //                var test = document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex]; // just for debugging...
                searchmod.setStartLatLn(document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex].latln);

                //set destination location
                searchmod.setDestLatLn(document.getElementById(searchdestdropdownid)[document.getElementById(searchdestdropdownid).selectedIndex].latln);
                searchmod.setSearchedSeatsNo(document.getElementById('searchseatsselect').selectedIndex + 1);

                searchmod.setStartAddr(document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex].text);

                searchmod.setDestAddr(document.getElementById(searchdestdropdownid)[document.getElementById(searchdestdropdownid).selectedIndex].text);

                //set price
                switch(document.getElementById('searchwaitimeselect').selectedIndex){
                    case 0:
                        searchmod.setMaxWaitingTime(10);
                        break;
                    case 1:
                        searchmod.setMaxWaitingTime(15);
                        break;
                    case 2:
                        searchmod.setMaxWaitingTime(20);
                        break;
                    case 3:
                        searchmod.setMaxWaitingTime(30);
                        break;
                    case 4:
                        searchmod.setMaxWaitingTime(45);
                        break;
                    case 5:
                        searchmod.setMaxWaitingTime(60);
                        break;
                    case 6:
                        searchmod.setMaxWaitingTime(120);
                        break;
                    case 7:
                        searchmod.setMaxWaitingTime(180);
                        break;
                    case 8:
                        searchmod.setMaxWaitingTime(240);
                        break;
                }
                //set comment
                //                searchmod.setComment(document.getElementById('searchcommentta').value);
                searchmod.setComment("F&uuml;r FOKUS-DAY aus der HTML-Seite genommen");
                //(validate and) commit new offer to server

                var eventlistenerTHIS = this;
                var serviceType = modulemanagermod.getServiceType();

                if ((searchmod.getStartLat() != searchmod.getDestLat()) || (searchmod.getStartLon() != searchmod.getDestLon())) {
                    if (serviceType == 'new') {
                        srvconn.POST('/OpenRideServer-RS/resources/users/'+username+'/rides/searches', false, searchmod.validateSearchRequest(), function(data) {
                            eventlistenerTHIS.ajaxsuccess = true;
                            if(eventlistenerTHIS.ajaxsuccess) {
                                showOverlayDialog('Ihr Gesuch wurde erfolgreich gespeichert.', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);', '', '');
                                slidingUITabListClick(document.getElementById('r'+data.PostSearchResponse.rideId));
                            }
                        }, function(x,s,e) {
                            fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihr neues Gesuch konnte leider nicht gespeichert werden.')
                        });
                    } else {
                        srvconn.PUT('/OpenRideServer-RS/resources/users/'+username+'/rides/searches/'+modulemanagermod.getRideId(), false, searchmod.validateSearchRequest(), function(data) {
                            eventlistenerTHIS.ajaxsuccess = true;
                            if(eventlistenerTHIS.ajaxsuccess) {
                                showOverlayDialog('Ihr Gesuch wurde erfolgreich ge&auml;ndert.', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);', '', '');
                                slidingUITabListClick(document.getElementById('r'+data.PostSearchResponse.rideId));
                            }
                        }, function(x,s,e) {
                            fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihr geändertes Gesuch konnte leider nicht gespeichert werden.')
                        });
                    }
                //                    var offerstartsel = document.getElementById(offerstartdropdownid);
                //                    if(offerstartsel.length > 2){
                //                        for(var k=2;k<offerstartsel.length;k++){
                //                            offerstartsel[k] = null;
                //                        }
                //                    }
                } else {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(null,'validateError',null,'Bitte geben Sie unterschiedliche Addressen für Start und Ziel an.');
                }
            }

            /*var offersavetempllink = document.getElementById('offersavetempllink');
            var offersavetemplimg = document.getElementById('offersavetemplimg');

            offersavetempllink.href = "javascript:void(0);";
            offersavetempllink.onclick = function(){
                if(!offertempl){
                    offersavetemplimg.src = '../img/checkactive.png';
                    offertempl = true;
                }else if(offertempl){
                    offersavetemplimg.src = '../img/checkinactive.png';
                    offertempl = false;
                }
            }*/

            /*var searchsavetempllink = document.getElementById('searchsavetempllink');
            var searchsavetemplimg = document.getElementById('searchsavetemplimg');

            searchsavetempllink.href = "javascript:void(0);";
            searchsavetempllink.onclick = function(){
                if(!searchtempl){
                    searchsavetemplimg.src = '../img/checkactive.png';
                    searchtempl = true;
                }else if(searchtempl){
                    searchsavetemplimg.src = '../img/checkinactive.png';
                    searchtempl = false;
                }
            }*/

            /*var offeractivelink = document.getElementById('offeractivelink');
            var offeractivateimg = document.getElementById('offeractivateimg');

            offeractivelink.href = "javascript:void(0);";
            offeractivelink.onclick = function(){
                if(!offeractive){
                    offeractivateimg.src = '../img/checkactive.png';
                    offeractive = true;
                }else if(offeractive){
                    offeractivateimg.src = '../img/checkinactive.png';
                    offeractive = false;
                }
            }*/

            /*var searchactivelink = document.getElementById('searchactivelink');
            var searchactivateimg = document.getElementById('searchactivateimg');

            searchactivelink.href = "javascript:void(0);";
            searchactivelink.onclick = function(){
                if(!searchactive){
                    searchactivateimg.src = '../img/checkactive.png';
                    searchactive = true;
                }else if(searchactive){
                    searchactivateimg.src = '../img/checkinactive.png';
                    searchactive = false;
                }
            }*/

            var resumebtnlink = document.getElementById('resumebtnlink');
            resumebtnlink.href  = "javascript:void(0);";

            resumebtnlink.onclick = function(){
                modulemanagermod.detailsClicked = true;
                modulemanagermod.setTabContent(1, 0);
            }

            var resumebtnlink2 = document.getElementById('resumebtnlink2');
            resumebtnlink2.href  = "javascript:void(0);";

            resumebtnlink2.onclick = function(){
                modulemanagermod.detailsClicked = true;
                modulemanagermod.setTabContent(1, 0);
            }

            /*var offerstartselectlink = document.getElementById('offerstartselectlink');
            offerstartselectlink.href = "javascript:void(0);";

            var offerstartdropd = document.getElementById('offerstartdropd');
            
            offerstartselectlink.onclick = function(){
                var clickevent=document.createEvent("MouseEvents");
                clickevent.initEvent("select", true, true);
                offerstartdropd.style.display = 'block';
                offerstartdropd.dispatchEvent(clickevent);
            }*/

            /* ------ configure full-screen gmap-related layout elements start ------ */
            
            //offer start
            var offerstartgmapaddressinput = document.getElementById('offerstartgmapaddressinput');
            offerstartgmapaddressinput.onchange = function(){
                mapmod.geocodeAddressFromInput('offerstartgmapaddressinput');
                mapmod.updateAddressInfo('offerstartgmapaddressinput');
            };
            var offerstartgmaplocateadressbtn = document.getElementById('offerstartgmaplocateadressbtn');
            offerstartgmaplocateadressbtn.onclick = function(){
                mapmod.geocodeAddressFromInput('offerstartgmapaddressinput');
                mapmod.updateAddressInfo('offerstartgmapaddressinput');
            };
            var offerstartgmapbackbtn = document.getElementById('offerstartgmapbackbtn');
            offerstartgmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            var offerstartgmapconfirmadressandbackbtn = document.getElementById('offerstartgmapconfirmadressandbackbtn');
            offerstartgmapconfirmadressandbackbtn.onclick = function(){
                modulemanagermod.detailsClicked = true;
                confirmMapAddr(offerstartdropdownid);
                offermod.setStartLat(mapmod.getCenterPosition().lat());
                offermod.setStartLon(mapmod.getCenterPosition().lng());
                modulemanagermod.returnFromFullscreenMapView();
            };
            var offerstartgmapzoominbtn = document.getElementById('offerstartgmapzoominbtn');
            offerstartgmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var offerstartgmapzoomoutbtn = document.getElementById('offerstartgmapzoomoutbtn');
            offerstartgmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
			
            //offer destintation
            var offerdestgmapaddressinput = document.getElementById('offerdestgmapaddressinput');
            offerdestgmapaddressinput.onchange = function(){
                mapmod.geocodeAddressFromInput('offerdestgmapaddressinput');
                mapmod.updateAddressInfo('offerdestgmapaddressinput');
            };
            var offerdestgmaplocateadressbtn = document.getElementById('offerdestgmaplocateadressbtn');
            offerdestgmaplocateadressbtn.onclick = function(){
                mapmod.geocodeAddressFromInput('offerdestgmapaddressinput');
                mapmod.updateAddressInfo('offerdestgmapaddressinput');
            };
            var offerdestgmapbackbtn = document.getElementById('offerdestgmapbackbtn');
            offerdestgmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            var offerdestgmapconfirmadressandbackbtn = document.getElementById('offerdestgmapconfirmadressandbackbtn');
            offerdestgmapconfirmadressandbackbtn.onclick = function(){
                modulemanagermod.detailsClicked = true;
                confirmMapAddr(offerdestdropdownid);
                offermod.setDestLat(mapmod.getCenterPosition().lat());
                offermod.setDestLon(mapmod.getCenterPosition().lng());
                modulemanagermod.returnFromFullscreenMapView();
            };
            var offerdestgmapzoominbtn = document.getElementById('offerdestgmapzoominbtn');
            offerdestgmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var offerdestgmapzoomoutbtn = document.getElementById('offerdestgmapzoomoutbtn');
            offerdestgmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            //search start
            var searchstartgmapaddressinput = document.getElementById('searchstartgmapaddressinput');
            searchstartgmapaddressinput.onchange = function(){
                mapmod.geocodeAddressFromInput('searchstartgmapaddressinput');
                mapmod.updateAddressInfo('searchstartgmapaddressinput');
            };
            var searchstartgmaplocateadressbtn = document.getElementById('searchstartgmaplocateadressbtn');
            searchstartgmaplocateadressbtn.onclick = function(){
                mapmod.geocodeAddressFromInput('searchstartgmapaddressinput');
                mapmod.updateAddressInfo('searchstartgmapaddressinput');
            };
            var searchstartgmapbackbtn = document.getElementById('searchstartgmapbackbtn');
            searchstartgmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            var searchstartgmapconfirmadressandbackbtn = document.getElementById('searchstartgmapconfirmadressandbackbtn');
            searchstartgmapconfirmadressandbackbtn.onclick = function(){
                modulemanagermod.detailsClicked = true;
                confirmMapAddr(searchstartdropdownid);
                searchmod.setStartLat(mapmod.getCenterPosition().lat());
                searchmod.setStartLon(mapmod.getCenterPosition().lng());
                modulemanagermod.returnFromFullscreenMapView();
            };
            var searchstartgmapzoominbtn = document.getElementById('searchstartgmapzoominbtn');
            searchstartgmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var searchstartgmapzoomoutbtn = document.getElementById('searchstartgmapzoomoutbtn');
            searchstartgmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };

            //search destination
            var searchdestgmapaddressinput = document.getElementById('searchdestgmapaddressinput');
            searchdestgmapaddressinput.onchange = function(){
                mapmod.geocodeAddressFromInput('searchdestgmapaddressinput');
                mapmod.updateAddressInfo('searchdestgmapaddressinput');
            };
            var searchdestgmaplocateadressbtn = document.getElementById('searchdestgmaplocateadressbtn');
            searchdestgmaplocateadressbtn.onclick = function(){
                mapmod.geocodeAddressFromInput('searchdestgmapaddressinput');
                mapmod.updateAddressInfo('searchdestgmapaddressinput');
            };
            var searchdestgmapbackbtn = document.getElementById('searchdestgmapbackbtn');
            searchdestgmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            var searchdestgmapconfirmadressandbackbtn = document.getElementById('searchdestgmapconfirmadressandbackbtn');
            searchdestgmapconfirmadressandbackbtn.onclick = function(){
                modulemanagermod.detailsClicked = true;
                confirmMapAddr(searchdestdropdownid);
                searchmod.setDestLat(mapmod.getCenterPosition().lat());
                searchmod.setDestLon(mapmod.getCenterPosition().lng());
                modulemanagermod.returnFromFullscreenMapView();
            };
            var searchdestgmapzoominbtn = document.getElementById('searchdestgmapzoominbtn');
            searchdestgmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var searchdestgmapzoomoutbtn = document.getElementById('searchdestgmapzoomoutbtn');
            searchdestgmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            //favorites
            var favoritesgmapaddressinput = document.getElementById('favoritesgmapaddressinput');
            favoritesgmapaddressinput.onchange = function(){
                mapmod.geocodeAddressFromInput('favoritesgmapaddressinput');
                mapmod.updateAddressInfo('favoritesgmapaddressinput');
            };
            var favoritesgmaplocateadressbtn = document.getElementById('favoritesgmaplocateadressbtn');
            favoritesgmaplocateadressbtn.onclick = function(){
                mapmod.geocodeAddressFromInput('favoritesgmapaddressinput');
                mapmod.updateAddressInfo('favoritesgmapaddressinput');
            };
            var favoritesgmapbackbtn = document.getElementById('favoritesgmapbackbtn');
            favoritesgmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
                modulemanagermod.setTabContent(3, 0);
            };
            var favoritesgmapconfirmadressandbackbtn = document.getElementById('favoritesgmapconfirmadressandbackbtn');
            favoritesgmapconfirmadressandbackbtn.onclick = function(){
                var coordstr = mapmod.getCenterPosition().lat() + ',' + mapmod.getCenterPosition().lng();
                favmod.setGeoCoordStr(coordstr);
                showOverlayDialog('Bitte geben Sie einen Namen für den Favoriten ein:', '<input id=\'favnameinput\' type=\'text\' style=\'width: 97.5%; background: url(../img/favstar.png) no-repeat center right\' />', 'Speichern', 'return fokus.openride.mobclient.controller.modules.uievents.addFavFromFullscreenMap();', 'Zur&uuml;ck', '');
            };
            var favoritesgmapzoominbtn = document.getElementById('favoritesgmapzoominbtn');
            favoritesgmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var favoritesgmapzoomoutbtn = document.getElementById('favoritesgmapzoomoutbtn');
            favoritesgmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            //simple offer route view
            var offerroutegmapbackbtn = document.getElementById('offerroutegmapbackbtn');
            offerroutegmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            /*var offerroutegmapaddptbtn = document.getElementById('offerroutegmapaddptbtn');
            offerroutegmapaddptbtn.onclick = function(){
                mapmod.addCorrectionPoint();
            };*/
            var offerroutegmapzoominbtn = document.getElementById('offerroutegmapzoominbtn');
            offerroutegmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var offerroutegmapzoomoutbtn = document.getElementById('offerroutegmapzoomoutbtn');
            offerroutegmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            //simple search route view
            var searchroutegmapbackbtn = document.getElementById('searchroutegmapbackbtn');
            searchroutegmapbackbtn.onclick = function(){
                modulemanagermod.returnFromFullscreenMapView();
            };
            var searchroutegmapzoominbtn = document.getElementById('searchroutegmapzoominbtn');
            searchroutegmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var searchroutegmapzoomoutbtn = document.getElementById('searchroutegmapzoomoutbtn');
            searchroutegmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            //route with viapoints view
            var viaptroutegmapbackbtn = document.getElementById('viaptroutegmapbackbtn');
            viaptroutegmapbackbtn.onclick = function(){
				mapmod.resetRiderMarkers();
                modulemanagermod.returnFromFullscreenMapView();
            };
            var viaptroutegmapzoominbtn = document.getElementById('viaptroutegmapzoominbtn');
            viaptroutegmapzoominbtn.onclick = function(){
                mapmod.zoomIn();
            };
            var viaptroutegmapzoomoutbtn = document.getElementById('viaptroutegmapzoomoutbtn');
            viaptroutegmapzoomoutbtn.onclick = function(){
                mapmod.zoomOut();
            };
            
            /* ------ configure full-screen gmap-related layout elements end ------ */
            
            
            //old - to be removed after testing new fullscreen version
            /* ------ configure map-related layout elements ------ */

            var offerstartaddrinput = document.getElementById('offerstartaddrinput');

            offerstartaddrinput.onchange = function(){
                mapmod.geocodeAddressFromInput('offerstartaddrinput');
                mapmod.updateAddressInfo('offerstartaddrinput');
            };

            var offerdestaddrinput = document.getElementById('offerdestaddrinput');

            offerdestaddrinput.onchange = function(){
                mapmod.geocodeAddressFromInput('offerdestaddrinput');
                mapmod.updateAddressInfo('offerdestaddrinput');
            };

            var searchstartaddrinput = document.getElementById('searchstartaddrinput');

            searchstartaddrinput.onchange = function(){
                mapmod.geocodeAddressFromInput('searchstartaddrinput');
                mapmod.updateAddressInfo('searchstartaddrinput');
            };

            var searchdestaddrinput = document.getElementById('searchdestaddrinput');

            searchdestaddrinput.onchange = function(){
                mapmod.geocodeAddressFromInput('searchdestaddrinput');
                mapmod.updateAddressInfo('searchdestaddrinput');
            };

            var offerstartconfirm = document.getElementById('offerstartconfirm');
            offerstartconfirm.href = "javascript:void(0);";

            offerstartconfirm.onclick = function(){
                confirmMapAddr(offerstartdropdownid);
                offermod.setStartLat(mapmod.getCenterPosition().lat());
                offermod.setStartLon(mapmod.getCenterPosition().lng());
            };

            var offerdestconfirm = document.getElementById('offerdestconfirm');
            offerdestconfirm.href = "javascript:void(0);";

            offerdestconfirm.onclick = function(){
                confirmMapAddr(offerdestdropdownid);
                offermod.setDestLat(mapmod.getCenterPosition().lat());
                offermod.setDestLon(mapmod.getCenterPosition().lng());
            };

            var searchstartconfirm = document.getElementById('searchstartconfirm');
            searchstartconfirm.href = "javascript:void(0);";

            searchstartconfirm.onclick = function(){
                confirmMapAddr(searchstartdropdownid);
            };

            var searchdestconfirm = document.getElementById('searchdestconfirm');
            searchdestconfirm.href = "javascript:void(0);";

            searchdestconfirm.onclick = function(){
                confirmMapAddr(searchdestdropdownid);
            };

            var newfavoriteconfirm = document.getElementById('newfavoriteconfirm');
            newfavoriteconfirm.href = "javascript:void(0);";

            newfavoriteconfirm.onclick = function(){
                var coordstr = mapmod.getCenterPosition().lat() + ',' + mapmod.getCenterPosition().lng();
                favmod.setGeoCoordStr(coordstr);
                showOverlayDialog('Bitte geben Sie einen Namen für den Favoriten ein:', '<input id=\'favnameinput\' type=\'text\' style=\'width: 97.5%; background: url(../img/favstar.png) no-repeat center right\' />', 'Favoriten anlegen', 'fokus.openride.mobclient.controller.modules.uievents.addFav();', 'Zur&uuml;ck', '');
            };

            var offerstartpickerlink = document.getElementById('offerstartpickerlink');
            offerstartpickerlink.href = "javascript:void(0);";

            offerstartpickerlink.onclick = function(){
                //modulemanagermod.setView('offerstartpickerUI');
                modulemanagermod.setFullScreenMapView('offerstartgmapscreencontainer');
                var index = document.getElementById(offerstartdropdownid).selectedIndex;
                if(index > 1){
                    var latlnstr = document.getElementById(offerstartdropdownid)[document.getElementById(offerstartdropdownid).selectedIndex].latln;
                    var Lat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                    var Ln = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
                    var dropDownSelectedLocation = new google.maps.LatLng(Lat, Ln);
                    mapmod.setMapToLocation(dropDownSelectedLocation);
                }
            };

            var offerdestpickerlink = document.getElementById('offerdestpickerlink');
            offerdestpickerlink.href = "javascript:void(0);";

            offerdestpickerlink.onclick = function(){
                //modulemanagermod.setView('offerdestpickerUI');
                modulemanagermod.setFullScreenMapView('offerdestgmapscreencontainer');
                var index = document.getElementById(offerdestdropdownid).selectedIndex;
                if(index > 1){
                    var latlnstr = document.getElementById(offerdestdropdownid)[document.getElementById(offerdestdropdownid).selectedIndex].latln;
                    var Lat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                    var Ln = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
                    var dropDownSelectedLocation = new google.maps.LatLng(Lat, Ln);
                    mapmod.setMapToLocation(dropDownSelectedLocation);
                }
            };

            var searchstartpickerlink = document.getElementById('searchstartpickerlink');
            searchstartpickerlink.href = "javascript:void(0);";

            searchstartpickerlink.onclick = function(){
                //modulemanagermod.setView('searchstartpickerUI');
                modulemanagermod.setFullScreenMapView('searchstartgmapscreencontainer');
                var index = document.getElementById(searchstartdropdownid).selectedIndex;
                if(index > 1){
                    var latlnstr = document.getElementById(searchstartdropdownid)[document.getElementById(searchstartdropdownid).selectedIndex].latln;
                    var Lat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                    var Ln = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
                    var dropDownSelectedLocation = new google.maps.LatLng(Lat, Ln);
                    mapmod.setMapToLocation(dropDownSelectedLocation);
                }
            };

            var searchdestpickerlink = document.getElementById('searchdestpickerlink');
            searchdestpickerlink.href = "javascript:void(0);";

            searchdestpickerlink.onclick = function(){
                //modulemanagermod.setView('searchdestpickerUI');
                modulemanagermod.setFullScreenMapView('searchdestgmapscreencontainer');
                var index = document.getElementById(searchdestdropdownid).selectedIndex;
                if(index > 1){
                    var latlnstr = document.getElementById(searchdestdropdownid)[document.getElementById(searchdestdropdownid).selectedIndex].latln;
                    var Lat = parseFloat(latlnstr.substr(0, latlnstr.indexOf(',')));
                    var Ln = parseFloat(latlnstr.substr(latlnstr.indexOf(',')+1, latlnstr.length-(latlnstr.indexOf(',')+1)));
                    var dropDownSelectedLocation = new google.maps.LatLng(Lat, Ln);
                    mapmod.setMapToLocation(dropDownSelectedLocation);
                }
            };


            /* ------ configure tab-related layout elements ------ */

            var tab01elem = document.getElementById('tab01link');



            tab01elem.href = "javascript:void(0);";

            tab01elem.onclick = function(){
                modulemanagermod.setTabContent(0, 0);
            };

            var tab02elem = document.getElementById('tab02link');
            tab02elem.href = "javascript:void(0);";

            tab02elem.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStart();
                calendarpicker.reset();
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);

                // delete addresses used for modifying an offer/search
                modulemanagermod.deleteModAdrFromBox(offerstartdropdownid);
                modulemanagermod.deleteModAdrFromBox(offerdestdropdownid);
                modulemanagermod.deleteModAdrFromBox(searchstartdropdownid);
                modulemanagermod.deleteModAdrFromBox(searchdestdropdownid);

                modulemanagermod.setServiceType('new');
                modulemanagermod.setTabContent(1, 0);
            };

            var tab03elem = document.getElementById('tab03link');
            tab03elem.href = "javascript:void(0);";

            tab03elem.onclick = function(){
                modulemanagermod.setTabContent(2, 0);
            };

            var tab04elem = document.getElementById('tab04link');
            tab04elem.href = "javascript:void(0);";

            tab04elem.onclick = function(){
                modulemanagermod.setTabContent(3, 0);
            };

            /*var tab05elem = document.getElementById('tab05link');
            tab05elem.href = "javascript:void(0);";

            tab05elem.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg12","tabimg13","tabimg14"));
                modulemanagermod.setTabContent(4, 0);
            };*/

            var tab11elem = document.getElementById('tab11link');
            tab11elem.href = "javascript:void(0);";

            tab11elem.onclick = function(){
                fokus.openride.mobclient.controller.modules.uievents.timerStart();
                calendarpicker.reset();
                refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
                refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);

                // delete addresses used for modifying an offer/search
                modulemanagermod.deleteModAdrFromBox(offerstartdropdownid);
                modulemanagermod.deleteModAdrFromBox(offerdestdropdownid);
                modulemanagermod.deleteModAdrFromBox(searchstartdropdownid);
                modulemanagermod.deleteModAdrFromBox(searchdestdropdownid);

                modulemanagermod.setServiceType('new');
                modulemanagermod.setTabContent(modulemanagermod.currentactivenodeindex, 0);
            };

            var tab12elem = document.getElementById('tab12link');
            tab12elem.href = "javascript:void(0);";

            tab12elem.onclick = function(){
                modulemanagermod.setTabContent(modulemanagermod.currentactivenodeindex, 1);
            };

            var tab13elem = document.getElementById('tab13link');
            tab13elem.href = "javascript:void(0);";

            tab13elem.onclick = function(){
                modulemanagermod.setTabContent(modulemanagermod.currentactivenodeindex, 2);
            };

            var tab14elem = document.getElementById('tab14link');
            tab14elem.href = "javascript:void(0);";

            tab14elem.onclick = function(){
                modulemanagermod.setTabContent(modulemanagermod.currentactivenodeindex, 3);
            };

            /* ------ configure home layout elements ------ */

            var usermodelink = document.getElementById('usermodelink');
            usermodelink.href = "javascript:void(0);";

            usermodelink.onclick = function(){
                var returnedmode = modulemanagermod.changemode();
                if(returnedmode == 0){
                    calendarpicker.reset();
                    refreshSimpleCalendarPickerLabels(dateLabels, timeLabels);
                    setLabelFocus(dateLabels, daylabel);
                    setLabelFocus(timeLabels, minutelabel);
                }
                else if(returnedmode == 1){
                    calendarpicker.reset();
                    refreshSimpleCalendarPickerLabels(searchdateLabels, searchtimeLabels);
                    setLabelFocus(searchdateLabels, searchdaylabel);
                    setLabelFocus(searchtimeLabels, searchminutelabel);
                }
            };

            /*var fastratelink = document.getElementById('fastratelink');
            fastratelink.href = "javascript:void(0);";

            fastratelink.onclick = function(){
                modulemanagermod.setTabContent(2, 0);
            };

            var fastpaymentlink = document.getElementById('fastpaymentlink');
            fastpaymentlink.href = "javascript:void(0);";

            fastpaymentlink.onclick = function(){
                modulemanagermod.setTabContent(4, 0);
            };*/

            var logoutlink = document.getElementById('logoutlink');
            logoutlink.href = "javascript:void(0);";

            logoutlink.onclick = function(){
                srvconn.PUTaction('/OpenRideServer-RS/resources/configuration/signoff', false);
                window.location.reload();
            };

        },

        //will be replaced by addFavFromFullscreenMap()
        addFav : function(){
            var inputaddr = document.getElementById('newfavoriteaddrinput').value;
            favmod.setAddress(inputaddr);

            var inputname = document.getElementById('favnameinput').value;
            favmod.setDisplayName(inputname);

            var coords = mapmod.getCenterPosition();
            favmod.setGeoCoords(coords.lat(), coords.lng());

            srvconn.POST('/OpenRideServer-RS/resources/users/'+username+'/favoritepoints', false, favmod.getFavPt(), function() {
                fokus.openride.mobclient.controller.modules.modulemanager.returnFromFullscreenMapView();
                showOverlayDialog('Neuer Ort erfolgreich gespeichert!', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setView(\'favlistUI\');', '', '')
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ein Ort mit diesem Namen existiert bereits.')
            } );
        },
        
        addFavFromFullscreenMap : function(){
            var inputaddr = document.getElementById('favoritesgmapaddressinput').value;
            favmod.setAddress(inputaddr);

            var inputname = document.getElementById('favnameinput').value.search(/^.{1,18}$/);
            if(inputname != -1) {
                inputname = document.getElementById('favnameinput').value;
                favmod.setDisplayName(inputname);

                var coords = mapmod.getCenterPosition();
                favmod.setGeoCoords(coords.lat(), coords.lng());

                srvconn.POST('/OpenRideServer-RS/resources/users/'+username+'/favoritepoints', false, favmod.getFavPt(), function() {
                    fokus.openride.mobclient.controller.modules.modulemanager.returnFromFullscreenMapView();
                    fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(3, 1);
                    showOverlayDialog('Neuer Ort erfolgreich gespeichert!', '', 'OK', '', '', '')
                }, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ein Ort mit diesem Namen existiert bereits.')
                    return false;
                } );
            } else {
                showOverlayDialog('Der Name des Favoriten darf bis zu 18 Zeichen lang sein.', '', 'OK', '', '', '');
                return false;
            }
        },

        // returns true if start/dest address are different
        validateDifferentAdr : function(offermod){
            var result = false;

            if ((offermod.getStartLat != offermodget.DestLat) || (offermod.getStartLon != offermod.getDestLon)) {
                result = true;
            }

            return result;
        }
    };
}();

var favs = new Array("Kaiserin Augusta Allee 31, Berlin","New York","Helmholtzstraße 9, Berlin");

function bindAutoSuggestEvents()
{
    //Find all of the INPUT tags
    var tags = document.getElementsByName('adrInput');
    for (i in tags)
    {
        var tag = tags[i];
        //If it's a text tag, attach an AutoSuggest object.
        if(tag.type && tag.type.toLowerCase() == "text")
        {
            new AutoSuggest(tag,favs);
        }
    }
}

function createCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name,"",-1);
}

window.onload = function(){
    fokus.openride.mobclient.controller.modules.uievents.start();
    fokus.openride.mobclient.controller.modules.uievents.refreshTimer();
    fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg13","tabimg14"));
    window.onresize();
    bindAutoSuggestEvents();

    // skip toolbar of mobile browsers:
    setTimeout(scrollTo, 0, 0, 1);

    // set body class "desktop" for non-mobile devices:
    if (!DetectMobileQuick()) {
        document.getElementsByTagName('body')[0].className = 'desktop';
    }
}
window.onresize = function(){
    adjustContentDimensions();
}

adjustContentDimensions = function() {

    // Adjusting only the height for now: innerHeight - tabmenu height (88px) - bottom border (5px)
    document.getElementById("content").style.minHeight = window.innerHeight - 88 - 5 + "px";
//document.getElementById("newfavoritepickerUI").style.height = document.getElementById("content").offsetHeight - 30 + "px";

}


