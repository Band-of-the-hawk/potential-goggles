import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    private final Object[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
    }
    
    /**
     * Empty the field.
     * @param all
     */
    public void clear(boolean all)
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                clear(new Location(row, col), all);
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     * @param all
     */
    public void clear(Location location, boolean all)
    {
        if(all){
            field[location.getRow()][location.getCol()] = null;
        } else {
        HashMap<Object, Object> actors;
        actors = (HashMap<Object, Object>) field[location.getRow()][location.getCol()];
        actors.remove(1);
        field[location.getRow()][location.getCol()] = actors;
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     * @param isStatic
     */
    public void clearActors(Location location, boolean isStatic)
    {
        HashMap<Object, Object> actors;
        actors = (HashMap<Object, Object>) field[location.getRow()][location.getCol()];
        if(actors != null) {
            if(isStatic){
                actors.remove(0);
            } else {
                actors.remove(1);
            }
        }
        field[location.getRow()][location.getCol()] = actors;
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param actor The actor to be placed.
     * @param isStatic
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object actor, boolean isStatic, int row, int col)
    {
        place(actor, isStatic, new Location(row, col));
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param actor The actor to be placed.
     * @param isStatic
     * @param location Where to place the animal.
     */
    public void place(Object actor, boolean isStatic, Location location)
    {
        Object o = field[location.getRow()][location.getCol()];
        HashMap<Object, Object> actors;
        if(o == null) {
            actors = new HashMap<>();
            if(isStatic) {
                actors.put(0, actor);
            } else {
                actors.put(1, actor);
            }
        } else {
            actors = (HashMap<Object, Object>) o;
            if(isStatic) {
                actors.put(0, actor);
            } else {
                actors.put(1, actor);
            }
        }
        field[location.getRow()][location.getCol()] = actors;
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @param isStatic
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location, boolean isStatic)
    {
        return getObjectAt(location.getRow(), location.getCol(), isStatic);
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @param isStatic
     * @return The actor at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col, boolean isStatic)
    {
        HashMap<Object, Object> actors;
        actors = (HashMap<Object, Object>) field[row][col];
        Object actor;
        if(actors == null) return null;
        if(isStatic) {
            actor = actors.get(0);
        } else {
            actor = actors.get(1);
        }
        return actor;
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next, false) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
