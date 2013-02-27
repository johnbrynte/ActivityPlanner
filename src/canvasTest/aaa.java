/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasTest;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author adur
 */
public class aaa
{
    
    private static customCanvas canvas1;
    private static javax.swing.JScrollPane jScrollPane1;
    
    public aaa()
    {
        
    }
    
    public static void main(String args[])
    {
    
        //x = new xxx();
        //x.setVisible(true);
        initComponents();
    }
    
    private static void initComponents() {

        JFrame fr = new JFrame("xxx");
        jScrollPane1 = new JScrollPane();
        canvas1 = new customCanvas();

        fr.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        canvas1.setMaximumSize(new Dimension(1200, 300));
        canvas1.setPreferredSize(new Dimension(1200, 300));
        jScrollPane1.setViewportView(canvas1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(fr.getContentPane());
        fr.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        fr.pack();
        
        fr.setVisible(true);
    }
}
