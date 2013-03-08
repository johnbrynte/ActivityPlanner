package john.planningchart;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;
import se.kth.csc.iprog.activityplanner.model.Activity;

/**
 * TEMPORARY VERSION OF THE TASK OBJECT
 * ONLY FOR TESTING PURPOSES
 * @author John
 */
public class Task extends JComponent {
    
	private static final long serialVersionUID = 1L;
	
	private Activity activity;
	private PlanningView view;
    private boolean  transparent = false;
    
    private static Color border      = new Color(100,100,100);
    private static Color tooEarly    = new Color(100,100,255);
    private static Color tooLate     = new Color(255,100,100);
    private static Color normal      = new Color(220,220,220);
    
    private static final long DAY_IN_MILLIS = (1000 * 60 * 60 * 24);
    
    public Task(PlanningView view) {
    	this.view = view;
    }
    
    /**
     * Associates a certain activity in the Model to this component from witch
     * data will be gathered to render this object.
     * @param task 
     */
    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }
    
    public Activity getActivity() {
    	return activity;
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
    public void setContentTransparent(boolean b)
    {
        this.transparent = b;
    }

    @Override
    public void paint(Graphics g) {
    	// Print with the normal color
    	g.setColor(normal);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	// Check if the task is out of its boundaries
    	int dateX = (int) (view.cellWidth
    			* (activity.getEarliestStartDate().getTimeInMillis()
    					- view.startDate.getTimeInMillis()) / DAY_IN_MILLIS);
    	// Check if task is to early
    	if(getLocation().x < dateX) {
    		g.setColor(tooEarly);
    		g.fillRect(0, 0, dateX - getLocation().x, this.getHeight());
    	}
    	
    	dateX = (int) (view.cellWidth
    			* (activity.getLatestEndDate().getTimeInMillis()
    					- view.startDate.getTimeInMillis()) / DAY_IN_MILLIS);
    	// Check if task is to late
    	if(getLocation().x + getWidth() > dateX) {
    		g.setColor(tooLate);
    		g.fillRect(
    				getWidth() - (getLocation().x + getWidth() - dateX), 0,
    				getLocation().x + getWidth() - dateX, this.getHeight());
    	}
    	
    	// Center the text
    	FontMetrics metrics = g.getFontMetrics(this.getFont());
    	int textX = (this.getWidth() - metrics.stringWidth(activity.getCustomer())) / 2;
    	int textY = (this.getHeight() + metrics.getHeight()) / 2;
    	
    	g.setColor(Color.black);
        g.drawString(activity.getCustomer(), textX, textY);
        
        // Draw border
        g.setColor(border);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
 
    
    /**************************************************************************/
    /** DRAG AND DROP *********************************************************/
    /**************************************************************************/
    
    
}
