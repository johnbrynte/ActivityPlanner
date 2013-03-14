package mvc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Represents a limiter for a Task object, showing either
 * the earliest or the latest date of its corresponding Activity.
 * @author John
 */
public class ChartLimiter extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage imageBuffer;
	private PlanningView view;
	
	private Dimension canvasSize = new Dimension(2, 0);
	private Color color;

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
	 */
	public void updateView() {
        if(view.canvasSize.width > 0 && view.canvasSize.height > 0) {
            canvasSize.height = view.canvasSize.height;

            imageBuffer = new BufferedImage(
                    canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_RGB);
            setSize(canvasSize);

            Graphics g = imageBuffer.getGraphics();

            g.setColor(color);
            g.fillRect(0, 0, canvasSize.width, canvasSize.height);
        } else {
            imageBuffer = null;
        }
	}
	
}
