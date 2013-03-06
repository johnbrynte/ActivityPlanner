package john.planningchart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * The Planning View consists of three sub views:
 * Time Line View, Chart View and Park View;
 * each of which is created by the Planning View.
 *  
 * @author John
 */
public class PlanningView implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel planningPanel;
	
	public TimeLineView timeLineView;
	public ChartView chartView;
	public ParkView parkView;
	
	public Dimension canvasSize = new Dimension();
	public int cellWidth = 50;
	public int cellHeight = 30;
	public int rows = 5;

	public PlanningView(PlanningModel model) {
		model.addObserver(this);
		
		planningPanel = new JPanel();
		
		planningPanel.setLayout(new BorderLayout());
		planningPanel.setPreferredSize(new Dimension(600, 400));

		timeLineView = new TimeLineView(this);
		planningPanel.add(timeLineView.getComponent(), BorderLayout.NORTH);
		
		chartView = new ChartView(this);
		planningPanel.add(chartView.getComponent(), BorderLayout.CENTER);
		
		parkView = new ParkView(model, this);
		planningPanel.add(parkView.getComponent(), BorderLayout.SOUTH);
	}

	/**
	 * Updates the canvas size and notifies its child views.
	 */
	@Override
	public void update(Observable observable, Object data) {
		PlanningModel model = (PlanningModel) observable;
		
		canvasSize = new Dimension(
				cellWidth * model.daysBetween,
				cellHeight * rows);
		
		timeLineView.updateView(model);
		chartView.updateView(model);
	}
	
	/**
	 * Returns this component.
	 * @return this component.
	 */
	public JPanel getComponent() {
		return planningPanel;
	}
	
}
