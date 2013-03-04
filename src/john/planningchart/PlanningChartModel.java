package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JFrame;

import types.Task;

public class PlanningChartModel extends Observable {

	public static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	public static Dimension canvasSize = new Dimension();
	public static int cellWidth = 50;
	public static int cellHeight = 30;
	public static int rows = 5;

	public static GregorianCalendar startDate;
	public static GregorianCalendar endDate;
	public static int daysBetween;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Activity chart");
		PlanningChartModel model = new PlanningChartModel();
		PlanningChart planningChart = new PlanningChart(model);
		
		model.setDateLimits(
				new GregorianCalendar(2012, 11, 20),
				new GregorianCalendar(2013, 2, 10));
		
		window.add(planningChart, BorderLayout.CENTER);
		
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
		canvasSize.width = cellWidth * daysBetween;
		canvasSize.height = cellHeight * rows;

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
