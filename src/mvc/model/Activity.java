package mvc.model;
import java.util.*;
import java.text.*;

public class Activity {

	private String customer;
	private int dateSpan;
	private GregorianCalendar startDate;
	private GregorianCalendar earliestStartDate;
	private GregorianCalendar latestEndDate;
	private ActivityHolder productionLine;
	
	public Activity(String customer,int dateSpan,GregorianCalendar earliestStartDate,GregorianCalendar latestEndDate,ActivityHolder productionLine)
	{
		this.customer=customer;
		this.dateSpan=dateSpan;
		if(earliestStartDate==null)this.earliestStartDate=null;
		else this.earliestStartDate=(GregorianCalendar)earliestStartDate.clone();
		if(latestEndDate==null)this.latestEndDate=null;
		else this.latestEndDate=(GregorianCalendar)latestEndDate.clone();
		this.productionLine=productionLine;
		this.startDate = new GregorianCalendar();
	}
	/*
	 * Gets the activity's customer
	 */
	public String getCustomer()
	{
		return customer;
	}
	/*
	 * Sets the activity's customer
	 * Can be called from outside the model since it cannot cause problems with other activities
	 */
	public void setCustomer(String customer)
	{
		this.customer=customer;
	}
	/*
	 * Gets the activity's date span
	 */
	public int getDateSpan()
	{
		return dateSpan;
	}
	
	/*
	 * The method is only called from within the model to ensure that constrictions regarding
	 * non-overlapping activities are honoured
	 */
	void setDateSpan(int dateSpan)
	{
		this.dateSpan=dateSpan;
	}
	/*
	 * Gets the activity's start date
	 */
	public GregorianCalendar getStartDate()
	{
		return (GregorianCalendar)startDate.clone();
	}
	/*
	 * The method is only called from within the model to ensure that constrictions regarding
	 * non-overlapping activities are honored
	 */
	public void setStartDate(GregorianCalendar startDate)
	{
		this.startDate=(GregorianCalendar)startDate.clone();
	}
	/*
	 * Gets the activity's end date
	 */
	public GregorianCalendar getEndDate()
	{
		GregorianCalendar a = getStartDate();
		a.add(GregorianCalendar.DAY_OF_MONTH, dateSpan);
		return a;
	}
	/*
	 * The method returns the production line which the current activity is associated with
	 * @return the production line, if this activity does not have a production line then this acitivity will return models unscheduledActivities ActivityHodler
	 */
	public ActivityHolder getProductionLine()
	{
		return productionLine;
	}
	/*
	 * The method is only called from within the model to ensure that constrictions regarding
	 * non-overlapping activities are honoured
	 */
	public void setProductionLine(ActivityHolder productionLine)
	{
		this.productionLine=productionLine;
	}
	/*
	 * Gets the activity's earliest start date
	 * This value may be null
	 */
	public GregorianCalendar getEarliestStartDate()
	{
		if(earliestStartDate==null)return null;
		return (GregorianCalendar)earliestStartDate.clone();
	}
	/*
	 * Gets the activity's latest end date
	 * This may be null
	 */
	public GregorianCalendar getLatestEndDate()
	{
		if(latestEndDate==null)return null;
		return (GregorianCalendar)latestEndDate.clone();
	}
	/*
	 * Sets the activity's earliest start date
	 * This value may be null
	 */
	void setEarliestStartDate(GregorianCalendar earliestStartDate)
	{ 
		if(earliestStartDate!=null)this.earliestStartDate=(GregorianCalendar)earliestStartDate.clone();
		else this.earliestStartDate=null;
	}
	/*
	 * Sets the activity's latest end date
	 * This may be null
	 */
	void setLatestEndDate(GregorianCalendar latestEndDate)
	{ 
		if(latestEndDate!=null)this.latestEndDate=(GregorianCalendar)latestEndDate.clone();
		else this.latestEndDate=null;
	}
	
	/*
	 * Prints information about the activity to the command line
	 * Used primarily for debugging purposes
	 */
	public void printActivity()
	{
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
		String sd = sdf.format(startDate.getTime());
		String ed = sdf.format(getEndDate().getTime());
		String esd = earliestStartDate!=null?sdf.format(earliestStartDate.getTime()):"NULL";
		String led = latestEndDate!=null?sdf.format(latestEndDate.getTime()):"NULL";
		String s = productionLine.getName()+"\tC:"+customer+"\tSD:"+sd+"\tED:"+ed+"\tESD:"+esd+"\tLED:"+led;
		if(earliestStartDate!=null && startDate.before(earliestStartDate))s+="\tTOO EARLY";
		if(latestEndDate!=null && getEndDate().after(latestEndDate))s+="\tTOO LATE";
		System.out.println(s);
	}
	

}
