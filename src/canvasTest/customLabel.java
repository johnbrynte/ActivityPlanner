/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;

/**
 *
 * @author adur
 */
public class customLabel extends JLabel{
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Rectangle r = g.getClipBounds();
        g.setColor(Color.red);
        g.fillRect(0, 0, r.width, r.height);
        super.paint(g);
    }
}
