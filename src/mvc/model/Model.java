package mvc.model;

import java.util.*;

public class Model extends Observable
{
	private ArrayList<ActivityHolder> productionLines;
	private ActivityHolder unscheduledActivities;
	private ActivityHolder allActivities;
	
	
	
	public Model()
	{
		productionLines=new ArrayList<ActivityHolder>();
		unscheduledActivities= new ActivityHolder(this,"Unscheduled");
		allActivities = new ActivityHolder(this, "All Activities");
                
                /* ADDING SOME DATA FOR TESTING
                GregorianCalendar todaysDay = new GregorianCalendar();
                GregorianCalendar tomorrowsDay = new GregorianCalendar();;
                tomorrowsDay.add(Calendar.DATE, 1);
                
                addActivity("A2", 10, todaysDay, tomorrowsDay);
                addActivity("A1", 20, todaysDay, tomorrowsDay);*/
	}
	/*
	 * Should be called when one of an activity's property has been changed.
	 * This is changes that doesn't change the scheduling or time parameters of the activity
	 * Any changes of that sort should be done through the models proxy-methods 
	 * 
	 * @param activity The activity that has been changed
	 */
	synchronized void activityChanged(Activity activity)
	{
		notifyObservers();
	}
	/*
	 * Should be called when one of the production lines inner parameters has changed
	 * without affecting the schedule in any way
	 * 
	 * @param productionLine The production line that has changed
	 */
	synchronized void productionLineChanged(ActivityHolder productionLine)
	{
		notifyObservers();
	}
	/*
	 * Return all activities, those assigned to the schedule and those which are not
	 * @return the activities
	 */
	public synchronized Activity[] getActivities()
	{
		return allActivities.getActivities();
	}
	/*
	 * Returns all production lines. That is all ActivityHolder:s except unscheduled activities
	 * @return the production lines
	 */
	public synchronized ActivityHolder[] getProductionLines()
	{
		ActivityHolder [] array = new ActivityHolder[productionLines.size()];
		array=productionLines.toArray(array);
		return array;
	}
	/*
	 * Gets the first and last time in milliseconds after 1970 where there is an activity or an activity parameter
	 * @return the time
	 */
	public synchronized DateRange getDateRange()
	{
		DateRange dateRange = new DateRange();
		Iterator<ActivityHolder> it = productionLines.iterator();
		while(it.hasNext())
		{
			ActivityHolder productionLine = it.next();
			ArrayList<Activity> list = productionLine.getActivityArrayList();
			Iterator<Activity> it2 = list.iterator();
			while(it2.hasNext())
			{
				Activity a = it2.next();
				GregorianCalendar esd = a.getEarliestStartDate();
				GregorianCalendar led = a.getLatestEndDate();
				GregorianCalendar sd = a.getStartDate();
				GregorianCalendar ed = a.getEndDate();
				if(dateRange.startDate!=null)
				{
					if(esd!=null)
					{
						if(esd.before(dateRange.startDate))dateRange.startDate=esd;
						if(esd.after(dateRange.endDate))dateRange.endDate=esd;
					}
					if(led!=null)
					{
						if(led.before(dateRange.endDate))dateRange.endDate=led;
						if(led.after(dateRange.endDate))dateRange.endDate=led;
					}
					if(sd.before(dateRange.startDate))dateRange.startDate=sd;
					if(ed.after(dateRange.endDate))dateRange.endDate=ed;
				}
				else
				{
					dateRange.startDate=sd;
					dateRange.endDate=ed;
					if(esd!=null)
					{
						if(esd.before(dateRange.startDate))dateRange.startDate=esd;
						if(esd.after(dateRange.endDate))dateRange.endDate=esd;
					}
					if(led!=null)
					{
						if(led.before(dateRange.startDate))dateRange.startDate=led;
						if(led.after(dateRange.endDate))dateRange.endDate=led;
					}
				}
			}
		}
		Iterator<Activity> itU = unscheduledActivities.getActivityArrayList().iterator();
		while(itU.hasNext())
		{
			Activity a = itU.next();
			GregorianCalendar esd = a.getEarliestStartDate();
			GregorianCalendar led = a.getLatestEndDate();
				if(esd!=null)
				{
					if(dateRange.startDate==null)
					{
						dateRange.startDate=esd;
						dateRange.endDate=esd;
					}
					else
					{
						if(esd.before(dateRange.startDate))dateRange.startDate=esd;
						if(esd.after(dateRange.endDate))dateRange.endDate=esd;
					}
					
				}
				if(led!=null)
				{
					if(dateRange.startDate==null)
					{
						dateRange.endDate=led;
						dateRange.endDate=led;
					}
					else
					{
						if(led.before(dateRange.startDate))dateRange.startDate=led;
						if(led.after(dateRange.endDate))dateRange.endDate=led;
					}
				}
		}
		return dateRange;
	}
	/*
	 * Return all activities associated with a certain production line
	 * @param productionLine the productionLine from which the activities should be extracted
	 * @return the activities
	 */
	public synchronized Activity[] getActivitiesFromProductionLine(ActivityHolder productionLine)
	{
		return productionLine.getActivities();
	}
	
	public synchronized ActivityHolder getUnscheduledActivities()
	{
		return unscheduledActivities;
	}
	/*
	 * The method reschedules an activity to another/same production line and another/same time
	 * The method takes into considerations any rules set regarding collisions in the schedule.
	 * To unschedule an activity use the unschedule method instead.
	 * 
	 * @param activity the activity to be moved
	 * @param productionLine the productionLine to where the activity will be moved
	 * @param startDate the new date on which the activity will start
	 *
	 */
	public synchronized void reschedule(Activity activity,ActivityHolder productionLine,GregorianCalendar startDate)
	{
		if(productionLine!=null && startDate!=null)
		{
			productionLine.insertActivity(activity, startDate);
		}
		notifyObservers();
	}
	/*
	 * Used to set an activity's date span, use this instead of editing the activity directly
	 * to make sure schedule behaviour is kept
	 * @param activity the activity to be edited
	 * @param dateSpan the new date span
	 */
	public synchronized void setActivityDateSpan(Activity activity,int dateSpan)
	{
		if(dateSpan>0)
		{
			//By internally changing the activity's date in accordance with its decrease in
			//dateSpan we can trigger the move back functionality. So if the dateSpan is less than
			//before the following activities will be moved back just as if the activity was removed from the
			//production line and being x days long.
			if(dateSpan<activity.getDateSpan())
			{
				int dateSpanBefore = activity.getDateSpan();
				
				GregorianCalendar actualStartDate = activity.getStartDate();
				GregorianCalendar fakeStartDate = activity.getStartDate();
				fakeStartDate.add(GregorianCalendar.DAY_OF_MONTH, dateSpanBefore-dateSpan);
				activity.setStartDate(fakeStartDate);
				activity.setDateSpan(dateSpan);
				reschedule(activity,activity.getProductionLine(),actualStartDate);
			}
			else
			{
				activity.setDateSpan(dateSpan);
				reschedule(activity,activity.getProductionLine(),activity.getStartDate());
			}
		}
		else
		{
			//If date span is not accepted, then just notify the observers to make sure 
			//there are no inconsistencies
			notifyObservers();
		}
	}
	public synchronized void setActivityEarliestStartDate(Activity activity, GregorianCalendar earliestStartDate)
	{
		activity.setEarliestStartDate(earliestStartDate);
		activityChanged(activity);
	}
	public synchronized void setActivityLatestEndDate(Activity activity, GregorianCalendar latestEndDate)
	{
		activity.setLatestEndDate(latestEndDate);
		activityChanged(activity);
	}
	/*
	 * Not necessary since customer can be changed from the activity but the method is 
	 * included for convenience and order
	 */
	public synchronized void setActivityCustomer(Activity activity,String customer)
	{
		activity.setCustomer(customer);
		activityChanged(activity);
	}
	public synchronized void setActivityStartDate(Activity activity, GregorianCalendar startDate)
	{
		activity.setStartDate(startDate);
		activityChanged(activity);
	}
        public synchronized void setActivityProductionLine(Activity activity, ActivityHolder productionLine)
	{
		activity.setProductionLine(productionLine);
		activityChanged(activity);
	}
	/*
	 * Takes an activity away from its production line
	 */
	public synchronized void unschedule(Activity activity)
	{
		activity.getProductionLine().activityRemovalProcedure(activity);
		notifyObservers();
	}
	public synchronized ActivityHolder addProductionLine(String name)
	{
		ActivityHolder productionLine = new ActivityHolder(this,name);
		productionLines.add(productionLine);
		notifyObservers();
		return productionLine;
	}
	public synchronized void removeProductionLine(ActivityHolder productionLine)
	{
		Iterator<Activity> it = productionLine.getActivityArrayList().iterator();
		while(it.hasNext())
		{
			productionLine.activityRemovalProcedure(it.next());
		}
		productionLines.remove(productionLine);
		notifyObservers();
		
	}
	public synchronized Activity addActivity(String customer,int dateSpan,GregorianCalendar earliestStartDate,GregorianCalendar latestEndDate)
	{
		if(dateSpan>0)
		{
			Activity a = new Activity(customer,dateSpan,earliestStartDate,latestEndDate,unscheduledActivities);
			unscheduledActivities.addActivity(a);
			allActivities.addActivity(a);
			notifyObservers();
			return a;
		}
		else
		{
			System.out.println("ERROR - TRIED TO CREATE AN ACTIVITY WITH A DATESPAN OF ZERO");
		}
		return null;
	}
	public synchronized void removeActivity(Activity activity)
	{
		unschedule(activity);
		activity.getProductionLine().removeActivity(activity);
		allActivities.removeActivity(activity);
		notifyObservers();
	}
        
        public Activity searchActivity(String name){
        // Post: search an activity by name

            Activity[] activities = allActivities.getActivities();
            for (int i=0; i < activities.length; i++){

                if (activities[i].getCustomer().equals(name)){

                    return activities[i];
                }
            }

            return null;

        }
        
        public ActivityHolder searchProductionLine(String name){
        // Post: search a productionLine by name

            for (int i=0; i < productionLines.size(); i++){

                if (productionLines.get(i).getName().equals(name)){

                    return productionLines.get(i);
                }
            }

            return null;

        }
        
	public synchronized void printAllActivities()
	{
		System.out.println("---------------PRINTING ACTIVITIES----------");
		Iterator<ActivityHolder> it = productionLines.iterator();
		while(it.hasNext())
		{
			ActivityHolder productionLine = it.next();
			System.out.println("\n");
			productionLine.printAllActivities();
		}
		System.out.println("\n");
		unscheduledActivities.printAllActivities();
		System.out.println("\n");
		allActivities.printAllActivities();
	}
	public synchronized void notifyObservers()
	{
		setChanged();
		super.notifyObservers();
	}
	
	
	//Including an algoritm for calculating the difference between two dates really fast
	//http://tripoverit.blogspot.com/2007/07/java-calculate-difference-between-two.html
	//Doing it by extracting milliseconds is not OK since it opens up for all kinds of wierd
	//errors when milliseconds are added because of calendar imperfections.
	//That could probably be fixed by rounding off to the closest integer but
	//this method ensures that we are not dependent on milliseconds and thus have 
	//a far wider timeRange to move on.
	public static long daysBetween(Calendar startDate, Calendar endDate) 
	{
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.get(Calendar.YEAR)<endDate.get(Calendar.YEAR)) 
		{
			daysBetween+=date.getActualMaximum(Calendar.DAY_OF_YEAR);
			date.add(Calendar.YEAR, 1);
		}
		daysBetween+=endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR);

		return daysBetween;
	}
	
}
