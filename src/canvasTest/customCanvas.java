/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasTest;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author adur
 */
public class customCanvas extends JComponent
{
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(0, 0, 200, 200);
    }
}
