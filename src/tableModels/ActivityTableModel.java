package tableModels;

import mvc.model.Model;
import mvc.model.Activity;
import java.text.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Álvaro
 */
public class ActivityTableModel extends AbstractTableModel {
    
    String[] nomColum={"CUST.", "DURATION", "EARLIEST", "LATEST", "START", "END", "L"};
    ArrayList<ArrayList<Object>> data= new ArrayList();
    Model model;
    
    public ActivityTableModel(Model m){
        model = m;    
    }

    @Override
    public String getColumnName(int i) {
    // Post: Returns the column name for the given index.

        return nomColum[i];
    }

    @Override
    public int getColumnCount() {
    // Post: Returns the number of colums of the table.

        return nomColum.length;
    }

    @Override
    public int getRowCount() {
    // Post: Returns the number of rows of the table.

        return data.size();
    }

    //retornamos el elemento indicado

    @Override
    public Object getValueAt(int row, int col) {
    // Post: Returns the data value of the table for the given index.

        return data.get(row).get(col);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
    // Post: Sets  the data value of the table for the given index.

        ArrayList<Object> fila = data.get(row); // Get the selected row.
        if (!fila.get(col).equals(value)) {
            
            // Looks for the activity in the model
            Activity foundActivity = model.searchActivity((String)data.get(row).get(0));
            if (foundActivity != null){
                
                // Activity found. Let's the the changed atribute and set it
                // into the model
                switch (col){
                    case 0:
                        // Customer name
                        model.setActivityCustomer(foundActivity, (String) value);
                        break;
                    case 1:
                        // Date span
                        model.setActivityDateSpan(foundActivity, (Integer) value);
                        break;
                    case 2:
                        // Earliest date
                        editDateOfActivity(foundActivity, 2, value);
                        break;
                    case 3:
                        // Latest date
                        editDateOfActivity(foundActivity, 3, value);
                        break;
                    case 4:
                        // Start date
                        editDateOfActivity(foundActivity, 4, value);
                        break;
                    case 5:
                        // End date: calculated with the startDate and the timeSpan
                        // Not used.
                        break;
                    case 6:
                        // ProductionLine
                        model.setActivityProductionLine(foundActivity, model.searchProductionLine((String) value));
                        break;
                    default:
                        break;
                }
                
            }

        }
    }
    
    private void editDateOfActivity(Activity activity, int cellColumn, Object value){
    // Post: Edits one of the date fields of the given activity.
        
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);   // The string input must be a correct
                                            // date.
            
            GregorianCalendar newDateValue = new GregorianCalendar();
            newDateValue.setTime(dateFormat.parse((String) value));
            
            switch (cellColumn){
                case 2:
                    // Earliest date
                    model.setActivityEarliestStartDate(activity, newDateValue);
                    break;
                case 3:
                    // Latest date
                    model.setActivityLatestEndDate(activity, newDateValue);
                    break;
                case 4:
                    // Start date
                    model.setActivityStartDate(activity, newDateValue);
                case 5:
                    // End date: calculated with the startDate and the timeSpan
                    // Not used.
                    break;
                default:
                    break;
            }

        } catch (ParseException ex) {

            // We set the model with the old value so It will be repainted
            switch (cellColumn){
                case 2:
                    // Earliest date
                    model.setActivityEarliestStartDate(activity, activity.getEarliestStartDate());
                    break;
                case 3:
                    // Latest date
                    model.setActivityLatestEndDate(activity, activity.getLatestEndDate());
                    break;
                default:
                    break;
            }

        }
        
        
        
    }

    @Override
    public boolean isCellEditable (int row, int column){
    // Post: Return a boolean value, depending if you can edit the value of a
    //       cell or not.

        if (column < 4) return true; // Only the first four columns are edible.
        return false;
    }

    @Override
    public Class getColumnClass(int c) {
    // Post: Returns the class of the column given by the index.

        return getValueAt(0, c).getClass();
    }

    public void setData(Model model){
    // Post: Writes on the table all the data we want to write in the screen.

        this.data.clear();
        Activity[] allActivities = model.getActivities();
        
        for(int i = 0; i < model.getActivities().length; i++){
            try {
                
                ArrayList<Object> file= new ArrayList();
                file.add(allActivities[i].getCustomer());
                file.add(allActivities[i].getDateSpan());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                file.add(dateFormat.format(allActivities[i].getEarliestStartDate().getTime()));
                file.add(dateFormat.format(allActivities[i].getLatestEndDate().getTime()));
                if (allActivities[i].getProductionLine().getName().equals("Unscheduled")) { 
                    file.add(""); file.add(""); file.add("");
                }
                else {
                    file.add(dateFormat.format(allActivities[i].getStartDate().getTime()));
                    file.add(dateFormat.format(allActivities[i].getEndDate().getTime()));
                    file.add(allActivities[i].getProductionLine().getName());
                }
                
                this.data.add(file);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                                              "Error: failed to load the data from the model.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public ArrayList<Object> getRow(int i){
        return data.get(i);
    }
    
}
