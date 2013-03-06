package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JFrame;

import se.kth.csc.iprog.activityplanner.model.Activity;

/**
 * The model of the planning area.
 * @author John
 */
public class PlanningModel extends Observable {
	
	private final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	public GregorianCalendar startDate;
	public GregorianCalendar endDate;
	public int daysBetween;
	
	public ArrayList<Task> parkTasks;
	public ArrayList<Task> chartTasks;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Activity chart");
		PlanningModel model = new PlanningModel();
		PlanningView view = new PlanningView(model);
		
		model.setDateLimits(
				new GregorianCalendar(2012, 11, 25),
				new GregorianCalendar(2013, 0, 12));
		
		Task task = new Task(view);
		task.setActivity(new Activity(
				"My test task #2", 3,
				new GregorianCalendar(2013,0,1),
				new GregorianCalendar(2013,0,6),
				null));
		model.chartTasks.add(task);
		
		task = new Task(view);
		task.setActivity(new Activity(
				"My test task #1", 10,
				new GregorianCalendar(2013,0,1),
				new GregorianCalendar(2013,0,6),
				null));
		model.chartTasks.add(task);
		
		model.setChanged();
		model.notifyObservers();
		
		// Move the task three days to the left...
		task.setLocation(
				task.getLocation().x - view.cellWidth * 3,
				task.getLocation().y);
		
		window.add(view.getComponent(), BorderLayout.CENTER);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public PlanningModel() {
		parkTasks = new ArrayList<Task>();
		chartTasks = new ArrayList<Task>();
	}
	
	public Point getChartPositionFromDate(int x, int y) {
		return new Point();
	}
	
	public void setDateLimits(GregorianCalendar cal1, GregorianCalendar cal2) {
		startDate = (GregorianCalendar) cal1.clone();
		endDate = (GregorianCalendar) cal2.clone();
		daysBetween = (int) (
				(endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS + 1);

		setChanged();
		notifyObservers();
	}

	/**
	 * Places the given task on the given date in the Chart View. 
	 * @param task
	 * @param date
	 */
	public void placeTaskOnChart(Component task, GregorianCalendar date) {
		// TODO
	}
	
}
