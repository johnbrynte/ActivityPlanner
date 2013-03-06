package john.planningchart;

import javax.swing.JPanel;

/**
 * The Time Line View displays a time line that corresponds
 * to the borders in the Chart View.
 * @author John
 */
public class TimeLineView {

	private TimeLinePanel timeLinePanel;
	
	public TimeLineView(PlanningView view) {
		timeLinePanel = new TimeLinePanel(view);
	}
	
	/**
	 * Returns this component.
	 * @return this component.
	 */
	public JPanel getComponent() {
		return (JPanel) timeLinePanel;
	}
	
	/**
	 * Updates this component.
	 * @param model the planning model.
	 */
	public void updateView(PlanningModel model) {
		timeLinePanel.updateView(model);
	}
	
	/**
	 * Notifies this component that the horizontal scroll
	 * of the Chart View has changed.
	 * @param scrollX the current horizontal scroll of the Chart View.
	 */
	public void notifyScrollChange(int scrollX) {
		timeLinePanel.setHorizontalScroll(scrollX);
	}
}
