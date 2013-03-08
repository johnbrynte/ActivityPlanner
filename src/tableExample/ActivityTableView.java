/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableExample;

import java.awt.*;
import java.util.Observable;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Observer;
import se.kth.csc.iprog.activityplanner.model.Model;

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
        JButton     buttonNew    = new JButton();
        JButton     buttonDelete = new JButton();
        JTable      activityTable;
        
        JPanel      root = new JPanel();
        
        // Model
        Model m = new Model();
        ActivityTableController contrl;

    // Constructor
    @SuppressWarnings("LeakingThisInConstructor")
    public ActivityTableView() {
        
        // Adds itself as a observer.
        m.addObserver(this);
        
        // Init the components
        initComponents();
        
        // PUT AFTER IN THE SUPER_CONTROLLER
        contrl = new ActivityTableController(m, this);
        
    }
    
    // Set the panel with the buttons. Layout: Spring Layout
    private void setButtons(){
        
        // Panel for the buttons. Layout: Box Layout
        SpringLayout layoutPanelButtons = new SpringLayout();
        panelButtons.setLayout(layoutPanelButtons);
        panelButtons.setLayout(layoutPanelButtons);
        panelButtons.setMinimumSize(new Dimension(80, 80)); // Set min width
        panelButtons.setMaximumSize(new Dimension(80, 80)); // Set max width
        panelButtons.setPreferredSize(new Dimension(80, 80));
            
        // Creation of the buttons
        buttonNew.setText("New");
        buttonDelete.setText("Delete");

            // We set the new button width to the other button width.
            buttonNew.setMaximumSize(buttonDelete.getMaximumSize());

        // Attachment to the JPanel 
        panelButtons.add(buttonNew);
        panelButtons.add(buttonDelete);
        
        // Alingment of the buttons in the middle.
        layoutPanelButtons.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonNew, 0, SpringLayout.HORIZONTAL_CENTER, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.VERTICAL_CENTER, buttonNew, -15, SpringLayout.VERTICAL_CENTER, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonDelete, 0, SpringLayout.HORIZONTAL_CENTER, panelButtons);
        layoutPanelButtons.putConstraint(SpringLayout.VERTICAL_CENTER, buttonDelete, 15, SpringLayout.VERTICAL_CENTER, panelButtons);
    }
    
    @SuppressWarnings("UnusedAssignment")
    // Set the panel with the table. Layout: no  layout.
    private void setTable(){
        
        // Creation of the table's model and fill it with the current data.
        MyTableModel model = new MyTableModel(m);
        model.setData(m);
        
        // Creation of the table
        activityTable = new JTable(model);
        
        ////////////////////////////////////////////////////////////////////////
        // Properties of the table.
            // Dimension
            activityTable.setPreferredScrollableViewportSize(new Dimension(370,200));
            activityTable.setShowVerticalLines(true);
            // Selection mode
            activityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // Terminate edition on focusLost so we can save the data.
            activityTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
            // Row sorting.
            activityTable.setAutoCreateRowSorter(true);
            // Resize mode: the table will grow if we resize the window.
            activityTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
        ////////////////////////////////////////////////////////////////////////
        
        // Creation of the cell renderer to center the cells.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Creation of the header and its properties.
        JTableHeader header = new JTableHeader();
        header = activityTable.getTableHeader();
        header.setReorderingAllowed(false);         // We forbid to reorder colums.
        header.setDefaultRenderer(centerRenderer);  // Centered headers.

        // Setting the size of the columns
        for (int i=0; i < activityTable.getColumnCount(); i++){
            
            // Get the column
            TableColumn col = activityTable.getColumnModel().getColumn(i);
            col.setCellRenderer(centerRenderer); // Set center alignment.
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

        // Setting close operation
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Seting frame layout (BorderLayout)
        //root.getContentPane().setLayout(new BorderLayout());
        
        // Setting the buttons and the table, and adding them to the frame
        setButtons();
        root.add(panelButtons, BorderLayout.LINE_START);
        setTable();
        root.add(panelTable, BorderLayout.CENTER);
            
        // Packing.
        //pack();
    }
    
    public JPanel getComponent()
    {
        return root;
    }

    
    @Override
    // OBERSERVER IMPLEMENTATION: We redraw the table.
    public void update(Observable o, Object arg) {
        
        // We refill the table with the data.
        ((MyTableModel) activityTable.getModel()).setData(m);
        
        // We tell the visitor
        ((AbstractTableModel) activityTable.getModel()).fireTableDataChanged();
    }
}
