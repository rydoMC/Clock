package PrivateGraphics;

public class MyTransformation2D extends MyMatrix {

	public MyTransformation2D(){
		//create a new homogeneous transformation with no action 
		super(3,3,0);
		setElement(0,0,1);
		setElement(1,1,1);
		setElement(2,2,1);
	}
	
	public void translate(double x, double y){
		setElement(0,2,x);
		setElement(1,2,y);
	}
	
	public void rotateByDegreesAroundOrigin(double angle){
		setElement(0,0, Math.cos(Math.toRadians(angle)));
		setElement(1,0, -Math.sin(Math.toRadians(angle)));
		setElement(0,1, Math.sin(Math.toRadians(angle)));
		setElement(1,1, Math.cos(Math.toRadians(angle)));
	}
}
