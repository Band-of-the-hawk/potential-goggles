import java.util.ArrayList;
import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param staticActor
     */
    public Actor(Field field, Location location, boolean staticActor)
    {
        alive = true;
        this.field = field;
        setLocation(location, staticActor);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newActors A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newActors);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     * @param isStatic
     */
    protected void setDead(boolean isStatic)
    {
        if(!isStatic) {
            alive = false;
            if(location != null) {
                field.clearActors(location, isStatic);
                location = null;
                field = null;
            }
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     * @param staticActor
     */
    protected void setLocation(Location newLocation, boolean staticActor)
    {
        if((location != null) && !staticActor) {
            field.clear(location, true);
        }
        location = newLocation;
        field.place(this, staticActor, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * 
     * @param locations
     * @param location
     * @param offset
     * @return 
     */
    protected Location findClosest(List<Location> locations, Location location, int offset)
    {
        int thisRow = location.getRow();
        int thisCol = location.getCol();
        for (int d = 0; d < offset; d++){
            for (int i = 0; i < d + 1; i++){
                int x1 = thisCol - d + i;
                int y1 = thisRow - i;
                Location newLoc1 = new Location(x1, y1);

                for(Location loc : locations) {
                    if(loc.hashCode() == newLoc1.hashCode())
                        return newLoc1;
                }

                int x2 = thisCol + d - i;
                int y2 = thisRow + i;
                Location newLoc2 = new Location(x2, y2);

                for(Location loc : locations) {
                    if(loc.hashCode() == newLoc2.hashCode())
                        return newLoc2;
                }
            }


            for (int i = 1; i < d; i++){
                int x1 = thisCol - i;
                int y1 = thisRow + d - i;
                Location newLoc1 = new Location(x1, y1);

                for(Location loc : locations) {
                    if(loc.hashCode() == newLoc1.hashCode())
                        return newLoc1;
                }
                // Check point (x1, y1)

                int x2 = thisCol + d - i;
                int y2 = thisRow - i;
                Location newLoc2 = new Location(x2, y2);

                for(Location loc : locations) {
                    if(loc.hashCode() == newLoc2.hashCode())
                        return newLoc2;
                }
            }
        }
       
        
        return null;
    }
    
     /**
      * 
      * @param locations
      * @param location
      * @param offset
      * @return 
      */
    protected Location findClosestV2(List<Location> locations, Location location, int offset)
    {
        int thisRow = location.getRow();
        int thisCol = location.getCol();
        for (int d = 0; d < offset; d++) {
            for (int row = thisRow - d; row < thisRow + d + 1; row++) {
                for (int col = thisCol - d + 1; col < thisCol + d; col++) {
                    // Point to check: (x, thisRow - d) and (x, thisRow + d) 
                    if (CheckPoint(locations, row, thisCol - d) == true) {
                        return new Location(row, thisCol - d);
                    }

                    if (CheckPoint(locations, row, thisCol + d) == true) {
                        return new Location(row, thisCol - d);
                    }
                    // Point to check = (thisCol - d, y) and (thisCol + d, y) 
                    if (CheckPoint(locations, row, thisRow - d) == true) {
                        return new Location(thisRow - d, col);
                    }

                    if (CheckPoint(locations, row, thisRow + d) == true) {
                        return new Location(thisRow - d, col);
                    }
                }
            }
        }
        return null;
    }
    
    protected Location findClosestV3(List<Location> locations, Location location, int offset)
    {
        if(locations == null || location == null || locations.size() < 1)
            return null;
        
        double thisRow = location.getRow();
        double thisCol = location.getCol();
        int closestLoc;
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        
        for(Location loc : locations) {
            double dist;
            dist = Math.sqrt((Math.pow(loc.getRow(), 2) - thisRow) + (Math.pow(loc.getCol(), 2) - thisCol));
            distances.add(dist);
        }
        
        double closest = 2000;
        for(Double dist : distances) {
            if(dist < closest) {
                closest = dist;
                indexes.add(distances.indexOf(dist));
            } else if(dist == closest) {
                indexes.add(distances.indexOf(dist));
            }
        }
        int randIndex;
        if(indexes.size() > 0) {
            randIndex = (Integer.parseInt(Long.toString(Math.round(Math.random())))*(indexes.size()-1));
            return locations.get(indexes.get(randIndex));
        }
        return null;
    }

    private boolean CheckPoint(List<Location> locations, int row, int col)
    {
                Location newLoc = new Location(row, col);

                for(Location loc : locations) {
                    if(loc.hashCode() == newLoc.hashCode())
                        return true;
                }
                return false;
    }
    
    /**
     * 
     * @param location
     * @return 
     */
    protected Location getDir(Location location)
    {
        if(location == null) return null;
        int thisRow = getLocation().getRow();
        int thisCol = getLocation().getCol();
        int targRow = location.getRow();
        int targCol = location.getCol();
        int newRow = 0;
        int newCol = 0;
        if((thisRow == targRow) && (thisCol > targCol)) {
            
            newCol = thisCol - 1;
        } else if((thisRow < targRow) && (thisCol > targCol)) {
            newRow = thisRow + 1;
            newCol = thisCol - 1;
        } else if((thisRow < targRow) && (thisCol == targCol)) {
            newRow = thisRow + 1;
            
        } else if((thisRow < targRow) && (thisCol < targCol)) {
            newRow = thisRow + 1;
            newCol = thisCol + 1;
        } else if((thisRow == targRow) && (thisCol < targCol)) {
            
            newCol = thisCol + 1;
        } else if((thisRow > targRow) && (thisCol < targCol)) {
            newRow = thisRow - 1;
            newCol = thisCol + 1;
        } else if((thisRow > targRow) && (thisCol == targCol)) {
            newRow = thisRow - 1;
            
        } else if((thisRow > targRow) && (thisCol > targCol)) {
            newRow = thisRow - 1;
            newCol = thisCol - 1;
        }
        if((newRow != thisRow) && (newCol != thisCol)) {
            return new Location(newRow, newCol);
        }
        return null;
    }
}
