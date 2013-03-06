package john.planningchart;

import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The Chart Controller listens to the scroll events of the
 * Chart View and notifies the Time Line View.
 * @author John
 */
public class ChartController implements ChangeListener {

	private PlanningView view;
	
	private int horizontalScroll;
	
	public ChartController(PlanningView view) {
		this.view = view;
		
		horizontalScroll = 0;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		JScrollPane chartView = view.chartView.getComponent();
		
		if(chartView.getViewport().getViewPosition().x != horizontalScroll) {
			horizontalScroll = chartView.getViewport().getViewPosition().x;
			
			view.timeLineView.notifyScrollChange(horizontalScroll);
		}
		
		view.parkView.updateSize();
	}
	
	public void dropEvent(MouseEvent event) {
		
	}

}
