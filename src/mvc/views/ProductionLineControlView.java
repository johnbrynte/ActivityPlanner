package mvc.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author John
 */
public class ProductionLineControlView {
    
    private JPanel controlPanel;
    
    public JButton addButton;
    public JButton deleteButton;
    public JTextField textField;
    
    public ProductionLineControlView(PlanningView view) {
        Dimension controlSize = new Dimension(ChartView.LEFT_OFFSET, 0);
        
        // Create control panel
        SpringLayout layout = new SpringLayout();
        controlPanel = new JPanel(layout);
        controlPanel.setSize(controlSize);
        controlPanel.setPreferredSize(controlSize);

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
        
        textField = new JTextField("Production line name...");
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
    }
    
    public JPanel getComponent() {
        return controlPanel;
    }
    
}
