package mvc.controllers;

import mvc.views.PlanningView;
import mvc.views.ChartView;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import mvc.model.Activity;
import mvc.model.ActivityHolder;
import mvc.model.Model;
import mvc.views.Task;

/**
 * The Chart Controller listens to the scroll events of the
 * Chart View and notifies the Time Line View.
 * @author John
 */
public class ChartController implements ChangeListener, MouseInputListener {

    private PlanningView view;
    private ChartView cv;

    private DnDController dnd;
    
    private Model model;

    // this variable differentiates drag and drop between the park and chart
    // views, or only inside the chart view. Communication means that there is
    // an inter-view communication.
    private boolean communication = false;

    private int horizontalScroll;
    private int verticalScroll;
    private int chartCanvasHeight;

    public ChartController(Model model, DnDController dnd, ChartView cv, PlanningView view) {
        this.dnd   = dnd;
        this.view  = view;
        this.cv    = cv;
        this.model = model;
        if (cv != null) {
            cv.getComponent().getViewport().addChangeListener(this);
            cv.getComponent().getViewport().addMouseListener(this);
            cv.getComponent().getViewport().addMouseMotionListener(this);
        }
        horizontalScroll = 0;
        verticalScroll = 0;
        chartCanvasHeight = cv.getComponent().getVisibleRect().height;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        JScrollPane chartView = view.chartView.getComponent();

        if(chartView.getViewport().getViewPosition().x != horizontalScroll) {
            horizontalScroll = chartView.getViewport().getViewPosition().x;

            view.timeLineView.notifyScrollChange(horizontalScroll);
        }
        
        // Always update vertical scroll
        verticalScroll = chartView.getViewport().getViewPosition().y;
        
        chartCanvasHeight = chartView.getVisibleRect().height;

        view.parkView.updateSize();
    }

    /**
     * Calculates the date on which the drop event was fired
     * and asks the model to place the component (Task) on the Chart View.
     * @param event the mouse event that caused the drop event.
     */
    public void dropEvent(MouseEvent event) {
        int x = event.getComponent().getLocation().x + event.getX();

        if(x > 0) {
            if (communication) x += horizontalScroll;

            GregorianCalendar date = new GregorianCalendar();
            date.setTimeInMillis(
                    view.startDate.getTimeInMillis() + view.DAY_IN_MILLIS
                    * ((long) x / view.cellWidth));

            int y;
            if (communication) y = (chartCanvasHeight + verticalScroll + event.getY()) / view.cellHeight;
            else               y = (event.getComponent().getLocation().y + event.getY()) / view.cellHeight;

            ActivityHolder[] productionLines = model.getProductionLines();
            Activity activity;

            //System.out.println("x index: " + (x / view.cellWidth));
            //System.out.println("y index: " + y);

            if(y >= 0 && y < productionLines.length) {
                activity = ((Task) event.getComponent()).getActivity();
                model.reschedule(activity, productionLines[y], date);
            }
        }
    }

    public void startComm()
    {
        communication = true;
    }

    public void endComm()
    {
        communication = false;
    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e)
    {
        dropEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        //System.out.println("x: " + (e.getComponent().getLocation().x + e.getX()));
        //System.out.println("y: " + ((int) ((chartCanvasHeight + e.getY()) / view.cellHeight)));
    }

    @Override
    public void mouseMoved(MouseEvent e){}

}
