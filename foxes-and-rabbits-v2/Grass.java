import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *
 *  Om gress
    Dør etter 10 år
    Blir spist av kanin
    Tar 1 år å gro tilbake

 */

/**
 *
 * @author ThomasSTodal
 */
public class Grass extends Actor
{

    /*
        GENERAL VARIABLES FOR GRASS
     */
    // Amount of steps before dying
    private static final int MAX_AGE = 3000;
    // Probability that grass will doSpread
    private static final double GROW_PROBABLITY = 0.001;
    // Amount of spread



    private static final Random rand = Randomizer.getRandom();

    /*
        INDIVIDUAL VARIABLES FOR GRASS
     */
    // The age of the grass
    private int age;


    public Grass(boolean randAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    @Override
    public void act(List<Actor> newGrass)
    {
        incrementAge();
        if(isAlive()) {
            doSpread(newGrass);
        }
    }

    /**
     * Increase the age of the grass
     */
    private void incrementAge() {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    private void doSpread(List<Actor> newGrass) {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int i = 0; i < free.size(); i++) {
            if(rand.nextDouble() < GROW_PROBABLITY) {
                Location loc = free.remove(0);
                Grass youngGrass = new Grass(false, field, loc);
                newGrass.add(youngGrass);
            }
        }
    }
}
