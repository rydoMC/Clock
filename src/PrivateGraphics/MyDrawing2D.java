package PrivateGraphics;

import java.awt.Graphics;
import java.util.ArrayList;


/**
 * @author Mark Dunlop
 *
 * MyDrawing2D is a container for holding lots of MyObject2D objects
 * As such a MyDrawing2D object can be either a simple line or a complex
 * drawing made up of lines, polygons, rectangles... and handled as a 
 * single drawable MyDrawing2D object.
 * 
 * This version is for the Homogeneous Coordinates so only implements transform(t)
 * and doesn't directly implement individual transformations
 */
public class MyDrawing2D implements MyObject2D {

	private ArrayList<MyObject2D> objects = new ArrayList<MyObject2D>();

	/**
	 * @param obj the MyDrawing2D object that is to be added to the drawing
	 */
	public void add(MyObject2D obj){
		objects.add(obj);
	}

	@Override
	public void draw(Graphics g) {
		for (MyObject2D object : objects)
			object.draw(g);
	}

	@Override
	public Object clone(){
		// Create a new MyDrawing2D
		// Run through the current ArrayList of objects and for each one:
		//    add a clone of that object to the new drawing

		MyDrawing2D newDrawing = new MyDrawing2D();
		for (MyObject2D oldPoint : objects)
			newDrawing.add((MyObject2D)oldPoint.clone());
		return newDrawing;
	}

	@Override
	public void transform(MyTransformation2D transformation) {
		for (MyObject2D object : objects)
			object.transform(transformation);
	}

}
