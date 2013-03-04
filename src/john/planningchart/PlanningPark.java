package john.planningchart;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PlanningPark extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	
	private Dimension canvasSize = new Dimension(100, PlanningChartModel.cellHeight);
	
	public PlanningPark(PlanningChartModel model) {
		model.addObserver(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JButton task = new JButton("Task 4");
		task.setPreferredSize(new Dimension(
				PlanningChartModel.cellWidth * 2, PlanningChartModel.cellHeight));
		add(task);
		
		task = new JButton("Task 5");
		task.setPreferredSize(new Dimension(
				PlanningChartModel.cellWidth * 2, PlanningChartModel.cellHeight));
		add(task);
		
		canvasSize.width = PlanningChartModel.cellWidth * 4;
		setSize(canvasSize);
	}

	@Override
	public void update(Observable model, Object data) {
		// TODO Auto-generated method stub
		
	}

}
