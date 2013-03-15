
package mvc.controllers;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mvc.model.Model;
import mvc.model.Activity;
import mvc.views.ActivityTableView;
import selectedTaskModels.SelectedTaskModel;
import tableModels.ActivityTableModel;


/**
 *
 * @author Álvaro
 */
public class ActivityTableController implements ListSelectionListener{
    
    private int nameActivityCounter;
    
    private Model model;
    private SelectedTaskModel selectedTaskModel;
    private ActivityTableView tableView;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ActivityTableController(Model activityModel, SelectedTaskModel selectedTaskModel,
                                   ActivityTableView activityTableView){
        
        this.nameActivityCounter = 0;
        
        this.model             = activityModel;
        this.selectedTaskModel = selectedTaskModel;
        this.tableView         = activityTableView;
        
        
        
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
    public void valueChanged(ListSelectionEvent e)
    {
        //System.out.println("Fila seleccionada!");
        int viewRow  = tableView.activityTable.getSelectedRow();
        Activity aux = ((ActivityTableModel) tableView.activityTable.getModel()).getRowActivity(viewRow);
        if (aux == null) {
            System.out.println("No activity found");
        }
        selectedTaskModel.setNewSelectedTask(aux);
    }
    
}
