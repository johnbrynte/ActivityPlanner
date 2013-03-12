package mvc.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.GregorianCalendar;
import javax.swing.JComponent;
import mvc.model.Activity;

public class Task extends JComponent
{
    private Activity task;
    private PlanningView view;
    private boolean  transparent = false;
    
    private static Color border      = null;
    private static Color tooEarly    = Color.GRAY;
    private static Color tooLate     = Color.GRAY;
    private static Color normal      = Color.GREEN;
    private static Color textC       = Color.BLACK;
    
    private static int tsize = 25;
    
    private static final long MILIS_DAY = (1000 * 60 * 60 * 24);
    
    public Task(PlanningView view) {
        this.view = view;
    }
    
    /**
     * Associates a certain activity in the Model to this component from witch
     * data will be gathered to render this object.
     * @param task 
     */
    public void setActivity(Activity task)
    {
        this.task = task;
    }
    
    public Activity getActivity() {
        return task;
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
    
    public static void setTextColor(Color c)
    {
        textC = c;
    }
    
    /**
     * Makes the component to be partially invisible. When set to true, only
     * the outer border will be rendered. Useful when dragging.
     * @param b 
     */
    public void setContentTransparent(boolean b)
    {
        this.transparent = b;
    }

    @Override
    public void paint(Graphics g)
    {
        if (!this.transparent) {
            // drawing the background
            int dw = this.getWidth()/task.getDateSpan();
            GregorianCalendar end = task.getEndDate();
            
            if (task.getEarliestStartDate().after(task.getStartDate())) {
                int diff1 = (int)( (task.getEarliestStartDate().getTimeInMillis() - task.getStartDate().getTimeInMillis())/MILIS_DAY);
                
                // paint with different color
                g.setColor(tooEarly);
                g.fillRect(0, 0, dw * diff1, this.getHeight() - 1);
                
                // check the remaining days
                if (end.after(task.getLatestEndDate())) {
                    int diff2 = (int)((end.getTimeInMillis() - task.getLatestEndDate().getTimeInMillis())/MILIS_DAY);
                    
                    g.setColor(tooLate);
                    g.fillRect(dw * (task.getDateSpan() - diff2), 0, dw * diff2, this.getHeight() - 1);
                    
                    g.setColor(normal);
                    g.fillRect(dw * diff1, 0, dw * (task.getDateSpan() - diff1 - diff2), this.getHeight() - 1);
                }
                else {
                    g.setColor(normal);
                    g.fillRect(dw * diff1, 0, dw * (task.getDateSpan() - diff1), this.getHeight() - 1);
                }
            }
            else {
                // check the remaining days
                if (end.after(task.getLatestEndDate())) {
                    int diff2 = (int)((end.getTimeInMillis() - task.getLatestEndDate().getTimeInMillis())/MILIS_DAY);
                    
                    g.setColor(tooLate);
                    g.fillRect(dw * (task.getDateSpan() - diff2), 0, dw * diff2, this.getHeight() - 1);
                    
                    g.setColor(normal);
                    g.fillRect(0, 0, dw * (task.getDateSpan() - diff2), this.getHeight() - 1);
                }
                else {
                    g.setColor(normal);
                    g.fillRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
                }
            }

            // getting font dimensions
            g.setColor(textC);
            Font font = new Font("Helvetica", Font.PLAIN, tsize);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(task.getCustomer(), g);
            
            // adapting text size
            while (this.getWidth() <= r.getWidth() || this.getHeight() <= r.getHeight()) {
                --tsize;
                font = new Font("Helvetica", Font.PLAIN, tsize);
                g.setFont(font);
                fm   = g.getFontMetrics();
                r    = fm.getStringBounds(task.getCustomer(), g);
            }
            
            // drawing the text
            int ix = (int)((this.getWidth() - r.getWidth())/2);
            int iy = (int)((this.getHeight()/2) + (r.getHeight()/4));
            
            g.drawString(task.getCustomer(), ix, iy);
            
            /*System.out.println("Text value: " + task.getCustomer());
            System.out.println("Text size: " + tsize);
            System.out.println("Text width: " + r.getWidth());
            System.out.println("Text height: " + r.getHeight());
            System.out.println("Text draw init x: " + ix);
            System.out.println("Text draw init y: " + iy);
            System.out.println("-----------------------------------");*/
        }
        
        // drawing the outer rectangle
        if (border != null) { g.setColor(border);      }
        else                { g.setColor(Color.black); }
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        
    }

    @Override
    public String toString()
    {
        return task.getCustomer();
    }
    
}
