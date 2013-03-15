package mvc.views;

import java.awt.Color;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import mvc.controllers.ChartController;
import mvc.model.Activity;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import selectedTaskModels.SelectedTaskModel;

/**
 * The Chart View is a scrollable area on which there
 * can be placed instances of the Task object.
 * @author John
 */
public class ChartView implements Observer {
	
    public static final int LEFT_OFFSET = 170;
    
    private Integer TASK_LAYER            = new Integer(10);
    private Integer DND_EFFECT_LAYER      = new Integer(5);
    private Integer DATE_LIMIT_LAYER      = new Integer(20);
    private Integer PRODUCTION_LINE_LAYER = new Integer(30);

    private PlanningView view;
    private JLayeredPane layeredPane;
    private ChartCanvas chartCanvas;
    private JScrollPane scrollPane;
    private ChartLimiter earliestLimit;
    private ChartLimiter latestLimit;
    
    public ProductionLineView productionLineView;
    public ChartController chartController;

    private Model model;
    private SelectedTaskModel selectedTaskModel;
    private Activity dragTask = null;

    public ChartView(Model model, SelectedTaskModel selectedTaskModel, PlanningView view) {
            this.view  = view;
            this.model = model;
            this.selectedTaskModel = selectedTaskModel;
            
            layeredPane = new JLayeredPane();

            scrollPane = new JScrollPane(layeredPane);
            scrollPane.setBorder(null);

            chartCanvas = new ChartCanvas(model, view);
            layeredPane.add(chartCanvas, JLayeredPane.FRAME_CONTENT_LAYER);
            
            productionLineView = new ProductionLineView(model);
            layeredPane.add(productionLineView.getComponent(), PRODUCTION_LINE_LAYER);

            earliestLimit = new ChartLimiter(view, new Color(0,0,255));
            layeredPane.add(earliestLimit, DATE_LIMIT_LAYER);
            
            latestLimit = new ChartLimiter(view, new Color(255,0,0));
            layeredPane.add(latestLimit, DATE_LIMIT_LAYER);

            model.addObserver(this);
            selectedTaskModel.addObserver(this);
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
    public void update(Observable o, Object arg)
    {
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
        Task task;
        int dragProdLine = 0;

        // Add the tasks from the model
        for(int i = 0; i < productionLines.length; i ++) {
            activities = productionLines[i].getActivities();

            // getting in which production line must the dnd effect auxiliar task go
            if (dragTask != null && productionLines[i].equals(dragTask.getProductionLine())) {
                dragProdLine = i;
            }

            for(Activity a : activities) {
                task = new Task(true);
                task.setLocation(view.getPositionFromDate(a.getStartDate()),
                                PlanningView.cellHeight * i);
                task.setActivity(a);
                task.addMouseListener(chartController);
                task.addMouseMotionListener(chartController);
                
                if (a.equals(selectedTaskModel.selectedTask)) {
                    task.selected(true);
                    
                    earliestLimit.setLocation(
                        view.getPositionFromDate(a.getEarliestStartDate()), 0);

                    latestLimit.setLocation(
                        view.getPositionFromDate(a.getLatestEndDate()), 0);
                }
                
                task.setVisible(true);

                layeredPane.add(task, TASK_LAYER);
            }
        }

        if (dragTask != null) {
            task = new Task(true);
            task.setBounds(
                    view.getPositionFromDate(dragTask.getStartDate()), PlanningView.cellHeight * dragProdLine,
                    dragTask.getDateSpan() * PlanningView.cellWidth, PlanningView.cellHeight);
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
    
    /**
     * Sets the earliest and latest date limits from the specified activity.
     * @param a The source acitivity.
     */
    public void setDateLimitsFromActivity(Activity a) {
        earliestLimit.setLocation(
                view.getPositionFromDate(a.getEarliestStartDate()), 0);

        latestLimit.setLocation(
                view.getPositionFromDate(a.getLatestEndDate()), 0);
    }

    /**
	 * Notifies this component that the horizontal scroll
	 * of the Chart View has changed.
	 * @param scrollX the current horizontal scroll of the Chart View.
	 */
	public void notifyScrollChange(int scrollX) {
		productionLineView.getComponent().setLocation(
                scrollX, productionLineView.getComponent().getLocation().y);
	}

}
