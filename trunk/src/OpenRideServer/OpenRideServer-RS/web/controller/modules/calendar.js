/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


fokus.openride.mobclient.controller.modules.calendar = function(){

    /* ------ private variabeles and methods ------ */
    var initDate = new Date();

    /* ------ public variabeles and methods ------ */
    return {
        date : initDate,

        getDate : function(){
            return this.date;
        },

        getDay : function(){
            return this.date.getDate();
        },

        getMonth : function(){
            return this.date.getMonth()+1;
        },

        getYear : function(){
            return this.date.getFullYear();
        },

        getHour : function(){
            return this.date.getHours();
        },

        getMin : function(){
            return this.date.getMinutes();
        },

        getDateString : function(){
            return ''+this.getDay()+'.'+this.getMonth()+'.'+this.getYear()+' '+this.getHours()+':'+this.getMinutes()+' Uhr';
        },

        //setters

        setDay : function(day){
            this.date.setDate(day);
        },

        setHour : function(hour){
            this.date.setHours(hour);
        },

        setMin : function(min){
            this.date.setMinutes(min);
        },

        setMonth : function(month){
            this.date.setMonth(month);
        },

        setYear : function(year){
            this.date.setYear(year);
        },

        increaseYear : function(){
            this.date.setFullYear(this.date.getFullYear()+1);
        },

        decreaseYear : function(){
            this.date.setFullYear(this.date.getFullYear()-1);
        },

        increaseMonth : function(){
            this.date.setMonth(this.date.getMonth()+1);
        },

        decreaseMonth : function(){
            this.date.setMonth(this.date.getMonth()-1);
        },

        increaseDay : function(){
            this.date.setDate(this.date.getDate()+1);
        },

        decreaseDay : function(){
            this.date.setDate(this.date.getDate()-1);
        },

        increaseHour : function(){
            this.date.setHours(this.date.getHours()+1);
        },

        decreaseHour : function(){
            this.date.setHours(this.date.getHours()-1);
        },

        increaseMin : function(min){
            if(min < 0)min = 0;

            var rest = this.date.getMinutes() % 5;

            // Round to 5
            if (rest != 0){
                min = 0;
                do {
                    min += 1;
                    rest = (this.date.getMinutes() + min) % 5;
                } while (rest != 0);
            }

            this.date.setMinutes(this.date.getMinutes()+min);
        },

        decreaseMin : function(min){
            if(min < 0)min = 0;

            var rest = this.date.getMinutes() % 5;

            // Round to 5
            if (rest != 0){
                min = 0;
                do {
                    min += 1;
                    rest = (this.date.getMinutes() - min) % 5;
                } while (rest != 0);
            }

            this.date.setMinutes(this.date.getMinutes()-min);
        },

        reset : function(){
            this.date = new Date();
        }
    };
}();