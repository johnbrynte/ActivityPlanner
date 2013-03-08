package john.planningchart;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 * The Chart View is a scrollable area on which there
 * can be placed instances of the Task object.
 * @author John
 */
public class ChartView {

	private static final long serialVersionUID = 1L;
	
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

	public ChartView(PlanningView view) {
		this.view = view;
		
		chartController = new ChartController(view);
		
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
	 */
	public void updateView() {
		layeredPane.setPreferredSize(view.canvasSize);
		chartCanvas.updateView();
		earliestLimit.updateView();
		latestLimit.updateView();
		
		// Remove all tasks from the chart
		Component[] placedTasks = layeredPane.getComponentsInLayer(TASK_LAYER);
		for(Component c : placedTasks)
			layeredPane.remove(c);
		
		/* TODO
		Activity a;
		int i = 0;
		
		// Add the tasks from the model
		for(Task t : model.chartTasks) {
			a = t.getActivity();
			
			t.setSize(new Dimension(
					view.cellWidth * a.getDateSpan(),
					view.cellHeight));
			t.setLocation(
					view.getPositionFromDate(
							a.getEarliestStartDate()), view.cellHeight * (i++));
			
			layeredPane.add(t, TASK_LAYER);
			
			earliestLimit.setLocation(
				view.getPositionFromDate(a.getEarliestStartDate()), 0);
			latestLimit.setLocation(
				view.getPositionFromDate(a.getLatestEndDate(), 0);
		}
		*/
	}
	
}
