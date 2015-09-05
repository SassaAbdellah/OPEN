/*******************************************************************************
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

	//get hold of the two calendars
	var calEarliest=PF('starttimeEarliestCal');
	var calLatest=PF('starttimeLatestCal');

	// if calendar for latest starttime is before earliestStarttime, then fix this
	var earliestDate=new Date(calEarliest.getDate());
	var latestDate=new Date(calLatest.getDate());
	// adjust 
	if(earliestDate.getTime()>latestDate.getTime()){
	calLatest.setDate(earliestDate);
	}
}
	
	
	
	