package mvc.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author John
 */
public class ProductionLineView {

    private JPanel productionLinePanel;
    
    private Dimension canvasSize = new Dimension(150, 300);
    
    ProductionLineView(PlanningView view) {
        SpringLayout productionLineLayout = new SpringLayout();
        
        productionLinePanel = new JPanel(productionLineLayout);
        productionLinePanel.setPreferredSize(canvasSize);
        productionLinePanel.setMinimumSize(canvasSize);
        productionLinePanel.setMaximumSize(canvasSize);
        
        JLabel productionLine = new JLabel("Production line 1");
        productionLinePanel.add(productionLine);
        
        productionLineLayout.putConstraint(
                SpringLayout.WEST, productionLine, 5,
                SpringLayout.WEST, productionLinePanel);
        productionLineLayout.putConstraint(
                SpringLayout.NORTH, productionLine, view.timeLineView.timeLinePanel.canvasSize.height + 5,
                SpringLayout.NORTH, productionLinePanel);
    }

    public JPanel getComponent() {
        return productionLinePanel;
    }
    
}
