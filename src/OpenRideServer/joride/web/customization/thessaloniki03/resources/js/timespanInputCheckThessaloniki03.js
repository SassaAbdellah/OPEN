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
	
	if(! isInt(minutesIn)) { minutesInput.value=0;}	
	if(minutesIn <  0)     { minutesInput.value=0;}
	
}
	
/** Ensure that hours input is in 0...23
 * 
 */
function adjustHours(){

	hoursInput=document.getElementById('f1:timespanHours');
	hoursIn=hoursInput.value;
	
	if(! isInt(hoursIn)) { hoursInput.value=0;}	
	if(hoursIn <  0)     { hoursInput.value=0;}
	
}
	
/** Ensure that days input is > 0
 * 
 */
function adjustDays(){
	
	daysInput=document.getElementById('f1:timespanDays');
	daysIn=daysInput.value;

	if(! isInt(daysIn)) { daysInput.value=0;}	
	if(daysIn <  0)     { daysInput.value=0;}
}
	