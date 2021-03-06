import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 180;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 120;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.0085;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a Wolf will be created in any given grid position.
    private static final double WOLF_CREATION_PROBABILITY = 0.002;

    // List of animals in the field.
    private List<Actor> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // Logger object
    private Logger logg;
    // Creator of files
    //private FileManager fileManager;

    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        logg = new Logger();
        //fileManager = new FileManager();

        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Wolf.class, Color.RED);

        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        int stepBefore = 1;
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            logg.populationSize(gatherStatsPopulation());
            /*if ((step - stepBefore) >= 500) {
                stepBefore = step;
                doCreateFile();
            }*/
        }
        doCreateFile();
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for new actors.
        List<Actor> newActors = new ArrayList<>();        
        // Let all actors act.
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isAlive()) {
                String stats = gatherStatsActors(actor);
                
                logg.newDeadActor(stats);
                //log.addToAges(actor.getAge());
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newActors);
        
        view.showStatus(step, field);
    }
    
    private String gatherStatsActors(Actor actor)
    {
        // Gather ALL the statistics!
        String oneLine = "";
        String age = Integer.toString(actor.getAge());
        String stepToAdd = Integer.toString(step);
        String animal = actor.getClass().toString();

        oneLine += age;
        oneLine += ("," + stepToAdd);
        oneLine += ("," + animal);
        return oneLine;
    }
    
    private String gatherStatsPopulation()
    {
        int rabbitNum = 0;
        int foxNum = 0;
        int wolfNum = 0;
        
        for(Actor actor : animals) {
            if(actor.getClass().getName().equalsIgnoreCase("rabbit")) {
                rabbitNum++;
            }
            if(actor.getClass().getName().equalsIgnoreCase("fox")) {
                foxNum++;
            }
            if(actor.getClass().getName().equalsIgnoreCase("wolf")) {
                wolfNum++;
            }
        }
        String returnString = "";
        
        returnString += Integer.toString(rabbitNum);
        returnString += "," + Integer.toString(foxNum);
        returnString += "," + Integer.toString(wolfNum);
        return returnString;
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /*public void printList() {
        logg.printAges();
    }*/

    public void doCreateFile() {
       logg.iterateOverRabbitList();
       logg.iterateOverFoxList();
       logg.iterateOverWolfList();
       logg.iterateOverPopulation();

    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear(true);
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                //if(false/*rand.nextDouble() <= GRASS_CREATION_PROBABILITY*/) {
                    Location location = new Location(row, col);
                    //Grass grass = new Grass(true, field, location);
                    //animals.add(grass);
                //}
                if(rand.nextDouble() <= WOLF_CREATION_PROBABILITY) {
                    //Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location);
                    animals.add(wolf);
                }
                else if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    //Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    //Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    animals.add(rabbit);
                }
                // else leave the location empty.

            }
        }
    }
}
