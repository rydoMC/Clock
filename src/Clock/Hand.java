package Clock;

import java.awt.Color;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyPolygon2D;

/**
 * This class creates the base polygon for the hands to be drawn on the canvas.
 * With being polygons then points can be added to change the shape very easily.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @created Aug 17, 2012
 */
public class Hand extends MyPolygon2D {
	
	public Hand(HandSize size, Color col){
		super(col);
		setupPoints(size);
	}
	
	
	/**
	 * Adds all points to this drawing for the hand size given.
	 * 
	 * @param size
	 */
	private void setupPoints(HandSize size){
		int[][] coor = new Coordinates().coordinates(size);
		
		for(int i = 0; i < coor.length; i++){
			for(int j = 0; j < coor[i].length; j++){
				addPoint(new MyPoint2D(coor[i][0],coor[i][1]));
			}
		}
	}

}