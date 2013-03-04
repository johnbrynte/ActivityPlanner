package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import types.Task;

public class PlanningChart extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private PlanningTimeLine timeLine;
	private ChartView canvas;
	private PlanningPark planningPark;
	private JLayeredPane layeredPane;
	private JScrollPane canvasScrollPane;
	private JScrollPane parkScrollPane;

	public PlanningChart(PlanningChartModel model) {
		model.addObserver(this);
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 400));

		timeLine = new PlanningTimeLine(model);
		add(timeLine, BorderLayout.NORTH);
		
		canvas = new ChartView(model);
		add(canvas, BorderLayout.CENTER);
		
		JButton task = new JButton("Task 1");
		task.setSize(new Dimension(PlanningChartModel.cellWidth * 3, PlanningChartModel.cellHeight));
		layeredPane.add(task, new Integer(2));
		
		task = new JButton("Task 2");
		task.setLocation(PlanningChartModel.cellWidth * 3, 0);
		task.setSize(new Dimension(PlanningChartModel.cellWidth * 4, PlanningChartModel.cellHeight));
		layeredPane.add(task, new Integer(2));
		
		task = new JButton("Task 3");
		task.setLocation(PlanningChartModel.cellWidth * 2, PlanningChartModel.cellHeight);
		task.setSize(new Dimension(PlanningChartModel.cellWidth * 6, PlanningChartModel.cellHeight));
		layeredPane.add(task, new Integer(2));
		
		planningPark = new PlanningPark(model);
		parkScrollPane = new JScrollPane(planningPark);
		parkScrollPane.setBorder(null);
		//parkScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		Dimension scrollBarSize = new Dimension(0, PlanningChartModel.cellHeight + 20);
		parkScrollPane.setPreferredSize(scrollBarSize);
		parkScrollPane.setMinimumSize(scrollBarSize);
		add(parkScrollPane, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable model, Object data) {
		layeredPane.setPreferredSize(PlanningChartModel.canvasSize);
	}
	
}
