package mvc.controllers;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import mvc.views.ParkView;
import mvc.views.Task;

public class ParkController implements MouseInputListener
{
    private ParkView view;
    private DnDController dnd;
    private Task dragging;
    
    public ParkController(DnDController d, ParkView v)
    {
        this.dnd    = d;
        this.view   = v;
    }
    
    void setDropResult(boolean result)
    {
        // if it is true, the activity was dropped succesfully
        if (result) {
            
        }
        
        // else, set the dragging activity to null, but not delete it.
        else {
            // keep activity in park
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        dragging = (Task)(e.getComponent());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDraggingEvent(e, DnDController.RELEASE); }
        
        // in case the mouse was in the upper view, await for the drop result,
        // else, nothing must be done, so the selected activity for dragging
        // must be set to null
        else { dragging = null; }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDraggingEvent(e, DnDController.DRAG); }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
}
