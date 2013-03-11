package project;

import java.util.GregorianCalendar;
import mvc.model.Model;

public class Main
{
    
    public static void main(String args[])
    {
        // getting the model
        Model m = new Model();
        
        // creating the GUI
        new ActivityPlanner(m);
        
        m.addActivity("Monstropolis", 5,
                new GregorianCalendar(2013, 0, 1),
                new GregorianCalendar(2013, 0, 10));
        
        m.addProductionLine("Monsters Inc.");
        m.addProductionLine("Aperture Science");
        m.addProductionLine("Skynet");
    }
    
}
