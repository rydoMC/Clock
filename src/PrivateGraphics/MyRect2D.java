package PrivateGraphics;

import java.awt.Color;


public class MyRect2D extends MyPolygon2D {

	public MyRect2D(MyPoint2D bottomLeft, MyPoint2D topRight, Color color){
		//reworked slightly from previous version to give more consistent names to easiest use case
		// No real difference in code
		super(color);
		MyPoint2D bottomRight = new MyPoint2D(topRight.getLogicalX(), bottomLeft.getLogicalY());
		MyPoint2D topLeft = new MyPoint2D(bottomLeft.getLogicalX(), topRight.getLogicalY());
		this.addPoint(bottomLeft);
		this.addPoint(bottomRight);
		this.addPoint(topRight);
		this.addPoint(topLeft);
	}
}
