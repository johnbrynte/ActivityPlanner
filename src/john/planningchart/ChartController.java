package john.planningchart;

import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

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
	private PlanningModel model;
	
	private int horizontalScroll;
	
	public ChartController(PlanningView view, PlanningModel model) {
		this.view = view;
		this.model = model;
		
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
		/*Point p = new Point(
				(int) ((task.getLocation().x - getLocation().x +
				canvasScrollPane.getViewport().getViewPosition().x) / cellWidth),
				
				(int) ((task.getLocation().y - getLocation().y +
				canvasScrollPane.getViewport().getViewPosition().y) / cellHeight));*/
		GregorianCalendar date = new GregorianCalendar();
		
		model.placeTaskOnChart(event.getComponent(), date);
	}

}
