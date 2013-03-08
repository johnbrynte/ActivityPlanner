package mvc.model;
import java.util.*;

public class ActivityHolder {
	private Model model;
	private String name;
	private ArrayList<Activity> activities;
	
	ActivityHolder(Model model,String name)
	{
		this.model=model;
		this.name=name;
		this.activities=new ArrayList<Activity>();
	}
	
	public Activity[] getActivities()
	{
		Activity [] activityArray = new Activity[activities.size()];
		return activities.toArray(activityArray);
	}
	ArrayList<Activity> getActivityArrayList()
	{
		return activities;
	}
	void addActivity(Activity activity)
	{
		activities.add(activity);
	}
	void removeActivity(Activity activity)
	{
		activities.remove(activity);
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
		model.productionLineChanged(this);
	}
	/*
	 * Inserts activities in the ActivityHolder and adjusts the these and the other activities 
	 * according to the collisionControl
	 * 
	 * The activities must be sorted in time from earliest the latest and must have no free 
	 * time between them at all
	 * 
	 * This method is onlly for recursive calls and does not honour activities moving back
	 * when activitites is taken away from the production line. For outer calls se insertActivity()
	 * 
	 */
	private void insertActivities(Activity [] activities,GregorianCalendar [] startDates)
	{
		//Remove these activities temporarily from the production line
		for(int i=0;i<activities.length;i++)
		{
			activities[i].getProductionLine().removeActivity(activities[i]);
		}
		Activity [] collidingActivities = getSortedCollidingActivities(activities,startDates);
		if(collidingActivities.length>0)
		{
			GregorianCalendar [] newStartDates = new GregorianCalendar[collidingActivities.length];
			//Generate info for all new activities
			GregorianCalendar currentStartDate = (GregorianCalendar)startDates[activities.length-1].clone();
			currentStartDate.add(GregorianCalendar.DAY_OF_MONTH, activities[activities.length-1].getDateSpan());
			for(int i=0;i<collidingActivities.length;i++)
			{
				
				newStartDates[i]=(GregorianCalendar)currentStartDate.clone();
				currentStartDate.add(GregorianCalendar.DAY_OF_MONTH,collidingActivities[i].getDateSpan());
			}
			//Recursive call for these new activities
			insertActivities(collidingActivities,newStartDates);
			
		}
		//When recursive call has happened and activities have been pushed
		//try to insert the activities again
		for(int i=0;i<activities.length;i++)
		{
			activities[i].setStartDate(startDates[i]);
			addActivity(activities[i]);
			activities[i].setProductionLine(this);
		}
	}
	void insertActivity(Activity activity,GregorianCalendar startDate)
	{
		//If the activity is moved from antoher production line or moved backwards in time 
		//then the removalprocedure of backing up following activities will apply
		if(activity.getProductionLine()==this)
		{
			if(startDate.before(activity.getStartDate()))
			{
				activity.getProductionLine().activityRemovalProcedure(activity,this,startDate);
				insertActivities(new Activity [] {activity},new GregorianCalendar[]{startDate});
			}
			else
			{
				insertActivities(new Activity [] {activity},new GregorianCalendar[]{startDate});
			}
		}
		else
		{
			activity.getProductionLine().activityRemovalProcedure(activity,this,startDate);
			insertActivities(new Activity [] {activity},new GregorianCalendar[]{startDate});
		}
	}
	
	/*
	 * This method is a shorcut to the method activityRemovalProcedure(Activity activity,ActivityHolder newProductionLine,GregorianCalendar newStartDate)
	 * This is when you want the normal removal procedure meaning any following activities will be moved back to a maximum
	 * of the removed activity's datespan ammount of days.
	 */
	void activityRemovalProcedure(Activity activity)
	{
		activityRemovalProcedure(activity,null,new GregorianCalendar());
	}
	
	
	
	/*
	 * This method is called when an activity is moved from the production line
	 * The method will go through all activities that were after the removed activity
	 * and if they were connected try to move them back to the first activity as long as they
	 * do not violate their earliest start date.
	 * 
	 * @param activity the activity which is to removed from this production line
	 */
	void activityRemovalProcedure(Activity activity,ActivityHolder newProductionLine,GregorianCalendar newStartDate)
	{
		removeActivity(activity);
		activity.setProductionLine(model.getUnscheduledActivities());
		model.getUnscheduledActivities().addActivity(activity);
		
		
		GregorianCalendar lastActivityStartDate=activity.getStartDate();
		int lastActivityDateSpan=activity.getDateSpan();
		GregorianCalendar lastActivityEndDate = activity.getEndDate();
		GregorianCalendar lastActivityNewStartDate=activity.getStartDate();
		//This is right in the case that the activity is moved away from the group
		//and the group can thus not follow this new activity
		lastActivityNewStartDate.add(GregorianCalendar.DAY_OF_MONTH,-lastActivityDateSpan);
		//If instead the activity only has been moved backwards in time and not passed any other activities
		//on the way the whole group will not only back to the last activty's former position but all the way
		//to the new location of the activity
		if(newProductionLine==this && newStartDate.before(activity.getStartDate()))
		{
			Iterator<Activity> it = activities.iterator();
			boolean activityCollision=false;
			while(it.hasNext())
			{
				Activity a = it.next();
				GregorianCalendar startDateA = a.getStartDate();
				GregorianCalendar endDateA = a.getEndDate();
				if(!(endDateA.before(newStartDate) || startDateA.after(lastActivityStartDate)))activityCollision=true;
			}
			if(!activityCollision)
			{
				lastActivityNewStartDate=(GregorianCalendar)newStartDate.clone();
			}
		}
		
		sortActivities();
		Iterator<Activity> it = activities.iterator();
		while(it.hasNext())
		{
			Activity a = it.next();
			GregorianCalendar startDateA = a.getStartDate();
			//Checks that we are at least after the activity which we are about to remove
			if(startDateA.after(lastActivityStartDate))
			{
				if(lastActivityEndDate.compareTo(startDateA)==0)
				{
					GregorianCalendar earliestStartDate = a.getEarliestStartDate();
					
					lastActivityStartDate=startDateA;
					lastActivityNewStartDate.add(GregorianCalendar.DAY_OF_MONTH, lastActivityDateSpan);
					//If we passed the earliest start date with THIS operation then only 
					//Must also check so earliest start date is not null as when the activity doesn't have an earliest start date
					if(earliestStartDate!=null && lastActivityNewStartDate.before(earliestStartDate) && !lastActivityStartDate.before(earliestStartDate))
					{
						lastActivityNewStartDate=earliestStartDate;
					}
					lastActivityEndDate=a.getEndDate();
					lastActivityDateSpan=a.getDateSpan();
					a.setStartDate(lastActivityNewStartDate);
					
					
				}
			}
		}
	}
	/*
	 * The method sorts the activities in the production line ascending by date
	 * 
	 */
	void sortActivities()
	{
		Collections.sort(activities,new ActivityDateAscendingComparator());
	}
	
	
	/*
	 * The method is used when an activity is inserted and collisions are handeled
	 * This methods finds the set of activities which collides with the specified activities
	 * and their new starttimes. The method only makes sure that the individual activity is not taken
	 * and compared against itself. The activities in the set may be compared against each other if that 
	 * has not been taken care of 
	 * 
	 *   @param activity the activities which are to be inserted
	 *   @param startTimes the new startTimes which the activities are about to have
	 *   @return the activities which collides with the specified activities
	 */
	private Activity[] getSortedCollidingActivities(Activity[] activity,GregorianCalendar[] startDates)
	{
		ArrayList<Activity> collidingActivities = new ArrayList<Activity>();
		for(int i=0;i<activity.length;i++)
		{
			Iterator<Activity> it = activities.iterator();
			int dateSpan = activity[i].getDateSpan();
			GregorianCalendar startDate=startDates[i];
			GregorianCalendar endDate = ((GregorianCalendar)startDate.clone());
			endDate.add(GregorianCalendar.DAY_OF_MONTH,dateSpan);
			while(it.hasNext())
			{
				Activity a = it.next();
				if(a!=activity[i])
				{
					GregorianCalendar startDateA = a.getStartDate();
					GregorianCalendar endDateA = a.getEndDate();
					
					//Checks if both dates in cal comes before or at the time of the start of calA or if it is the other way around 
					if(!((!startDate.after(startDateA) && !endDate.after(startDateA)) || (!startDateA.after(startDate) && !endDateA.after(startDate))))
					{
						if(!collidingActivities.contains(a))collidingActivities.add(a);
					}
				}
			}
		}
		Collections.sort(collidingActivities,new ActivityDateAscendingComparator());
		Activity[] ca = new Activity[collidingActivities.size()];
		ca = collidingActivities.toArray(ca);
		return ca;
	}
	/*
	 * A simple debugging method used to output the activities in the production line.
	 */
	public void printAllActivities()
	{
		System.out.println("---------------"+name+"---------------");
		Iterator<Activity> it = activities.iterator();
		while(it.hasNext())
		{
			Activity a = it.next();
			a.printActivity();
		}
	}
	
}
