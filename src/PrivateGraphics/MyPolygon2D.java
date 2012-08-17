package PrivateGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * @author Mark Dunlop
 * A filled polygon using the MyObject2D framework. A MyPolygon2D is composed of
 * a list of MyPoint2D points and does not need to be a closed polygon.
 */
public class MyPolygon2D implements MyObject2D {

	private ArrayList<MyPoint2D> points;
	private static MyTransformation2D flip = new MyTransformation2D();
	private static MyTransformation2D deflip = new MyTransformation2D();
	private static MyTransformation2D sFlip = new MyTransformation2D();
	private static MyTransformation2D sdeFlip = new MyTransformation2D();

	private Color color;

	static {
		flip.translate(-500,-500);
		deflip.translate(500, 500);
		sFlip.translate(-500,-250);
		sdeFlip.translate(500,250);
	}
	public MyPolygon2D(ArrayList<MyPoint2D> points, Color color){
		this.points = points;
		this.color = color;
	}
	public MyPolygon2D(Color color){
		this(new ArrayList<MyPoint2D>(), color);
	}
	public MyPolygon2D(){
		this( new ArrayList<MyPoint2D>(), Color.BLACK) ;
	}

	/**
	 * Add the new point to the end of the list of points comprising the polygon
	 * @param point the new point to be added
	 */
	public void addPoint(MyPoint2D point){
		points.add(point);
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(color);

		//create a java.awt.Polygon and add the display versions of each logical point to it
		Polygon poly = new Polygon();
		for (MyPoint2D point : points)
			poly.addPoint(point.getDisplayX(), point.getDisplayY());

		//draw the java.awt.Polygon
		g.fillPolygon(poly);

		g.setColor(oldColor);
	}
	@Override
	public Object clone(){
		// Create a new MyPolygon2D
		// Run through the current ArrayList of points and for each one:
		//    add a clone of that point to the new drawing

		MyPolygon2D newPolygon = new MyPolygon2D(new Color(color.getRGB()));
		for (MyPoint2D oldPoint : points)
			newPolygon.addPoint((MyPoint2D)oldPoint.clone());
		return newPolygon;

	}

	public void rotateAroundAnchor(MyTransformation2D t){
		transform(flip);
		transform(t);
		transform(deflip);
	}

	public void rotateAroundAnchorSmall(MyTransformation2D t){
		transform(sFlip);
		transform(t);
		transform(sdeFlip);
	}
	
	public void transform(MyTransformation2D transformation) {
		for (MyPoint2D point : points)
			point.transform(transformation);
	}

}
