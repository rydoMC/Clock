package PrivateGraphics;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Mark Dunlop
 * 
 * A simple point object using the MyObject2D framework - using Isotropic coordinates
 * This simple point stores an x,y coordinate for the point and can draw itself using the draw(g) method
 * This version is for the Matrix/Transformation approach so only implements the single transformation 
 * method transform(t) and not individual methods
 * 
 * The point maps from a (0...MAXX, 0..MAXY) logical space onto the physical screen size
 * Uses Isotropic coordinates that fill the display best possible while maintaining aspect ratio
 * Sets (0,0) to be bottom left corner 
 * Requires setDisplaySize calls before mapping to display coordinates are valid
 * Uses static declarations for display sizes so DOES NOT SUPPORT MULTIPLE WINDOWS
 */
public class MyPoint2D extends MyMatrix implements MyObject2D{
	
	public static final double MAXX = 1000, MAXY = 1000;
	private static double displayWidth = 500, displayHeight = 500, scaleX=0.5, scaleY=0.5, offsetX=0, offsetY=0;
	private Color color;
	
	public MyPoint2D(double x, double y){
		this(x,y,Color.BLACK);
	}

	public MyPoint2D(double x, double y, Color color){
		super(3,1,1);// create a new Homogeneous vector [1,1,1]
		this.setElement(0, 0, x);
		this.setElement(1, 0, y);
		this.color = color;
	}
	public int getDisplayX(){
		return (int) Math.round(getElement(0,0) * scaleX + offsetX);
	}
	public int getDisplayY(){
		return (int) Math.round(   displayHeight-getElement(1,0)*scaleY  - offsetY);  
	}
	public double getLogicalX(){
		return getElement(0,0);
	}
	public double getLogicalY(){
		return getElement(1,0);
	}
	public static double getLogicalX(int x){
		return (x*1.0-offsetX)/scaleX ;
	}
	public static double getLogicalY(int y){
		return (displayHeight - (y*1.0+offsetY))/scaleY;
	}
	public void setLogicalX(double x){
		setElement(0, 0, x);
	}
	public void setLogicalY(double y){
		setElement(1, 0, y);
	}
	@Override
	public void draw(Graphics g) {
		// Get the old color of the graphics, set the color to this object's
		// then draw the object and finally set the color back to avoid side effects
		//
		// Cannot draw a point directly in AWT so draw a very short line instead 
		Color oldColor = g.getColor();
		g.setColor(color);
		g.drawLine(this.getDisplayX(), this.getDisplayY(), this.getDisplayX(), this.getDisplayY());
		g.setColor(oldColor);
	}
	public static void setDisplaySize(int displayWidth, int displayHeight){
		MyPoint2D.displayWidth = displayWidth;
		MyPoint2D.displayHeight = displayHeight;
		scaleX = displayWidth/MAXX;
		scaleY = displayHeight/MAXY;
		
		// calculate the isotropic scaling factors
		scaleX = Math.min(scaleX, scaleY);
		scaleY=scaleX;
		// calculate the x and y offsets to centre the scaled logical area on the display area
		offsetX = (displayWidth-MAXX*scaleX)/2;
		offsetY= (displayHeight-MAXY*scaleY)/2;
	}
	
	@Override
	public Object clone(){
		//copy the x and y (they are values) and effectively clone the Color object
		return new MyPoint2D(getLogicalX(), getLogicalY(), new Color(color.getRGB()));
	}

	@Override
	public void transform(MyTransformation2D transformation) {
		super.transform(transformation);
	}
}
