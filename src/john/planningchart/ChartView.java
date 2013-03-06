package john.planningchart;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 * The Chart View is a scrollable area on which there
 * can be placed instances of the Task object.
 * @author John
 */
public class ChartView {

	private static final long serialVersionUID = 1L;
	
	private PlanningView view;
	private JLayeredPane layeredPane;
	private ChartController chartController;
	private ChartCanvas chartCanvas;

	public TimeLineView timeLine;
	public JScrollPane scrollPane;

	public ChartView(PlanningView view, TimeLineView timeLine) {
		this.view = view;
		this.timeLine = timeLine;
		
		chartController = new ChartController(this);
		
		chartCanvas = new ChartCanvas(view);
		
		layeredPane = new JLayeredPane();
		layeredPane.add(chartCanvas, new Integer(1));
		
		scrollPane = new JScrollPane(layeredPane);
		scrollPane.setBorder(null);
		scrollPane.getViewport().addChangeListener(chartController);
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
		layeredPane.setPreferredSize(view.canvasSize);
		chartCanvas.updateView(model);
	}
}
