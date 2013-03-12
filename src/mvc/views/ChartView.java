package mvc.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
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
	
    //private Integer CANVAS_LAYER     = new Integer(0);
    private Integer TASK_LAYER       = new Integer(10);
    private Integer DND_EFFECT_LAYER = new Integer(15);
    private Integer DATE_LIMIT_LAYER = new Integer(20);

    private PlanningView view;
    private JLayeredPane layeredPane;
    private ChartCanvas chartCanvas;
    private JScrollPane scrollPane;
    private ChartLimiter earliestLimit;
    private ChartLimiter latestLimit;

    public ChartController chartController;

    private Model model;
    private Activity dragTask = null;

    public ChartView(Model model, PlanningView view) {
            this.view  = view;
            this.model = model;

            chartCanvas = new ChartCanvas(view);

            layeredPane = new JLayeredPane();

            scrollPane = new JScrollPane(layeredPane);
            scrollPane.setBorder(null);

            layeredPane.add(chartCanvas, JLayeredPane.FRAME_CONTENT_LAYER);

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
            Component[] placedTasks = layeredPane.getComponentsInLayer(2);
            for(Component c : placedTasks)
                    layeredPane.remove(c);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Remove all tasks from the pane
        Component[] tasks = layeredPane.getComponentsInLayer(TASK_LAYER);
        for(Component c : tasks)
                layeredPane.remove(c);
        
        // removing the dnd effect auxiliar task
        tasks = layeredPane.getComponentsInLayer(DND_EFFECT_LAYER);
        for(Component c : tasks)
                layeredPane.remove(c);
        
        ActivityHolder[] productionLines = model.getProductionLines();
        Activity[] activities;
        Dimension size;
        Task task;
        int dragProdLine = 0;
		
	// Add the tasks from the model
        for(int i = 0; i < productionLines.length; i ++) {
            activities = productionLines[i].getActivities();
            
            // getting in which production line must the dnd effect auxiliar task must go
            if (dragTask != null && productionLines[i].getName().equals(dragTask.getProductionLine().getName())) {
                dragProdLine = i;
            }
            
            for(Activity a : activities) {
                task = new Task(null);
                task.setBounds(view.getPositionFromDate(a.getStartDate()), view.cellHeight * i, view.cellWidth * a.getDateSpan(), view.cellHeight);
                task.setActivity(a);
                task.addMouseListener(chartController);
                task.addMouseMotionListener(chartController);
                task.setVisible(true);

                layeredPane.add(task, TASK_LAYER);

                earliestLimit.setLocation(
                        view.getPositionFromDate(a.getEarliestStartDate()), 0);
                latestLimit.setLocation(
                        view.getPositionFromDate(a.getLatestEndDate()), 0);
            }
        }
        
        if (dragTask != null) {
            task = new Task(null);
            task.setBounds(view.getPositionFromDate(dragTask.getStartDate()), view.cellHeight * dragProdLine, view.cellWidth * dragTask.getDateSpan(), view.cellHeight);
            task.setContentTransparent(true);
            task.setVisible(true);
            layeredPane.add(task, DND_EFFECT_LAYER);
        }
        
        layeredPane.repaint();
        chartCanvas.repaint();
    }

    public void updateDragTask(Activity dragActivity)
    {
        dragTask = dragActivity;
        this.update(null, null);
    }

}
