package mvc.controllers;

import java.awt.event.MouseEvent;

public class DnDController
{
    private ChartController cctrl;
    private ParkController pctrl;
    
    private boolean dragging = false;
    
    public static final int DRAG    = 0;
    public static final int RELEASE = 1;
    
    public DnDController(ChartController c, ParkController p)
    {
        this.cctrl = c;
        this.pctrl = p;
    }
    
    public DnDController()
    {}
    
    public void setDnDSourceAndDestination(ChartController c, ParkController p)
    {
        this.cctrl = c;
        this.pctrl = p;
    }
    
    public void transferDraggingEvent(MouseEvent e, int type)
    {
        if (!dragging) {
            dragging = true;
            cctrl.startComm();
        }
        
        if (type == DRAG) {
            cctrl.mouseDragged(e);
        }
        else {
            cctrl.dropEvent(e);
            dragging = false;
            cctrl.endComm();
        }
    }
}
