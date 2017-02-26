import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ThomasSTodal, VinhKTran, KristofferMartinsen
 */
public class Wolf extends Actor
{

    // Characteristics shared by all wolves (class variables).
    
    // The age at which a wolf can start to breed.
    private static final int BREEDING_AGE = 700;
    // The age to which a wolf can live.
    private static final int MAX_AGE = 2900;
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.008;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single fox. In effect, this is the
    // number of steps a wolf can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 160;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a wolf can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 5;
    // Whether or not the actor 'walks'
    private static final boolean STATIC_ACTOR = false;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The wolf's age.
    private int age;
    // The wolf's food level, which is increased by eating foxes.
    private int foodLevel;

    /**
     * Create a wolf. A wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the wolf will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location)
    {
        super(field, location, STATIC_ACTOR);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(FOX_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = FOX_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the wolf does most of the time: it hunts for
     * foxes. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newWolves A list to return newly born foxes.
     */
    @Override
    public void act(List<Actor> newWolves)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newWolves);            
            // Move towards a source of food if found.
            Location newLocation = null;//eatFood(findFood());
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Increase the age. This could result in the wolf's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead(STATIC_ACTOR);
        }
    }

    @Override
    public int getAge() {
        return this.age;
    }
    
    /**
     * Make this wolf more hungry. This could result in the wolf's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead(STATIC_ACTOR);
        }
    }
    
    /**
     * Look for foxes adjacent to the current location.
     * Only the first live fox is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocationsAll(getLocation(), 15);
        List<Location> otherActors = new LinkedList<>();
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where, false);
            if(actor instanceof Fox) {
                otherActors.add(where);
            }
            else if(actor instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) actor;
                if(rabbit.isAlive()) {
                    rabbit.setDead(false);
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return getDir(findClosest(otherActors, getLocation(), 15));
    }
    
    /**
     * 
     * @param location
     * @return 
     */
    private Location eatFood(Location location)
    {
        if(location != null) {
            Field field = getField();
            Object actor = field.getObjectAt(location, false);
            if(actor instanceof Fox) {
                Fox fox = (Fox) actor;
                if(fox.isAlive()) { 
                    fox.setDead(false);
                    foodLevel = FOX_FOOD_VALUE;
                    return location;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this wolf is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newWolves A list to return newly born foxes.
     */
    private void giveBirth(List<Actor> newWolves)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation(), 1);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newWolves.add(young);
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

    /**
     * A wolf can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
}
