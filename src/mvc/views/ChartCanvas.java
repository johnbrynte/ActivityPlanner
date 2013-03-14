package mvc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import mvc.model.ActivityHolder;
import mvc.model.Model;

/**
 * The Chart Canvas draws the borders of the Chart View
 * with the specified dimensions in the Chart View.
 * @author John
 */
public class ChartCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
    private Model model;
	private PlanningView view;
	private BufferedImage imageBuffer;

	public ChartCanvas(Model model, PlanningView view) {
        this.model = model;
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
		g.fillRect(0, 0,
                    view.canvasSize.width + ChartView.LEFT_OFFSET,
                    view.canvasSize.height);
        
        ActivityHolder[] productionLines = model.getProductionLines();

		// draw horizontal lines
		for (int i = 0; i < productionLines.length; i++) {            
            g.setColor(Color.lightGray);
			g.drawLine(0, i * PlanningView.cellHeight,
                        view.canvasSize.width + ChartView.LEFT_OFFSET,
                        i * PlanningView.cellHeight);
		}
		
		// draw vertical lines
		for (int i = 0; i <= view.daysBetween; i++) {
			day.setTimeInMillis(view.startDate.getTimeInMillis() +
					((long) i) * view.DAY_IN_MILLIS);
			
			if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
				week = day.get(GregorianCalendar.WEEK_OF_YEAR);
				g.setColor(Color.black);
				
				g.drawLine(i * PlanningView.cellWidth + ChartView.LEFT_OFFSET + 1, 0,
                            i * PlanningView.cellWidth + ChartView.LEFT_OFFSET + 1,
                            view.canvasSize.height);
			} else {
				g.setColor(Color.lightGray);
			}
			
			g.drawLine(i * PlanningView.cellWidth + ChartView.LEFT_OFFSET, 0,
                        i * PlanningView.cellWidth + ChartView.LEFT_OFFSET,
                        view.canvasSize.height);
		}
		
		repaint();
	}
	
	/**
	 * Updates the size and redraws the chart canvas.
	 */
	public void updateView() {
        if(view.canvasSize.width > 0 && view.canvasSize.height > 0) {
            imageBuffer = new BufferedImage(
                    view.canvasSize.width, view.canvasSize.height, BufferedImage.TYPE_INT_RGB);
            
            setSize(view.canvasSize);

            drawBorders();
        } else {
            imageBuffer = null;
        }
    }

}
