/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Observer;
import java.util.Observable;

import mvc.model.Model;
import tableModels.ActivityTableModel;
import mvc.controllers.ActivityTableController;
import selectedTaskModels.SelectedTaskModel;

/**
 *
 * @author Álvaro
 */
public class ActivityTableView implements Observer {
    
    // Class Components
    
        // Panels for buttons and the table
        JPanel      panelButtons  = new JPanel();
        JScrollPane panelTable;
        // Buttons and table
        public JButton     buttonNew    = new JButton();
        public JButton     buttonDelete = new JButton();
        public JTable      activityTable;
        
        JPanel      root = new JPanel();
        
        // Model
        Model model;
        SelectedTaskModel selectedTaskModel;
        public ActivityTableController activityTableController;

    // Constructor
    @SuppressWarnings("LeakingThisInConstructor")
    public ActivityTableView(Model model, SelectedTaskModel selectedTaskModel) {
        
        // Sets the model
        this.model = model;
        this.selectedTaskModel = selectedTaskModel;
        
        // Init the components
        initComponents();
        
        // Adds itself as a observer.
        model.addObserver(this);
        selectedTaskModel.addObserver(this);
        
    }
    
    // Set the panel with the buttons. Layout: Spring Layout
    private void setButtons(){
        
        // Panel for the buttons. Layout: Grid Layout
        SpringLayout layoutPanelButtons = new SpringLayout();
        panelButtons.setLayout(layoutPanelButtons);
        panelButtons.setMinimumSize(new Dimension(80, 80)); // Set min width
        panelButtons.setMaximumSize(new Dimension(80, 80)); // Set max width
        panelButtons.setPreferredSize(new Dimension(80, 80));
            
        // Creation of the buttons
        buttonNew.setText("New");
        buttonDelete.setText("Delete");

            // We set the new button width to the other button width.
            buttonNew.setMinimumSize(buttonDelete.getMaximumSize());
            buttonNew.setPreferredSize(buttonDelete.getMaximumSize());

        // Attachment to the JPanel 
        panelButtons.add(buttonNew);
        panelButtons.add(buttonDelete);
        
        // Alingment of the buttons in the middle.
        layoutPanelButtons.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonNew, 0, SpringLayout.HORIZONTAL_CENTER, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.VERTICAL_CENTER, buttonNew, 35, SpringLayout.NORTH, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonDelete, 0, SpringLayout.HORIZONTAL_CENTER, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.VERTICAL_CENTER, buttonDelete, 20, SpringLayout.SOUTH, buttonNew);
    }
    
    @SuppressWarnings("UnusedAssignment")
    // Set the panel with the table. Layout: no  layout.
    private void setTable(){
        
        // Creation of the table's model and fill it with the current data.
        ActivityTableModel tableModel = new ActivityTableModel(model);
        tableModel.setData(model);
        
        // Creation of the table
        activityTable = new JTable(tableModel);
        
        ////////////////////////////////////////////////////////////////////////
        // Properties of the table.
            // Dimension
            activityTable.setPreferredScrollableViewportSize(new Dimension(500,100));
            activityTable.setShowVerticalLines(true);
            // Selection mode & backgoung color.
            activityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            activityTable.setSelectionBackground(Color.yellow);
            // Terminate edition on focusLost so we can save the data.
            activityTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
            // Row sorting.
            activityTable.setAutoCreateRowSorter(true);
            // Resize mode: the table will grow if we resize the window.
            activityTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
        ////////////////////////////////////////////////////////////////////////
        
        // Creation of the cell renderers to center the cells.
        DefaultTableCellRenderer whiteCenterRenderer = new DefaultTableCellRenderer();
        whiteCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer yellowCenterRenderer = new DefaultTableCellRenderer();
        yellowCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        yellowCenterRenderer.setBackground(Color.ORANGE);
        
        ////////////////////////////////////////////////////////////////////////
        // Creation of the header and its properties.
        JTableHeader header = new JTableHeader();
        header = activityTable.getTableHeader();
        header.setReorderingAllowed(false);         // We forbid to reorder colums.
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                    // Set cursor to show more
                                                    // explicitely that you can
                                                    // reorder rows by the columns.
        ////////////////////////////////////////////////////////////////////////

        // Setting the size of the columns
        for (int i=0; i < activityTable.getColumnCount(); i++){
            
            // Get the column and set its properties
            TableColumn col = activityTable.getColumnModel().getColumn(i);
            if (i <= 3) col.setCellRenderer(whiteCenterRenderer);
            else col.setCellRenderer(yellowCenterRenderer);
            switch(i){
                case 0: col.setPreferredWidth(50); break;
                case 1: col.setPreferredWidth(80); break;
                case 2: col.setPreferredWidth(80); break;
                case 3: col.setPreferredWidth(80); break;
                case 4: col.setPreferredWidth(50); break;
                case 5: col.setPreferredWidth(40); break;
                case 6: col.setPreferredWidth(20); break;
            }
        }
        
        // Adding the header & table to the panel
        panelTable = new JScrollPane(activityTable);
        panelTable.add(activityTable.getTableHeader());
        
        
        
    }

    // Init Components                     
    private void initComponents() {
        
        // Seting frame layout (BorderLayout)
        root.setLayout(new BorderLayout());
        
        // Setting the buttons and the table, and adding them to the frame
        setButtons();
        root.add(panelButtons, BorderLayout.LINE_START);
        setTable();
        root.add(panelTable, BorderLayout.CENTER);
            
    }
    
    public JPanel getComponent()
    {
        return root;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // the behavior separation depending on the root observable is necessary
        // because otherwise a infinite loop is generated:
        // update -> selectRow -> update -> selectRow -> update -> ...........
        if (o instanceof Model) {
            // We refill the table with the data.
            ((ActivityTableModel) activityTable.getModel()).setData(model);

            // We tell the table that the data has been changed. We can't use 
            // fireTableRowsInserted since we don't know the exact index.
            try{
                ((AbstractTableModel) activityTable.getModel()).fireTableDataChanged();
            } catch (IndexOutOfBoundsException ex){
                // This exception rises when you delete the last activity and the
                // model gets empty.
            }
        }
        else {
            //highlight the selected row !!! so when the selectedTask changes, it will be highlighted!!
            int row = ((ActivityTableModel) activityTable.getModel()).getRowWithActivity(selectedTaskModel.selectedTask);
            if (row >= 0) {
                activityTable.setRowSelectionInterval(row, row);
            }
            activityTable.repaint();
            root.repaint();
        }
    }
}
