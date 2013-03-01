/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package types;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import se.kth.csc.iprog.activityplanner.model.Activity;

/**
 *
 * @author adur
 */
public class Task extends JComponent
{
    private Activity task;
    private Color    border      = null;
    private boolean  transparent = false;
    
    public Task(Activity task)
    {
        this.task = task;
    }
    
    public void setActivity(Activity task)
    {
        this.task = task;
    }
    
    public void setBorderColor(Color c)
    {
        this.border = c;
    }
    
    public void setContentTrasnparent(boolean b)
    {
        this.transparent = b;
    }

    @Override
    public void paint(Graphics g)
    {
        if (!this.transparent) {
            double tw = this.getFont().getStringBounds(task.getCustomer(), null).getWidth();
            double th = this.getFont().getStringBounds(task.getCustomer(), null).getHeight();

            // drawing the background


            // drawing the text centered
            int ix = (int)((this.getWidth() - tw)/2);
            int iy = (int)((this.getHeight() - th)/2);
            g.drawString(task.getCustomer(), ix, iy);
        }

        // drawing the outer rectangle
        if (border != null) { g.setColor(border);      }
        else                { g.setColor(Color.black); }
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }
    
}
