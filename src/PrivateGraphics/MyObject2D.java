package PrivateGraphics;

import java.awt.Graphics;

/**
 * @author Mark Dunlop
 *
 * This is a simple interface that all 2D objects should implement
 * The main purpose is that they can all be treated as MyObject2D objects 
 * and thus all be added to the same datastrcutres. In this version the only
 * thing that implementers have to implement is draw, transform and clone.
 * It is thus compatible with the Matrix/Transformation approach
 */
public interface MyObject2D extends Cloneable {

	public void draw(Graphics g);
	public void transform(MyTransformation2D transformation);
	public Object clone();//Cloneable doesn't actually require implementation of this - but we do...
}
