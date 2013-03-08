package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JFrame;

/**
 * The model of the planning area.
 * @author John
 */
public class PlanningModel extends Observable {
	
	public GregorianCalendar startDate;
	public GregorianCalendar endDate;
	public int daysBetween;
	
	public ArrayList<Task> parkTasks;
	public ArrayList<Task> chartTasks;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Activity chart");
		PlanningView view = new PlanningView();
		
		view.setDateLimits(
				new GregorianCalendar(2012, 11, 25),
				new GregorianCalendar(2013, 0, 12));
		
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

	/**
	 * Places the given task on the given date in the Chart View. 
	 * @param task the Task object to be placed.
	 * @param date the date on which the task should be placed.
	 */
	public void placeTaskOnChart(Component task, GregorianCalendar date) {
		// TODO
	}
	
}
