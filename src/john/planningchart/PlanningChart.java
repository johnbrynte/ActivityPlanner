package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PlanningChart {
	
	public static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private PlanningCanvas canvas;
	private PlanningTimeLine timeLine;
	private JScrollPane scrollPane;
	
	public static Dimension canvasSize = new Dimension();
	public static int cellWidth = 50;
	public static int cellHeight = 30;
	public static int rows = 5;

	public static GregorianCalendar startDate;
	public static GregorianCalendar endDate;
	public static int daysBetween;
	
	public static void main(String[] args) {
		new PlanningChart();
	}

	public PlanningChart() {
		JFrame window = new JFrame("Activity chart");
		Container content = window.getContentPane();

		timeLine = new PlanningTimeLine();
		content.add(timeLine, BorderLayout.NORTH);
		
		canvas = new PlanningCanvas();
		
		scrollPane = new JScrollPane(canvas);
		scrollPane.getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				timeLine.notifyScrollChange(scrollPane.getViewport().getViewPosition().x);
			}

		});
		content.add(scrollPane, BorderLayout.CENTER);
		
		setDateLimits(
				new GregorianCalendar(2012, 11, 20),
				new GregorianCalendar(2013, 2, 10));
		
		System.out.println(daysBetween);

		window.setSize(new Dimension(600, 400));
		//window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public void setDateLimits(GregorianCalendar cal1, GregorianCalendar cal2) {
		startDate = cal1;
		endDate = cal2;
		daysBetween = (int) (
				(endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS + 1);
		canvasSize.width = cellWidth * daysBetween;
		canvasSize.height = cellHeight * rows;

		canvas.notifySizeChange();
		timeLine.notifySizeChange();
	}
	
}
