package mvc.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import mvc.model.ActivityHolder;
import mvc.model.Model;

/**
 *
 * @author John
 */
public class ProductionLineView implements Observer {

    private JPanel productionLinePanel;
    private PlanningView view;
    
    private Dimension canvasSize = new Dimension(150, 300);
    
    ProductionLineView(Model model, PlanningView view) {
        this.view = view;
        
        model.addObserver(this);
        
        productionLinePanel = new JPanel();
        productionLinePanel.setPreferredSize(canvasSize);
        productionLinePanel.setMinimumSize(canvasSize);
        productionLinePanel.setMaximumSize(canvasSize);
    }

    public JPanel getComponent() {
        return productionLinePanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        
        // Remove all tasks from the panel
		Component[] tasks = productionLinePanel.getComponents();
		for(Component c : tasks)
			productionLinePanel.remove(c);
        
        int topOffset = view.timeLineView.timeLinePanel.canvasSize.height;
        
        ActivityHolder[] productionLines = model.getProductionLines();
        JLabel productionLine;
        
        productionLinePanel.setLayout(new GridLayout(0, 1));
        
        for(int i = 0; i < productionLines.length; i ++) {
            productionLine = new JLabel(productionLines[i].getName());
            productionLine.setSize(new Dimension(
                    canvasSize.width, view.cellHeight));
            productionLine.setLocation(5, topOffset + view.cellHeight * i);
            productionLinePanel.add(productionLine);
        }
    }
    
}
