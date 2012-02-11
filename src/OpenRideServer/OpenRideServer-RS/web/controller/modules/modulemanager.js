/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
fokus.openride.mobclient.controller.modules.modulemanager = function(){

    /* ------ private variabeles and methods ------ */

    const eDiv = '</div>';

    var mapmod = fokus.openride.mobclient.controller.modules.mapmanager;
    var nativemod = fokus.openride.mobclient.controller.modules.nativemodule;
    var srvconn = fokus.openride.mobclient.controller.serverconnector;
    var calendar = fokus.openride.mobclient.controller.modules.calendar;

    var DRIVERMODE = 0;
    var RIDERMODE = 1;

    var usermode = DRIVERMODE;

    var offerstartdropdownid = 'offerstartdropd';
    var offerdestdropdownid = 'offerdestdropd';
    var searchstartdropdownid = 'searchstartdropd';
    var searchdestdropdownid = 'searchdestdropd';

    //option elemt id's for setting user position address, when screen gets set to offer/search ui
    var offerstartselectcurrpos = 'offerstartselectcurrpos';
    var offerdestselectcurrpos = 'offerdestselectcurrpos';
    var searchstartselectcurrpos = 'searchstartselectcurrpos';
    var searchdestselectcurrpos = 'searchdestselectcurrpos';

    // Determin wether create a new service or modify an existing
    var modifyService = "modify";
    var newService = "new";
    var serviceType = '';

    var rideId = '';

    var initialviewid = 'newofferUI';

    var activeMatchContentDiv;

    //arrays for the tab-related dom elements
    var tablinkslvl_0 = ['tab01link', 'tab02link', 'tab03link', 'tab04link', 'tab05link'];
    var tabimgslvl_0 = ['tabimg01', 'tabimg02', 'tabimg03', 'tabimg04'];
    var tabcontentdivslvl_0 = ['tab01link', 'tab02link', 'tab03link', 'tab04link'];

    var tablinkslvl_1 = ['tab11link', 'tab12link', 'tab13link', 'tab14link'];
    var tabimgslvl_1 = ['tabimg11', 'tabimg12', 'tabimg13', 'tabimg14'];
    var tabcontentdivslvl_1 = ['tab01link', 'tab02link', 'tab03link', 'tab04link'];

    var tmpRideId = '';
    var tmpRide = '';

    var driverupdatecount = '';
    var riderupdatecount = '';

    /* ------ data structure for tab menu tree - for driver and rider usermode ------ */

    //driver mode tree data

    var drivernode0 = {
        imgsrc : '../img/tab0home_inact_wide.png',
        imgactivesrc : '../img/tab0home_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/home1green_wide.png',
            imgactivesrc : '../img/home1white_wide.png',
            contentdivid : 'homeUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1profilegreen_wide.png',
            imgactivesrc : '../img/tab1profilewhite_wide.png',
            contentdivid : 'profileUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var drivernode1 = {
        imgsrc : '../img/tab0driver_inact_wide.png',
        imgactivesrc : '../img/tab0driver_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1NeuesAngebot_wide.png',
            imgactivesrc : '../img/tab1NeuesAngebotActive_wide.png',
            contentdivid : 'newofferUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1MeineAngebote_wide.png',
            imgactivesrc : '../img/tab1MeineAngeboteActive_wide.png',
            contentdivid : 'activeofferUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1AlteAngebote_wide.png',
            imgactivesrc : '../img/tab1AlteAngeboteActive_wide.png',
            contentdivid : 'completedtripsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var drivernode2 = {
        imgsrc : '../img/tab0thumb_inact_wide.png',
        imgactivesrc : '../img/tab0ride_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1ratinggreen_wide.png',
            imgactivesrc : '../img/tab1ratingwhite_wide.png',
            contentdivid : 'ratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1openratsgreen_wide.png',
            imgactivesrc : '../img/tab1openratswhite_wide.png',
            contentdivid : 'openratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1receivedratsgreen_wide.png',
            imgactivesrc : '../img/tab1receivedratswhite_wide.png',
            contentdivid : 'receivedratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var drivernode3 = {
        imgsrc : '../img/tab0star_inact_wide.png',
        imgactivesrc : '../img/tab0star_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1favlistgreen_wide.png',
            imgactivesrc : '../img/tab1favlistwhite_wide.png',
            contentdivid : 'favlistUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1newfavgreen_wide.png',
            imgactivesrc : '../img/tab1newfavwhite_wide.png',
            contentdivid : 'newfavoritepickerUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var drivernode4 = {
        imgsrc : '../img/tab0euro_inact.png',
        imgactivesrc : '../img/tab0euro_act.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        }
        ]
    };

    //rider mode tree data
    var ridernode0 = {
        imgsrc : '../img/tab0home_inact_wide.png',
        imgactivesrc : '../img/tab0home_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/home1green_wide.png',
            imgactivesrc : '../img/home1white_wide.png',
            contentdivid : 'homeUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1profilegreen_wide.png',
            imgactivesrc : '../img/tab1profilewhite_wide.png',
            contentdivid : 'profileUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var ridernode1 = {
        imgsrc : '../img/tab0rider_inact_wide.png',
        imgactivesrc : '../img/tab0rider_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1NeuesGesuch_wide.png',
            imgactivesrc : '../img/tab1NeuesGesuchActive_wide.png',
            contentdivid : 'newsearchUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1MeineGesuche_wide.png',
            imgactivesrc : '../img/tab1MeineGesucheActive_wide.png',
            contentdivid : 'activesearchUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1AlteGesuche_wide.png',
            imgactivesrc : '../img/tab1AlteGesucheActive_wide.png',
            contentdivid : 'completedtripsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var ridernode2 = {
        imgsrc : '../img/tab0thumb_inact_wide.png',
        imgactivesrc : '../img/tab0ride_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1ratinggreen_wide.png',
            imgactivesrc : '../img/tab1ratingwhite_wide.png',
            contentdivid : 'ratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1openratsgreen_wide.png',
            imgactivesrc : '../img/tab1openratswhite_wide.png',
            contentdivid : 'openratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1receivedratsgreen_wide.png',
            imgactivesrc : '../img/tab1receivedratswhite_wide.png',
            contentdivid : 'receivedratingsUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var ridernode3 = {
        imgsrc : '../img/tab0star_inact_wide.png',
        imgactivesrc : '../img/tab0star_act_wide.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1favlistgreen_wide.png',
            imgactivesrc : '../img/tab1favlistwhite_wide.png',
            contentdivid : 'favlistUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1newfavgreen_wide.png',
            imgactivesrc : '../img/tab1newfavwhite_wide.png',
            contentdivid : 'newfavoritepickerUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'dummyUI',
            isactive : false
        }
        ]
    };

    var ridernode4 = {
        imgsrc : '../img/tab0euro_inact.png',
        imgactivesrc : '../img/tab0euro_act.png',
        isavtive : false,
        leafs : [
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        },
        {
            imgsrc : '../img/tab1greentempl.png',
            imgactivesrc : '../img/tab1whitetempl.png',
            contentdivid : 'accountUI',
            isactive : false
        }
        ]
    };

    var drivernodes = [drivernode0, drivernode1, drivernode2, drivernode3];
    var ridernodes = [ridernode0, ridernode1, ridernode2, ridernode3];

    var DUMMYPOSITION; /*new google.maps.LatLng(52.525798, 13.314266);*/


    var activeofferlist = '';
    var activesearchlist = '';
    var simpleroutexml = '';
    var matchlist = '';
    var inactsearchmatchlist = '';
    var inactoffermatchlist = '';
    var ridelist = '';
    var favoritelist = '';
    var ratingssummary = '';
    var openratingslist = '';
    var receivedratingslist = '';
    var favnames = new Array();

    var modulemanagerTHIS = this;

    /* ------ public variabeles and methods ------ */
    return {
        displayedFullScreenMapId: '',

        username : "nick",

        offerfavsset : false,
        searchfavsset : false,
        modifyfavsset : false,

        activeofferlistdiv : 'activeofferlist',
        activeofferlisthtml : '',

        activesearchlistdiv : 'activesearchlist',
        activesearchlisthtml : '',

        completedtrips : 'completedtrips',
        completedtriplisthtml : '',

        favoritelistdiv : 'favlistUI',
        favoritelisthtml : '',

        ratingssummarydiv : 'ratingsUI',
        ratingssummaryhtml : '',

        openratingslistdiv : 'openratingsUI',
        openratingslisthtml : '',

        receivedratingslistdiv : 'receivedratingsUI',
        receivedratingslisthtml : '',

        currentdisplayedview : initialviewid,

        currentactivenodeindex : 0,
        currentactiveleafindex : 0,

        detailsClicked : false,

        clone : function (o) {
            function OneShotConstructor(){}
            OneShotConstructor.prototype = o;
            return new OneShotConstructor();
        },

        setupTabs: function(){
            //select tab tree depending on usermode
            var nodes;
            if(usermode==DRIVERMODE){
                nodes = drivernodes;
            }else if(usermode==RIDERMODE){
                nodes = ridernodes;
            }

            //traverse tree and set up dom elements
            for(var i=0; i< tabimgslvl_0.length; i++){
                if(i<nodes.length){
                    if(i==this.currentactivenodeindex){
                        document.getElementById(tabimgslvl_0[i]).src = nodes[i].imgactivesrc;
                        for(var j=0;j<tabimgslvl_1.length; j++){
                            if(i < nodes[i].leafs.length){
                                if(j==0){
                                    document.getElementById(tabimgslvl_1[j]).src = nodes[i].leafs[j].imgactivesrc;
                                }else
                                    document.getElementById(tabimgslvl_1[j]).src = nodes[i].leafs[j].imgsrc;
                            }
                        }
                    }else
                        document.getElementById(tabimgslvl_0[i]).src = nodes[i].imgsrc;
                }
            }
            this.setTabContent(0, 0);
        },

        setTabContent: function(acticvenodeindex, activeleafindex){

            //select tab tree depending on usermode
            var nodes;
            if(usermode==DRIVERMODE){
                nodes = drivernodes;
            }else if(usermode==RIDERMODE){
                nodes = ridernodes;
            }

            if(this.currentactivenodeindex==acticvenodeindex){
                if(this.currentactiveleafindex!=activeleafindex){
                    //set current leaf tab inactive
                    nodes[this.currentactivenodeindex].leafs[this.currentactiveleafindex].isactive = false;
                    //set current leaf tab image inactive
                    document.getElementById(tabimgslvl_1[this.currentactiveleafindex]).src = nodes[this.currentactivenodeindex].leafs[this.currentactiveleafindex].imgsrc;

                    //set new leaf tab active
                    nodes[acticvenodeindex].leafs[activeleafindex].isactive = true;
                    //set new leaf tab active image
                    document.getElementById(tabimgslvl_1[activeleafindex]).src = nodes[acticvenodeindex].leafs[activeleafindex].imgactivesrc;
                }
            }else {
                //set current node tab inactive
                nodes[this.currentactivenodeindex].isactive = false;
                //set current node tab image inactive
                document.getElementById(tabimgslvl_0[this.currentactivenodeindex]).src = nodes[this.currentactivenodeindex].imgsrc;

                //set new node tab active
                nodes[acticvenodeindex].isactive = true;
                //set new node tab active image
                document.getElementById(tabimgslvl_0[acticvenodeindex]).src = nodes[acticvenodeindex].imgactivesrc;

                //set current leaf tab inactive
                nodes[this.currentactivenodeindex].leafs[this.currentactiveleafindex].isactive = false;
                //set current leaf tab image inactive
                document.getElementById(tabimgslvl_1[this.currentactiveleafindex]).src = nodes[this.currentactivenodeindex].leafs[this.currentactiveleafindex].imgsrc;

                //set new leaf tab active
                nodes[acticvenodeindex].leafs[activeleafindex].isactive = true;
                //set new leaf tab active image
                document.getElementById(tabimgslvl_1[activeleafindex]).src = nodes[acticvenodeindex].leafs[activeleafindex].imgactivesrc;

                for(var i=0; i<nodes[acticvenodeindex].leafs.length;i++ ){
                    if(i != activeleafindex){
                        //set inactive leaf tab images
                        document.getElementById(tabimgslvl_1[i]).src = nodes[acticvenodeindex].leafs[i].imgsrc;
                    }
                }
            }

            //            //temporarily switch to fav-list (tab 3,1) instead of new favrotite (tab 3,0) for FOKUS DAY
            //            if(acticvenodeindex==3 && activeleafindex==0 && (this.currentactivenodeindex != 3 || this.currentactiveleafindex != 1) ){
            //                this.setTabContent(3,1);
            //                return false;
            //            }
            this.setView(nodes[acticvenodeindex].leafs[activeleafindex].contentdivid);
            this.currentactivenodeindex = acticvenodeindex;
            this.currentactiveleafindex = activeleafindex;
        },

        setActiveOfferList : function(list){
            activeofferlist = JSON.stringify(list);
        },

        setActiveSearchList : function(list){
            activesearchlist = JSON.stringify(list);
        },

        setMatches : function(list){
            matchlist = JSON.stringify(list);
        },

        setInactiveOfferMatches : function(list){
            inactoffermatchlist = 'undefined';
            inactoffermatchlist = JSON.stringify(list);
        },

        setInactiveSearchMatches : function(list){
            inactsearchmatchlist = 'undefined';
            inactsearchmatchlist = JSON.stringify(list);
        },

        setRide : function(offer){
            ridelist = JSON.stringify(offer);
        },

        setFavoriteList : function(list){
            favoritelist = JSON.stringify(list);
        },

        setRatingsSummary : function(list){
            ratingssummary = JSON.stringify(list);
        },

        setOpenRatingsList : function(list){
            openratingslist = JSON.stringify(list);
        },

        setReceivedRatingsList : function(list){
            receivedratingslist = JSON.stringify(list);
        },

        parsesimpleroute : function(routexml){
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

                    mapmod.setRoutePath(routearr);
                    return true;
                }else{//no route                    
                    showOverlayDialog('Es wurde keine Route gefunden!', '', 'OK', '', '', '');
                    return false;
                }
            }
        },

        parseactiveofferlist : function(){
            var result = JSON.parse(activeofferlist);
            var sb = new StringBuilder();

            var updatecount = 0;

            if(typeof (result.list) != 'undefined' && typeof (result.list[0].Offer) != 'undefined'){
                if(typeof (result.list[0].Offer.length) == 'undefined'){
                    result.list[0].Offer = [result.list[0].Offer];
                }
                for(var i=0;i< result.list[0].Offer.length; i++){
                    var entry = result.list[0].Offer[i];

                    var startDate = new Date(entry.ridestartTime);
                    var oday = startDate.getDate();
                    if(oday < 10)oday = '0'+oday;
                    var omonth = startDate.getMonth()+1;
                    if(omonth < 10)omonth = '0'+omonth;
                    var oyear = startDate.getFullYear();
                    var ohours = startDate.getHours();
                    if(ohours < 10)ohours = '0'+ohours;
                    var omin = startDate.getMinutes();
                    if(omin < 10)omin = '0'+omin;

                    sb.append('<li><a name="r'+entry.rideId+'"></a>');                    
                    if (entry.updated == true) {
                        sb.append('<h3 class="linkslide_0 updated" id="r'+entry.rideId+'">');
                        sb.append('<span class="update" style="float: right; background: red; color: #fff; border: 0px solid #fff; -moz-border-radius: 8px; border-radius: 8px; font-size: 12px; line-height: 18px; text-align: center; font-weight: bold; width: auto; padding: 0 6px; margin-right: 10px;">Update!</span>');
                        updatecount++;
                    }
                    else {
                        sb.append('<h3 class="linkslide_0" id="r'+entry.rideId+'">');
                    }
                    sb.append(oday +"."+omonth+"."+oyear+", "+ohours+":"+omin+' Uhr<br /><small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Start:</span> '+entry.startptAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endptAddress+'</small></h3>');
                    sb.append('<div class="slide_0"></div>');
                    sb.append('</li>');
                }
            }else{
                sb.clear();
                sb.append('<p>Keine aktiven Angebote vorhanden.</p>');
            }

            this.setdriverupdatecount(updatecount);

            document.getElementById(this.activeofferlistdiv).innerHTML = sb.toString();

            setupUITabList();
        },

        parseactivesearcheslist : function(){
            var result = JSON.parse(activesearchlist);
            var sb = new StringBuilder();

            var updatecount = 0;

            if(typeof (result.list) != 'undefined' && typeof (result.list[0].Search) != 'undefined'){
                if(typeof (result.list[0].Search.length) == 'undefined'){
                    result.list[0].Search = [result.list[0].Search];
                }
                for(var i=0;i< result.list[0].Search.length; i++){
                    var entry = result.list[0].Search[i];

                    var startDate = new Date(entry.ridestartTimeEarliest);
                    var oday = startDate.getDate();
                    if(oday < 10)oday = '0'+oday;
                    var omonth = startDate.getMonth()+1;
                    if(omonth < 10)omonth = '0'+omonth;
                    var oyear = startDate.getFullYear();
                    var ohours = startDate.getHours();
                    if(ohours < 10)ohours = '0'+ohours;
                    var omin = startDate.getMinutes();
                    if(omin < 10)omin = '0'+omin;

                    sb.append('<li>');                    
                    if (entry.updated == true) {
                        sb.append('<h3 class="linkslide_0 updated" id="r'+entry.riderRouteId+'">');
                        sb.append('<span class="update" style="float: right; background: red; color: #fff; border: 0px solid #fff; -moz-border-radius: 8px; border-radius: 8px; font-size: 12px; line-height: 18px; text-align: center; font-weight: bold; width: auto; padding: 0 6px; margin-right: 10px;">Update!</span>');
                        updatecount++;
                    }
                    else {
                        sb.append('<h3 class="linkslide_0" id="r'+entry.riderRouteId+'">');
                    }
                    sb.append(oday +"."+omonth+"."+oyear+", "+ohours+":"+omin+' Uhr<br />');

                    sb.append('<small><table>');
                    sb.append('<tr>');
                    sb.append('<td valign="top" align="right" style="color:#666666;">Abholort:</td>');
                    sb.append('<td valign="top">'+entry.startptAddress+'</td>');
                    sb.append('</tr>');
                    sb.append('<tr>');
                    sb.append('<td valign="top" align="right" style="color:#666666;">Ziel:</td>');
                    sb.append('<td valign="top">'+entry.endptAddress+'</td>');
                    sb.append('</tr>');
                    sb.append('</table></small></h3>');

                    //                    sb.append('<small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Abholort:</span> '+entry.startptAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endptAddress+'</small></h3>');
                    sb.append('<div class="slide_0"></div>');
                    sb.append('</li>');
                }
            }else{
                sb.clear();
                sb.append('<p>Keine aktiven Gesuche vorhanden.</p>');
            }

            this.setriderupdatecount(updatecount);

            document.getElementById(this.activesearchlistdiv).innerHTML = sb.toString();

            setupUITabList();
        },

        parsecompletedtriplist : function(){
            try {
                if(usermode==RIDERMODE)
                    var completedrides = JSON.parse(activesearchlist);
            } catch (e) {
            }

            try {
                if(usermode==DRIVERMODE)
                    var completeddrives = JSON.parse(activeofferlist);
            } catch (e) {
            }

            var completedtriplist = new Array();

            var completedridesarr = new Array();
            var completeddrivesarr = new Array();

            //generating html list from completedtriplist
            var sb = new StringBuilder();

            if(usermode == RIDERMODE){

                sb.append('<h3>Alte Gesuche</h3>');

                //add completed rides to completedtriplist
                if(typeof (completedrides.list) != 'undefined' && typeof (completedrides.list[0].Search) != 'undefined'){
                    if(typeof (completedrides.list[0].Search.length) == 'undefined'){
                        completedrides.list[0].Search = [completedrides.list[0].Search];
                    }
                    for(var i=0;i< completedrides.list[0].Search.length; i++){
                        var entry = completedrides.list[0].Search[i];
                        //completedridesarr.push(entry);

                        var startDate = new Date(entry.ridestartTimeEarliest);
                        var oday = startDate.getDate();
                        if(oday < 10)oday = '0'+oday;
                        var omonth = startDate.getMonth()+1;
                        if(omonth < 10)omonth = '0'+omonth;
                        var oyear = startDate.getFullYear();
                        var ohours = startDate.getHours();
                        if(ohours < 10)ohours = '0'+ohours;
                        var omin = startDate.getMinutes();
                        if(omin < 10)omin = '0'+omin;

                        sb.append('<li>');
                        sb.append('<h3 class="linkslide_0" id="r'+entry.riderRouteId+'">');
                        sb.append(oday +"."+omonth+"."+oyear+", "+ohours+":"+omin+' Uhr<br />');

                        sb.append('<small><table>');
                        sb.append('<tr>');
                        sb.append('<td valign="top" align="right" style="color:#666666;">Abholort:</td>');
                        sb.append('<td valign="top">'+entry.startptAddress+'</td>');
                        sb.append('</tr>');
                        sb.append('<tr>');
                        sb.append('<td valign="top" align="right" style="color:#666666;">Ziel:</td>');
                        sb.append('<td valign="top">'+entry.endptAddress+'</td>');
                        sb.append('</tr>');
                        sb.append('</table></small></h3>');

                        //                        sb.append('<small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Abholort:</span> '+entry.startptAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endptAddress+'</small></h3>');
                        sb.append('<div class="slide_0"></div>');
                        sb.append('</li>');
                    }
                }else{
                    sb.clear();
                    sb.append('<h3>Keine alten Gesuche vorhanden.</h3>');
                }
            }
            else if(usermode == DRIVERMODE){
                sb.append('<h3>Alte Angebote</h3>');

                //add completed drives to completedtriplist
                if(typeof (completeddrives.list) != 'undefined' && typeof (completeddrives.list[0].Offer) != 'undefined'){
                    if(typeof (completeddrives.list[0].Offer.length) == 'undefined'){
                        completeddrives.list[0].Offer = [completeddrives.list[0].Offer];
                    }
                    for(var i=0;i< completeddrives.list[0].Offer.length; i++){
                        var entry = completeddrives.list[0].Offer[i];
                        completeddrivesarr.push(entry);

                        var startDate = new Date(entry.ridestartTime);
                        var oday = startDate.getDate();
                        if(oday < 10)oday = '0'+oday;
                        var omonth = startDate.getMonth()+1;
                        if(omonth < 10)omonth = '0'+omonth;
                        var oyear = startDate.getFullYear();
                        var ohours = startDate.getHours();
                        if(ohours < 10)ohours = '0'+ohours;
                        var omin = startDate.getMinutes();
                        if(omin < 10)omin = '0'+omin;

                        sb.append('<li><a name="r'+entry.rideId+'"></a>');
                        sb.append('<h3 class="linkslide_0" id="r'+entry.rideId+'">');
                        sb.append(oday +"."+omonth+"."+oyear+", "+ohours+":"+omin+' Uhr<br /><small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Start:</span> '+entry.startptAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endptAddress+'</small></h3>');
                        sb.append('<div class="slide_0"></div>');
                        sb.append('</li>');
                    }
                }else{
                    sb.clear();
                    sb.append('<h3>Keine alten Angebote vorhanden.</h3>');
                }
            }

            document.getElementById(this.completedtrips).innerHTML = sb.toString();

            setupCompletedTripUITabList();
        },

        setupViaPtRoute : function(rideID){
            //
            srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+ rideID +'/route', false, function(routeResult){
                //implement success callback here

                //parse route coords
                var routearr = new Array();
                if(typeof (routeResult.list) != 'undefined' && typeof (routeResult.list[0].list[0].Coordinate) != 'undefined'){
                    /*if(typeof (routeResult.list[0].FavoritePointResponse.length) == 'undefined'){
                    	routeResult.list[0].FavoritePointResponse = [routeResult.list[0].FavoritePointResponse];
                	}*/
                    for(var i=0;i< routeResult.list[0].list[0].Coordinate.length; i++){
                        var entry = routeResult.list[0].list[0].Coordinate[i];

                        var coordLat = entry.latititude;
                        var coordLon = entry.longitude;

                        var latlnObj = new google.maps.LatLng(coordLat, coordLon);
                        routearr.push(latlnObj);
                    }
                    mapmod.setRoutePath(routearr);
                }else{//no route
                    showOverlayDialog('Es wurde keine Route gefunden!', '', 'OK', '', '', '');                    
                    return false;
                }

                //parse viapoint start coords
                var viastartptarr = new Array();
                if(typeof (routeResult.list[0].list[1].Coordinate) != 'undefined'){
                    if(typeof (routeResult.list[0].list[1].Coordinate.length) == 'undefined'){
                        routeResult.list[0].list[1].Coordinate = [routeResult.list[0].list[1].Coordinate];
                    }
                    for(var i=0;i< routeResult.list[0].list[1].Coordinate.length; i++){
                        var entry = routeResult.list[0].list[1].Coordinate[i];

                        var coordLat1 = entry.latititude;
                        var coordLon1 = entry.longitude;

                        var latlnObj1 = new google.maps.LatLng(coordLat1, coordLon1);
                        viastartptarr.push(latlnObj1);
                    }
                    mapmod.setViaStartPoints(viastartptarr);

                }else{//no route
                //return false;
                }
                
                //parse viapoint destination coords
                var viadestptarr = new Array();
                if(routeResult.list[0].list[2]){
                    if(typeof (routeResult.list[0].list[2].Coordinate) != 'undefined'){
                        if(typeof (routeResult.list[0].list[2].Coordinate.length) == 'undefined'){
                            routeResult.list[0].list[2].Coordinate = [routeResult.list[0].list[2].Coordinate];
                        }
                        for(var i=0;i< routeResult.list[0].list[2].Coordinate.length; i++){
                            var entry = routeResult.list[0].list[2].Coordinate[i];
	
                            var coordLat1 = entry.latititude;
                            var coordLon1 = entry.longitude;

                            var latlnObj1 = new google.maps.LatLng(coordLat1, coordLon1);
                            viadestptarr.push(latlnObj1);
                        }
                        mapmod.setViaDestPoints(viadestptarr);

                    }else{//no route
                //return false;
                }
                }

                fokus.openride.mobclient.controller.modules.modulemanager.setFullScreenMapView('viaptroutegmapscreencontainer');
                return true;
            }, function(){
                //implemet error callback here
                showOverlayDialog('Die Route konnte nicht ermittelt werden! Bitte pr&uuml;fen Sie Ihre Internetverbindung und versuchen Sie es erneut!', '', 'OK', '', '', '');               
                return false; 
            });
        },

        parsematcheslist : function(rideId, contentDiv){
            try {
                var result = JSON.parse(matchlist);
            } catch (e) {
                result = 'undefined';
            }
            var sb = new StringBuilder();
            this.tmpRide = '';

            if(typeof (result.list) != 'undefined' && typeof (result.list[0].MatchResponse) != 'undefined'){
                if(typeof (result.list[0].MatchResponse.length) == 'undefined'){
                    result.list[0].MatchResponse = [result.list[0].MatchResponse];
                }
                else {
                    // We have more than 1 match --> sort them by state (1st: booked, 2nd: undecided, 3rd: rejected + n/a)
                    result.list[0].MatchResponse = $(result.list[0].MatchResponse).sort(function(a, b) {
                        acc_a = a.driverState == 1 && a.riderState == 1;
                        acc_b = b.driverState == 1 && b.riderState == 1;
                        rej_a = fokus.openride.mobclient.controller.modules.modulemanager.isrejectedmatch(a.driverState, a.riderState);
                        rej_b = fokus.openride.mobclient.controller.modules.modulemanager.isrejectedmatch(b.driverState, b.riderState);
                        if ((acc_a && acc_b) || (rej_a && rej_b)) {
                            return 0;
                        }
                        if (acc_a) return -1;
                        if (acc_b) return 1;
                        if (rej_a) return 1;
                        if (rej_b) return -1;
                        return 0;

                    });
                }

                for(var i=0;i< result.list[0].MatchResponse.length; i++){
                    var entry = result.list[0].MatchResponse[i];
                    this.tmpRide = entry;

                    var startDate = new Date(entry.matchExpectedStartTime);
                    var oday = startDate.getDate();
                    if(oday < 10)oday = '0'+oday;
                    var omonth = startDate.getMonth()+1;
                    if(omonth < 10)omonth = '0'+omonth;
                    var oyear = startDate.getFullYear();
                    var ohours = startDate.getHours();
                    if(ohours < 10)ohours = '0'+ohours;
                    var omin = startDate.getMinutes();
                    if(omin < 10)omin = '0'+omin;

                    var priceEuro = entry.matchPriceCents/100;
                    priceEuro = priceEuro.toFixed(2).replace('.',',');

                    //DEBUG
                    //entry.riderState=1
                    //entry.driverState=0

                    var isrejected = this.isrejectedmatch(entry.driverState, entry.riderState);

                    var stateHighlightColor = '#fffacd'; // yellow
                    if (entry.driverState == 1 && entry.riderState == 1)
                        stateHighlightColor = '#f0fff0'; // green
                    else if (isrejected)
                        stateHighlightColor = '#ffe4e1'; // red

                    

                    // Beginning of matching row:
                    sb.append('<div class="matching-row" style="padding: 5px; border-top: 1px solid #e2e2e2;">');

                    // Show details only for unrejected matches:
                    if (!isrejected) {
                        sb.append('  <div class="profile-info-short" style="margin: 0 15px 0 0; float: left; text-align: right;">');
                        if (usermode == DRIVERMODE) {
                            sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.riderNickname + '_' + entry.riderCustId + '_thumb.jpg" alt="Profilbild von ' + entry.riderNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.riderRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                        }
                        else {
                            sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.driverNickname + '_' + entry.driverCustId + '_thumb.jpg" alt="Profilbild von ' + entry.driverNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.driverRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                        }
                        sb.append('  </div>');
                    }

                    sb.append('  <div style="margin: -5px -5px 3px 0; padding: 1px 5px 0px 5px; float: right; text-align: center; background: '+stateHighlightColor+'; min-width: 85px;">');
                    sb.append('    <form class="ride">');
                    sb.append(this.getMatchStateInfoControls(entry.rideid, entry.riderRouteId, entry.driverState, entry.riderState, contentDiv));
                    sb.append('    </form>');
                    sb.append('  </div>');

                    // Show details only for unrejected matches:
                    if (!isrejected) {

                        if (usermode == DRIVERMODE) {
                            var sharedDistanceKm = entry.matchSharedDistanceMeters/1000;
                            sharedDistanceKm = round(sharedDistanceKm,2);
                            var detourKm = entry.matchDetourMeters/1000;
                            detourKm = round(detourKm,2);

                            var start = entry.startPtAddress;
                            var end = entry.endPtAddress;

                            if (start.match("^.*:.*$")) {
                                start = start.split(':')[1];
                            }

                            if (end.match("^.*:.*$")) {
                                end = end.split(':')[1];
                            }

                            sb.append('  <div style="line-height: 150%; padding-left: 75px;">');
                            sb.append('    <strong>' + entry.riderNickname + '</strong> &ndash;<br />');
                            sb.append('    <small>Gesch&auml;tzter Mitnahmezeitpunkt: ' + ohours + ':' + omin + ' Uhr, Mitnahmestrecke: '+sharedDistanceKm+' km, Umweg: '+detourKm+' km, Preisvorschlag: ' + round(entry.matchPriceCents/100,2) + ' &euro;, Anz. Pers.: ' + entry.noOfPassengers);
                            if (typeof (entry.riderMobilePhoneNo) != 'undefined') {
                                sb.append(', Handy: <a href="tel:' + entry.riderMobilePhoneNo + '" class="phone">' + entry.riderMobilePhoneNo + '</a>');
                            }

                            sb.append('</small><small><table>');
                            sb.append('<tr>');
                            sb.append('<td valign="top" align="right" style="color:#666666;">Abholort:</td>');
                            sb.append('<td valign="top">'+start+'</td>');
                            sb.append('</tr>');
                            sb.append('<tr>');
                            sb.append('<td valign="top" align="right" style="color:#666666;">Ziel:</td>');
                            sb.append('<td valign="top">'+end+'</td>');
                            sb.append('</tr>');
                            sb.append('</table></small>');

                            //                            sb.append('</small><br style="clear: right;" /><small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Start:</span> '+start+'<br /><span style="margin-left: -34px;">Ziel:</span> '+end+'</small>');
                            sb.append('  </div>');
                        }
                        else {
                            sb.append(' <div style="line-height: 140%; padding-left: 75px;">');
                            sb.append('   <strong>' + entry.driverNickname + '</strong> &ndash;<br />');
                            sb.append('   <small>Gesch&auml;tzter Mitnahmezeitpunkt: ' + ohours + ':' + omin + ' Uhr, Preisvorschlag: ' + round(entry.matchPriceCents/100,2) + ' &euro;');
                            if (typeof (entry.driverMobilePhoneNo) != 'undefined') {
                                sb.append('<br />Handy: <a href="tel:' + entry.driverMobilePhoneNo + '" class="phone">' + entry.driverMobilePhoneNo + '</a>');
                                sb.append('<br />Auto: ' + entry.driverCarBrand + ', ' + entry.driverCarColour);
                                if (typeof (entry.driverCarBuildYear) != 'undefined' && entry.driverCarBuildYear != '') {
                                    sb.append(', Bj. ' + entry.driverCarBuildYear);
                                }
                                if (typeof (entry.driverCarPlateNo) != 'undefined' && entry.driverCarPlateNo != '') {
                                    sb.append(', Kennz. ' + entry.driverCarPlateNo);
                                }
                            }
                            sb.append('</small><br style="clear: both;" />');
                            sb.append(' </div>');
                        }
                    }
                    else {
                        sb.append(' <div style="line-height: 140%;">');
                        if (usermode == DRIVERMODE) {
                            sb.append('   <small><strong>' + entry.riderNickname + '</strong></small>');
                        }
                        else {
                            sb.append('   <small><strong>' + entry.driverNickname + '</strong></small>');
                        }
                        sb.append(' </div>');
                    }
                    sb.append('</div>');
                }
            }
            else{
                sb.clear();
                if (usermode == DRIVERMODE) {
                    sb.append('<p>Leider wurden bisher keine passenden Mitfahrer gefunden.</p>');
                }
                else {
                    var searchExternalLink = 'javascript:href="http://www.efa-bw.de/nvbw/XSLT_TRIP_REQUEST2?language=de"';
                    sb.append('<p>Leider wurden bisher keine passenden Fahrer gefunden.</p>');
                }
            }

            // Buttons to change or delete a ride. Show buttons only if driver and ride not accapted/declined the offer/search.
            var showRouteInvocation;
            var realRideId = rideId.replace('r','');
            var deleteRideInvocation = "javascript:showOverlayDialog('Diese Fahrt wirklich l&ouml;schen?', '', '      Ja      ', 'fokus.openride.mobclient.controller.modules.modulemanager.deleteRide("+realRideId+");', '     Nein     ', '');";
            showRouteInvocation = "javascript:fokus.openride.mobclient.controller.modules.modulemanager.setupViaPtRoute(\'"+realRideId+"\');";
            var modRide = "fokus.openride.mobclient.controller.modules.modulemanager.modifyRide("+realRideId+")";
            if ((typeof (this.tmpRide.driverState) == 'undefined') && (typeof (this.tmpRide.riderState) == 'undefined')) {
                sb.append('<form>');
                sb.append('  <div style="padding: 5px 0; text-align: center; clear: both;">');

                if(usermode == DRIVERMODE) {
                    sb.append('    <input type="button" class="rounded compact" value="&Auml;ndern" onclick="'+modRide+'" style="width: 72px;" />');
                    sb.append('    <input type="button" class="rounded compact" value="L&ouml;schen" onclick="'+deleteRideInvocation+'" style="width: 80px;" />');
                    sb.append('    <input type="button" class="rounded compact" value="Route anzeigen" onclick="'+showRouteInvocation+'" style="width: 130px;" />');
                } else {
                    sb.append('  <a href="http://www.efa-bw.de/nvbw/XSLT_TRIP_REQUEST2?language=de">Alternativ &uuml;ber die &Ouml;PNV-Fahrplanauskunft Ba-W&uuml; suchen</a><br /><br />');
                    sb.append('    <input type="button" class="rounded compact" value="&Auml;ndern" onclick="'+modRide+'" style="width: 141px;" />');
                    sb.append('    <input type="button" class="rounded compact" value="L&ouml;schen" onclick="'+deleteRideInvocation+'" style="width: 141px;" />');
                /*
                	var searchExternalLink = "javascript:window.location.href='http://www.efa-bw.de/nvbw/XSLT_TRIP_REQUEST2?language=de'";
			sb.append('    <br /><br /><input type="button" class="rounded compact" value="Im &Ouml;PNV suchen" onclick="'+searchExternalLink+'" style="width: 290px;" />');*/
                	
                /*Here the rider route (centered on the fetch point) wlil be linked to the button*/
                /*var showFetchPtInvocation = "fokus.openride.mobclient.controller.modules.modulemanager.setFullScreenMapView('searchroutegmapscreencontainer');"
                    sb.append('  <div style="padding: 5px 0; text-align: center;">');
                    sb.append('    <input type="button" class="rounded compact" value="Abholpunkt anzeigen" onclick="'+showFetchPtInvocation+'" style="width: 290px;" />');
                    sb.append('  </div>');*/
                }
                sb.append('  </div>');
                sb.append('</form>');
            } else {
                realRideId = rideId.replace('r','');
                showRouteInvocation = "javascript:fokus.openride.mobclient.controller.modules.modulemanager.setupViaPtRoute(\'"+realRideId+"\');";
                if(usermode == DRIVERMODE) {
                    sb.append('  <div style="padding: 5px 0; text-align: center;">');
                    sb.append('    <input type="button" class="rounded compact" value="Route anzeigen" onclick="'+showRouteInvocation+'" style="width: 290px;" />');
                    sb.append('  </div>');
                }
            }

            if (contentDiv) {
                activeMatchContentDiv = contentDiv;
            }

            activeMatchContentDiv[0].innerHTML = sb.toString();

            // Remove update notification
            if (activeMatchContentDiv.prev("h3").is(".updated")) {
                activeMatchContentDiv.prev("h3").removeClass("updated");
                activeMatchContentDiv.prev("h3").find("span.update").remove();
                if(usermode == DRIVERMODE) {
                    this.reducedriverupdatecount();
                }
                else {
                    this.reduceriderupdatecount();
                }
            }

        },

        parseinactivematcheslist : function(rideId, contentDiv){

            var sb = new StringBuilder();
            this.tmpRide = '';

            if(usermode == DRIVERMODE){
                var inactofferresult = '';
                try {
                    inactofferresult = JSON.parse(inactoffermatchlist);
                } catch (e) {
                    inactofferresult = 'undefined';
                }

                if(typeof (inactofferresult.list) != 'undefined' && typeof (inactofferresult.list[0].MatchResponse) != 'undefined'){
                    if(typeof (inactofferresult.list[0].MatchResponse.length) == 'undefined'){
                        inactofferresult.list[0].MatchResponse = [inactofferresult.list[0].MatchResponse];
                    }

                    for(var i=0;i< inactofferresult.list[0].MatchResponse.length; i++){
                        var entry = inactofferresult.list[0].MatchResponse[i];
                        this.tmpRide = entry;

                        var startDate = new Date(entry.matchExpectedStartTime);
                        var oday = startDate.getDate();
                        if(oday < 10)oday = '0'+oday;
                        var omonth = startDate.getMonth()+1;
                        if(omonth < 10)omonth = '0'+omonth;
                        var oyear = startDate.getFullYear();
                        var ohours = startDate.getHours();
                        if(ohours < 10)ohours = '0'+ohours;
                        var omin = startDate.getMinutes();
                        if(omin < 10)omin = '0'+omin;

                        var priceEuro = entry.matchPriceCents/100;
                        priceEuro = priceEuro.toFixed(2).replace('.',',');

                        //DEBUG
                        //entry.riderState=1
                        //entry.driverState=1

                        var isrejected = this.isrejectedmatch(entry.driverState, entry.riderState);

                        var stateHighlightColor = '#fffacd'; // yellow
                        if (entry.driverState == 1 && entry.riderState == 1)
                            stateHighlightColor = '#f0fff0'; // green
                        else if (isrejected)
                            stateHighlightColor = '#ffe4e1'; // red

                        // Beginning of matching row:
                        sb.append('<div class="matching-row" style="padding: 5px; border-top: 1px solid #e2e2e2;">');

                        // Show details only for unrejected matches:
                        if (entry.riderNickname) {
                            sb.append('  <div class="profile-info-short" style="margin: 0 15px 0 0; float: left; text-align: right;">');
                            if (entry.riderNickname) {
                                sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.riderNickname + '_' + entry.riderCustId + '_thumb.jpg" alt="Profilbild von ' + entry.riderNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.riderRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                            }
                            else {
                                sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.driverNickname + '_' + entry.driverCustId + '_thumb.jpg" alt="Profilbild von ' + entry.driverNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.driverRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                            }
                            sb.append('  </div>');
                        }

                        sb.append('  <div style="margin: -5px -5px 3px 0; padding: 1px 5px 0px 5px; float: right; text-align: center; background: '+stateHighlightColor+'; min-width: 85px;">');
                        sb.append('    <form class="ride">');
                        sb.append(this.getRatingStateInfoControls(entry.riderRouteId));
                        sb.append('    </form>');
                        sb.append('  </div>');

                        if (entry.riderNickname) {
                            var sharedDistanceKm = entry.matchSharedDistanceMeters/1000;
                            sharedDistanceKm = round(sharedDistanceKm,2);
                            var detourKm = entry.matchDetourMeters/1000;
                            detourKm = round(detourKm,2);

                            var start = entry.startPtAddress;
                            var end = entry.endPtAddress;

                            if (start.match("^.*:.*$")) {
                                start = start.split(':')[1];
                            }

                            if (end.match("^.*:.*$")) {
                                end = end.split(':')[1];
                            }

                            sb.append('  <div style="line-height: 150%; padding-left: 75px;">');
                            sb.append('    <strong>' + entry.riderNickname + '</strong> &ndash;<br />');
                            sb.append('    <small>' + ohours + ':' + omin + ' Uhr, Mitnahmestrecke: '+sharedDistanceKm+' km, Umweg: '+detourKm+' km, Preisvorschlag: ' + round(entry.matchPriceCents/100,2) + ' &euro;, Anz. Pers.: ' + entry.noOfPassengers);
                            if (typeof (entry.riderMobilePhoneNo) != 'undefined') {
                                sb.append(', Handy: <a href="tel:' + entry.riderMobilePhoneNo + '" class="phone">' + entry.riderMobilePhoneNo + '</a>');
                            }

                            sb.append('</small><small><table>');
                            sb.append('<tr>');
                            sb.append('<td valign="top" align="right" style="color:#666666;">Abholort:</td>');
                            sb.append('<td valign="top">'+start+'</td>');
                            sb.append('</tr>');
                            sb.append('<tr>');
                            sb.append('<td valign="top" align="right" style="color:#666666;">Ziel:</td>');
                            sb.append('<td valign="top">'+end+'</td>');
                            sb.append('</tr>');
                            sb.append('</table></small>');

                            //                            sb.append('</small><br style="clear: right;" /><small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Start:</span> '+entry.startPtAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endPtAddress+'</small>');
                            sb.append('  </div>');
                        }
                        else {
                            sb.append(' <div style="line-height: 140%; padding-left: 75px;">');
                            sb.append('   <strong>' + entry.driverNickname + '</strong> &ndash;<br />');
                            sb.append('   <small>' + ohours + ':' + omin + ' Uhr');
                            if (typeof (entry.driverMobilePhoneNo) != 'undefined') {
                                sb.append('<br />Handy: <a href="tel:' + entry.driverMobilePhoneNo + '" class="phone">' + entry.driverMobilePhoneNo + '</a>');
                                sb.append('<br />Auto: ' + entry.driverCarBrand + ', ' + entry.driverCarColour);
                                if (typeof (entry.driverCarBuildYear) != 'undefined' && entry.driverCarBuildYear != '') {
                                    sb.append(', Bj. ' + entry.driverCarBuildYear);
                                }
                                if (typeof (entry.driverCarPlateNo) != 'undefined' && entry.driverCarPlateNo != '') {
                                    sb.append(', Kennz. ' + entry.driverCarPlateNo);
                                }
                            }
                            sb.append('</small><br style="clear: both;" />');
                            sb.append(' </div>');
                        }
                        sb.append('</div>');
                    }
                }
                else{
            //sb.append('<p>Leider wurden f&uuml;r diese Fahrt keine passenden Mitfahrer gefunden.</p>');
            }
            }
            else if(usermode == RIDERMODE){
                var inactsearchresult = '';

                try {
                    inactsearchresult = JSON.parse(inactsearchmatchlist);
                }
                catch (e) {
                    inactsearchresult = 'undefined';
                }

                if(typeof (inactsearchresult.list) != 'undefined' && typeof (inactsearchresult.list[0].MatchResponse) != 'undefined'){
                    if(typeof (inactsearchresult.list[0].MatchResponse.length) == 'undefined'){
                        inactsearchresult.list[0].MatchResponse = [inactsearchresult.list[0].MatchResponse];
                    }

                    for(var i=0;i< inactsearchresult.list[0].MatchResponse.length; i++){
                        var entry = inactsearchresult.list[0].MatchResponse[i];
                        this.tmpRide = entry;

                        var startDate = new Date(entry.matchExpectedStartTime);
                        var oday = startDate.getDate();
                        if(oday < 10)oday = '0'+oday;
                        var omonth = startDate.getMonth()+1;
                        if(omonth < 10)omonth = '0'+omonth;
                        var oyear = startDate.getFullYear();
                        var ohours = startDate.getHours();
                        if(ohours < 10)ohours = '0'+ohours;
                        var omin = startDate.getMinutes();
                        if(omin < 10)omin = '0'+omin;

                        var priceEuro = entry.matchPriceCents/100;
                        priceEuro = priceEuro.toFixed(2).replace('.',',');

                        //DEBUG
                        //entry.riderState=1
                        //entry.driverState=1

                        var isrejected = this.isrejectedmatch(entry.driverState, entry.riderState);

                        var stateHighlightColor = '#fffacd'; // yellow
                        if (entry.driverState == 1 && entry.riderState == 1)
                            stateHighlightColor = '#f0fff0'; // green
                        else if (isrejected)
                            stateHighlightColor = '#ffe4e1'; // red

                        // Beginning of matching row:
                        sb.append('<div class="matching-row" style="padding: 5px; border-top: 1px solid #e2e2e2;">');

                        sb.append('  <div class="profile-info-short" style="margin: 0 15px 0 0; float: left; text-align: right;">');
                        if (entry.riderNickname) {
                            sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.riderNickname + '_' + entry.riderCustId + '_thumb.jpg" alt="Profilbild von ' + entry.riderNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.riderRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                        }
                        else {
                            sb.append('    <img src="../../OpenRideWeb/pictures/profile/' + entry.driverNickname + '_' + entry.driverCustId + '_thumb.jpg" alt="Profilbild von ' + entry.driverNickname + '" style="background: rgb(221, 221, 221); width: 60px; height: 60px; display: block; margin: 0 0 3px 0;" /><small>' + entry.driverRatingsRatioPercent + '% <img src="../img/rated_1.png" style="vertical-align: -3px;" /></small>');
                        }
                        sb.append('  </div>');

                        sb.append('  <div style="margin: -5px -5px 3px 0; padding: 1px 5px 0px 5px; float: right; text-align: center; background: '+stateHighlightColor+'; min-width: 85px;">');
                        sb.append('    <form class="ride">');
                        sb.append(this.getRatingStateInfoControls(entry.riderRouteId));
                        sb.append('    </form>');
                        sb.append('  </div>');

                        if (entry.riderNickname) {
                            var sharedDistanceKm = entry.matchSharedDistanceMeters/1000;
                            sharedDistanceKm = round(sharedDistanceKm,2);
                            var detourKm = entry.matchDetourMeters/1000;
                            detourKm = round(detourKm,2);

                            sb.append('  <div style="line-height: 150%; padding-left: 75px;">');
                            sb.append('    <strong>' + entry.riderNickname + '</strong> &ndash;<br />');
                            sb.append('    <small>' + ohours + ':' + omin + ' Uhr, Strecke: '+sharedDistanceKm+' km, Umweg: '+detourKm+' km, Preisvorschlag: ' + round(entry.matchPriceCents/100,2) + ' &euro;');
                            if (typeof (entry.riderMobilePhoneNo) != 'undefined') {
                                sb.append(', Handy: <a href="tel:' + entry.riderMobilePhoneNo + '" class="phone">' + entry.riderMobilePhoneNo + '</a>');
                            }
                            sb.append('</small><br style="clear: right;" /><small style="display: block; margin-left: 34px;"><span style="margin-left: -34px;">Start:</span> '+entry.startPtAddress+'<br /><span style="margin-left: -34px;">Ziel:</span> '+entry.endPtAddress+'</small>');
                            sb.append('  </div>');
                        }
                        else {
                            sb.append(' <div style="line-height: 140%; padding-left: 75px;">');
                            sb.append('   <strong>' + entry.driverNickname + '</strong> &ndash;<br />');
                            sb.append('   <small>' + ohours + ':' + omin + ' Uhr, Preisvorschlag: ' + round(entry.matchPriceCents/100,2) + ' &euro;');
                            if (typeof (entry.driverMobilePhoneNo) != 'undefined') {
                                sb.append('<br />Handy: <a href="tel:' + entry.driverMobilePhoneNo + '" class="phone">' + entry.driverMobilePhoneNo + '</a>');
                                sb.append('<br />Auto: ' + entry.driverCarBrand + ', ' + entry.driverCarColour);
                                if (typeof (entry.driverCarBuildYear) != 'undefined' && entry.driverCarBuildYear != '') {
                                    sb.append(', Bj. ' + entry.driverCarBuildYear);
                                }
                                if (typeof (entry.driverCarPlateNo) != 'undefined' && entry.driverCarPlateNo != '') {
                                    sb.append(', Kennz. ' + entry.driverCarPlateNo);
                                }
                            }
                            sb.append('</small><br style="clear: both;" />');
                            sb.append(' </div>');
                        }
                        sb.append('</div>');
                    }
                }
                else{
                    sb.clear();
                    sb.append('');
                //sb.append('<p>Leider wurde keine passenden Fahrer gefunden.</p>');
                }
            }

            // Buttons to change or delete a ride. Show buttons only if driver and ride not accapted/declined the offer/search.
            var showRouteInvocation;
            if ((typeof (this.tmpRide.driverState) == 'undefined') && (typeof (this.tmpRide.riderState) == 'undefined') && (usermode != RIDERMODE)) {
                var realRideId = rideId.replace('r','');
                //                var deleteRideInvocation = "javascript:showOverlayDialog('Diese Fahrt wirklich l&ouml;schen?', '', '      Ja      ', 'fokus.openride.mobclient.controller.modules.modulemanager.deleteRide("+realRideId+");', '     Nein     ', '');";
                showRouteInvocation = "javascript:fokus.openride.mobclient.controller.modules.modulemanager.setupViaPtRoute(\'"+realRideId+"\');";
                //                var modRide = "fokus.openride.mobclient.controller.modules.modulemanager.modifyRide("+realRideId+")";
                sb.append('<form>');
                sb.append('  <div style="padding: 5px 0; text-align: center; clear: both;">');
                //sb.append('    <input type="button" class="rounded compact" value="L&ouml;schen" onclick="'+deleteRideInvocation+'" style="width: 80px;" />');
                sb.append('    <input type="button" class="rounded compact" value="Route anzeigen" onclick="'+showRouteInvocation+'" style="width: 290px;" />');

                sb.append('  </div>');
                sb.append('</form>');
            }
            if (contentDiv) {
                activeMatchContentDiv = contentDiv;
            }

            activeMatchContentDiv[0].innerHTML = sb.toString();
        },

        isrejectedmatch : function(driverState, riderState) {
            return (driverState==0 || driverState==2 || driverState==3 || riderState==0 ||  riderState==2 || riderState==3);
        },

        getMatchStateInfoControls : function(rideId, riderRouteId, driverState, riderState, contentDiv) {

            var htmlInfoControls = '';

            if (driverState == 1 && riderState == 1) {
                // It is settled => Passenger / Driver view
                htmlInfoControls = '<small>Fahrt gebucht!</small>';
            }
            else if (driverState == 2 || riderState == 2) {
                // At least one of the parties canceled => canceled match view
                if (usermode == DRIVERMODE) {
                    if (driverState == 2) {
                        htmlInfoControls = '<small>Storniert</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Hat storniert</small>';
                    }
                }
                else if (usermode == RIDERMODE) {
                    if (riderState == 2) {
                        htmlInfoControls = '<small>Storniert</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Hat storniert</small>';
                    }
                }
            }
            else if (driverState == 3 || riderState == 3) {
                // At least one of the parties became unavailable
                if (usermode == DRIVERMODE) {
                    if (driverState == 3) {
                        htmlInfoControls = '<small>Ausgebucht</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Schon vergeben</small>';
                    }
                }
                else if (usermode == RIDERMODE) {
                    if (riderState == 3) {
                        htmlInfoControls = '<small>Schon vergeben</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Ausgebucht</small>';
                    }
                }
            }
            else if (driverState == 0 || riderState == 0) {
                // At least one of the parties rejected => rejected match view
                if (usermode == DRIVERMODE) {
                    if (driverState == 0) {
                        htmlInfoControls = '<small>Abgelehnt</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Hat abgelehnt</small>';
                    }
                }
                else if (usermode == RIDERMODE) {
                    if (riderState == 0) {
                        htmlInfoControls = '<small>Abgelehnt</small>';
                    }
                    else {
                        htmlInfoControls = '<small>Hat abgelehnt</small>';
                    }
                }
            }
            else {
                // We're still awaiting a response => candidate match view
                if (usermode == DRIVERMODE) {

                    acceptOnClickAction = 'fokus.openride.mobclient.controller.serverconnector.PUTaction(\'/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+ rideId +'/matches/'+ riderRouteId +'/accept\', false, function() {fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches(\'r'+rideId+'\', false)}, function(x,s,e) { fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,\'Dieses Gesuch ist inzwischen leider nicht mehr verf&uuml;gbar.\')})';
                    rejectOnClickAction = 'fokus.openride.mobclient.controller.serverconnector.PUTaction(\'/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+ rideId +'/matches/'+ riderRouteId +'/reject\', false, function() {fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches(\'r'+rideId+'\', false)}, function(x,s,e) { fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,\'Dieses Gesuch ist inzwischen leider nicht mehr verf&uuml;gbar.\')})';

                    if (riderState == 1) {
                        // Offer [Accept] and [Reject] buttons
                        htmlInfoControls = '<small>Will mitfahren</small><br />'
                        + '<input type="button" class="adjustleft" value="Annehmen" onclick="'+acceptOnClickAction+'" /><br />'
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }
                    else if (driverState == 1) {
                        // Offer [Reject] button
                        htmlInfoControls = '<small>Angefragt</small><br />'
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }
                    else {
                        // Offer [Request] and [Reject] buttons
                        htmlInfoControls = '<input type="button" value="Anfragen" onclick="'+acceptOnClickAction+'" /><br />'
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }

                }
                else if (usermode == RIDERMODE) {

                    acceptOnClickAction = 'fokus.openride.mobclient.controller.serverconnector.PUTaction(\'/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+ riderRouteId +'/matches/'+ rideId +'/accept\', false, function() {fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches(\'r'+riderRouteId+'\', false)}, function(x,s,e) { fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,\'Dieses Fahrtangebot ist inzwischen leider nicht mehr verf&uuml;gbar.\')})';
                    rejectOnClickAction = 'fokus.openride.mobclient.controller.serverconnector.PUTaction(\'/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+ riderRouteId +'/matches/'+ rideId +'/reject\', false, function() {fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches(\'r'+riderRouteId+'\', false)}, function(x,s,e) { fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,\'Dieses Fahrtangebot ist inzwischen leider nicht mehr verf&uuml;gbar.\')})';

                    if (riderState == 1) {
                        // Offer [Reject] button
                        htmlInfoControls = '<small>Angefragt</small><br />'
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }
                    else if (driverState == 1) {
                        // Offer [Book] and [Reject] buttons
                        htmlInfoControls = '<small>Bietet Fahrt an</small><br />'
                        + '<input type="button" value="Buchen" onclick="'+acceptOnClickAction+'" /><br />'
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }
                    else {
                        // Offer [Request] and [Reject] buttons
                        htmlInfoControls = '<input type="button" value="Anfragen" onclick="'+acceptOnClickAction+'" /><br /> '
                        + '<input type="button" value="Ablehnen" onclick="'+rejectOnClickAction+'" />';
                    }
                }
            }

            return htmlInfoControls;
        },

        getRatingStateInfoControls: function(matchRiderRouteID){

            var openratings = '';
            try{
                openratings = JSON.parse(openratingslist);
            }
            catch(e){
                openratings = '';
            }

            var htmlInfoControls = '';

            rateOnClickAction = 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(2, 1)';

            //check open ratings
            //if match has open ratings, show button linked to open rating UI
            if(typeof (openratings.list[0].OpenRatingResponse) != 'undefined'){
                if(typeof (openratings.list[0].OpenRatingResponse.length) == 'undefined'){
                    openratings.list[0].OpenRatingResponse = [openratings.list[0].OpenRatingResponse];
                }
                for(var i=0;i< openratings.list[0].OpenRatingResponse.length; i++){
                    var entry = openratings.list[0].OpenRatingResponse[i];
                    if(entry.riderRouteId == matchRiderRouteID)
                        htmlInfoControls =  '<input type="button" value="Bewerten" onclick="'+rateOnClickAction+'" />';
                }
            }

            return htmlInfoControls;
        },

        receiveMatches : function(rideId, contentDiv){
            // Create dynamic list depending on usermode
            if (usermode == DRIVERMODE) {
                // Get all matches for ride.
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+rideId.replace('r','')+'/matches',
                    false, this.setMatches, function(x,s,e) {
                        clearInterval(tabListActiveRefreshTimer);
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Dieses Fahrtangebot ist nicht mehr verf&uuml;gbar.')
                    });
            } else {
                // Get all matches for search.
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+rideId.replace('r','')+'/matches',
                    false, this.setMatches, function(x,s,e) {
                        clearInterval(tabListActiveRefreshTimer);
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Dieses Gesuch ist nicht mehr verf&uuml;gbar.')
                    });
            }
            this.parsematcheslist(rideId, contentDiv);
        },

        receiveInactiveMatches : function(rideId, contentDiv){
            if(usermode == DRIVERMODE){
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+rideId.replace('r','')+'/matches',
                    false, this.setInactiveOfferMatches, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Dieses Fahrtangebot ist nicht mehr verf&uuml;gbar.')
                    });
            }
            else if(usermode == RIDERMODE){
                // Get all matches for search.
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+rideId.replace('r','')+'/matches',
                    false, this.setInactiveSearchMatches, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Dieses Gesuch ist nicht mehr verf&uuml;gbar.')
                    });
            }

            this.parseinactivematcheslist(rideId, contentDiv);
        },

        receiveUpdates : function(){
            srvconn.GET('/OpenRideServer-RS/resources/configuration/updates',
                false, function(updateData) {
                    fokus.openride.mobclient.controller.modules.modulemanager.setriderupdatecount(updateData.UpdateResponse.updatedsearches);
                    fokus.openride.mobclient.controller.modules.modulemanager.setdriverupdatecount(updateData.UpdateResponse.updatedoffers);
                }, function(x,s,e) {
                    clearInterval(updateCountRefreshTimer);
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Fehler beim Empfangen der Update-Daten.')
                });
        },

        getServiceType : function() {
            return serviceType;
        },

        setServiceType : function(type) {
            serviceType = type;
        },

        getRideId : function() {
            return rideId;
        },

        deleteModAdrFromBox : function(ddBoxId) {
            var ddBox = document.getElementById(ddBoxId);
            if (ddBox.length > 0) {
                for (var i = 0; i < ddBox.length; i++) {
                    if (ddBox[i].mod) {
                        ddBox[i] = null;
                    }
                }
            }
        },

        modifyRide : function (rideid) {
            var ride = this.tmpRide;

            rideId = rideid;

            var optionStartAdr;
            var adrStartBox;
            var optionDestAdr;
            var adrDestBox;
            var startDate;
            var oday;
            var omonth;
            var oyear;
            var ohours;
            var omin;

            this.deleteModAdrFromBox(offerstartdropdownid);
            this.deleteModAdrFromBox(offerdestdropdownid);
            this.deleteModAdrFromBox(searchstartdropdownid);
            this.deleteModAdrFromBox(searchdestdropdownid);

            if (usermode == DRIVERMODE) {

                if (ride != '') {
                    rideid = ride.rideid;
                }
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+rideid,
                    false, this.setRide, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Das zu bearbeitende Angebot konnte leider nicht vom Server geladen werden.')
                    });

                var rideList = JSON.parse(ridelist).Offer;

                optionStartAdr = document.createElement('option');
                adrStartBox = document.getElementById(offerstartdropdownid);
                optionStartAdr.latln = rideList.ridestartPtLat+','+rideList.ridestartPtLon;
                optionStartAdr.innerHTML = rideList.startptAddress;
                optionStartAdr.mod = true;
                adrStartBox.add(optionStartAdr, null);
                adrStartBox.selectedIndex = adrStartBox.length - 1;

                optionDestAdr = document.createElement('option');
                adrDestBox = document.getElementById(offerdestdropdownid);
                optionDestAdr.latln = rideList.rideendPtLat+','+rideList.rideendPtLon;
                optionDestAdr.innerHTML = rideList.endptAddress;
                optionDestAdr.mod = true;
                adrDestBox.add(optionDestAdr, null);
                adrDestBox.selectedIndex = adrDestBox.length - 1;

                //Für FOKUS-DAY
                //                document.getElementById('offercommentta').value = rideList.rideComment;

                switch (rideList.acceptableDetourInKm) {
                    case 1:
                        document.getElementById('offerdetourselect').selectedIndex = 0;
                        break;
                    case 2:
                        document.getElementById('offerdetourselect').selectedIndex = 1;
                        break;
                    case 5:
                        document.getElementById('offerdetourselect').selectedIndex = 2;
                        break;
                    case 10:
                        document.getElementById('offerdetourselect').selectedIndex = 3;
                        break;
                    case 20:
                        document.getElementById('offerdetourselect').selectedIndex = 4;
                        break
                    case 30:
                        document.getElementById('offerdetourselect').selectedIndex = 5;
                        break;
                }

                document.getElementById('offerseatsselect').selectedIndex = rideList.offeredSeatsNo - 1;

                switch (rideList.rideprice) {
                    case 1.8:
                        document.getElementById('offerpriceselect').selectedIndex = 0;
                        break;
                    case 1.9:
                        document.getElementById('offerpriceselect').selectedIndex = 1;
                        break;
                    case 2.0:
                        document.getElementById('offerpriceselect').selectedIndex = 2;
                        break;
                    case 2.1:
                        document.getElementById('offerpriceselect').selectedIndex = 3;
                        break;
                    case 2.2:
                        document.getElementById('offerpriceselect').selectedIndex = 4;
                        break;
                    case 2.3:
                        document.getElementById('offerpriceselect').selectedIndex = 5;
                        break;
                }

                startDate = new Date(rideList.ridestartTime);
                oday = startDate.getDate();
                if(oday < 10)oday = '0'+oday;
                omonth = startDate.getMonth()+1;
                if(omonth < 10)omonth = '0'+omonth;
                oyear = startDate.getFullYear();
                ohours = startDate.getHours();
                if(ohours < 10)ohours = '0'+ohours;
                omin = startDate.getMinutes();
                if(omin < 10)omin = '0'+omin;

                document.getElementById('dayLabel').innerHTML = oday;
                document.getElementById('monthLabel').innerHTML = omonth;
                document.getElementById('yearLabel').innerHTML = oyear;
                document.getElementById('hourLabel').innerHTML = ohours;
                document.getElementById('minuteLabel').innerHTML = omin;

                calendar.setDay(startDate.getDate());
                calendar.setMin(startDate.getMinutes());
                calendar.setHour(startDate.getHours());
                calendar.setMonth(startDate.getMonth());
                calendar.setYear(startDate.getFullYear());


            } else {
                if (ride != '') {
                    rideid = ride.riderRouteId;
                }
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+rideid,
                    false, this.setRide, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Das zu bearbeitende Gesuch konnte leider nicht vom Server geladen werden.')
                    });

                var searchRideList = JSON.parse(ridelist).Search;

                optionStartAdr = document.createElement('option');
                adrStartBox = document.getElementById(searchstartdropdownid);
                optionStartAdr.latln = searchRideList.ridestartPtLat+','+searchRideList.ridestartPtLon;
                optionStartAdr.innerHTML = searchRideList.startptAddress;
                optionStartAdr.mod = true;
                adrStartBox.add(optionStartAdr, null);
                adrStartBox.selectedIndex = adrStartBox.length - 1;

                optionDestAdr = document.createElement('option');
                adrDestBox = document.getElementById(searchdestdropdownid);
                optionDestAdr.latln = searchRideList.rideendPtLat+','+searchRideList.rideendPtLon;
                optionDestAdr.innerHTML = searchRideList.endptAddress;
                optionDestAdr.mod = true;
                adrDestBox.add(optionDestAdr, null);
                adrDestBox.selectedIndex = adrDestBox.length - 1;

                //Für FOKUS-DAY
                //                document.getElementById('searchcommentta').value = searchRideList.rideComment;

                var watingTime = ((searchRideList.ridestartTimeLatest - searchRideList.ridestartTimeEarliest) / 1000) / 60;

                switch (watingTime) {
                    case 10:
                        document.getElementById('searchwaitimeselect').selectedIndex = 0;
                        break;
                    case 15:
                        document.getElementById('searchwaitimeselect').selectedIndex = 1;
                        break;
                    case 20:
                        document.getElementById('searchwaitimeselect').selectedIndex = 2;
                        break;
                    case 30:
                        document.getElementById('searchwaitimeselect').selectedIndex = 3;
                        break;
                    case 45:
                        document.getElementById('searchwaitimeselect').selectedIndex = 4;
                        break
                    case 60:
                        document.getElementById('searchwaitimeselect').selectedIndex = 5;
                        break;
                    case 120:
                        document.getElementById('searchwaitimeselect').selectedIndex = 6;
                        break;
                    case 180:
                        document.getElementById('searchwaitimeselect').selectedIndex = 7;
                        break;
                    case 240:
                        document.getElementById('searchwaitimeselect').selectedIndex = 8;
                        break;
                }

                document.getElementById('searchseatsselect').selectedIndex = searchRideList.searchedSeatsNo - 1;

                startDate = new Date(searchRideList.ridestartTimeEarliest);
                oday = startDate.getDate();
                if(oday < 10)oday = '0'+oday;
                omonth = startDate.getMonth()+1;
                if(omonth < 10)omonth = '0'+omonth;
                oyear = startDate.getFullYear();
                ohours = startDate.getHours();
                if(ohours < 10)ohours = '0'+ohours;
                omin = startDate.getMinutes();
                if(omin < 10)omin = '0'+omin;

                document.getElementById('searchdayLabel').innerHTML = oday;
                document.getElementById('searchmonthLabel').innerHTML = omonth;
                document.getElementById('searchyearLabel').innerHTML = oyear;
                document.getElementById('searchhourLabel').innerHTML = ohours;
                document.getElementById('searchminuteLabel').innerHTML = omin;

                calendar.setDay(startDate.getDate());
                calendar.setMin(startDate.getMinutes());
                calendar.setHour(startDate.getHours());
                calendar.setMonth(startDate.getMonth());
                calendar.setYear(startDate.getFullYear());
            }

            serviceType = modifyService;
            this.setTabContent(1, 0);
        },

        deleteRide : function (rideId) {

            if (usermode == DRIVERMODE) {
                srvconn.DELETE('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/'+rideId,
                    false,
                    showOverlayDialog('Ihr Anbegot wurde erfolgreich gel&ouml;scht.', '', 'OK', fokus.openride.mobclient.controller.modules.modulemanager.setView('activeofferUI'), '', ''),
                    function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Das Angebot konnte leider nicht gel&ouml;scht werden.')
                    });
                fokus.openride.mobclient.controller.modules.modulemanager.setView('activeofferUI');

            } else {
                srvconn.DELETE('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/'+rideId,
                    false,
                    showOverlayDialog('Ihr Gesuch wurde erfolgreich gel&ouml;scht.', '', 'OK', fokus.openride.mobclient.controller.modules.modulemanager.setView('activesearchUI'), '', ''),
                    function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Das Gesuch konnte leider nicht gel&ouml;scht werden.')
                    });

                fokus.openride.mobclient.controller.modules.modulemanager.setView('activesearchUI');

            }
        },

        setFullScreenMapView: function(viewId){

            var currViewId = document.getElementById(viewId);

            /* hide complete body content */
            document.getElementById('tabmenu').style.display = 'none';
            document.getElementById('content').style.display = 'none';

            /* show selected fullscreen map */
            currViewId.style.display = 'block';

            var position = nativemod.getUserLocation();
            if (position == null)
                position = new google.maps.LatLng(52.525798, 13.314266); /*DUMMYPOSITION;*/
            else {
                var pos = position;
                position = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
            }

            if (viewId == 'offerstartgmapscreencontainer') {
                mapmod.setMapMode(0);
                mapmod.initialize('offerstartgmap', 'offerstartgmapaddressinput', position);
            }

            else if (viewId == 'offerdestgmapscreencontainer') {
                mapmod.setMapMode(0);
                mapmod.initialize('offerdestgmap', 'offerdestgmapaddressinput', position);
            }

            else if (viewId == 'searchstartgmapscreencontainer') {
                mapmod.setMapMode(0);
                mapmod.initialize('searchstartgmap', 'searchstartgmapaddressinput', position);
            }

            else if (viewId == 'searchdestgmapscreencontainer') {
                mapmod.setMapMode(0);
                mapmod.initialize('searchdestgmap', 'searchdestgmapaddressinput', position);
            }

            else if (viewId == 'offerroutegmapscreencontainer') {

                mapmod.setMapMode(1);
                if(!(document.getElementById('dummyaddrinput')) ){
                    var dummydiv = document.createElement('div');
                    dummydiv.id = "dummyaddrinput";
                }

                var routearr = mapmod.getRoutePath();
                var center;

                if (routearr != null && routearr != 'undefined') {
                    if (routearr.length >= 2) {
                        mapmod.initialize('offerroutegmap', 'dummyaddrinput', routearr[0]);
                    }
                    else
                        showOverlayDialog('Route anzeigen', '<br />Die Route kann momentan nicht ermittelt werden! Bitte pr&uuml;fen Sie die Internetverbindung und versuchen Sie es erneut!<br />', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 0)', '', '');
                }
            }

            else if (viewId == 'searchroutegmapscreencontainer') {

                mapmod.setMapMode(1);
                if(!(document.getElementById('dummyaddrinput')) ){
                    var dummydiv = document.createElement('div');
                    dummydiv.id = "dummyaddrinput";
                }
                var routearr = mapmod.getRoutePath();
                var center;
                if (routearr != null && routearr != 'undefined') {
                    if (routearr.length >= 2) {
                        mapmod.initialize('searchroutegmap', 'dummyaddrinput', routearr[0]);
                    }
                    else
                        showOverlayDialog('Route anzeigen', '<br />Die Route kann momentan nicht ermittelt werden! Bitte pr&uuml;fen Sie die Internetverbindung und versuchen Sie es erneut!<br />', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 0)', '', '');
                }
            }

            else if (viewId == 'viaptroutegmapscreencontainer') {
                mapmod.setMapMode(2);
                if(!(document.getElementById('dummyaddrinput')) ){
                    var dummydiv = document.createElement('div');
                    dummydiv.id = "dummyaddrinput";
                }
                var routearr = mapmod.getRoutePath();
                var center;
                if (routearr != null && routearr != 'undefined') {
                    if (routearr.length >= 2) {
                        mapmod.initialize('viaptroutegmap', 'dummyaddrinput', routearr[0]);
                    }
                    else
                        showOverlayDialog('Route anzeigen', '<br />Start und Ziel m&uuml;ssen unterschiedlich sein!<br />', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 0)', '', '');
                }
            }

            else if (viewId == 'favoritesgmapscreencontainer') {
                mapmod.setMapMode(0);
                mapmod.initialize('favoritesgmap', 'favoritesgmapaddressinput', position);
            }

            this.displayedFullScreenMapId = viewId;
        },

        returnFromFullscreenMapView: function(){

            /* hide full screen map */
            document.getElementById(this.displayedFullScreenMapId).style.display = 'none';

            /* show previous content */

            /* hide complete body content */
            document.getElementById('tabmenu').style.display = 'block';
            document.getElementById('content').style.display = 'block';

        },

        parsefavoriteslist : function(favoriteslistdiv, resultlist){
            var result = JSON.parse(resultlist);
            var listhtml = '';
            favnames = new Array();

            if(typeof (result.list[0].FavoritePointResponse) != 'undefined'){
                if(typeof (result.list[0].FavoritePointResponse.length) == 'undefined'){
                    result.list[0].FavoritePointResponse = [result.list[0].FavoritePointResponse];
                }
                for(var i=0;i< result.list[0].FavoritePointResponse.length; i++){
                    var entry = result.list[0].FavoritePointResponse[i];

                    var favname = entry.favptDisplayName;
                    favnames.push(favname);
                    var favaddress = entry.favptAddress;
                    var favid = entry.favptId;

                    listhtml += "<div class='fav-row' style='border-bottom: 1px solid #ccc; padding: 5px;'>"+
                    "<div style='float: right; margin: 2px 0px 0 0;'><input type=\"button\" class=\"rounded compact\" onclick=\"showOverlayDialog('Diesen Favoriten wirklich l&ouml;schen?', '', 'Ja', 'fokus.openride.mobclient.controller.modules.modulemanager.deleteFavPt("+i+");', 'Nein', '');\" value=\"X\" /></div>"+
                    "<div style='line-height: 140%'>"+
                    "<strong>"+favname+"</strong><br />"+
                    favaddress+
                    "</div>"+
                    "</div>"
                }
            }else{
                listhtml = "<span style='font-weight:bold;padding:10px;'>Keine Favoriten vorhanden!</span>";
            }

            document.getElementById(favoriteslistdiv).innerHTML = listhtml;
        },

        deleteFavPt : function(nameindex){
            srvconn.DELETE('/OpenRideServer-RS/resources/users/'+this.username+'/favoritepoints/'+encodeURI($("<div />").html(favnames[nameindex]).text()), true, function() {
                fokus.openride.mobclient.controller.modules.modulemanager.setView('favlistUI')
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Der Favorit konnte leider nicht gelöscht werden.')
            });
        },

        parseprofilepersonaldata : function(result){

            if(typeof (result.ProfileResponse) != 'undefined'){

                var personalData = result.ProfileResponse;

                document.getElementById('profilepersonaldatafirstname').innerHTML = personalData.firstName;
                document.getElementById('profilepersonaldatalastname').innerHTML = personalData.lastName;
                if (personalData.gender == 'm') {
                    genderString = 'm&auml;nnlich';
                }
                else {
                    genderString = 'weiblich';
                }
                document.getElementById('profilepersonaldatagender').innerHTML = genderString;
                if (typeof (personalData.dateOfBirth) != 'undefined') {
                    dateOfBirth = new Date(personalData.dateOfBirth);
                    dateOfBirthString = dateOfBirth.getDate() + '.' + (dateOfBirth.getMonth() + 1) + '.' + dateOfBirth.getFullYear()
                }
                else {
                    dateOfBirthString = ''; //<em>nicht angegeben</em>';
                }
                document.getElementById('profilepersonaldatadateofbirth').value = dateOfBirthString;
                document.getElementById('profilepersonaldataemail').value = $("<div />").html(personalData.email).text();
                document.getElementById('profilepersonaldatamobilephonenumber').value = personalData.mobilePhoneNumber || '';
                //document.getElementById('profilepersonaldatafixedphonenumber').value = personalData.fixedPhoneNumber || '';
                if (personalData.streetAddress) {
                    document.getElementById('profilepersonaldatastreetaddress').value = $("<div />").html(personalData.streetAddress).text() || '';
                }
                document.getElementById('profilepersonaldatazipcode').value = personalData.zipCode || '';
                if (personalData.city) {
                    document.getElementById('profilepersonaldatacity').value = $("<div />").html(personalData.city).text() || '';
                }
                if (personalData.isSmoker == 'n') {
                    isSmokerOption = 'profilepersonaldataissmoker-no';
                }
                else if (personalData.isSmoker == 'y') {
                    isSmokerOption = 'profilepersonaldataissmoker-yes';
                }
                else {
                    isSmokerOption = 'profilepersonaldataissmoker-null';
                }
                document.getElementById(isSmokerOption).checked = 'checked';
                
                //document.getElementById('profilepersonaldatalicensedate').value = personalData.licenseDate || '';
                if (personalData.carColour) {
                    document.getElementById('profilepersonaldatacarcolour').value = $("<div />").html(personalData.carColour).text() || '';
                }
                if (personalData.carBrand) {
                    document.getElementById('profilepersonaldatacarbrand').value = $("<div />").html(personalData.carBrand).text() || '';
                }
                //document.getElementById('profilepersonaldatacarbuildyear').value = personalData.carBuildYear || '';
                if (personalData.carPlateNo) {
                    document.getElementById('profilepersonaldatacarplateno').value = $("<div />").html(personalData.carPlateNo).text() || '';
                }

            }
        },

        validateDate : function(inputDateString) {
            validMonth = false;
            if (inputDateString.split(".").length==3 && inputDateString.split(".")[1] > 0 && inputDateString.split(".")[1] <= 12 && inputDateString.split(".")[0] > 0 && inputDateString.split(".")[0] <= 31 && inputDateString.split(".")[2] > 1900) {
                validMonth = true;
            }
            parsedDate = this.parsedate(inputDateString);
            if (!validMonth || !parsedDate) {
                return false;
            }
            else {
                return parsedDate;
            }
        },

        putprofilepersonaldata : function(){

            /* Validation */

            // email address: required
            if (document.getElementById('profilepersonaldataemail').value == '') {
                //TODO: check for valid email address...
                showOverlayDialog('Die Angabe einer E-Mail-Adresse ist zwingend erforderlich.', '', 'OK', '', '', '');
                return;
            }
            // email address: @ZU
            /*if (document.getElementById('profilepersonaldataemail').value.indexOf("zeppelin-university.de") == -1 && document.getElementById('profilepersonaldataemail').value.indexOf("zeppelin-university.net") == -1) {
                showOverlayDialog('E-Mail-Adresse muss auf "zeppelin-university.[net|de]" enden.', '', 'OK', '', '', '');
                return;
            }*/
            // mobile phone no.: required in valid format
            if (document.getElementById('profilepersonaldatamobilephonenumber').value == '') {
                showOverlayDialog('Die Angabe einer Handynummer ist zwingend erforderlich.', '', 'OK', '', '', '');
                return;
            }
            else if (document.getElementById('profilepersonaldatamobilephonenumber').value.match(/^[(]?0[ ]?1[5-7][0-9][ ]?[-/)]?[ ]?[1-9][-0-9 ]{6,16}$/) == null) {
                showOverlayDialog('Bitte tragen Sie eine g&uuml;ltige deutsche Handynummer ein.', '', 'OK', '', '', '');
                return;
            }
            // date of birth: not required, but if provided needs valid format
            var dateOfBirthValue;
            if (document.getElementById('profilepersonaldatadateofbirth').value != '') {
                dateOfBirthValue = this.validateDate(document.getElementById('profilepersonaldatadateofbirth').value);

                if (!dateOfBirthValue | !isValidDate(document.getElementById('profilepersonaldatadateofbirth').value, "DMY")) {
                    showOverlayDialog('Bitte ein g&uuml;ltiges Geburtsdatum im Format "TT.MM.JJJJ" angeben. Das Datum darf nicht in der Zukunft liegen.', '', 'OK', '', '', '');
                    return;
                }
            }
            // license date: not required, but if provided needs valid format
            /*var licenseDateValue = document.getElementById('profilepersonaldatalicensedate').value;
            if (licenseDateValue != '') {
                var today = new Date();
                if (licenseDateValue < 1900 || licenseDateValue > today.getFullYear()) {
                    showOverlayDialog('Bitte ein gültiges Jahr der Führerscheinausstellung im Format "JJJJ" angeben.', '', 'OK', '', '', '');
                    return;
                }
            }*/
            // carBuildYear: not required, but if provided needs valid format
            /*var carBuildYearValue = document.getElementById('profilepersonaldatacarbuildyear').value;
            if (carBuildYearValue != '') {
                today = new Date();
                if (carBuildYearValue < 1900 || carBuildYearValue > today.getFullYear()) {
                    showOverlayDialog('Bitte ein gültiges Baujahr im Format "JJJJ" angeben.', '', 'OK', '', '', '');
                    return;
                }
            }*/

            function isValidDate(dateStr, format) {
                if (format == null) {
                    format = "MDY";
                }
                format = format.toUpperCase();
                if (format.length != 3) {
                    format = "MDY";
                }
                if ( (format.indexOf("M") == -1) || (format.indexOf("D") == -1) ||
                    (format.indexOf("Y") == -1) ) {
                    format = "MDY";
                }
                if (format.substring(0, 1) == "Y") { // If the year is first
                    var reg1 = /^\d{2}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
                    var reg2 = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/
                } else if (format.substring(1, 2) == "Y") { // If the year is second
                    var reg1 = /^\d{1,2}(\-|\/|\.)\d{2}\1\d{1,2}$/
                    var reg2 = /^\d{1,2}(\-|\/|\.)\d{4}\1\d{1,2}$/
                } else { // The year must be third
                    var reg1 = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{2}$/
                    var reg2 = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
                }
                // If it doesn't conform to the right format (with either a 2 digit year or 4 digit year), fail
                if ( (reg1.test(dateStr) == false) && (reg2.test(dateStr) == false) ) {
                    return false;
                }
                var parts = dateStr.split(RegExp.$1); // Split into 3 parts based on what the divider was
                // Check to see if the 3 parts end up making a valid date
                if (format.substring(0, 1) == "M") {
                    var mm = parts[0];
                } else
                if (format.substring(1, 2) == "M") {
                    var mm = parts[1];
                } else {
                    var mm = parts[2];
                }
                if (format.substring(0, 1) == "D") {
                    var dd = parts[0];
                } else
                if (format.substring(1, 2) == "D") {
                    var dd = parts[1];
                } else {
                    var dd = parts[2];
                }
                if (format.substring(0, 1) == "Y") {
                    var yy = parts[0];
                } else
                if (format.substring(1, 2) == "Y") {
                    var yy = parts[1];
                } else {
                    var yy = parts[2];
                }
                if (parseFloat(yy) <= 50) {
                    yy = (parseFloat(yy) + 2000).toString();
                }
                if (parseFloat(yy) <= 99) {
                    yy = (parseFloat(yy) + 1900).toString();
                }
                var dt = new Date(parseFloat(yy), parseFloat(mm)-1, parseFloat(dd), 0, 0, 0, 0);
                if (parseFloat(dd) != dt.getDate()) {
                    return false;
                }
                if (parseFloat(mm)-1 != dt.getMonth()) {
                    return false;
                }

                var now = new Date();
                if (dt.getTime() >= now.getTime()) {
                    return false;
                }

                return true;
            }



            /* Build the request */

            var emptyvar;
            var profilemod = fokus.openride.mobclient.controller.modules.profile;

            profilemod.setDateOfBirth(dateOfBirthValue);
            profilemod.setEmail(document.getElementById('profilepersonaldataemail').value);
            profilemod.setMobilePhoneNumber(document.getElementById('profilepersonaldatamobilephonenumber').value);
            //profilemod.setFixedPhoneNumber(document.getElementById('profilepersonaldatafixedphonenumber').value);
            profilemod.setStreetAddress(document.getElementById('profilepersonaldatastreetaddress').value);
            profilemod.setZipCode(document.getElementById('profilepersonaldatazipcode').value || 0);
            profilemod.setCity(document.getElementById('profilepersonaldatacity').value);
            if (document.getElementById('profilepersonaldataissmoker-yes').checked) {
                isSmokerValue = 'y';
            }
            else if (document.getElementById('profilepersonaldataissmoker-no').checked) {
                isSmokerValue = 'n';
            }
            else {
                isSmokerValue = '-';
            }
            profilemod.setIsSmoker(isSmokerValue);
            //if (licenseDateValue == "") {
            profilemod.setLicenseDate(emptyvar);
            /*} else {
                profilemod.setLicenseDate(licenseDateValue);
            }*/
            profilemod.setCarColour(document.getElementById('profilepersonaldatacarcolour').value || emptyvar);
            profilemod.setCarBrand(document.getElementById('profilepersonaldatacarbrand').value || emptyvar);
            //profilemod.setCarBuildYear(document.getElementById('profilepersonaldatacarbuildyear').value || emptyvar);
            profilemod.setCarBuildYear(emptyvar);
            profilemod.setCarPlateNo(document.getElementById('profilepersonaldatacarplateno').value || emptyvar);

            // Submit PUT request
            srvconn.PUT('/OpenRideServer-RS/resources/users/'+this.username+'/profile', true, profilemod.getProfileRequest(), function() {
                showOverlayDialog('Persönliche Daten erfolgreich gespeichert!', '', 'OK', '', '', '')
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre persönlichen Daten konnten leider nicht gespeichert werden.')
            } );

        },

        parsedate : function(inputvalue){
            var components = inputvalue.split('.'); // assuming date is entered in format dd.mm.yyyy
            if (components.length != 3) return false; // must include day, month, year
            var thedate = new Date(components[2], components[1]-1, components[0]);
            return thedate.getTime();
        },

        parseprofilepreferences : function(result){

            if(typeof (result.PreferencesResponse) != 'undefined'){

                var preferencesData = result.PreferencesResponse;

                if (preferencesData.prefIsSmoker == 'y') {
                    isSmokerOption = 'profileprefissmoker-yes';
                }
                else if (preferencesData.prefIsSmoker == 'n') {
                    isSmokerOption = 'profileprefissmoker-no';
                }
                else {
                    isSmokerOption = 'profileprefissmoker-null';
                }
                document.getElementById(isSmokerOption).checked = 'checked';

            /*if (preferencesData.prefGender == 'f') {
                    genderOption = 'profileprefgender-f';
                }
                else {
                    genderOption = 'profileprefgender-null';
                }
                document.getElementById(genderOption).checked = 'checked';*/

            }
        },

        putprofilepreferences : function(){

            /* Build the request */

            var profilemod = fokus.openride.mobclient.controller.modules.profile;

            if (document.getElementById('profileprefissmoker-yes').checked) {
                isSmokerValue = 'y';
            }
            else if (document.getElementById('profileprefissmoker-no').checked) {
                isSmokerValue = 'n';
            }
            else {
                isSmokerValue = '-';
            }
            profilemod.setPrefIsSmoker(isSmokerValue);

            /*if (document.getElementById('profileprefgender-f').checked) {
                genderValue = 'f';
            }
            else {
                genderValue = '-';
            }
            profilemod.setPrefGender(genderValue);*/

            // Submit PUT request
            srvconn.PUT('/OpenRideServer-RS/resources/users/'+this.username+'/profile/preferences', true, profilemod.getPreferencesRequest(), function() {
                showOverlayDialog('Präferenzen erfolgreich gespeichert!', '', 'OK', '', '', '')
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Präferenzen konnten leider nicht gespeichert werden.')
            });

        },

        putprofilepassword : function(){

            /* Validation */

            if (document.getElementById('profilepasswordold').value == '' || document.getElementById('profilepassword').value == '' || document.getElementById('profilepasswordcheck').value == '') {
                showOverlayDialog('Die Angabe von altem und neuem Passwort ist zwingend erforderlich.', '', 'OK', '', '', '');
                return;
            }

            if (document.getElementById('profilepassword').value != document.getElementById('profilepasswordcheck').value) {
                showOverlayDialog('Die Passwörter stimmen nicht überein.', '', 'OK', '', '', '');
                return;
            }

            /* Build the request */

            var profilemod = fokus.openride.mobclient.controller.modules.profile;

            profilemod.setPasswordOld(document.getElementById('profilepasswordold').value);
            profilemod.setPassword(document.getElementById('profilepassword').value);

            // Submit PUT request
            srvconn.PUT('/OpenRideServer-RS/resources/users/'+this.username+'/profile/password', true, profilemod.getPasswordRequest(), function() {
                showOverlayDialog('Passwort erfolgreich gespeichert!', '', 'OK', '', '', '')
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihr Passwort konnte leider nicht gespeichert werden.')
            });

        },

        parseratingssummary : function(ratingssummarydiv, resultlist){
            var result = JSON.parse(resultlist);

            if(typeof (result.RatingsSummaryResponse) != 'undefined'){
                var entry = result.RatingsSummaryResponse;
                document.getElementById("ratingssummarytotal").innerHTML = entry.ratingsTotal;
                document.getElementById("ratingssummaryratio").innerHTML = entry.ratingsRatioPercent + "%";
                document.getElementById("ratingssummarypositive").innerHTML = entry.ratingsLatestPositive;
                document.getElementById("ratingssummaryneutral").innerHTML = entry.ratingsLatestNeutral;
                document.getElementById("ratingssummarynegative").innerHTML = entry.ratingsLatestNegative;
            }

        },

        parseopenratingslist : function(openratingslistdiv, resultlist){
            var result = JSON.parse(resultlist);
            var listhtml = '<h3>Bewertungen abgeben</h3>';
            favnames = new Array();

            if(typeof (result.list[0].OpenRatingResponse) != 'undefined'){
                listhtml += '<p>Bitte bewerten Sie Ihre Fahrer und Mitfahrer:</p>';
                if(typeof (result.list[0].OpenRatingResponse.length) == 'undefined'){
                    result.list[0].OpenRatingResponse = [result.list[0].OpenRatingResponse];
                }
                for(var i=0;i< result.list[0].OpenRatingResponse.length; i++){
                    var entry = result.list[0].OpenRatingResponse[i];

                    var rateeRoleName = '';
                    if (entry.custRole == 'd') {
                        if (entry.custGender == 'm') {
                            rateeRoleName = "Ihr Fahrer";
                        }
                        else if (entry.custGender == 'f') {
                            rateeRoleName = "Ihre Fahrerin";
                        }
                    }
                    else if (entry.custRole == 'r') {
                        if (entry.custGender == 'm') {
                            rateeRoleName = "Ihr Mitfahrer";
                        }
                        else if (entry.custGender == 'f') {
                            rateeRoleName = "Ihre Mitfahrerin";
                        }
                    }

                    var dateRealized = new Date(entry.timestamprealized);

                    listhtml += '<div class="open-rating-row" style="border-bottom: 1px solid #ccc; padding: 5px; min-height: 60px; clear: both;" id="openrating' + entry.riderRouteId + '">'
                    + '    <div class="profile-info-short" style="float: left; margin: 0 15px 0 0; text-align: right;"><img src="../../OpenRideWeb/pictures/profile/' + entry.custNickname + '_' + entry.custId + '_thumb.jpg" alt="Profilbild von ' + entry.custNickname + '" style="width: 60px; height: 60px; display: block; background: #ddd;" /></div>'
                    + '    <div style="line-height: 140%; padding-left: 75px;">'
                    + '        <strong>' + entry.custNickname + '</strong> &ndash; <br />'
                    + '        ' + rateeRoleName + ' am ' + dateRealized.getDate() + '.' + (dateRealized.getMonth() + 1) + '.' + dateRealized.getFullYear() + ':'
                    + '    </div>'
                    + '    <div class="open-rating-buttons" style="line-height: 100%;">'
                    + '        <input type="radio" id="openrating' + entry.riderRouteId + '_pos" name="openrating' + entry.riderRouteId + '" onclick="showOverlayDialog(\'Bitte kommentieren Sie Ihre positive Bewertung:\', \'<input type=\\\'text\\\' style=\\\'width: 97.5%; background: url(../img/rated_1.png) no-repeat center right\\\' id=\\\'ratingcomment' + entry.riderRouteId + '_pos\\\' maxlength=\\\'80\\\' />\', \'Speichern\', \'fokus.openride.mobclient.controller.modules.modulemanager.postrating(' + entry.riderRouteId + ', 1, document.getElementById(\\\'ratingcomment' + entry.riderRouteId + '_pos\\\').value);\', \'Zur&uuml;ck\', \'\');" /><label for="openrating' + entry.riderRouteId + '_pos"> <img src="../img/rated_1.png" style="vertical-align: -3px; padding: 0 22px 0 0;" /></label>'
                    + '        <input type="radio" id="openrating' + entry.riderRouteId + '_neu" name="openrating' + entry.riderRouteId + '" onclick="showOverlayDialog(\'Bitte kommentieren Sie Ihre neutrale Bewertung:\', \'<input type=\\\'text\\\' style=\\\'width: 97.5%; background: url(../img/rated_0.png) no-repeat center right\\\' id=\\\'ratingcomment' + entry.riderRouteId + '_neu\\\' maxlength=\\\'80\\\' />\', \'Speichern\', \'fokus.openride.mobclient.controller.modules.modulemanager.postrating(' + entry.riderRouteId + ', 0, document.getElementById(\\\'ratingcomment' + entry.riderRouteId + '_neu\\\').value);\', \'Zur&uuml;ck\', \'\');" /><label for="openrating' + entry.riderRouteId + '_neu"> <img src="../img/rated_0.png" style="vertical-align: -3px; padding: 0 22px 0 0;" /></label>'
                    + '        <input type="radio" id="openrating' + entry.riderRouteId + '_neg" name="openrating' + entry.riderRouteId + '" onclick="showOverlayDialog(\'Bitte kommentieren Sie Ihre negative Bewertung:\', \'<input type=\\\'text\\\' style=\\\'width: 97.5%; background: url(../img/rated_-1.png) no-repeat center right\\\' id=\\\'ratingcomment' + entry.riderRouteId + '_neg\\\' maxlength=\\\'80\\\' />\', \'Speichern\', \'if (document.getElementById(\\\'ratingcomment' + entry.riderRouteId + '_neg\\\').value==\\\'\\\') {document.getElementById(\\\'ratingcomment' + entry.riderRouteId + '_neg\\\').style.borderColor=\\\'red\\\'; return false;} fokus.openride.mobclient.controller.modules.modulemanager.postrating(' + entry.riderRouteId + ', -1, document.getElementById(\\\'ratingcomment' + entry.riderRouteId + '_neg\\\').value);\', \'Zur&uuml;ck\', \'\');" /><label for="openrating' + entry.riderRouteId + '_neg"> <img src="../img/rated_-1.png" style="vertical-align: -3px; padding: 0 22px 0 0;" /></label>'
                    + '    </div>'
                    + '</div>';
                }
            }
            else {
                listhtml += "<p>Sie haben alle Ihre bisherigen Fahrer und Mitfahrer bewertet.</p>";
            }

            document.getElementById(openratingslistdiv).innerHTML = listhtml;
        },

        postrating : function(riderRouteId, rating, ratingComment){

            var ratingsmod = fokus.openride.mobclient.controller.modules.ratings;
            ratingsmod.setRiderRouteId(riderRouteId);
            ratingsmod.setGivenRating(rating);
            ratingsmod.setGivenRatingComment(ratingComment);

            // submit POST request
            srvconn.POST('/OpenRideServer-RS/resources/users/'+this.username+'/ratings', true, ratingsmod.getGivenRatingRequest(), function() {
                //document.getElementById('openrating'+riderRouteId).style.display='none';
                showOverlayDialog('Ihre Bewertung wurde erfolgreich &uuml;bermittelt.', '', 'OK', '', '', '');
                fokus.openride.mobclient.controller.modules.modulemanager.setView("openratingsUI");
            }, function(x,s,e) {
                fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Bewertung konnte leider nicht übermittelt werden.')
            });

        },

        parsereceivedratingslist : function(receivedratingslistdiv, resultlist){
            var result = JSON.parse(resultlist);
            var listhtml = '<h3>Erhaltene Berwertungen</h3>';
            favnames = new Array();

            if(typeof (result.list[0].ReceivedRatingResponse) != 'undefined'){
                if(typeof (result.list[0].ReceivedRatingResponse.length) == 'undefined'){
                    result.list[0].ReceivedRatingResponse = [result.list[0].ReceivedRatingResponse];

                }
                for(var i=0;i< result.list[0].ReceivedRatingResponse.length; i++){
                    var entry = result.list[0].ReceivedRatingResponse[i];

                    var raterRoleName = '';
                    if (entry.custRole == 'd') {
                        if (entry.custGender == 'm') {
                            raterRoleName = "Ihr Fahrer";
                        }
                        else if (entry.custGender == 'f') {
                            raterRoleName = "Ihre Fahrerin";
                        }
                    }
                    else if (entry.custRole == 'r') {
                        if (entry.custGender == 'm') {
                            raterRoleName = "Ihr Mitfahrer";
                        }
                        else if (entry.custGender == 'f') {
                            raterRoleName = "Ihre Mitfahrerin";
                        }
                    }

                    var dateRealized = new Date(entry.timestamprealized);

                    var comment = '';
                    if (typeof (entry.receivedRatingComment) != 'undefined' && entry.receivedRatingComment != '') {
                        comment = '&bdquo;' + entry.receivedRatingComment + '&ldquo;';
                    }
                    else {
                        switch(entry.receivedRating) {
                            case 1:
                                comment = '<span style="color: #aaa; font-style: italic;">Positiv bewertet</span>';
                                break;
                            case 0:
                                comment = '<span style="color: #aaa; font-style: italic;">Neutral bewertet</span>';
                                break;
                            case -1:
                                comment = '<span style="color: #aaa; font-style: italic;">Negativ bewertet</span>';
                                break;
                        }
                    }

                    listhtml += '<div class="rating-row" style="border-bottom: 1px solid #ccc; padding: 5px; min-height: 60px; clear: both;">'
                    + '<div class="profile-info-short" style="float: left; margin: 0 15px 0 0; text-align: right;"><img src="../../OpenRideWeb/pictures/profile/' + entry.custNickname + '_' + entry.custId + '_thumb.jpg" alt="Profilbild von ' + entry.custNickname + '" style="width: 60px; height: 60px; display: block; background: #ddd;" /></div>'
                    + '<div style="line-height: 140%; padding-left: 75px;">'
                    + '<strong>' + entry.custNickname + '</strong> &ndash; <br />'
                    + raterRoleName + ' am ' + dateRealized.getDate() + '.' + (dateRealized.getMonth() + 1) + '.' + dateRealized.getFullYear() + ':<br />'
                    + '<img src="../img/rated_' + entry.receivedRating + '.png" style="vertical-align: -3px; padding: 0 5px 0 5px;" /> '
                    + comment
                    + '</div>'
                    + '</div>';
                }
            }
            else {
                listhtml += "<p>Bisher hat niemand eine Bewertung für Sie abgegeben.</p>";
            }

            document.getElementById(receivedratingslistdiv).innerHTML = listhtml;
        },

        setView : function (viewId){

            document.getElementById(this.currentdisplayedview).style.display = 'none';
            document.getElementById(viewId).style.display = 'block';

            var position = nativemod.getUserLocation();
            if(position == null)
                position = new google.maps.LatLng(52.525798, 13.314266); /*DUMMYPOSITION;*/
            else{
                var pos = position;
                position = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
            }

            // clear tabListActiveRefreshTimer when switching tabs - those that need it will set it again
            clearInterval(tabListActiveRefreshTimer);
            // clear updateCountRefreshTimer when switching to active offers / searches list:
            if (viewId == 'activeofferUI' || viewId == 'activesearchUI') {
                clearInterval(updateCountRefreshTimer);
                updateCountRefreshTimer = 0;
            }
            // set updateCountRefreshTimer when switching to other tabs if not yet running:
            else if (!updateCountRefreshTimer) {
                if (updateCountRefreshTimer == 0)
                    this.receiveUpdates();
                updateCountRefreshTimer = setInterval("fokus.openride.mobclient.controller.modules.modulemanager.receiveUpdates()", 15000);
            }


            if(viewId == 'offerstartpickerUI'){
                /*if(!mapInitialized){*/
                mapmod.setMapMode(0);
                mapmod.initialize('offerstartmap', 'offerstartaddrinput', position);
            /*mapInitialized = true;
                  }*/
            }

            //fill start/dest selects with current position and favorite points newofferdetailsUI
            else if(viewId == 'newofferUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));


                // Car details
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/profile', false, function(result) {
                    if(typeof (result.ProfileResponse) != 'undefined'){
                        var personalData = result.ProfileResponse;
                        if (!personalData.carColour || !personalData.carBrand || !personalData.carPlateNo) {
                            showOverlayDialog('Bitte vervollständigen Sie in Ihrem Profil Ihre Autobeschreibung, bevor Sie ein Fahrtangebot einstellen.', '', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(0, 1);', '', '');
                        }
                    }
                }, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Fehler beim Laden der Profildaten (Autofarbe, -modell).')
                });

                if(serviceType == 'modify'){
                    document.getElementById('tabimg11').src = "../img/tab1AngebotAendernActive_wide.png";
                } else {
                    document.getElementById('tabimg11').src = "../img/tab1NeuesAngebotActive_wide.png";
                }

                document.getElementById(offerstartselectcurrpos).value = 'Standort: nicht ermittelbar!';
                document.getElementById(offerstartselectcurrpos).latln = 'none';
                document.getElementById(offerdestselectcurrpos).value = 'Standort: nicht ermittelbar!';
                document.getElementById(offerdestselectcurrpos).latln = 'none';

                try{
                    mapmod.insertRevGeocodedAddr(nativemod.getUserLocation(), offerstartselectcurrpos);
                    mapmod.insertRevGeocodedAddr(nativemod.getUserLocation(), offerdestselectcurrpos);
                }catch(e){

                }

                //                calendar.reset();
                //                calendar.refreshSimpleCalendarPickerLabels(calendar.dateLabels, calendar.timeLabels);

                /*var offerlatln = nativemod.getUserLocation();
                if(offerlatln == null || offerlatln == 'undefined'){
                    document.getElementById(offerstartselectcurrpos).value = 'Standort: nicht ermittelbar!';
                    document.getElementById(offerstartselectcurrpos).latln = 'none';
                    document.getElementById(offerdestselectcurrpos).value = 'Standort: nicht ermittelbar!';
                    document.getElementById(offerdestselectcurrpos).latln = 'none';
                }else{
                    mapmod.insertRevGeocodedAddr(offerlatln, offerstartselectcurrpos);
                    mapmod.insertRevGeocodedAddr(offerlatln, offerdestselectcurrpos);
                }*/


                /*document.getElementById(offerdestselectcurrpos).innerHTML = document.getElementById(offerstartselectcurrpos).innerHTML;*/

                if (!this.detailsClicked) {
                    var ddBox = document.getElementById(offerstartdropdownid);
                    var count = ddBox.length;
                    for (var i = count - 1; i > 1; i--) {
                        if (!ddBox[i].mod) {
                            ddBox[i] = null;
                        }
                    }

                    ddBox = document.getElementById(offerdestdropdownid);
                    count = ddBox.length;
                    for (var j = count - 1; j > 1; j--) {
                        if (!ddBox[j].mod) {
                            ddBox[j] = null;
                        }
                    }

                    srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/favoritepoints', false, function(data){
                        var offerstartsel = document.getElementById(offerstartdropdownid);
                        var offerdestsel = document.getElementById(offerdestdropdownid);

                        var result = data;

                        if(typeof (result.list[0].FavoritePointResponse) != 'undefined'){
                            if(typeof (result.list[0].FavoritePointResponse.length) == 'undefined'){
                                var favorite = result.list[0].FavoritePointResponse;

                                var name = favorite.favptDisplayName;
                                var address = favorite.favptAddress;
                                var favptGeoCoords = favorite.favptGeoCoords;
                                var id = favorite.favptId;

                                var favoption0 = document.createElement('option');
                                var favoption01 = document.createElement('option');
                                favoption0.innerHTML = name + ': ' +  address;
                                favoption0.latln = favptGeoCoords;
                                /*favoption0.latln = */

                                favoption01.innerHTML = name + ': ' +  address;
                                favoption01.latln = favptGeoCoords;

                                offerstartsel.add(favoption0,null);
                                offerdestsel.add(favoption01,null);

                            }else{
                                for(var j=0;j< result.list[0].FavoritePointResponse.length; j++){
                                    var entry = result.list[0].FavoritePointResponse[j];

                                    var favname0 = entry.favptDisplayName;
                                    var favaddress0 = entry.favptAddress;
                                    var favptGeoCoords0 = entry.favptGeoCoords;
                                    var favid0 = entry.favptId;

                                    var favoption1 = document.createElement('option');
                                    var favoption11 = document.createElement('option');
                                    favoption1.innerHTML = favname0 + ': ' +  favaddress0;
                                    favoption1.latln = favptGeoCoords0;
                                    /*favoption0.latln = */

                                    favoption11.innerHTML = favname0 + ': ' +  favaddress0;
                                    favoption11.latln = favptGeoCoords0;

                                    offerstartsel.add(favoption1,null);
                                    offerdestsel.add(favoption11,null);
                                }
                            }
                        }else{
                    }
                    }, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Favoriten konnten leider nicht geladen werden.')
                    } );
                    this.offerfavsset = true;
                }
            }

            else if(viewId == 'newsearchUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                if(serviceType == 'modify'){
                    document.getElementById('tabimg11').src = "../img/tab1GesuchAendernActive_wide.png";
                } else {
                    document.getElementById('tabimg11').src = "../img/tab1NeuesGesuchActive_wide.png";
                }

                document.getElementById(searchstartselectcurrpos).value = 'Standort: nicht ermittelbar!';
                document.getElementById(searchstartselectcurrpos).latln = 'none';
                document.getElementById(searchdestselectcurrpos).value = 'Standort: nicht ermittelbar!';
                document.getElementById(searchdestselectcurrpos).latln = 'none';

                try {
                    mapmod.insertRevGeocodedAddr(nativemod.getUserLocation(), searchstartselectcurrpos);
                    mapmod.insertRevGeocodedAddr(nativemod.getUserLocation(), searchdestselectcurrpos);
                } catch (e) {

                }

                /*var searchlatln = nativemod.getUserLocation();

                if(offerlatln == 'undefined'){
                    document.getElementById(searchstartselectcurrpos).value = 'Standort: nicht ermittelbar!';
                    document.getElementById(searchstartselectcurrpos).latln = 'none';
                    document.getElementById(searchdestselectcurrpos).value = 'Standort: nicht ermittelbar!';
                    document.getElementById(searchdestselectcurrpos).latln = 'none';
                }else{
                    mapmod.insertRevGeocodedAddr(searchlatln, searchstartselectcurrpos);
                    mapmod.insertRevGeocodedAddr(searchlatln, searchdestselectcurrpos);
                }*/


                /*document.getElementById(searchdestselectcurrpos).innerHTML = document.getElementById(searchstartselectcurrpos).innerHTML;*/

                if (!this.detailsClicked) {
                    ddBox = document.getElementById(searchstartdropdownid);
                    count = ddBox.length;
                    for (i = count - 1; i > 1; i--) {
                        if (!ddBox[i].mod) {
                            ddBox[i] = null;
                        }
                    }

                    ddBox = document.getElementById(searchdestdropdownid);
                    count = ddBox.length;
                    for (j = count - 1; j > 1; j--) {
                        if (!ddBox[j].mod) {
                            ddBox[j] = null;
                        }
                    }

                    srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/favoritepoints', false, function(data){
                        var searchstartsel = document.getElementById(searchstartdropdownid);
                        var searchdestsel = document.getElementById(searchdestdropdownid);

                        var result = data;

                        if(typeof (result.list[0].FavoritePointResponse) != 'undefined'){
                            if(typeof (result.list[0].FavoritePointResponse.length) == 'undefined'){
                                var favorite = result.list[0].FavoritePointResponse;

                                var name1 = favorite.favptDisplayName;
                                var address1 = favorite.favptAddress;
                                var geocoords = favorite.favptGeoCoords;
                                var id1 = favorite.favptId;

                                var favoption2 = document.createElement('option');
                                var favoption21 = document.createElement('option');
                                favoption2.innerHTML = name1 + ': ' +  address1;
                                favoption2.latln = geocoords;
                                /*favoption0.latln = */

                                favoption21.innerHTML = name1 + ': ' +  address1;
                                favoption21.latln = geocoords;

                                searchstartsel.add(favoption2,null);
                                searchdestsel.add(favoption21,null);

                            }else{
                                for(var j=0;j< result.list[0].FavoritePointResponse.length; j++){
                                    var entry = result.list[0].FavoritePointResponse[j];

                                    var favname1 = entry.favptDisplayName;
                                    var favaddress1 = entry.favptAddress;
                                    var geocoords1 = entry.favptGeoCoords;
                                    var favid1 = entry.favptId;

                                    var favoption3 = document.createElement('option');
                                    var favoption31 = document.createElement('option');
                                    favoption3.innerHTML = favname1 + ': ' +  favaddress1;
                                    favoption3.latln = geocoords1;
                                    /*favoption0.latln = */

                                    favoption31.innerHTML = favname1 + ': ' +  favaddress1;
                                    favoption31.latln = geocoords1;

                                    searchstartsel.add(favoption3,null);
                                    searchdestsel.add(favoption31,null);
                                }
                            }
                        }else{
                    }
                    }, function(x,s,e) {
                        fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Favoriten konnten leider nicht geladen werden.')
                    });
                    this.searchfavsset = true;
                }
            }

            else if(viewId == 'offerdestpickerUI'){
                mapmod.setMapMode(0);
                mapmod.initialize('offerdestmap', 'offerdestaddrinput', position);
            }

            else if(viewId == 'searchstartpickerUI'){
                mapmod.setMapMode(0);
                mapmod.initialize('searchstartmap', 'searchstartaddrinput', position);
            }

            else if(viewId == 'searchdestpickerUI'){
                mapmod.setMapMode(0);
                mapmod.initialize('searchdestmap', 'searchdestaddrinput', position);
            }

            else if(viewId == 'newfavoritepickerUI'){
                /*mapmod.setMapMode(0);
                mapmod.initialize('newfavoritepickermap', 'newfavoriteaddrinput', position);*/

                //set view to fullscreen favorite picker map as temporary approach
                //TODO: connect fullscreen map view to tab tree
                this.setFullScreenMapView('favoritesgmapscreencontainer');
            }

            else if(viewId == 'showofferrouteUI'){
                mapmod.setMapMode(1);
                var dummydiv = document.createElement('div');
                dummydiv.id = "dummyaddrinput";
                var routearr = mapmod.getRoutePath();
                var center;
                if(routearr != null && routearr != 'undefined'){
                    if(routearr.length >=2){
                        mapmod.initialize('offersimpleroutemap', 'dummyaddrinput', routearr[0]);
                    }else
                        showOverlayDialog('Route anzeigen', '<br />Start und Ziel m&uuml;ssen unterschiedlich sein!<br />', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 0)', '', '');
                }
            }

            else if(viewId == 'showsearchrouteUI'){
                mapmod.setMapMode(1);
                var dummydiv = document.createElement('div');
                dummydiv.id = "dummyaddrinput";
                var routearr = mapmod.getRoutePath();
                var center;
                if(routearr != null && routearr != 'undefined'){
                    if(routearr.length >=2){
                        mapmod.initialize('searchsimpleroutemap', 'dummyaddrinput', routearr[0]);
                    }else
                        showOverlayDialog('Route anzeigen', '<br />Start und Ziel m&uuml;ssen unterschiedlich sein!<br />', 'OK', 'fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 0)', '', '');
                }
            }

            else if(viewId == 'activeofferUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));
                
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers', false, this.setActiveOfferList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Angebote konnten leider nicht geladen werden.')
                });
                this.parseactiveofferlist();
            }
            else if(viewId == 'completedtripsUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/offers/inactive', false, this.setActiveOfferList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Angebote konnten leider nicht geladen werden.')
                });
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches/inactive', false, this.setActiveSearchList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Angebote konnten leider nicht geladen werden.')
                });
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/ratings/open', false, this.setOpenRatingsList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre offenen Bewertungen konnten leider nicht geladen werden.')
                });
                this.parsecompletedtriplist();
            }
            else if(viewId == 'activesearchUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/rides/searches', false, this.setActiveSearchList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Gesuche konnten leider nicht geladen werden.')
                });
                this.parseactivesearcheslist();
            }
            else if(viewId == 'favlistUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg13","tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/favoritepoints', false, this.setFavoriteList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Favoriten konnten leider nicht geladen werden.')
                });
                this.parsefavoriteslist(this.favoritelistdiv, favoritelist);
            }
            else if(viewId == 'ratingsUI'){
                
                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/ratings/summary', false, this.setRatingsSummary, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre gegebenen Bewertungen konnten leider nicht geladen werden.')
                });
                this.parseratingssummary(this.ratingssummarydiv, ratingssummary);
            }
            else if(viewId == 'openratingsUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/ratings/open', false, this.setOpenRatingsList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre offenen Bewertungen konnten leider nicht geladen werden.')
                });
                this.parseopenratingslist(this.openratingslistdiv, openratingslist);
            }
            else if(viewId == 'receivedratingsUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/ratings', false, this.setReceivedRatingsList, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre erhaltenen Bewertungen konnten leider nicht geladen werden.')
                });
                this.parsereceivedratingslist(this.receivedratingslistdiv, receivedratingslist);
            }
            else if(viewId == 'profileUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg13","tabimg14"));

                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/profile', false, this.parseprofilepersonaldata, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Profildaten konnten leider nicht geladen werden.')
                });
                srvconn.GET('/OpenRideServer-RS/resources/users/'+ this.username +'/profile/preferences', false, this.parseprofilepreferences, function(x,s,e) {
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Ihre Einstellungen konnten leider nicht geladen werden.')
                });
            }   
            else if(viewId == 'homeUI'){

                fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
                fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg13","tabimg14"));
				
                // Get initialization data
                srvconn.GET('/OpenRideServer-RS/resources/configuration/init', false, fokus.openride.mobclient.controller.modules.uievents.parseInitData, function(x,s,e){
                    fokus.openride.mobclient.controller.modules.modulemanager.alertajaxerror(x,s,e,'Die Initialdaten konnten leider nicht geladen werden.')
                } );
            }

            this.currentdisplayedview = viewId;
            this.detailsClicked = false;
        },

        dummy : function() {
            return
        },

        changeViewAndUserMode : function(view) {
            fokus.openride.mobclient.controller.modules.uievents.unhideAllTabs();
            fokus.openride.mobclient.controller.modules.uievents.hideUnusedTabs(new Array("tabimg14"));
            switch(view){
                case 'offers':
                    if(usermode != DRIVERMODE) {
                        fokus.openride.mobclient.controller.modules.modulemanager.changemode();
                    }
                    fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);
                    break;
                case 'searches':
                    if(usermode != RIDERMODE) {
                        fokus.openride.mobclient.controller.modules.modulemanager.changemode();
                    }
                    fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(1, 1);
                    break;
                case 'ratings':
                    fokus.openride.mobclient.controller.modules.modulemanager.setTabContent(2, 1);
                    break;
            }
        },

        changemode: function(){
            var drivermodeimg = document.getElementById('drivermodeimg');
            var ridermodeimg = document.getElementById('ridermodeimg');

            var retval = -1;
            if(usermode == DRIVERMODE){
                usermode = RIDERMODE;
                drivermodeimg.src = "../img/drivermodebtn_inactive.png";
                ridermodeimg.src = "../img/ridermodebtn_active.png";
                document.getElementById('usermodeslider').style.marginLeft = '0px';
                document.getElementById('usermodelabel').innerHTML = 'Mitfahrer';

                document.getElementById('riderupdatecount2').style.display = (document.getElementById('riderupdatecount2').innerHTML!='')?'block':'none';
                document.getElementById('driverupdatecount2').style.display = 'none';

                retval = 1;
            }else if(usermode == RIDERMODE){
                usermode = DRIVERMODE;
                drivermodeimg.src = "../img/drivermodebtn_active.png";
                ridermodeimg.src = "../img/ridermodebtn_inactive.png";
                document.getElementById('usermodeslider').style.marginLeft = '41px';
                document.getElementById('usermodelabel').innerHTML = 'Fahrer';

                document.getElementById('driverupdatecount2').style.display = (document.getElementById('driverupdatecount2').innerHTML!='')?'block':'none';
                document.getElementById('riderupdatecount2').style.display = 'none';

                retval = 0;
            }
            createCookie('usermode', retval, 365);
            this.setupTabs();
            return retval;
        },

        setriderupdatecount: function(count) {
            if (!count>0)
                count='';

            document.getElementById('riderupdatecount').innerHTML = count;
            document.getElementById('riderupdatecount2').innerHTML = count;
            document.getElementById('riderupdatecount3').innerHTML = count;

            document.getElementById('riderupdatecount').style.display = (count!='')?'block':'none';

            if (usermode == RIDERMODE) {
                document.getElementById('riderupdatecount2').style.display = (document.getElementById('riderupdatecount2').innerHTML!='')?'block':'none';
            }

            riderupdatecount = count;
        },
        reduceriderupdatecount: function() {
            this.setriderupdatecount(riderupdatecount - 1);
        },
        setdriverupdatecount: function(count) {
            if (!count>0)
                count='';

            document.getElementById('driverupdatecount').innerHTML = count;
            document.getElementById('driverupdatecount2').innerHTML = count;
            document.getElementById('driverupdatecount3').innerHTML = count;

            document.getElementById('driverupdatecount').style.display = (count!='')?'block':'none';

            if (usermode == DRIVERMODE) {
                document.getElementById('driverupdatecount2').style.display = (document.getElementById('driverupdatecount2').innerHTML!='')?'block':'none';
            }

            driverupdatecount = count;
        },
        reducedriverupdatecount: function() {
            this.setdriverupdatecount(driverupdatecount - 1);
        },

        alertajaxerror: function(xhr, textStatus, errorThrown, customMessage){

            var textMessage = '';
            var reload = false;

            switch (textStatus) {
                case 'error':
                    switch (xhr.status) {
                        case 500: // Server error
                            textMessage = 'Die Anfrage konnte durch den Server nicht verarbeitet werden.<br />Sollte dieses Problem weiterhin bestehen, wenden Sie sich bitte an unser Support-Team.';
                            reload = false;
                            break;
                        case 502: // Bad gateway
                            textMessage = 'Die Anfrage konnte nicht an den Server weitergeleitet werden.<br />Bitte versuchen Sie es noch einmal.';
                            reload = false;
                            break;
                        case 400: // Bad request - display custom message, if supplied:
                            textMessage = customMessage || 'Die Anfrage wurde vom Server als ungültig abgewiesen. Bitte überprüfen Sie Ihre Angaben.';
                            break;
                        case 404: // Not found - display custom message, if supplied:
                            textMessage = customMessage || 'Die Anfrage wurde vom Server als ungültig abgewiesen.';
                            break;
                        case 0: // Connection problems - reload
                            location.href="./";
                            break;
                    }
                    //DEBUG:
                    textMessage = textMessage + ' <br /><br /><small style="color:#999;margin-bottom:-1em;">HTTP status: ' + xhr.status + '</small>';
                    break;
                case 'parseerror':
                    textMessage = 'Die Antwort des Servers konnte nicht verarbeitet werden. Bitte versuchen Sie es später noch einmal.';
                    break;
                case 'timeout':
                    textMessage = 'Die Anfrage konnte nicht an den Server gesendet werden. Bitte prüfen Sie Ihre Internetverbindung.';
                    break;
                case 'notmodified':
                    // ok.
                    break;
                case 'validateError':
                    textMessage = customMessage;
                    break;
            }

            if (textMessage != '') {
                if (reload) {
                    showOverlayDialog('Fehler:', textMessage, 'OK', 'location.reload()', '', '');
                }
                else {
                    showOverlayDialog('Fehler:', textMessage, 'OK', '', '', '');
                }
            }

        }
    };
}();

jQuery.fn.sort = function() {
    return this.pushStack( [].sort.apply( this, arguments ), []);
};


function round(zahl,n_stelle){
    n_stelle = (n_stelle == "" || n_stelle == 0 ? 1 : Math.pow(10,n_stelle));

    zahl = Math.round(zahl * n_stelle) / n_stelle;

    return zahl;
}



function dumpProps(obj, parent) {
    // Go through all the properties of the passed-in object
    for (var i in obj) {
        // if a parent (2nd parameter) was passed in, then use that to
        // build the message. Message includes i (the object's property name)
        // then the object's property value on a new line
        if (parent) {
            var msg = parent + "." + i + "\n" + obj[i];
        } else {
            var msg = i + "\n" + obj[i];
        }
        // Display the message. If the user clicks "OK", then continue. If they
        // click "CANCEL" then quit this level of recursion
        if (!confirm(msg)) {
            return;
        }
        // If this property (i) is an object, then recursively process the object
        if (typeof obj[i] == "object") {
            if (parent) {
                dumpProps(obj[i], parent + "." + i);
            } else {
                dumpProps(obj[i], i);
            }
        }
    }
}
