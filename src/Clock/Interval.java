package Clock;

import java.awt.Color;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyPolygon2D;

/**
 * This class is to allow markings of intervals for each minute/second to be drawn using logical co-ordinates.
 * The type of interval can be chosen via the param passed to the constructors.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @created Aug 17, 2012
 */
public class Interval extends MyPolygon2D{

	public Interval(Color c, IntervalSize size){
		super(c);
		setupPoints(size);
	}

	
	/**
	 * Adds all points to this drawing for the hand size given.
	 * 
	 * @param size
	 */
	private void setupPoints(IntervalSize size){
		int[][] coor = new Coordinates().coordinates(size);

		for(int i = 0; i < coor.length; i++){
			for(int j = 0; j < coor[i].length; j++){
				addPoint(new MyPoint2D(coor[i][0],coor[i][1]));
			}
		}
	}
}