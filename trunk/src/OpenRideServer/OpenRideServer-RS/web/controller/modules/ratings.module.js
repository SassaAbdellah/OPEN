fokus.openride.mobclient.controller.modules.ratings = function(){

    /* ------ private variabeles and methods ------ */

    /*var respRatingsSummary = {
        'RatingsSummaryResponse':[
        {
            'ratingsTotal': '',
            'ratingsRatioPercent': '',
            'ratingsLatestPositive': '',
            'ratingsLatestNeutral': '',
            'ratingsLatestNegative': ''
        }
        ]
    }
    
    var respReceivedRating = {
        'ReceivedRatingResponse':[
        {
            'custId': '',
            'custNickname': '',
            'custGender': '',
            'custRole': '',
            'timestamprealized': '',
            'receivedRating': '',
            'receivedRatingComment': ''
        }
        ]
    }

    var respOpenRating = {
        'OpenRatingResponse':[
        {
            'riderRouteId': '',
            'custId': '',
            'custNickname': '',
            'custGender': '',
            'custRole': '',
            'timestamprealized': ''
        }
        ]
    }*/

    var reqGivenRating = {
        'GivenRatingRequest':[
        {
            'riderRouteId': '',
            'givenRating': '',
            'givenRatingComment': ''
        }
        ]
    }


    return {

        setRiderRouteId : function(id){
            reqGivenRating.GivenRatingRequest[0].riderRouteId = id;
        },

        getRiderRouteId : function(){
            return reqGivenRating.GivenRatingRequest[0].riderRouteId;
        },

        setGivenRating : function(rating){
            reqGivenRating.GivenRatingRequest[0].givenRating = rating;
        },

        getGivenRating : function(){
            return reqGivenRating.GivenRatingRequest[0].givenRating;
        },

        setGivenRatingComment : function(comment){
            reqGivenRating.GivenRatingRequest[0].givenRatingComment = comment;
        },

        getGivenRatingComment : function(){
            return reqGivenRating.GivenRatingRequest[0].givenRatingComment;
        },

        getGivenRatingRequest : function(){
            return reqGivenRating;
        }

    };
}();
