package john.planningchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChartView implements Observer {

	private static final long serialVersionUID = 1L;

	private JPanel canvas;
	private JLayeredPane canvasLayeredPane;
	private ChartController chartController;

	public PlanningTimeLine timeLine;
	public JScrollPane canvasScrollPane;
	
	private BufferedImage imageBuffer;

	public ChartView(PlanningChartModel model, PlanningTimeLine timeLine) {
		model.addObserver(this);
		
		this.timeLine = timeLine;
		
		chartController = new ChartController(this);
		
		canvas = new JPanel();
		
		canvasLayeredPane = new JLayeredPane();
		canvasLayeredPane.add(canvas, new Integer(1));
		
		canvasScrollPane = new JScrollPane(canvasLayeredPane);
		canvasScrollPane.setBorder(null);
		canvasScrollPane.getViewport().addChangeListener(chartController);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(imageBuffer, 0, 0, this);
	}

	private void drawBorders() {
		Graphics g = imageBuffer.getGraphics();
		
		GregorianCalendar day = new GregorianCalendar(Locale.ITALY);
		day.setTimeInMillis(PlanningChartModel.startDate.getTimeInMillis());
		int week = day.get(GregorianCalendar.WEEK_OF_YEAR);

		g.setColor(Color.white);
		g.fillRect(0, 0, PlanningChartModel.canvasSize.width, PlanningChartModel.canvasSize.height);
		g.setColor(Color.lightGray);

		// draw horizontal lines
		for (int i = 0; i < PlanningChartModel.rows; i++) {
			g.drawLine(
					0, i * PlanningChartModel.cellHeight,
					PlanningChartModel.canvasSize.width, i * PlanningChartModel.cellHeight);
		}
		// draw vertical lines
		for (int i = 0; i < PlanningChartModel.daysBetween; i++) {
			day.setTimeInMillis(PlanningChartModel.startDate.getTimeInMillis() +
					((long) i) * PlanningChartModel.DAY_IN_MILLIS);
			
			if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
				week = day.get(GregorianCalendar.WEEK_OF_YEAR);
				g.setColor(Color.black);
				
				g.drawLine(
						i * PlanningChartModel.cellWidth + 1, 0,
						i * PlanningChartModel.cellWidth + 1, PlanningChartModel.canvasSize.height);
			} else {
				g.setColor(Color.lightGray);
			}
			
			g.drawLine(
					i * PlanningChartModel.cellWidth, 0,
					i * PlanningChartModel.cellWidth, PlanningChartModel.canvasSize.height);
		}

		repaint();
	}

	@Override
	public void update(Observable model, Object data) {
		imageBuffer = new BufferedImage(
				PlanningChartModel.canvasSize.width, PlanningChartModel.canvasSize.height,
				BufferedImage.TYPE_INT_ARGB);

		setSize(PlanningChartModel.canvasSize);
		drawBorders();
	}
}
