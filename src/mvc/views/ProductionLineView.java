package mvc.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import mvc.model.ActivityHolder;
import mvc.model.Model;

/**
 *
 * @author John
 */
public class ProductionLineView implements Observer {

    private JPanel rootPanel;
    private JPanel productionLinePanel;
    private PlanningView view;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField textField;
    
    private Dimension canvasSize = new Dimension(150, 0);
    
    ProductionLineView(Model model, PlanningView view) {
        this.view = view;
        
        model.addObserver(this);
        
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setPreferredSize(canvasSize);
        rootPanel.setMinimumSize(canvasSize);
        rootPanel.setMaximumSize(canvasSize);
                
        int topHeight = view.timeLineView.timeLinePanel.canvasSize.height;
        
        Dimension controlSize = new Dimension(canvasSize.width, topHeight);
        
        SpringLayout layout = new SpringLayout();
        JPanel controlPanel = new JPanel(layout);
        controlPanel.setPreferredSize(controlSize);
        controlPanel.setMinimumSize(controlSize);
        controlPanel.setMaximumSize(controlSize);
        controlPanel.setLocation(0, 0);
        rootPanel.add(controlPanel, BorderLayout.NORTH);

        addButton = new JButton("Add");
        layout.putConstraint(
                SpringLayout.NORTH, addButton, 3,
                SpringLayout.NORTH, controlPanel);
        layout.putConstraint(
                SpringLayout.WEST, addButton, 5,
                SpringLayout.WEST, controlPanel);
        controlPanel.add(addButton);
        
        deleteButton = new JButton("Delete");
        layout.putConstraint(
                SpringLayout.NORTH, deleteButton, 3,
                SpringLayout.NORTH, controlPanel);
        layout.putConstraint(
                SpringLayout.EAST, deleteButton, -5,
                SpringLayout.EAST, controlPanel);
        controlPanel.add(deleteButton);
        
        textField = new JTextField();
        layout.putConstraint(
                SpringLayout.SOUTH, textField, 0,
                SpringLayout.SOUTH, controlPanel);
        layout.putConstraint(
                SpringLayout.WEST, textField, 0,
                SpringLayout.WEST, controlPanel);
        layout.putConstraint(
                SpringLayout.EAST, textField, 0,
                SpringLayout.EAST, controlPanel);
        controlPanel.add(textField);
        
        productionLinePanel = new JPanel();
        productionLinePanel.setLayout(null);
        productionLinePanel.setLocation(0, topHeight);
        rootPanel.add(productionLinePanel, BorderLayout.CENTER);
    }

    public JPanel getComponent() {
        return rootPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        
        // Remove all tasks from the panel
		Component[] tasks = productionLinePanel.getComponents();
		for(Component c : tasks)
			productionLinePanel.remove(c);
        
        ActivityHolder[] productionLines = model.getProductionLines();
        JLabel productionLine;
        
        productionLinePanel.setSize(
                productionLinePanel.getWidth(),
                productionLines.length * view.cellHeight);
        
        for(int i = 0; i < productionLines.length; i ++) {
            productionLine = new JLabel(productionLines[i].getName());
            productionLine.setSize(new Dimension(
                    canvasSize.width, view.cellHeight));
            productionLine.setLocation(5, view.cellHeight * i);
            productionLinePanel.add(productionLine);
        }
    }
    
}
