package Clock;
/**
 * @author RyanMcNulty
 * This class is to allow markings of intervals for each minute/second to be drawn using logical co-ordinates.
 * The type of interval can be chosen via the param passed to the constructors.
 */
import java.awt.Color;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyPolygon2D;

public class Interval extends MyPolygon2D{
	 public Interval(){
		 super(Color.white);
		 setupPoints();
	 }
	 
	 public Interval(Color c){
		 super(c);
		 setupPointsBig();
	 }
	 
	 public Interval(char c){
		 super(Color.white);
		 setupPointsMini();
	 }
	 
	 public void setupPointsMini(){
		 addPoint(new MyPoint2D(498,400));
		 addPoint(new MyPoint2D(498,380));
		 addPoint(new MyPoint2D(502,380));
		 addPoint(new MyPoint2D(502,400));
	 }
	 
	 public void setupPoints(){
		 addPoint(new MyPoint2D(498, 998));
		 addPoint(new MyPoint2D(499, 999));
		 addPoint(new MyPoint2D(500, 1000));
		 addPoint(new MyPoint2D(501, 1000));
		 addPoint(new MyPoint2D(502, 999));
		 addPoint(new MyPoint2D(503, 998));
		 addPoint(new MyPoint2D(502, 997));
		 addPoint(new MyPoint2D(501, 996));
		 addPoint(new MyPoint2D(500, 996));
		 addPoint(new MyPoint2D(499, 997));
	 }
	 
	 public void setupPointsBig(){
		 addPoint(new MyPoint2D(490, 1000));
		 addPoint(new MyPoint2D(490, 980));
		 addPoint(new MyPoint2D(510, 980));
		 addPoint(new MyPoint2D(510,1000));
	 }
}