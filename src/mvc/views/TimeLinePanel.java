package mvc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JPanel;

/**
 * The Time Line Panel draws the time line of the Time Line View.
 * The current year and current month as well as the current day
 * and date of each date is always visible in the time line and
 * is done so by taking into account the current horizontal scroll
 * of the Chart View.
 * @author John
 */
public class TimeLinePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final DateFormat MONTH_FORMAT = new SimpleDateFormat("MMMM", Locale.ENGLISH);
	private final DateFormat DAY_FORMAT = new SimpleDateFormat("EEE", Locale.ENGLISH);
	
	private final Dimension CLEAR_BOX = new Dimension(60, 14);
	private final int TEXT_HEIGHT = 12;
	private final int YEAR_Y = 12;
	private final int MONTH_Y = 30;
	private final int DATE_Y = 48;
	private final int MARGIN_X = 4;
	
	private Dimension canvasSize = new Dimension(0, 52);
	private int scrollX;
	
	private PlanningView view;
	private BufferedImage imageBuffer;
	
	public TimeLinePanel(PlanningView view) {
		this.view = view;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(imageBuffer, 0, 0, this);
	}
	
	/**
	 * Redraws the time line of the Time Line View.
	 * Everything is drawn to a buffered image in order to prevent flickering. 
	 * @param model the planning model.
	 */
	private void drawTimeLine() {
		Graphics bg = imageBuffer.getGraphics();
		
		// Create a calendar with weeks starting on a Monday
		GregorianCalendar day = new GregorianCalendar(Locale.ITALY);
		day.setTimeInMillis(view.startDate.getTimeInMillis());
		
		int year = -1;
		int week = day.get(GregorianCalendar.WEEK_OF_YEAR);
		int month = -1;
		
		bg.setColor(Color.white);
		bg.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		for (int i = 0; i <= view.daysBetween; i++) {
			day.setTimeInMillis(view.startDate.getTimeInMillis() +
					((long) i) * view.DAY_IN_MILLIS);
			
			// print year
			if(day.get(GregorianCalendar.YEAR) != year) {
				year = day.get(GregorianCalendar.YEAR);
				
				if(i * view.cellWidth < scrollX) {
					clearBox(bg, 0, YEAR_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(Integer.toString(day.get(GregorianCalendar.YEAR)),
							MARGIN_X, YEAR_Y);
				} else {
					clearBox(bg, i * view.cellWidth - scrollX, YEAR_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(Integer.toString(day.get(GregorianCalendar.YEAR)),
							i * view.cellWidth - scrollX + MARGIN_X, YEAR_Y);
				}
			}
			// print month
			if(day.get(GregorianCalendar.MONTH) != month) {
				month = day.get(GregorianCalendar.MONTH);
				
				if(i * view.cellWidth < scrollX) {
					clearBox(bg, 0, MONTH_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(getMonthName(day),
							MARGIN_X, MONTH_Y);
				} else {
					clearBox(bg, i * view.cellWidth - scrollX, MONTH_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(getMonthName(day),
							i * view.cellWidth - scrollX + MARGIN_X, MONTH_Y);
				}
			}
			// print day
			if(i * view.cellWidth > scrollX - view.cellWidth - 2) {
				bg.setColor(Color.black);
				bg.drawString(getDayName(day) + " " + day.get(GregorianCalendar.DATE),
						i * view.cellWidth - scrollX + MARGIN_X, DATE_Y);
				
				if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
					week = day.get(GregorianCalendar.WEEK_OF_YEAR);
					
					bg.drawLine(
							i * view.cellWidth - scrollX, DATE_Y - TEXT_HEIGHT,
							i * view.cellWidth - scrollX, canvasSize.height);
					bg.drawLine(
							i * view.cellWidth - scrollX + 1, DATE_Y - TEXT_HEIGHT,
							i * view.cellWidth - scrollX + 1, canvasSize.height);
				}
			}
		}
		
		repaint();
	}
	
	/**
	 * Clears (paints white) a rectangle at the given position
	 * and draws a vertical line border on the left side.
	 * Used as an underlay when drawing text on top of something.
	 * @param g the Graphics object to write to.
	 * @param x the x coordinate of the rectangle (left)
	 * @param y the y coordinate of the rectangle (bottom)
	 */
	private void clearBox(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, CLEAR_BOX.width + MARGIN_X * 2, CLEAR_BOX.height);
		
		g.setColor(Color.gray);
		g.drawLine(x, y, x, y + CLEAR_BOX.height);
	}
	
	/**
	 * Converts the given calendar date to the corresponding month name.
	 * The month name is returned as a 3-character string,
	 * as specified by MONTH_FORMAT. 
	 * @param c the calendar date to be converted.
	 * @return the corresponding month name to the given calendar date.
	 */
	private String getMonthName(GregorianCalendar c) {
		try {
			return MONTH_FORMAT.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Converts the given calendar date to the corresponding day name.
	 * The day name is returned as a 3-character string,
	 * as specified by DAY_FORMAT. 
	 * @param c the calendar date to be converted.
	 * @return the corresponding day name to the given calendar date.
	 */
	private String getDayName(GregorianCalendar c) {
		try {
			return DAY_FORMAT.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Updates the size and redraws the time line.
	 */
	public void updateView() {
		imageBuffer = new BufferedImage(
				view.canvasSize.width, view.canvasSize.height,
				BufferedImage.TYPE_INT_ARGB);

		canvasSize.width = view.canvasSize.width;
		setPreferredSize(canvasSize);
		
		drawTimeLine();
	}

	/**
	 * Sets the current horizontal scroll.
	 * @param scrollX the horizontal scroll the Chart View.
	 */
	public void setHorizontalScroll(int scrollX) {
		this.scrollX = scrollX;
		
		drawTimeLine();
	}

}
