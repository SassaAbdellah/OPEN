/*
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

var tabListActiveRideId;
var tabListActiveRefreshTimer;

var updateCountRefreshTimer;

function slidingUITabListClick(objClicked) {

    clearInterval(tabListActiveRefreshTimer);

    // Get DIV containing the match rows
    var contentdiv = $(objClicked).next('.slide_0');

    // Toggle CSS of the clicked item
    $(objClicked).toggleClass("active");

    // Clicked item now != previous
    $(objClicked).removeClass("previous");

    // Previous active items now become inactive + their content is hidden
    if($(".previous").is('.active')) {
        $(".previous").next('.slide_0').slideToggle("slow");
        $(".previous").toggleClass("active");
        $(".previous").removeClass("previous");
    }

    // If the click activated this item, mark it "previous" so we can
    // hide it once the next item is clicked + get the content
    if ($(objClicked).is('.active')) {

        $(objClicked).addClass("previous");

        // Before showing the item's content: Retreive it!
        fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches(objClicked.id, contentdiv);
        // Refresh content infinitely...
        tabListActiveRefreshTimer = setInterval("fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches('"+objClicked.id+"', false)", 15000);

        //currentHref = window.location.href;
        //window.location.href = currentHref.substr(0, currentHref.lastIndexOf("#")) + "#"+this.id;

        tabListActiveRideId = objClicked.id;
        // All done; finally toggle height of the content DIV + scroll
        contentdiv.slideToggle("slow", function() {

            // Compute distance we need to scroll downwards
            heightWindowDiffBottom  = $("#"+tabListActiveRideId).offset().top - $('html,body').scrollTop() + $("#"+tabListActiveRideId).parent().height() - window.innerHeight;
            // Compute distance we need to scroll upwards
            heightWindowDiffTop = $("#"+tabListActiveRideId).offset().top - $('html,body').scrollTop();

            // Only do the scrolling if part of the element is actually hidden - not otherwise
            scrollDiff = 0;

            if (heightWindowDiffTop < 0)
                scrollDiff = heightWindowDiffTop;
            else if (heightWindowDiffBottom > 0)
                scrollDiff = heightWindowDiffBottom;
            //DEBUG:
            //alert($("#"+tabListActiveRideId).offset().top +" - "+ $('html,body').scrollTop())

            scrollTop = 0;
            if ($('html,body').scrollTop() + scrollDiff > $("#"+tabListActiveRideId).offset().top)
                scrollTop = $("#"+tabListActiveRideId).offset().top;
            else
                scrollTop = $('html,body').scrollTop() + scrollDiff;

            if (scrollDiff != 0)
                $('html,body').animate({
                    scrollTop: scrollTop
                }, "slow");

        });

    }
    else {
        contentdiv.slideToggle("slow");
    }

    return false;
     
}

function slidingUIComplTripsTabListClick(objClicked) {

    clearInterval(tabListActiveRefreshTimer);

    // Get DIV containing the match rows
    var contentdiv = $(objClicked).next('.slide_0');

    // Toggle CSS of the clicked item
    $(objClicked).toggleClass("active");

    // Clicked item now != previous
    $(objClicked).removeClass("previous");

    // Previous active items now become inactive + their content is hidden
    if($(".previous").is('.active')) {
        $(".previous").next('.slide_0').slideToggle("slow");
        $(".previous").toggleClass("active");
        $(".previous").removeClass("previous");
    }

    // If the click activated this item, mark it "previous" so we can
    // hide it once the next item is clicked + get the content
    if ($(objClicked).is('.active')) {

        $(objClicked).addClass("previous");

        // Before showing the item's content: Retreive it!
        fokus.openride.mobclient.controller.modules.modulemanager.receiveInactiveMatches(objClicked.id, contentdiv);
        // Refresh content infinitely...
        //tabListActiveRefreshTimer = setInterval("fokus.openride.mobclient.controller.modules.modulemanager.receiveMatches('"+objClicked.id+"', false)", 20000);

        //currentHref = window.location.href;
        //window.location.href = currentHref.substr(0, currentHref.lastIndexOf("#")) + "#"+this.id;

        tabListActiveRideId = objClicked.id;
        // All done; finally toggle height of the content DIV + scroll
        contentdiv.slideToggle("slow", function() {

            // Compute distance we need to scroll downwards
            heightWindowDiffBottom  = $("#"+tabListActiveRideId).offset().top - $('html,body').scrollTop() + $("#"+tabListActiveRideId).parent().height() - window.innerHeight;
            // Compute distance we need to scroll upwards
            heightWindowDiffTop = $("#"+tabListActiveRideId).offset().top - $('html,body').scrollTop();

            // Only do the scrolling if part of the element is actually hidden - not otherwise
            scrollDiff = 0;

            if (heightWindowDiffTop < 0)
                scrollDiff = heightWindowDiffTop;
            else if (heightWindowDiffBottom > 0)
                scrollDiff = heightWindowDiffBottom;
            //DEBUG:
            //alert($("#"+tabListActiveRideId).offset().top +" - "+ $('html,body').scrollTop())

            if (scrollDiff != 0)
                $('html,body').animate({
                    scrollTop: $('html,body').scrollTop() + scrollDiff
                }, "slow");

        });

    }
    else {
        contentdiv.slideToggle("slow");
    }
    return false;    
}

function setupUITabList(){
    $(".linkslide_0").click( function() {
        slidingUITabListClick(this);
    } );
}

function setupCompletedTripUITabList(){
    $(".linkslide_0").click( function() {
        slidingUIComplTripsTabListClick(this);
    } );
}