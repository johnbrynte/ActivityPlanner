package project;

import mvc.model.Model;
import selectedTaskModels.SelectedTaskModel;

public class Main
{
    
    public static void main(String args[])
    {
        // getting the model
        Model m                     = new Model();
        SelectedTaskModel taskModel = new SelectedTaskModel();
        
        // creating the GUI
        new ActivityPlanner(m, taskModel);
    }
    
}
