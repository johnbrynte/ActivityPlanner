package mvc.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import mvc.controllers.ChartController;
import mvc.model.Activity;
import mvc.model.ActivityHolder;
import mvc.model.Model;

/**
 * The Chart View is a scrollable area on which there
 * can be placed instances of the Task object.
 * @author John
 */
public class ChartView implements Observer {
	
	private final Integer CANVAS_LAYER = 1;
	private final Integer TASK_LAYER = 2;
	private final Integer DATE_LIMIT_LAYER = 3;
	
	private PlanningView view;
	private JLayeredPane layeredPane;
	private ChartCanvas chartCanvas;
	private JScrollPane scrollPane;
	private ChartLimiter earliestLimit;
	private ChartLimiter latestLimit;
        
        public ChartController chartController;

	public ChartView(Model model, PlanningView view) {
		this.view = view;
			
		chartCanvas = new ChartCanvas(view);
		
		layeredPane = new JLayeredPane();
		
		scrollPane = new JScrollPane(layeredPane);
		scrollPane.setBorder(null);
		
		layeredPane.add(chartCanvas, CANVAS_LAYER);
		
		earliestLimit = new ChartLimiter(view, new Color(0,0,255));
		latestLimit = new ChartLimiter(view, new Color(255,0,0));
		
		layeredPane.add(earliestLimit, DATE_LIMIT_LAYER);
		layeredPane.add(latestLimit, DATE_LIMIT_LAYER);
                
                model.addObserver(this);
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
	 */
	public void updateView() {
		layeredPane.setPreferredSize(view.canvasSize);
		chartCanvas.updateView();
		earliestLimit.updateView();
		latestLimit.updateView();
		
		// Remove all tasks from the chart
		Component[] placedTasks = layeredPane.getComponentsInLayer(TASK_LAYER);
		for(Component c : placedTasks)
			layeredPane.remove(c);
	}

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        
        // Remove all tasks from the pane
		Component[] tasks = layeredPane.getComponentsInLayer(TASK_LAYER);
		for(Component c : tasks)
			layeredPane.remove(c);
        
        ActivityHolder[] productionLines = model.getProductionLines();
        Activity[] activities;
        Dimension size;
        Task task;
		
		// Add the tasks from the model
        for(int i = 0; i < productionLines.length; i ++) {
            activities = productionLines[i].getActivities();
            
            for(Activity a : activities) {
                task = new Task(null);
                //size = new Dimension(view.cellWidth * a.getDateSpan(), view.cellHeight);
                
                /*task.setSize(size);
                task.setPreferredSize(size);
                task.setLocation(
                        view.getPositionFromDate(a.getStartDate()),
                        view.cellHeight * i);*/
                task.setBounds(view.getPositionFromDate(a.getStartDate()), view.cellHeight * i, view.cellWidth * a.getDateSpan(), view.cellHeight);
                task.setActivity(a);
                task.addMouseListener(chartController);
                task.addMouseMotionListener(chartController);
                task.setVisible(true);
                
                layeredPane.add(task, new Integer(2));

                earliestLimit.setLocation(
                        view.getPositionFromDate(a.getEarliestStartDate()), 0);
                latestLimit.setLocation(
                        view.getPositionFromDate(a.getLatestEndDate()), 0);
            }
            
            scrollPane.repaint();
            layeredPane.repaint();
        }
    }
	
}
