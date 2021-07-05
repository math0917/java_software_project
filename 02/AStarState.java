package project_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    
    private Map<Location,Waypoint>openWayPoints = new HashMap<>();
    private Map<Location,Waypoint>closedWayPoints = new HashMap<>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }
   
    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
    	if (openWayPoints.isEmpty()) {
    		return null;
    	}
    	else {// put Waypoint in List and sort by collections and choose first index
    		//I make three version I think it is a best way
    		List <Waypoint> sort_Waypoint = new ArrayList<Waypoint>(openWayPoints.values());
    		Collections.sort(sort_Waypoint, new Comparator<>() {
    			public int compare (Waypoint p1, Waypoint p2) {
    				return Float.compare(p1.getTotalCost(),p2.getTotalCost());
    			}
    	    });
    		return sort_Waypoint.get(0);
    		 
    	}
    	
    }
    
    /*
      List<Waypoint> min_Waypoint= new ArrayList<>(1);
    		 for (Location key: openWayPoints.keySet()) {
    			 min_Waypoint.add(openWayPoints.get(key));    			 
    		}
    		Collections.sort(min_Waypoint, new Comparator<Waypoint>() {
				 public int compare(Waypoint p1, Waypoint p2) {
					 if (p1.getTotalCost()<p2.getTotalCost()) {
						return -1; 
					 }
					 else if(p1.getTotalCost()==p2.getTotalCost()){
						return 0;
					 }
					 else {
						return 1;
					 }
				 }
			 });
    		return min_Waypoint.get(0);
     */
    		/*
    		Waypoint min_p = null;
    		
    		float min=0;
    		
    		for(Waypoint p : openWayPoints.values()) {
    			if(min_p==null||min>p.getTotalCost()) {
    				min_p=p;
    				min=p.getTotalCost();
    			}
    		}
    		return min_p;
    	 	
    	
        // TODO:  Implement.
        }
    }
     */
    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one ONLY
     * IF the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
	public boolean addOpenWaypoint(Waypoint newWP)
    {
        if(openWayPoints.containsValue(newWP)==false) {
        	openWayPoints.put(newWP.loc, newWP);
        	return true;
        }
        else {
        	Waypoint compare=null;
        	for (Waypoint p : openWayPoints.values()) {
        		if(p==newWP) {
        			compare=p;
        			break;
        		}
        	}
        	if (compare.getPreviousCost()>newWP.getPreviousCost()) {
        		openWayPoints.remove(compare.loc);
        		openWayPoints.put(newWP.loc, newWP);
            	return true;
        	}
        	else {
        		return false;
        	}
        }
        
    }


    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints()
    {
        return openWayPoints.size();
    	// TODO:  Implement.
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
    	Waypoint p=openWayPoints.get(loc);
    	openWayPoints.remove(loc);
    	closedWayPoints.put(loc, p);
        // TODO:  Implement.
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
    	if (closedWayPoints.containsKey(loc)) {
    		return true;
    	}
        // TODO:  Implement.
        return false;
    }
}
