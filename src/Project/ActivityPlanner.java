/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mvc.controllers.ActivityTableController;
import mvc.controllers.ChartController;
import mvc.controllers.DnDController;
import mvc.controllers.ParkController;
import mvc.views.ActivityTableView;
import mvc.views.PlanningView;
import mvc.model.Model;

/**
 *
 * @author David
 */
public class ActivityPlanner
{

    private JPanel jPanelRight;
    private JFrame mainWindow;
    
    
    public PlanningView pv;
    public ActivityTableView tv;
    
    private ChartController         cc;
    private ParkController          pc;
    private DnDController           dnd;
    private ActivityTableController atc;
    
    private Model model;
    
    public ActivityPlanner(Model model)
    {
        this.model = model;
        loadGUI();
    }
    
    private void loadGUI()
    {
        pv  = new PlanningView(model);
        tv  = new ActivityTableView();

        mainWindow = new JFrame();
        
        mainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new java.awt.Dimension(400, 400));
        mainWindow.setPreferredSize(new java.awt.Dimension(900, 600));
               
        //creation of left panel and its views
        jPanelRight = new JPanel();
        jPanelRight.setLayout(new java.awt.GridLayout(0, 1));
        jPanelRight.add(pv.getComponent());
        // to do
        pv.setDateLimits(new GregorianCalendar(2013,0,1), new GregorianCalendar(2013,3,10));
        
        jPanelRight.add(tv.getComponent());
        
        mainWindow.getContentPane().add(jPanelRight, java.awt.BorderLayout.CENTER);
        mainWindow.pack();
        
        dnd = new DnDController();
        cc  = new ChartController(model, dnd, pv.chartView, pv);
        pc  = new ParkController(dnd, pv.parkView);
        pv.parkView.parkController   = pc;
        pv.chartView.chartController = cc;
        atc = new ActivityTableController(model, tv);
        
        dnd.setDnDSourceAndDestination(cc, pc);
        
        mainWindow.setVisible(true);
    }
}
