import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public class Rabbit extends Actor
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 180;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 1000;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.13;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single grass
    private static final int GRASS_FOOD_VALUE = 2;
    // Whether or not the grass 'walks'
    private static final boolean STATIC_ACTOR = false;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;
    // The rabbit's food level
    private int foodLevel;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location, STATIC_ACTOR);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        } else {
            age = 0;
            foodLevel = GRASS_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newRabbits);
            // Try to move into a free location.
            Location newLocation = findFood();
            if(newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }

            if(newLocation != null) {
                setLocation(newLocation, STATIC_ACTOR);
            }
            else {
                // Overcrowding.
                setDead(STATIC_ACTOR);
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead(STATIC_ACTOR);
        }
    }

    /**
     * increment hunger
     */
    private void incrementHunger() {
        //foodLevel--;
        if(foodLevel <= 0) {
            setDead(STATIC_ACTOR);
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
                    System.out.println(Integer.toString(foodLevel));
        while (it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where, true);
            if(actor instanceof Grass) {
                Grass grass = (Grass) actor;
                if(grass.isEdible()) {
                    grass.eat();
                    foodLevel = GRASS_FOOD_VALUE;
                    if(foodLevel!=2) System.out.println(Integer.toString(foodLevel));
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
