package Project;

import se.kth.csc.iprog.activityplanner.model.Model;
import templateTests.View;

public class Main
{
    
    public static void main(String args[])
    {
        // getting the model
        Model m = new Model();
        
        // creating the GUI
        new View(m);
    }
    
}
