package john.planningchart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.GregorianCalendar;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

import se.kth.csc.iprog.activityplanner.model.Activity;

/**
 * The Chart View is a scrollable area on which there
 * can be placed instances of the Task object.
 * @author John
 */
public class ChartView {

	private static final long serialVersionUID = 1L;
	
	private final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private final Integer CANVAS_LAYER = 1;
	private final Integer TASK_LAYER = 2;
	private final Integer DATE_LIMIT_LAYER = 3;
	
	private PlanningView view;
	private JLayeredPane layeredPane;
	private ChartController chartController;
	private ChartCanvas chartCanvas;
	private JScrollPane scrollPane;
	private ChartLimiter earliestLimit;
	private ChartLimiter latestLimit;
	
	// Local copy of the same field in the Planning Model
	private GregorianCalendar startDate;

	public ChartView(PlanningView view, PlanningModel model) {
		this.view = view;
		startDate = new GregorianCalendar();
		
		chartController = new ChartController(view, model);
		
		chartCanvas = new ChartCanvas(view);
		
		layeredPane = new JLayeredPane();
		
		scrollPane = new JScrollPane(layeredPane);
		scrollPane.setBorder(null);
		scrollPane.getViewport().addChangeListener(chartController);
		
		layeredPane.add(chartCanvas, CANVAS_LAYER);
		
		earliestLimit = new ChartLimiter(view, new Color(0,0,255));
		latestLimit = new ChartLimiter(view, new Color(255,0,0));
		
		layeredPane.add(earliestLimit, DATE_LIMIT_LAYER);
		layeredPane.add(latestLimit, DATE_LIMIT_LAYER);
	}
	
	/**
	 * Returns this component.
	 * @return this component.
	 */
	public JScrollPane getComponent() {
		return scrollPane;
	}
	
	/**
	 * Updates the size and demands a repaint of the chart canvas.
	 * @param model the planning model.
	 */
	public void updateView(PlanningModel model) {
		startDate = (GregorianCalendar) model.startDate.clone();
		
		layeredPane.setPreferredSize(view.canvasSize);
		chartCanvas.updateView(model);
		earliestLimit.updateView(model);
		latestLimit.updateView(model);
		
		// Remove all tasks from the chart
		Component[] placedTasks = layeredPane.getComponentsInLayer(TASK_LAYER);
		for(Component c : placedTasks)
			layeredPane.remove(c);
		
		Activity a;
		int i = 0; //TODO
		
		// Add the tasks from the model
		for(Task t : model.chartTasks) {
			a = t.getActivity();
			
			t.setSize(new Dimension(
					view.cellWidth * a.getDateSpan(),
					view.cellHeight));
			t.setLocation(
					getPositionFromDate(a.getEarliestStartDate()), view.cellHeight * (i++));
			
			layeredPane.add(t, TASK_LAYER);
			
			earliestLimit.setPositionFromDate(a.getEarliestStartDate());
			latestLimit.setPositionFromDate(a.getLatestEndDate());
		}
	}

	/**
	 * Translates a calendar date to a position in the Chart View.
	 * @param date the calendar date to translate. 
	 * @return an int with the translated x position.
	 */
	private int getPositionFromDate(GregorianCalendar date) {
		return (int) (view.cellWidth
				* ((date.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS));
	}
}
