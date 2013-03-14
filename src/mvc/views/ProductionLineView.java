package mvc.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import tableModels.ProductionLineTableModel;

/**
 *
 * @author John
 */
public class ProductionLineView implements Observer {

    private JPanel rootPanel;
    private JPanel productionLinePanel;
    private PlanningView view;
    
    public JButton addButton;
    public JButton deleteButton;
    public JTextField textField;
    public JTable table;
    
    private DefaultTableModel tableModel;
    
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
        
        // Create control panel
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
        
        // Create table
        table = new JTable();
        table.setRowHeight(view.cellHeight);
        rootPanel.add(table, BorderLayout.CENTER);
        tableModel = new ProductionLineTableModel();
        table.setModel(tableModel);
    }

    public JPanel getComponent() {
        return rootPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        
        ActivityHolder[] productionLines = model.getProductionLines();
        
        // Empty the table
        while(table.getRowCount() > 0)
            tableModel.removeRow(0);
        
        for(int i = 0; i < productionLines.length; i ++) {
            tableModel.addRow(new Object[]{
                productionLines[i].getName()
            });
        }
    }
    
}
