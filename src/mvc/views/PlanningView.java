package mvc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;

import javax.swing.JPanel;

/**
 * The Planning View consists of three sub views:
 * Time Line View, Chart View and Park View;
 * each of which is created by the Planning View.
 *  
 * @author John
 */
public class PlanningView {
	
	public final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private JPanel planningPanel;
	
	public TimeLineView timeLineView;
        public ProductionLineView productionLineView;
	public ChartView chartView;
	public ParkView parkView;
	
	public Dimension canvasSize = new Dimension();
	public int cellWidth = 50;
	public int cellHeight = 30;
	public int rows = 5;
	
	public GregorianCalendar startDate;
	public GregorianCalendar endDate;
	public int daysBetween;

	public PlanningView() {
		planningPanel = new JPanel();
		
		planningPanel.setLayout(new BorderLayout());
                
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BorderLayout());
                rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		timeLineView = new TimeLineView(this);
		rightPanel.add(timeLineView.getComponent(), BorderLayout.NORTH);
                
		chartView = new ChartView(this);
		rightPanel.add(chartView.getComponent(), BorderLayout.CENTER);
		
		parkView = new ParkView(this);
		rightPanel.add(parkView.getComponent(), BorderLayout.SOUTH);
                
                planningPanel.add(rightPanel, BorderLayout.CENTER);
                
                productionLineView = new ProductionLineView(this);
                productionLineView.getComponent().setBorder(
                        BorderFactory.createLineBorder(Color.black));
                planningPanel.add(productionLineView.getComponent(), BorderLayout.LINE_START);
	}

	/**
	 * Sets the start (left) and end (right) date limits in the Planning View.
	 * @param start the leftmost date in the Planning View.
	 * @param end the rightmost date in the Planning View.
	 */
	public void setDateLimits(GregorianCalendar start, GregorianCalendar end) {
		startDate = (GregorianCalendar) start.clone();
		endDate = (GregorianCalendar) end.clone();
		daysBetween = (int) (
				(endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS);

		canvasSize = new Dimension(cellWidth * (daysBetween + 1), cellHeight * rows);
		
		timeLineView.updateView();
		chartView.updateView();
	}
	
	/**
	 * Translates a calendar date to a position in the Chart View.
	 * @param date the calendar date to translate. 
	 * @return an int with the translated x position.
	 */
	public int getPositionFromDate(GregorianCalendar date) {
		return (int) (cellWidth
				* ((date.getTimeInMillis() - startDate.getTimeInMillis())
						/ DAY_IN_MILLIS));
	}
	
	/**
	 * Translates a position in the Chart View to a calendar date.
	 * @param date the x position to translate.
	 * @return a GregorianCalendar with the translated date.
	 */
	public GregorianCalendar getDateFromPosition(int x) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(startDate.getTimeInMillis() + DAY_IN_MILLIS * x / cellWidth);
		return date;
	}
	
	/**
	 * Returns this component.
	 * @return this component.
	 */
	public JPanel getComponent() {
		return planningPanel;
	}
	
}
