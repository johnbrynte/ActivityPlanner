package john.planningchart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class PlanningTimeLine extends JPanel implements Observer {

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
	
	private BufferedImage imageBuffer;
	
	public PlanningTimeLine(PlanningChartModel model) {
		model.addObserver(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(imageBuffer == null)
			return;
		
		GregorianCalendar day = new GregorianCalendar(Locale.ITALY);
		day.setTimeInMillis(PlanningChartModel.startDate.getTimeInMillis());
		Graphics bg = imageBuffer.getGraphics();
		int year = -1;
		int week = day.get(GregorianCalendar.WEEK_OF_YEAR);
		int month = -1;
		
		bg.setColor(Color.white);
		bg.fillRect(0, 0, canvasSize.width, canvasSize.height);
		
		for (int i = 0; i < PlanningChartModel.daysBetween; i++) {
			day.setTimeInMillis(PlanningChartModel.startDate.getTimeInMillis() +
					((long) i) * PlanningChartModel.DAY_IN_MILLIS);
			
			// print year
			if(day.get(GregorianCalendar.YEAR) != year) {
				year = day.get(GregorianCalendar.YEAR);
				
				if(i * PlanningChartModel.cellWidth < scrollX) {
					clearBox(bg, 0, YEAR_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(Integer.toString(day.get(GregorianCalendar.YEAR)),
							MARGIN_X, YEAR_Y);
				} else {
					clearBox(bg, i * PlanningChartModel.cellWidth - scrollX, YEAR_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(Integer.toString(day.get(GregorianCalendar.YEAR)),
							i * PlanningChartModel.cellWidth - scrollX + MARGIN_X, YEAR_Y);
				}
			}
			// print month
			if(day.get(GregorianCalendar.MONTH) != month) {
				month = day.get(GregorianCalendar.MONTH);
				
				if(i * PlanningChartModel.cellWidth < scrollX) {
					clearBox(bg, 0, MONTH_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(getMonthName(day),
							MARGIN_X, MONTH_Y);
				} else {
					clearBox(bg, i * PlanningChartModel.cellWidth - scrollX, MONTH_Y - TEXT_HEIGHT);
					bg.setColor(Color.black);
					bg.drawString(getMonthName(day),
							i * PlanningChartModel.cellWidth - scrollX + MARGIN_X, MONTH_Y);
				}
			}
			// print day
			if(i * PlanningChartModel.cellWidth > scrollX - PlanningChartModel.cellWidth - 2) {
				bg.setColor(Color.black);
				bg.drawString(getDayName(day) + " " + day.get(GregorianCalendar.DATE),
						i * PlanningChartModel.cellWidth - scrollX + MARGIN_X, DATE_Y);
				
				if(day.get(GregorianCalendar.WEEK_OF_YEAR) != week) {
					week = day.get(GregorianCalendar.WEEK_OF_YEAR);
					
					bg.drawLine(
							i * PlanningChartModel.cellWidth - scrollX, DATE_Y - TEXT_HEIGHT,
							i * PlanningChartModel.cellWidth - scrollX, canvasSize.height);
					bg.drawLine(
							i * PlanningChartModel.cellWidth - scrollX + 1, DATE_Y - TEXT_HEIGHT,
							i * PlanningChartModel.cellWidth - scrollX + 1, canvasSize.height);
				}
			}
		}
		
		g.drawImage(imageBuffer, 0, 0, this);
	}
	
	public void notifyScrollChange(int scrollX) {
		this.scrollX = scrollX;
		
		repaint();
	}
	
	private void clearBox(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, CLEAR_BOX.width + MARGIN_X * 2, CLEAR_BOX.height);
		
		g.setColor(Color.gray);
		g.drawLine(x, y, x, y + CLEAR_BOX.height);
	}
	
	private String getMonthName(GregorianCalendar c) {
		try {
			return MONTH_FORMAT.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
	}
	
	private String getDayName(GregorianCalendar c) {
		try {
			return DAY_FORMAT.format(c.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void update(Observable model, Object data) {
		imageBuffer = new BufferedImage(
				PlanningChartModel.canvasSize.width, PlanningChartModel.canvasSize.height,
				BufferedImage.TYPE_INT_ARGB);

		canvasSize.width = PlanningChartModel.canvasSize.width;
		setPreferredSize(canvasSize);
	}

}
