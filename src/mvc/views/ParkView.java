package mvc.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import mvc.controllers.ParkController;
import mvc.model.Activity;
import mvc.model.Model;

/**
 * The Park View shows all tasks that can be placed on the Chart View.
 * 
 * @author John
 */
public class ParkView implements Observer {

    private Dimension canvasSize;
    private JPanel parkPanel;
    private JScrollPane scrollPane;
    private PlanningView view;
    
    public ParkController parkController;
    
    private Model model;

    public ParkView(Model model, PlanningView view) {
            this.view  = view;
            this.model = model;
            
            model.addObserver(this);

            parkPanel = new JPanel(new GridLayout(1, 0, 0, 0));
            parkPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            
            canvasSize = new Dimension(PlanningView.cellWidth,
                                        PlanningView.cellHeight);
            parkPanel.setPreferredSize(canvasSize);
            
            scrollPane = new JScrollPane(parkPanel);
            scrollPane.setBorder(null);
            scrollPane.setPreferredSize(canvasSize);
    }

    /**
     * Returns this component.
     * 
     * @return this component.
     */
    public JScrollPane getComponent() {
            return scrollPane;
    }

    /**
     * Updates the size taking into account the size of the scroll bar. 
     */
    public void updateSize() {
        if (scrollPane.getHorizontalScrollBar().isVisible()) {
                canvasSize.height = view.cellHeight
                                + scrollPane.getHorizontalScrollBar().getBounds().height;
        } else {
                canvasSize.height = view.cellHeight;
        }

        scrollPane.setMinimumSize(canvasSize);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        Activity[] unscheduled = model.getUnscheduledActivities().getActivities();
        //System.out.println("Number of unscheduled activities: " + unscheduled.length);
        canvasSize = new Dimension(view.cellWidth * unscheduled.length, view.cellHeight);
        
        updateSize();
        
        // Remove all tasks from the panel
		Component[] tasks = parkPanel.getComponents();
		for(Component c : tasks)
			parkPanel.remove(c);
        
        Task task;
        Dimension defaultSize = new Dimension(1,1); // Only to make it visible
        
        for(int i = 0; i < unscheduled.length; ++i) {
            Activity a = unscheduled[i];
            task = new Task(false);
            task.setPreferredSize(defaultSize);
            task.setActivity(a);
            task.addMouseListener(parkController);
            task.addMouseMotionListener(parkController);
            task.setVisible(true);
            parkPanel.add(task);
        }
        
        parkPanel.validate();
        parkPanel.repaint();
    }

}
