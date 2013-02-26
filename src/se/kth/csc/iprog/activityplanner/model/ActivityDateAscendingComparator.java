package se.kth.csc.iprog.activityplanner.model;

import java.util.*;

public class ActivityDateAscendingComparator implements Comparator<Activity> {
	
	public int compare(Activity a1, Activity a2)
	{
		int cmp =0;
		GregorianCalendar cal1 = a1.getStartDate();
		GregorianCalendar cal2 = a2.getStartDate();
		if(cal1.before(cal2))cmp=-1;
		else if(cal2.before(cal1))cmp=1;
		return cmp;
	}

}
