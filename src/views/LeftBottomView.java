/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class LeftBottomView {
    
    private JButton jButtonAdd;
    private JButton jButtonRemove;
    private JPanel jPanelLeftBottom;
    
    private final int LEFTBUTTONSPAN = 10;
    private final int TOPBUTTONSPAN = 90;
    private final int SPANBETWEENBUTTONS = 30;
    private final int BUTTONSHEIGHT = 80;
    
    public LeftBottomView(JPanel jPanelLeft){
        jPanelLeftBottom = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        
        jPanelLeftBottom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonAdd.setText("Add");
        jPanelLeftBottom.add(jButtonAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(LEFTBUTTONSPAN, TOPBUTTONSPAN, BUTTONSHEIGHT, -1));

        jButtonRemove.setText("Remove");
        jPanelLeftBottom.add(jButtonRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(LEFTBUTTONSPAN, TOPBUTTONSPAN+SPANBETWEENBUTTONS, BUTTONSHEIGHT, -1));

        jPanelLeft.add(jPanelLeftBottom);
    }
}
