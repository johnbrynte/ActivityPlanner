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
        
        view.task1.addMouseListener(this);
        view.task1.addMouseMotionListener(this);
        view.task2.addMouseListener(this);
        view.task2.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        dragging = (Task)(e.getComponent());
        System.out.println(dragging.toString());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDraggingEvent(e, DnDController.RELEASE); }
        
        // whether the drop was succesful or not, the drag and drop is finished 
        dragging = null;
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
