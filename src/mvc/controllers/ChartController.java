package mvc.controllers;

import mvc.views.PlanningView;
import mvc.views.ChartView;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import mvc.model.Model;

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
    }

    @Override
    public void stateChanged(ChangeEvent event) {
            JScrollPane chartView = view.chartView.getComponent();

            if(chartView.getViewport().getViewPosition().x != horizontalScroll) {
                    horizontalScroll = chartView.getViewport().getViewPosition().x;

                    view.timeLineView.notifyScrollChange(horizontalScroll);
            }

            view.parkView.updateSize();
    }

    /**
     * Calculates the date on which the drop event was fired
     * and asks the model to place the component (Task) on the Chart View.
     * @param event the mouse event that caused the drop event.
     */
    public void dropEvent(MouseEvent event) {
        // using x position relative to the Chart View
        int x = horizontalScroll + event.getX();

        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(
                        view.startDate.getTimeInMillis() + view.DAY_IN_MILLIS * x / view.cellWidth);

        // TODO
        //model.placeTaskOnChart(event.getComponent(), date);
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
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        //System.out.println("pressed in chart view");
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        //System.out.println("released in chart view");
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        //System.out.println("entered in chart view");
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        //System.out.println("exited chart view");
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        //if (!communication) System.out.println("CHART - inside drag");
        //else                System.out.println("CHART - outside drag");
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }

}
