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
         !isNaN(parseInt(value));
}

	
/** Normalize display so that minutes are in (0...59), hours are in (0...23) 
 *  and days are > 0.
 * 
 */

function normalizeTimespan(){
	
	
	
	alert(
			"Raw Input: \n"+
			"Minutes : "+document.getElementById('f1:timespanMinutes').value+"\n"+
			"Hours   : "+document.getElementById('f1:timespanHours').value+"\n"+
			"Days    : "+document.getElementById('f1:timespanDays').value+"\n"
	);
	

	minutesInput=document.getElementById('f1:timespanMinutes');
	minutesIn=minutesInput.value;
	if(!(isInt(minutesIn))){minutesIn=0;} 
	if(minutesIn <  0){minutesIn=0;}
	minutes=parseInt(minutesIn);	
	
	hoursInput=document.getElementById('f1:timespanHours');
	hoursIn=hoursInput.value;
	if(!(isInt(hoursIn))){hoursIn=0;} 
	if(hoursIn <  0){hoursIn=0;}
	hours=parseInt(hoursIn);
	
	daysInput=document.getElementById('f1:timespanDays');
	daysIn=daysInput.value;
	if(! (isInt(daysIn))){daysIn=0;} 
	if(daysIn <  0){daysIn=0;}
	days=parseInt(daysIn);
	
	//
	//
	alert(
			"Processed Input: \n"+
			"Minutes : "+minutes+"\n"+
			"Hours   : "+hours+"\n"+
			"Days    : "+days+"\n"
	);
	
	
	
	// minutes to display
	minutesLeft=(days*24*60)+(hours*60)+minutes;
	minutesDisplay=minutesLeft % 60;
	minutesInput.value=minutesDisplay;

	alert("Minutes To Display : "+minutesDisplay+" Minutes Left : "+minutesLeft);
	
	// hours to display
	hoursLeft=((minutesLeft-minutesDisplay)/60);
	hoursDisplay=hoursLeft%24;
	hoursInput.value=hoursDisplay;

	alert("Hours To Display : "+minutesDisplay+" Hours Left : "+hoursLeft);

	// days to display
	daysDisplay=((hoursLeft-hoursDisplay)/24);
	daysInput.value=daysDisplay;

}

