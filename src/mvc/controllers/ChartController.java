package mvc.controllers;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import mvc.views.ChartView;

public class ChartController implements MouseInputListener
{
    private ChartView view;
    private DnDController dnd;
    private boolean communication = false;
    
    public ChartController(DnDController d, ChartView v)
    {
        this.dnd  = d;
        this.view = v;
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
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        /*
        boolean result = view. ...
        if (result) {
            if (communication) { dnd.notifyDropResult(true); }
            else { performDrop }
        }
        else {
           if (communication) { dnd.notifyDropResult(false); }
           else { cancelDrop }
        }
        */
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }

}
