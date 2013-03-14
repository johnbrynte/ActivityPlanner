package mvc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import mvc.views.ProductionLineControlView;
import mvc.views.ProductionLineView;

/**
 *
 * @author John
 */
public class ProductionLineController {
    
    public ProductionLineController(final Model model,
                                    final ProductionLineView view,
                                    final ProductionLineControlView control) {
        
        final JTable table = view.table;
        
        control.addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addProductionLine(control.textField.getText());
            }
        });
        
        control.deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table.getSelectedRow();
                
                if(rowIndex >= 0) {
                    ActivityHolder[] productionLines =
                            model.getProductionLines();
                    model.removeProductionLine(productionLines[rowIndex]);
                }
            }
        });
    }
}
