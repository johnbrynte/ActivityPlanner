
package selectedTaskModels;

import java.util.Observable;
import mvc.model.Activity;

/**
 *
 * @author David
 */
public class SelectedTaskModel extends Observable{
    
    public Activity selectedTask = null;
    
    public SelectedTaskModel() {}
    
    public void setNewSelectedTask(Activity t)
    {
        selectedTask = t;
        
        //notify Observers
        setChanged();
        super.notifyObservers();
    }
}
