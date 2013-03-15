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
import mvc.controllers.ProductionLineController;
import mvc.views.ActivityTableView;
import mvc.views.PlanningView;
import mvc.model.Model;
import selectedTaskModels.SelectedTaskModel;

/**
 *
 * @author David
 */
public class ActivityPlanner
{

    private JPanel jPanelRight;
    private JFrame mainWindow;
    
    
    public PlanningView planningView;
    public ActivityTableView tableView;
    
    private ChartController          chartController;
    private ParkController           parkController;
    private DnDController            dndController;
    private ActivityTableController  tableController;
    private ProductionLineController plTableController;
    
    private Model model;
    private SelectedTaskModel selectedTaskModel;
    
    public ActivityPlanner(Model model)
    {
        this.model = model;
        selectedTaskModel = new SelectedTaskModel();
        loadGUI();
    }
    
    private void loadGUI()
    {
        planningView  = new PlanningView(model, selectedTaskModel);
        tableView  = new ActivityTableView(model, selectedTaskModel);

        mainWindow = new JFrame();
        
        mainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new java.awt.Dimension(400, 400));
        mainWindow.setPreferredSize(new java.awt.Dimension(900, 600));
               
        //creation of left panel and its views
        jPanelRight = new JPanel();
        jPanelRight.setLayout(new java.awt.GridLayout(0, 1));
        jPanelRight.add(planningView.getComponent());
        // to do
        planningView.setDateLimits(new GregorianCalendar(2013,0,1), new GregorianCalendar(2013,3,10));
        
        jPanelRight.add(tableView.getComponent());
        
        mainWindow.getContentPane().add(jPanelRight, java.awt.BorderLayout.CENTER);
        mainWindow.pack();
        
        dndController    = new DnDController();
        chartController  = new ChartController(model, selectedTaskModel, dndController, planningView.chartView, planningView);
        parkController   = new ParkController(dndController, planningView.parkView, selectedTaskModel);
        tableController  = new ActivityTableController(model, tableView);
        plTableController = new ProductionLineController(
                model, planningView.chartView.productionLineView,
                planningView.productionLineControlView);
        
        planningView.parkView.parkController   = parkController;
        planningView.chartView.chartController = chartController;
        
        dndController.setDnDSourceAndDestination(chartController, parkController);
        
        mainWindow.setVisible(true);
    }
}
