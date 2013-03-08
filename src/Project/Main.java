package Project;

import mvc.model.Model;

public class Main
{
    
    public static void main(String args[])
    {
        // getting the model
        Model m = new Model();
        
        // creating the GUI
        new ActivityPlanner(m);
    }
    
}
