/* ******************************************************************************
 * javascript functions used on ride create page
 * *****************************************************************************
 * 
 */

/** Function to be called when "starttime earliest" time picker is called
 *  Just takes care that "startTimeLatest" is not before starttime earliest
 */
function onStartimeEarliestChange(){
	
	adjustLatestToEarliest();
}


/** Function to be called when "starttime latest" time picker is called
 * 
 */
function onStarttimeLatestChange(){
	adjustLatestToEarliest();
}


/** Set starttimeLatest to maximum of starttimeEarliest, starttimeLatest
 *  Just takes care that "startTimeLatest" is not before starttime earliest
 * 
 */
function adjustLatestToEarliest(){

	//get hold of the two calendars, desktop case
	var calEarliest=PF('starttimeEarliestCal');
	var calLatest=PF('starttimeLatestCal');
	
	//get hold of the two calendars, mobile case
	var calEarliestMobile=PF('starttimeEarliestCalMobile');
	var calLatestMobile=PF('starttimeLatestCalMobile');
	

	// if calendar for latest starttime is before earliestStarttime, then fix this
	
	// desktop case
	var earliestDate;
	var latestDate;
	
	if(calEarliest){ earliestDate=new Date(calEarliest.getDate());}
	if(calLatest)  { latestDate=new Date(calLatest.getDate());    }
	
	// mobile case
	var earliestDateMobile;
	var latestDateMobile;
	
	if(calEarliestMobile){earliestDateMobile=new Date(calEarliestMobile.getDate());}
	if(calLatestMobile)  {latestDateMobile=new Date(calLatestMobile.getDate());}
	
	
	// adjust (desktop case)
	
	if((undefined != earliestDate) && (earliestDate.getTime()>latestDate.getTime())){
	calLatest.setDate(earliestDate);
	}
	// adjust (mobile case)
	if((undefined != earliestDateMobile) && (earliestDateMobile.getTime()>latestDateMobile.getTime())){
	calLatestMobile.setDate(earliestDateMobile);
	}
}
	
	
	
	