package mvc.controllers;

import mvc.views.PlanningView;
import mvc.views.ChartView;
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
	private ChartView cv;
        
	private int horizontalScroll;
	
	public ChartController(ChartView cv, PlanningView view) {
		this.view = view;
		this.cv   = cv;
                cv.getComponent().getViewport().addChangeListener(this);
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
	
	/**
	 * Calculates the date on which the drop event was fired
	 * and asks the model to place the component (Task) on the Chart View.
	 * @param event the mouse event that caused the drop event.
	 */
	public void dropEvent(MouseEvent event) {
		// using x position relative to the Chart View
		int x = horizontalScroll + event.getX();
		
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(
				view.startDate.getTimeInMillis() + view.DAY_IN_MILLIS * x / view.cellWidth);
		
		// TODO
		//model.placeTaskOnChart(event.getComponent(), date);
	}

}
