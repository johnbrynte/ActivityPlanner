package mvc.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The Park View shows all tasks that can be placed on the Chart View.
 * 
 * @author John
 */
public class ParkView implements Observer{

    private Dimension canvasSize;
    private JPanel parkPanel;
    private JScrollPane scrollPane;
    private PlanningView view;
    
    public JButton task1;
    public JButton task2;

    public ParkView(PlanningView view) {
            this.view = view;

            parkPanel = new JPanel();

            parkPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

            task1 = new JButton("Task 4");
            task1.setPreferredSize(new Dimension(view.cellWidth * 2, view.cellHeight));
            parkPanel.add(task1);

            task2 = new JButton("Task 5");
            task2.setPreferredSize(new Dimension(view.cellWidth * 2, view.cellHeight));
            parkPanel.add(task2);

            canvasSize = new Dimension(view.cellWidth * 4, view.cellHeight);
            parkPanel.setPreferredSize(canvasSize);

            scrollPane = new JScrollPane(parkPanel);
            scrollPane.setBorder(null);
            scrollPane.setPreferredSize(canvasSize);
            scrollPane.setMinimumSize(canvasSize);
    }

    /**
     * Returns this component.
     * 
     * @return this component.
     */
    public JScrollPane getComponent() {
            return scrollPane;
    }

    /**
     * Updates the size taking into account the size of the scroll bar. 
     */
    public void updateSize() {
		if (scrollPane.getHorizontalScrollBar().isVisible()) {
			canvasSize.height = view.cellHeight
					+ scrollPane.getHorizontalScrollBar().getBounds().height;
		} else {
			canvasSize.height = view.cellHeight;
		}

		scrollPane.setMinimumSize(canvasSize);
	}

    @Override
    public void update(Observable o, Object arg)
    {
        // if the drop event was succesful, the model will be changed and this
        // method called. So this method must delete the moved activity.
    }

}
