package PrivateGraphics;

import java.awt.Color;
import java.awt.Graphics;


/**
 * @author Mark Dunlop
 * A 2D coloured line
 * 
 * This version is for the Matrix/Transformation approach so only implements transform(t)
 * and doesn't directly implement individual transformations
 */
public class MyLine2D implements MyObject2D {

	private MyPoint2D start, end;
	private Color color;

	public MyLine2D(MyPoint2D start, MyPoint2D end){
		this(start, end, Color.BLACK);
	}
	public MyLine2D(MyPoint2D start, MyPoint2D end, Color color){
		this.start = start;
		this.end = end;
		this.color = color;
	}
	@Override
	public void draw(Graphics g) {
		// Get the old color of the graphics, set the color to this object's
		// then draw the object and finally set the color back to avoid side effects
		Color oldColor = g.getColor();
		g.setColor(color);
		g.drawLine(start.getDisplayX(), start.getDisplayY(), end.getDisplayX(), end.getDisplayY());
		g.setColor(oldColor);
	}
	@Override
	public Object clone(){
		//create a new MyLine2D and populate with clones of the start and end points (and effective clone of the Color object)
		//NB: Cloning returns objects of type Object so we need to cast these to MyPoint2D objects
		return new MyLine2D((MyPoint2D)start.clone(), (MyPoint2D)end.clone(), new Color(color.getRGB()));
	}
	@Override
	public void transform(MyTransformation2D transformation) {
		start.transform(transformation);
		end.transform(transformation);
	}
}
