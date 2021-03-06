package project_1;
/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof Location) {
    		Location other= (Location) o;
    		if (xCoord == other.xCoord&&
    			yCoord == other.yCoord) 
    		{
    			return true;
    		}
    	}
        return false;
    }
    @Override
    public int hashCode() {
    	final int result=31;
    	return result*xCoord+yCoord;
    }
}
