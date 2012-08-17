package Clock;
/**
 * @author RyanMcNulty
 * This class creates the base polygon for the hands to be drawn on the canvas.
 * With being polygons then points can be added to change the shape very easily.
 */
import java.awt.Color;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyPolygon2D;

public class Hand extends MyPolygon2D{

	public Hand(char c, Color col){
		super(col);
		validateHandType(c);
	}
	
	/**
	 * evaluates which size of hand and position to use.
	 */
	public void validateHandType(char c){
		// if the character indicates its a small hand by s or big hand by anything else
		if(c == 's'){
			setupPointsSmall();
		}
		else if(c == 'd'){
			setupPointsStopwatch();
		}
		else if(c == 'f'){
			setupPointsStopwatchSmall();
		}
		else{
			setupPointsBig();
		}
	}

	public void setupPointsStopwatchSmall(){
		addPoint(new MyPoint2D(497, 250));
		addPoint(new MyPoint2D(497, 360));
		addPoint(new MyPoint2D(503, 360));
		addPoint(new MyPoint2D(503, 250));
	}
	public void setupPointsStopwatch(){
		addPoint(new MyPoint2D(497,250));
		addPoint(new MyPoint2D(497,380));
		addPoint(new MyPoint2D(503, 380));
		addPoint(new MyPoint2D(503, 250));
	}
	public void setupPointsBig(){
		addPoint(new MyPoint2D(495, 498));
		addPoint(new MyPoint2D(495, 920));
		addPoint(new MyPoint2D(485, 920));
		addPoint(new MyPoint2D(500, 950));
		addPoint(new MyPoint2D(520, 920));
		addPoint(new MyPoint2D(507, 920));
		addPoint(new MyPoint2D(507, 498));
	}
	public void setupPointsSmall(){
		addPoint(new MyPoint2D(492, 500));
		addPoint(new MyPoint2D(492, 800));
		addPoint(new MyPoint2D(508, 800));
		addPoint(new MyPoint2D(508, 500));
	}
}
