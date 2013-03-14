package tableModels;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author John
 */
public class ProductionLineTableModel extends DefaultTableModel {
    
    public ProductionLineTableModel() {
        super(new Object[] {""}, 0);
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
}
