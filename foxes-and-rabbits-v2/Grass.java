import java.util.List;
import java.util.Random;

/**
 * Om gress
 * Dør etter 6 mnd
 * Blir spist av kanin
 * Tar 1 år å gro tilbake
 */

/**
 *
 * @author ThomasSTodal, VinhKTran, KristofferMartinsen
 */
public class Grass extends Actor
{

    /*
        GENERAL VARIABLES FOR GRASS
     */
    // Amount of steps before dying
    private static final int MAX_AGE = 180;
    // Maximum possible height for grass
    private static final int MAX_HEIGHT = 3;
    // Probability that grass will doSpread
    private static final double GROW_RATE = 1;
    // Whether or not the grass 'walks'
    private static final boolean STATIC_ACTOR = true;



    private static final Random rand = Randomizer.getRandom();

    /*
        INDIVIDUAL VARIABLES FOR GRASS
     */
    // The age of the grass
    private int age;
    // Height of the grass
    private int height;


    public Grass(boolean randAge, Field field, Location location)
    {
        super(field, location, STATIC_ACTOR);
        age = 0;
        height = 2;
        if(randAge) {
            age = rand.nextInt(MAX_AGE);
            height = (age % MAX_HEIGHT) + 1;
        }
    }

    /**
     * 
     * @param newGrass 
     */
    @Override
    public void act(List<Actor> newGrass)
    {
        int before = height;
        incrementAge();
        if(isAlive()) {
            doGrow(newGrass);
        }
        /*if(height > before) {
            System.out.print("grass grew\n");    //TODO
        }*/
    }
    
    /**
     * 
     * @param isStatic 
     */
    @Override
    public void setDead(boolean isStatic)
    {
        height = 0;
        age = 0;
    }

    /**
     * Increase the age of the grass
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            this.setDead(STATIC_ACTOR);
        }
    }
    
    public boolean isEdible()
    {
        //System.out.println("Grass is edible");   //TODO
        return height > 1;
    }
    
    /**
     * Decrease height when grass is eaten
     */
    public void eat()
    {
        height = height - 2;
    }
    
    /**
     * 
     * @param newGrass not used
     */
    private void doGrow(List<Actor> newGrass) {
        if((rand.nextDouble() <= GROW_RATE) && (height < MAX_HEIGHT)) {
            height++;
        }
    }
}
