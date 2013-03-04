package mvc.controllers;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import mvc.views.ParkView;

public class ParkController implements MouseInputListener
{
    private ParkView view;
    private DnDController dnd;
    
    public ParkController(DnDController d, ParkView v)
    {
        this.dnd    = d;
        this.view   = v;
    }
    
    void setDropResult(boolean result)
    {
        if (result) {
            // delete activity from park
        }
        else {
            // keep activity in park
        }
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
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDraggingEvent(e, DnDController.RELEASE); }
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
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDraggingEvent(e, DnDController.DRAG); }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }
    
}
