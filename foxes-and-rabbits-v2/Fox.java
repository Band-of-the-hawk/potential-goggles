import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Fox extends Actor
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 310;
    // The age to which a fox can live.
    private static final int MAX_AGE = 1090;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.037;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 40;
    
    private static final int MAX_FOOD_VALUE = 120;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The fox's age.
    private int age;
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location)
    {
        super(field, location, false);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newFoxes A list to return newly born foxes.
     */
    @Override
    public void act(List<Actor> newFoxes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);            
            // Move towards a source of food if found.
            Location newLocation = eatFood(findFood());
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation, false);
            }
            else {
                // Overcrowding.
                setDead(false);
            }
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead(false);
        }
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead(false);
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocationsAll(getLocation(), 7);
        List<Location> otherActors = new LinkedList<>();
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where, false);
            if(actor instanceof Rabbit) { 
                if(((Rabbit) actor).isAlive()) {
                    otherActors.add(where);
                }
            }
        }
        Location direction = null;
        int i = 1;
        boolean locLegal = false;
        do {
            i++;
            direction = getDir(findClosestV3(otherActors, getLocation(), 7));
            locLegal = field.isLocationLegal(direction);
        } while(!locLegal && (i < 8));
        if(!locLegal)
            return null;
        return direction;
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
            if(actor instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) actor;
                if(rabbit.isAlive()) { 
                    rabbit.setDead(false);
                    foodLevel += RABBIT_FOOD_VALUE;
                    if(foodLevel > MAX_FOOD_VALUE)
                    {
                        foodLevel = MAX_FOOD_VALUE;
                    }
                    
                    return location;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Actor> newFoxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation(), 1);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false, field, loc);
            newFoxes.add(young);
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

    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
