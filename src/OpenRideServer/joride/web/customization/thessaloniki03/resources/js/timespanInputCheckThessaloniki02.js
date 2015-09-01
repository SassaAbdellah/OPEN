/*******************************************************************************
 * javascript functions used on ride create page
 * *****************************************************************************
 * 
 */


/** Ensure that input is a valid integer.
 */
function isInt(value) {
  return !isNaN(value) && 
         parseInt(Number(value)) == value && 
         !isNaN(parseInt(value, 10));
}




/** Ensure that minutes input is in 0...59
 * 
 */
function adjustMinutes(){

	minutesInput=document.getElementById('f1:timespanMinutes');
	minutesIn=minutesInput.value;
	
	if(isInt(minutesIn)){
		if(minutesIn <  0) { minutesInput.value="00";}
		if(minutesIn > 59) { minutesInput.value=59;}
	}	else {
		minutesInput.value="00";
	}	
}
	
/** Ensure that hours input is in 0...23
 * 
 */
function adjustHours(){

	hoursInput=document.getElementById('f1:timespanHours');
	hoursIn=hoursInput.value;
	if(isInt(hoursIn)){
		if(hoursIn <  0) { hoursInput.value="00";}
		if(hoursIn > 23) { hoursInput.value=23;}
	} else {
		hoursInput.value="00";
	}
}
	
/** Ensure that days input is > 0
 * 
 */
function adjustDays(){
	
	daysInput=document.getElementById('f1:timespanDays');
	daysIn=daysInput.value;
	if(isInt(daysIn)){
		if(daysIn <  0) { daysInput.value="000";}
	}else{
		daysInput.value="000";
	}
}
	