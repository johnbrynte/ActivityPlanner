package mvc.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JPanel;

/**
 * The Chart Canvas draws the borders of the Chart View
 * with the specified dimensions in the Chart View.
 * @author John
 */
public class ChartCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private PlanningView view;
	private BufferedImage imageBuffer;

	public ChartCanvas(PlanningView view) {
		this.view = view;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(imageBuffer, 0, 0, this);
	}

	/**
	 * Redraws the borders of the Chart View.
	 * Everything is drawn to a buffered image in order to prevent flickering. 
	 * @param model the planning model.
	 */
	private  void drawBorders() {
		Graphics g = imageBuffer.getGraphics();
		
		GregorianCalendar day = new GregorianCalendar(Locale.ITALY);
		day.setTimeInMillis(view.startDate.getTimeInMillis());
		int week = day.get(GregorianCalendar.WEEK_OF_YEAR);

		g.setColor(Color.white);
		g.fillRect(0, 0, view.canvasSize.width, view.canvasSize.height);
		g.setColor(Color.lightGray);

		// draw horizontal lines
		for (int i = 0; i < view.rows; i++) {
			g.drawLine(
					0, i * view.cellHeight,
					view.canvasSize.width, i * view.cellHeight);
		}
		
		// draw vertical lines
		for (int i = 0; i <= view.daysBetween; i++) {
			day.setTimeInMillis(view.startDate.getTimeInMillis() +
					((long) i) * view.DAY_IN_MILLIS);
			
			if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
				week = day.get(GregorianCalendar.WEEK_OF_YEAR);
				g.setColor(Color.black);
				
				g.drawLine(
						i * view.cellWidth + 1, 0,
						i * view.cellWidth + 1, view.canvasSize.height);
			} else {
				g.setColor(Color.lightGray);
			}
			
			g.drawLine(
					i * view.cellWidth, 0,
					i * view.cellWidth, view.canvasSize.height);
		}
		
		repaint();
	}
	
	/**
	 * Updates the size and redraws the chart canvas.
	 */
	public void updateView() {
        imageBuffer = new BufferedImage(
                view.canvasSize.width, view.canvasSize.height, BufferedImage.TYPE_INT_RGB);
        setSize(view.canvasSize);

        drawBorders();
    }
}
