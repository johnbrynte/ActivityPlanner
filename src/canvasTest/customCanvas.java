/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasTest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author adur
 */
public class customCanvas extends Canvas
{
    @Override
    public void paint(Graphics g)
    {
        System.out.println("Entered");
        g.setColor(Color.RED);
        g.fillRect(100, 100, 200, 200);
    }
}
