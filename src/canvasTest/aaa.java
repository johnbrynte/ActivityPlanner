/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import se.kth.csc.iprog.activityplanner.model.Activity;
import types.Task;

/**
 *
 * @author adur
 */
public class aaa
{
    
    private static customCanvas canvas1;
    private static JScrollPane jScrollPane1;
    private static JLabel jLabel1;
    private static JLayeredPane jLayerPane1;
    
    private static Activity a;
    private static Task t;
    
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

        JFrame fr    = new JFrame("xxx");
        jScrollPane1 = new JScrollPane();
        jLayerPane1  = new JLayeredPane();
        canvas1      = new customCanvas();
        jLabel1      = new JLabel("Some Text");
        
        GregorianCalendar calendar = new GregorianCalendar();
        GregorianCalendar calendar1 = calendar;
        calendar1.add(Calendar.MONTH, 2);
        a            = new Activity("test", 3, calendar, calendar1, null);
        t            = new Task();
        t.setActivity(a);
        t.setBounds(50, 50, 200, 50);
        Task.setEarlyColor(Color.red);
        Task.setBorderColor(Color.black);
        Task.setLateColor(Color.GRAY);
        Task.setNormalColor(Color.GREEN);

        fr.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        canvas1.setMaximumSize(new Dimension(1200, 300));
        canvas1.setPreferredSize(new Dimension(1200, 300));
        canvas1.setBounds(150, 150, 1000, 1000);
        canvas1.addMouseListener(mouseListener);
        //jScrollPane1.setViewportView(canvas1);
        
        //jLabel1.setOpaque(true);
        jLabel1.setBounds(150, 150, 100, 100);
        //jLabel1.setText("Some text");
        //canvas1.add(jLabel1);
        
        jLayerPane1.setPreferredSize(new Dimension(600, 300));
        jLayerPane1.add(canvas1, new Integer(1));
        jLayerPane1.add(jLabel1, new Integer(2));
        jScrollPane1.add(jLayerPane1);
        //jScrollPane1.setViewportView(jLayerPane1);

        /*javax.swing.GroupLayout layout = new javax.swing.GroupLayout(fr.getContentPane());
        fr.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );*/
        jScrollPane1.setPreferredSize(new Dimension(600, 300));
        fr.setPreferredSize(new Dimension(600, 300));
        //fr.add(jScrollPane1);
        fr.add(t);
        fr.add(jLayerPane1);

        fr.pack();
        
        fr.setVisible(true);
    }
    
    static MouseListener mouseListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("[" + e.getX() + ", " + e.getY() + "]");
        }
    };
}
