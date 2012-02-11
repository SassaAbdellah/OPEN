fokus.openride.mobclient.controller.serverconnector = function(){
    
    /* ------ private variabeles and methods ------ */

    callbackWrapper = function(response, cbFunction) {
        // Check whether the session has expired, i.e. if the reponse contains an HTML #loginForm element:
        if (typeof response == "string" && response.indexOf("loginForm") != -1) {
            // Need to reload -> require the user to login again
            location.href="./";       
        } else if (typeof cbFunction == 'function'){
            // Coninue with actual callback function
            cbFunction(response, "error");
        }
    }

    /* ------ public variabeles and methods ------ */
    
    return {
        // Base URI - Leave empty for clients runnning on the same host:
        baseURI : '',
        // ..or set server IP + port number for other scenarios, e.g. for iPhone/PhoneGap client:
        //baseURI : 'http://193.174.152.244:3003',

        POST : function(scopeURI, asynch, data, successcallback, errorcallback){
            $.ajax({
                url: fokus.openride.mobclient.controller.serverconnector.baseURI+scopeURI,
                async: asynch,
                data: JSON.stringify(data),
//                beforeSend: function() {
//                    showProgressDialog();
//                },
//                complete: function() {
//                    hideProgressDialog();
//                },
                success: function(response) {
                    callbackWrapper(response, successcallback);
                },
                error: function(response) {
                    callbackWrapper(response, errorcallback);
                },
                type: 'POST'
            });

        },

        GET : function(scopeURI, asynch, successcallback, errorcallback){
            $.ajax({
                url: fokus.openride.mobclient.controller.serverconnector.baseURI+scopeURI,
                async: asynch,
//                beforeSend: function() {
//                    showProgressDialog();
//                },
//                complete: function() {
//                    hideProgressDialog();
//                },
                success: function(response) {
                    callbackWrapper(response, successcallback);
                },
                error: function(response) {
                    callbackWrapper(response, errorcallback);
                },
                type: 'GET'
            });
        },

        PUT : function(scopeURI, asynch, data, successcallback, errorcallback){
            $.ajax({
                url: fokus.openride.mobclient.controller.serverconnector.baseURI+scopeURI,
                async: asynch,
                data: JSON.stringify(data),
//                beforeSend: function() {
//                    showProgressDialog();
//                },
//                complete: function() {
//                    hideProgressDialog();
//                },
                success: function(response) {
                    callbackWrapper(response, successcallback);
                },
                error: function(response) {
                    callbackWrapper(response, errorcallback);
                },
                type: 'PUT'
            });
        },

        PUTaction : function(scopeURI, asynch, successcallback, errorcallback){
            $.ajax({
                url: fokus.openride.mobclient.controller.serverconnector.baseURI+scopeURI,
                async: asynch,
//                beforeSend: function() {
//                    showProgressDialog();
//                },
//                complete: function() {
//                    hideProgressDialog();
//                },
                success: function(response) {
                    callbackWrapper(response, successcallback);
                },
                error: function(response) {
                    callbackWrapper(response, errorcallback);
                },
                type: 'PUT'
            });
        },

        DELETE : function(scopeURI, asynch, successcallback, errorcallback){
            $.ajax({
                url: fokus.openride.mobclient.controller.serverconnector.baseURI+scopeURI,
                async: asynch,
//                beforeSend: function() {
//                    showProgressDialog();
//                },
//                complete: function() {
//                    hideProgressDialog();
//                },
                success: function(response) {
                    callbackWrapper(response, successcallback);
                },
                error: function(response) {
                    callbackWrapper(response, errorcallback);
                },
                type: 'DELETE'
            });
        }
    };
}();


