package mvc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import mvc.views.ProductionLineView;

/**
 *
 * @author John
 */
public class ProductionLineController {
    
    public ProductionLineController(final Model model,
                                    final ProductionLineView view) {
        
        final JTable table = view.table;
        
        view.addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addProductionLine(view.textField.getText());
            }
        });
        
        view.deleteButton.addActionListener(new ActionListener(){
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
