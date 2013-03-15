/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controllers;

import java.util.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mvc.model.Model;
import mvc.model.Activity;
import mvc.model.ActivityHolder;
import mvc.views.ActivityTableView;
import tableModels.ActivityTableModel;


/**
 *
 * @author Álvaro
 */
public class ActivityTableController implements ListSelectionListener{
    
    private int nameActivityCounter;
    
    private Model model;
    private ActivityTableView tableView;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ActivityTableController(Model activityModel, ActivityTableView activityTableView){
        
        this.nameActivityCounter = 0;
        
        this.model = activityModel;
        this.tableView = activityTableView;
        
        
        
        ////////////////////////////////////////////////////////////////////////
        // Listeners' assignment to the buttons.
        this.tableView.buttonNew.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Creates a new activity by default:
                // Customer: Cust_XX        DateSpan: 1
                // EarlistStartDay: today   EarlistEndDay: one week after.
                GregorianCalendar todaysDay = new GregorianCalendar();
                GregorianCalendar nextWeekDay = new GregorianCalendar();
                nextWeekDay.add(Calendar.DATE, 7);
                
                nameActivityCounter++;
                model.addActivity("Cust_" + nameActivityCounter, 1, todaysDay, nextWeekDay);
                
            }
        });
        
        this.tableView.buttonDelete.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if ((tableView.activityTable.getSelectedRowCount() > 0)){
                    
                    String customerName = (String) tableView.activityTable.getModel().
                            getValueAt(tableView.activityTable.getSelectedRow(), 0);

                    // Search for the activity.
                    Activity activityToDelete = model.searchActivity(customerName);

                    // If found, deletes it.
                    if (activityToDelete != null){
                        model.removeActivity(activityToDelete);
                    }

                    // The rows' listeners will be also updated, as the controler is
                    // also a observer.
                    
                }
                
                
            }
        });
        ////////////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////////////////////////////////////////
        // Listeners assignment to the table's rows and cells
       tableView.activityTable.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                tableView.activityTable.getSelectionModel()
                        .setSelectionInterval(tableView.activityTable.getSelectedRow(), 
                                              tableView.activityTable.getSelectedRow());
            }
           
       });
       /////////////////////////////////////////////////////////////////////////
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Fila seleccionada!");
        JTable table = tableView.activityTable;
        int viewRow = table.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    ArrayList<Object> row = ((ActivityTableModel) table.getModel()).getRow(modelRow);
                    String customer = (String) row.get(0);
                    int dateSpan = (int) row.get(1);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date date=null;
                    try {
                        date = df.parse((String)row.get(2));
                    } catch (ParseException ex) {
                        Logger.getLogger(ActivityTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(date);
                    GregorianCalendar earliestStartDate = (GregorianCalendar) cal;
                    try {
                        date = df.parse((String)row.get(3));
                    } catch (ParseException ex) {
                        Logger.getLogger(ActivityTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cal.setTime(date);
                    GregorianCalendar latestEndDate = (GregorianCalendar) cal;
                    ActivityHolder productionLine = new ActivityHolder(model, (String) row.get(7));
                    Activity a = new Activity( customer, dateSpan, earliestStartDate, latestEndDate, productionLine);
                    //and now just call selectedTaskModel.setnewactivity ...
                }
    }
    
}
