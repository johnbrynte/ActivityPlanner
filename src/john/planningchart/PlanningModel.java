package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JFrame;

import types.Task;
/**
 * The model of the planning area.
 * @author John
 */
public class PlanningModel extends Observable {
	
	private final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	public GregorianCalendar startDate;
	public GregorianCalendar endDate;
	public int daysBetween;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Activity chart");
		PlanningModel model = new PlanningModel();
		PlanningView planningChart = new PlanningView(model);
		
		model.setDateLimits(
				new GregorianCalendar(2012, 11, 20),
				new GregorianCalendar(2013, 2, 10));
		
		window.add(planningChart.getComponent(), BorderLayout.CENTER);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public Point getChartPositionFromDate(int x, int y) {
		return new Point();
	}
	
	public void setDateLimits(GregorianCalendar cal1, GregorianCalendar cal2) {
		startDate = cal1;
		endDate = cal2;
		daysBetween = (int) (
				(endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS + 1);

		setChanged();
		notifyObservers();
	}
	
	public Point getAlignedCoordinates(Task task) {
		/*
		return new Point(
				(int) ((task.getLocation().x - getLocation().x +
				canvasScrollPane.getViewport().getViewPosition().x) / cellWidth),
				
				(int) ((task.getLocation().y - getLocation().y +
				canvasScrollPane.getViewport().getViewPosition().y) / cellHeight));*/
		return new Point();
	}
	
}
