package john.planningchart;

import java.awt.event.MouseEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChartController implements ChangeListener {

	private ChartView view;
	
	public ChartController(ChartView view) {
		this.view = view;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		view.timeLine.notifyScrollChange(view.canvasScrollPane.getViewport().getViewPosition().x);
	}
	
	public void dropEvent(MouseEvent event) {
		
	}

}
