package mvc.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import tableModels.ProductionLineTableModel;

/**
 *
 * @author John
 */
public class ProductionLineView implements Observer {
    
    public JTable table;
    
    private DefaultTableModel tableModel;
    private Dimension tableSize = new Dimension(ChartView.LEFT_OFFSET, 0);
    
    ProductionLineView(Model model) {
        model.addObserver(this);
        
        // Create table
        table = new JTable();
        table.setRowHeight(PlanningView.cellHeight);
        tableModel = new ProductionLineTableModel();
        table.setModel(tableModel);
    }

    public JTable getComponent() {
        return table;
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
        
        tableSize.height = productionLines.length * PlanningView.cellHeight;
        table.setSize(tableSize);
    }
    
}
