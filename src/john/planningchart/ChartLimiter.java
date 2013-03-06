package john.planningchart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;

import javax.swing.JComponent;

/**
 * Represents a limiter for a Task object, showing either
 * the earliest or the latest date of its corresponding Activity.
 * @author John
 */
public class ChartLimiter extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	private BufferedImage imageBuffer;
	private PlanningView view;
	
	private Dimension canvasSize = new Dimension(2, 0);
	private Color color;
	private GregorianCalendar startDate;

	public ChartLimiter(PlanningView view, Color color) {
		this.view = view;
		this.color = color;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(imageBuffer, 0, 0, this);
	}
	
	/**
	 * Updates the size and redraws the chart canvas.
	 * @param model the planning model.
	 */
	public void updateView(PlanningModel model) {
		startDate = (GregorianCalendar) model.startDate.clone();
		canvasSize.height = view.canvasSize.height;
		
		imageBuffer = new BufferedImage(
				canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_RGB);
		setSize(canvasSize);
		
		Graphics g = imageBuffer.getGraphics();
		
		g.setColor(color);
		g.fillRect(0, 0, canvasSize.width, canvasSize.height);
	}
	
	/**
	 * Translated the given date into coordinates on the Chart View.
	 * @param date the date to be translated.
	 */
	public void setPositionFromDate(GregorianCalendar date) {
		setLocation((int) (view.cellWidth
				* (date.getTimeInMillis() - startDate.getTimeInMillis()) / DAY_IN_MILLIS),
				0);
	}
	
}
