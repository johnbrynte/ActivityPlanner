package john.planningchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class PlanningCanvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage imageBuffer;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(imageBuffer, 0, 0, this);
	}

	private void drawBorders() {
		Graphics g = imageBuffer.getGraphics();
		
		GregorianCalendar day = new GregorianCalendar();
		day.setTimeInMillis(PlanningChart.startDate.getTimeInMillis());
		int week = day.get(GregorianCalendar.WEEK_OF_YEAR);

		g.setColor(Color.white);
		g.fillRect(0, 0, PlanningChart.canvasSize.width, PlanningChart.canvasSize.height);
		g.setColor(Color.lightGray);

		// draw horizontal lines
		for (int i = 0; i < PlanningChart.rows; i++) {
			g.drawLine(
					0, i * PlanningChart.cellHeight,
					PlanningChart.canvasSize.width, i * PlanningChart.cellHeight);
		}
		// draw vertical lines
		for (int i = 0; i < PlanningChart.daysBetween; i++) {
			day.setTimeInMillis(PlanningChart.startDate.getTimeInMillis() +
					((long) i) * PlanningChart.DAY_IN_MILLIS);
			
			if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
				week = day.get(GregorianCalendar.WEEK_OF_YEAR);
				g.setColor(Color.black);
				
				g.drawLine(
						i * PlanningChart.cellWidth + 1, 0,
						i * PlanningChart.cellWidth + 1, PlanningChart.canvasSize.height);
			} else {
				g.setColor(Color.lightGray);
			}
			
			g.drawLine(
					i * PlanningChart.cellWidth, 0,
					i * PlanningChart.cellWidth, PlanningChart.canvasSize.height);
		}

		repaint();
	}

	public void notifySizeChange() {
		imageBuffer = new BufferedImage(
				PlanningChart.canvasSize.width, PlanningChart.canvasSize.height,
				BufferedImage.TYPE_INT_ARGB);

		setSize(PlanningChart.canvasSize);
		drawBorders();
	}
}
