package john.planningchart;

import java.awt.event.MouseEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The Chart Controller listens to the scroll events of the
 * Chart View and notifies the Time Line View.
 * @author John
 */
public class ChartController implements ChangeListener {

	private ChartView view;
	
	private int horizontalScroll;
	
	public ChartController(ChartView view) {
		this.view = view;
		
		horizontalScroll = 0;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if(view.scrollPane.getViewport().getViewPosition().x != horizontalScroll) {
			horizontalScroll = view.scrollPane.getViewport().getViewPosition().x;
			
			view.timeLine.notifyScrollChange(horizontalScroll);
		}
	}
	
	public void dropEvent(MouseEvent event) {
		
	}

}
