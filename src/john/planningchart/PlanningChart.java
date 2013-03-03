package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import types.Task;

public class PlanningChart extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static final int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private PlanningCanvas canvas;
	private PlanningTimeLine timeLine;
	private JLayeredPane layeredPane;
	private JScrollPane scrollPane;
	
	public static Dimension canvasSize = new Dimension();
	public static int cellWidth = 50;
	public static int cellHeight = 30;
	public static int rows = 5;

	public static GregorianCalendar startDate;
	public static GregorianCalendar endDate;
	public static int daysBetween;
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Activity chart");
		PlanningChart planningChart = new PlanningChart();
		
		window.add(planningChart, BorderLayout.CENTER);
		
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public PlanningChart() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 400));

		timeLine = new PlanningTimeLine();
		add(timeLine, BorderLayout.NORTH);
		
		canvas = new PlanningCanvas();
		layeredPane = new JLayeredPane();
		layeredPane.add(canvas, new Integer(1));
		
		scrollPane = new JScrollPane(layeredPane);
		scrollPane.setBorder(null);
		scrollPane.getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				timeLine.notifyScrollChange(scrollPane.getViewport().getViewPosition().x);
			}

		});
		add(scrollPane, BorderLayout.CENTER);
		
		JButton task = new JButton("Task 1");
		task.setSize(new Dimension(cellWidth * 3, cellHeight));
		layeredPane.add(task, new Integer(2));
		
		task = new JButton("Task 2");
		task.setLocation(cellWidth * 3, 0);
		task.setSize(new Dimension(cellWidth * 4, cellHeight));
		layeredPane.add(task, new Integer(2));
		
		task = new JButton("Task 3");
		task.setLocation(cellWidth * 2, cellHeight);
		task.setSize(new Dimension(cellWidth * 6, cellHeight));
		layeredPane.add(task, new Integer(2));
		
		setDateLimits(
				new GregorianCalendar(2012, 11, 20),
				new GregorianCalendar(2013, 2, 10));
	}
	
	public void setDateLimits(GregorianCalendar cal1, GregorianCalendar cal2) {
		startDate = cal1;
		endDate = cal2;
		daysBetween = (int) (
				(endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS + 1);
		canvasSize.width = cellWidth * daysBetween;
		canvasSize.height = cellHeight * rows;

		layeredPane.setPreferredSize(canvasSize);
		canvas.notifySizeChange();
		timeLine.notifySizeChange();
	}
	
	public Point getAlignedCoordinates(Task task) {
		return new Point(
				(int) ((task.getLocation().x - getLocation().x +
				scrollPane.getViewport().getViewPosition().x) / cellWidth),
				
				(int) ((task.getLocation().y - getLocation().y +
				scrollPane.getViewport().getViewPosition().y) / cellHeight));
	}
	
}
