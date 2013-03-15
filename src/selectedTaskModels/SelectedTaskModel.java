/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selectedTaskModels;

import java.util.Observable;
import mvc.views.Task;

/**
 *
 * @author David
 */
public class SelectedTaskModel extends Observable{
    
    public Task selectedTask;
    
    public SelectedTaskModel(){}
    
    public void setNewSelectedTask(Task t){
        if(selectedTask != null)
            selectedTask.selected(false); //the previous tasks is not selected anymore
        selectedTask = t;
        selectedTask.selected(true);
        
        //notify Observers
        setChanged();
        super.notifyObservers();
    }
}
