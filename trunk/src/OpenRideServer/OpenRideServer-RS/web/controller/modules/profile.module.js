fokus.openride.mobclient.controller.modules.profile = function(){


    /* ------ private variabeles and methods ------ */
    var reqProfile = {
        'ProfileRequest':[
            {
                'dateOfBirth'       : '',
                'email'             : '',
                'mobilePhoneNumber' : '',
                'fixedPhoneNumber'  : '',
                'streetAddress'     : '',
                'zipCode'           : '',
                'city'              : '',
                'isSmoker'          : '',
                'licenseDate'       : '',
                'carColour'         : '',
                'carBrand'          : '',
                'carBuildYear'      : '',
                'carPlateNo'        : ''
            }
        ]
    }

    var reqPreferences = {
        'PreferencesRequest':[
            {
                'prefIsSmoker'      : '',
                'prefGender'        : ''
            }
        ]
    }

    var reqPassword = {
        'PasswordRequest':[
            {
                'passwordOld'       : '',
                'password'          : ''
            }
        ]
    }

    /* ------ public variabeles and methods ------ */
    return {

        setDateOfBirth : function(dateofbirth){
            reqProfile.ProfileRequest[0].dateOfBirth = dateofbirth;
        },

        getDateOfBirth : function(){
            return reqProfile.ProfileRequest[0].dateOfBirth;
        },

        setEmail : function(mailaddress){
            reqProfile.ProfileRequest[0].email = mailaddress;
        },

        getEmail : function(){
            return reqProfile.ProfileRequest[0].email;
        },

        setMobilePhoneNumber : function(mobileno){
            reqProfile.ProfileRequest[0].mobilePhoneNumber = mobileno;
        },

        getMobilePhoneNumber : function(){
            return reqProfile.ProfileRequest[0].mobilePhoneNumber;
        },

        setFixedPhoneNumber : function(phoneno){
            reqProfile.ProfileRequest[0].fixedPhoneNumber = phoneno;
        },

        getFixedPhoneNumber : function(){
            return reqProfile.ProfileRequest[0].fixedPhoneNumber;
        },

        setStreetAddress : function(street){
            reqProfile.ProfileRequest[0].streetAddress = street;
        },

        getStreetAddress : function(){
            return reqProfile.ProfileRequest[0].streetAddress;
        },

        setZipCode : function(zip){
            reqProfile.ProfileRequest[0].zipCode = zip;
        },

        getZipCode : function(){
            return reqProfile.ProfileRequest[0].zipCode;
        },

        setCity : function(city){
            reqProfile.ProfileRequest[0].city = city;
        },

        getCity : function(){
            return reqProfile.ProfileRequest[0].city;
        },

        setIsSmoker : function(issmoker){
            reqProfile.ProfileRequest[0].isSmoker = issmoker;
        },

        getIsSmkoker : function(){
            return reqProfile.ProfileRequest[0].isSmoker;
        },

        setLicenseDate : function(licensedate){
            reqProfile.ProfileRequest[0].licenseDate = licensedate;
        },

        getLicenseDate : function(){
            return reqProfile.ProfileRequest[0].licenseDate;
        },

        setCarColour : function(carColour){
            reqProfile.ProfileRequest[0].carColour = carColour;
        },

        getCarColour : function(){
            return reqProfile.ProfileRequest[0].carColour;
        },

        setCarBrand : function(carBrand){
            reqProfile.ProfileRequest[0].carBrand = carBrand;
        },

        getCarBrand : function(){
            return reqProfile.ProfileRequest[0].carBrand;
        },

        setCarBuildYear : function(carBuildYear){
            reqProfile.ProfileRequest[0].carBuildYear = carBuildYear;
        },

        getCarBuildYear : function(){
            return reqProfile.ProfileRequest[0].carBuildYear;
        },

        setCarPlateNo : function(carPlateNo){
            reqProfile.ProfileRequest[0].carPlateNo = carPlateNo;
        },

        getCarPlateNo : function(){
            return reqProfile.ProfileRequest[0].carPlateNo;
        },

        getProfileRequest : function(){
            return reqProfile;
        },


        setPrefIsSmoker : function (prefissmoker){
            reqPreferences.PreferencesRequest[0].prefIsSmoker = prefissmoker;
        },

        getPrefIsSmoker : function (){
            return reqPreferences.PreferencesRequest[0].prefIsSmoker;
        },

        setPrefGender : function (prefgender){
            reqPreferences.PreferencesRequest[0].prefGender = prefgender;
        },

        getPrefGender : function (){
            return reqPreferences.PreferencesRequest[0].prefGender;
        },

        getPreferencesRequest : function(){
            return reqPreferences;
        },

        setPasswordOld : function (passwordold){
            reqPassword.PasswordRequest[0].passwordOld = passwordold;
        },

        getPasswordOld : function (){
            return reqPassword.PasswordRequest[0].passwordOld;
        },

        setPassword : function (password){
            reqPassword.PasswordRequest[0].password = password;
        },

        getPassword : function (){
            return reqPassword.PasswordRequest[0].password;
        },

        getPasswordRequest : function(){
            return reqPassword;
        }

    };
}();

