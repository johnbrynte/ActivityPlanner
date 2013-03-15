package mvc.controllers;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import mvc.views.ParkView;
import mvc.views.Task;
import selectedTaskModels.SelectedTaskModel;

public class ParkController implements MouseInputListener
{
    private ParkView view;
    private DnDController dnd;
    private SelectedTaskModel selectedTaskModel;
    
    public ParkController(DnDController d, ParkView v, SelectedTaskModel selectedTaskModel)
    {
        this.dnd    = d;
        this.view   = v;
        this.selectedTaskModel = selectedTaskModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDnDEvent(e, DnDController.RELEASE); }
        
        if(e.getComponent() instanceof Task) {
            selectedTaskModel.setNewSelectedTask(((Task) e.getComponent()).getActivity());
        }
        
        // whether the drop was succesful or not, the drag and drop is finished 
        //dragging = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // detect whether the mouse is on the upper view
        if (e.getY() < 0) { dnd.transferDnDEvent(e, DnDController.DRAG); }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
}
