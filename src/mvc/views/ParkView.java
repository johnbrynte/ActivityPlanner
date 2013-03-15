package mvc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

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

    public static int LEFT_OFFSET = 150;
    
    private Dimension canvasSize;
    private JPanel parkPanel;
    private JPanel rootPanel;
    private PlanningView view;
    
    public ParkController parkController;
    
    private Model model;

    public ParkView(Model model, PlanningView view) {
            this.view  = view;
            this.model = model;
            
            model.addObserver(this);
            
            rootPanel = new JPanel(new BorderLayout());
            rootPanel.setPreferredSize(canvasSize);
            
            JLabel label = new JLabel("Unscheduled tasks: ");
            Dimension labelSize = new Dimension(LEFT_OFFSET, PlanningView.cellHeight);
            label.setSize(labelSize);
            label.setPreferredSize(labelSize);
            rootPanel.add(label, BorderLayout.WEST);
            
            canvasSize = new Dimension(PlanningView.cellWidth,
                                        PlanningView.cellHeight);
            parkPanel = new JPanel(new GridLayout(1, 0, 0, 0));
            parkPanel.setPreferredSize(canvasSize);
            rootPanel.add(parkPanel, BorderLayout.CENTER);
    }

    /**
     * Returns this component.
     * 
     * @return this component.
     */
    public JPanel getComponent() {
            return rootPanel;
    }

    /**
     * Updates the size taking into account the size of the scroll bar. 
     */
    public void updateSize() {
        /*
        if (scrollPane.getHorizontalScrollBar().isVisible()) {
                canvasSize.height = view.cellHeight
                                + scrollPane.getHorizontalScrollBar().getBounds().height;
        } else {
                canvasSize.height = view.cellHeight;
        }

        scrollPane.setMinimumSize(canvasSize);
        */
    }

    @Override
    public void update(Observable o, Object arg)
    {
        Activity[] unscheduled = model.getUnscheduledActivities().getActivities();
        //System.out.println("Number of unscheduled activities: " + unscheduled.length);
        canvasSize = new Dimension(PlanningView.cellWidth * unscheduled.length, PlanningView.cellHeight);
        
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
