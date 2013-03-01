package types;

import java.awt.Color;
import java.awt.Graphics;
import java.util.GregorianCalendar;
import javax.swing.JComponent;
import se.kth.csc.iprog.activityplanner.model.Activity;

public class Task extends JComponent
{
    private Activity task;
    private boolean  transparent = false;
    private static Color border      = null;
    private static Color tooEarly    = Color.GRAY;
    private static Color tooLate     = Color.GRAY;
    private static Color normal      = Color.GREEN;
    
    /**
     * Associates a certain activity in the Model to this component from witch
     * data will be gathered to render this object.
     * @param task 
     */
    public void setActivity(Activity task)
    {
        this.task = task;
    }
    
    /**
     * Set the border color for all instances of this custom JComponent.
     * @param c 
     */
    public static void setBorderColor(Color c)
    {
        border = c;
    }
    
    /**
     * Set the rendering background color for when the starting date is
     * previous to the earliest starting date specified in the activity.
     * @param c 
     */
    public static void setEarlyColor(Color c)
    {
        tooEarly = c;
    }
    
    /**
     * Set the rendering background color for when the end date is
     * later to the lates date specified in the activity.
     * @param c 
     */
    public static void setLateColor(Color c)
    {
        tooLate = c;
    }
    
    /**
     * Set the rendering background color for when the activity period is
     * between the earliest start and latest end.
     * @param c 
     */
    public static void setNormalColor(Color c)
    {
        normal = c;
    }
    
    /**
     * Makes the component to be partially invisible. When set to true, only
     * the outer border will be rendered. Useful when dragging.
     * @param b 
     */
    public void setContentTrasnparent(boolean b)
    {
        this.transparent = b;
    }

    @Override
    public void paint(Graphics g)
    {
        if (!this.transparent) {
            // drawing the background
            // the activity start date must be modified during the drag so it
            // is available when dropping to paint the different backgrounds
            int dw = this.getWidth()/task.getDateSpan();
            GregorianCalendar end = task.getEndDate();
            
            if (task.getEarliestStartDate().after(task.getStartDate())) {
                int diff1 = (int)( (task.getEarliestStartDate().getTimeInMillis() - task.getStartDate().getTimeInMillis()) / (1000 * 60 * 60 * 24) );
                
                // paint with different color
                g.setColor(tooEarly);
                g.fillRect(0, 0, dw * diff1, this.getHeight());
                
                // check the remaining days
                if (end.after(task.getLatestEndDate())) {
                    int diff2 = (int)((end.getTimeInMillis() - task.getLatestEndDate().getTimeInMillis())/(1000 * 60 * 60 * 24));
                    
                    g.setColor(tooLate);
                    g.fillRect(dw * (task.getDateSpan() - diff2), 0, dw * diff2, this.getHeight());
                    
                    g.setColor(normal);
                    g.fillRect(dw * diff1, 0, dw * (task.getDateSpan() - diff1 - diff2), this.getHeight());
                }
                else {
                    g.setColor(normal);
                    g.fillRect(dw * diff1, 0, dw * (task.getDateSpan() - diff1), this.getHeight());
                }
            }
            else {
                // check the remaining days
                if (end.after(task.getLatestEndDate())) {
                    int diff2 = (int)((end.getTimeInMillis() - task.getLatestEndDate().getTimeInMillis())/(1000 * 60 * 60 * 24));
                    
                    g.setColor(tooLate);
                    g.fillRect(dw * (task.getDateSpan() - diff2), 0, dw * diff2, this.getHeight());
                    
                    g.setColor(normal);
                    g.fillRect(0, 0, dw * (task.getDateSpan() - diff2), this.getHeight());
                }
                else {
                    g.setColor(normal);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            }

            // drawing the text centered
            double tw = this.getFont().getStringBounds(task.getCustomer(), null).getWidth();
            double th = this.getFont().getStringBounds(task.getCustomer(), null).getHeight();
            int    ix = (int)((this.getWidth() - tw)/2);
            int    iy = (int)((this.getHeight() - th)/2);
            g.drawString(task.getCustomer(), ix, iy);
        }

        // drawing the outer rectangle
        if (border != null) { g.setColor(border);      }
        else                { g.setColor(Color.black); }
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }
    
}
