package mvc.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import mvc.model.Model;
import selectedTaskModels.SelectedTaskModel;

/**
 * The Planning View consists of three sub views:
 * Time Line View, Chart View and Park View;
 * each of which is created by the Planning View.
 *  
 * @author John
 */
public class PlanningView implements Observer {
	
	public final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private JPanel planningPanel;
	
    public ProductionLineControlView productionLineControlView;
	public TimeLineView timeLineView;
	public ChartView chartView;
	public ParkView parkView;
	
	public Dimension canvasSize = new Dimension();
	public static int cellWidth = 50;
	public static int cellHeight = 30;
    public int rows = 0;
	
	public GregorianCalendar startDate;
	public GregorianCalendar endDate;
	public int daysBetween;

	public PlanningView(Model model, SelectedTaskModel selectedTaskModel) {
        model.addObserver(this);
        selectedTaskModel.addObserver(this);
        
		planningPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        planningPanel.add(topPanel, BorderLayout.NORTH);
        
		timeLineView = new TimeLineView(this);
		topPanel.add(timeLineView.getComponent(), BorderLayout.CENTER);
        
        productionLineControlView = new ProductionLineControlView(this);
        topPanel.add(productionLineControlView.getComponent(), BorderLayout.WEST);
                
		chartView = new ChartView(model, selectedTaskModel, this);
		planningPanel.add(chartView.getComponent(), BorderLayout.CENTER);
		
		parkView = new ParkView(model, selectedTaskModel, this);
		planningPanel.add(parkView.getComponent(), BorderLayout.SOUTH);
	}

	/**
	 * Sets the start (left) and end (right) date limits in the Planning View.
	 * @param start the leftmost date in the Planning View.
	 * @param end the rightmost date in the Planning View.
	 */
	public void setDateLimits(GregorianCalendar start, GregorianCalendar end) {
        startDate = new GregorianCalendar();
        endDate = new GregorianCalendar();
		startDate.set(
                start.get(GregorianCalendar.YEAR),
                start.get(GregorianCalendar.MONTH),
                start.get(GregorianCalendar.DATE), 0, 0, 0);
        startDate.set(GregorianCalendar.MILLISECOND, 0);
		endDate.set(
                end.get(GregorianCalendar.YEAR),
                end.get(GregorianCalendar.MONTH),
                end.get(GregorianCalendar.DATE), 0, 0, 0);
        endDate.set(GregorianCalendar.MILLISECOND, 0);
		daysBetween = getDaysBetween(startDate, endDate);
        
		canvasSize.width = cellWidth * (daysBetween + 1);
		
        timeLineView.updateView();
        chartView.updateView();
	}
	
	/**
	 * Translates a calendar date to a position in the Chart View.
	 * @param date the calendar date to translate. 
	 * @return an int with the translated x position.
	 */
	public int getPositionFromDate(GregorianCalendar date) {
        int deltaDays = getDaysBetween(startDate, date);
		return (int) (ChartView.LEFT_OFFSET + cellWidth * deltaDays);
	}
    
    /**
     * Rounds off the given time in milliseconds to the same day at 00:00 am.
     * @param time The time in milliseconds to be rounded off.
     * @return The rounded off time in milliseconds.
     */
    private int getDaysBetween(GregorianCalendar start, GregorianCalendar end) {
        long startDays = (long) Math.floor(start.getTimeInMillis() / DAY_IN_MILLIS);
        long endDays = (long) Math.floor(end.getTimeInMillis() / DAY_IN_MILLIS);
        return (int) (endDays - startDays);
    }
	
	/**
	 * Translates a position in the Chart View to a calendar date.
	 * @param date the x position to translate.
	 * @return a GregorianCalendar with the translated date.
	 */
	public GregorianCalendar getDateFromPosition(int x) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(startDate.getTimeInMillis() +
                DAY_IN_MILLIS * (x / cellWidth));
		return date;
	}
	
	/**
	 * Returns this component.
	 * @return this component.
	 */
	public JPanel getComponent() {
		return planningPanel;
	}

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Model){
            Model model = (Model) o;

            rows = model.getProductionLines().length;
            canvasSize.height = cellHeight * rows;
        }
        timeLineView.updateView();
        chartView.updateView();
    }
	
}
