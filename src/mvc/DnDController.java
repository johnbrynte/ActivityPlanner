package mvc;

public class DnDController
{
    private ChartController cctrl;
    private ParkController pctrl;
    
    public DnDController(ChartController c, ParkController p)
    {
        this.cctrl = c;
        this.pctrl = p;
    }
    
    
}
