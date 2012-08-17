package PrivateGraphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Mark Dunlop
 * 
 * This class is core to Graphics - this Canvas is where we do all the drawing
 * This version does most of the work in the constructor 
 * It implements stop-start animation on mouse click
 * All transformations are done using transform methods using Matrix/Transformation approach
 * 
 */
public class MyCanvas extends Canvas implements MouseMotionListener, MouseListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8047427907134629499L;
	private Image bufferImage;
	private MyDrawing2D drawing;
	private MyRect2D square;
	private MyTransformation2D rotateSquareAroundSelf;

	/**
	 * Constructor does:
	 * 	a) calls the standard Canvas constructor
	 *  b) adds itself as a listener for mouse movement events
	 *  c) creates a complex drawing object and stores this locally 
	 *  
	 *  Part (c) moves the main effort out of paint() and into the constructor compared 
	 *  to previous versions of MyCanvas
	 */
	public MyCanvas(){
		super();

		addMouseMotionListener(this);
		addMouseListener(this);

		drawing = new MyDrawing2D();
		drawing.add(new MyRect2D(new MyPoint2D(0,0),new MyPoint2D(1000,1000),Color.lightGray));

		MyPoint2D pt = new MyPoint2D(500,500);
		drawing.add(pt);

		MyLine2D line = new MyLine2D(new MyPoint2D(600,200), new MyPoint2D(800,400), Color.BLUE);
		drawing.add(line);

		MyTransformation2D translate1 = new MyTransformation2D(); translate1.translate(-700, -300);
		MyTransformation2D rotate = new MyTransformation2D(); rotate.rotateByDegreesAroundOrigin(10);
		MyTransformation2D translate2 = new MyTransformation2D(); translate2.translate(700, 300);

		MyTransformation2D rotateAroundSelf = new MyTransformation2D();
		rotateAroundSelf.transform(translate1);
		rotateAroundSelf.transform(rotate);
		rotateAroundSelf.transform(translate2);

		MyLine2D line2= (MyLine2D) line.clone();
		for (int i=1; i<=18; i++){
			line2.transform(rotateAroundSelf);
			drawing.add(line2);
			line2 = (MyLine2D) line2.clone();
		}

		MyDrawing2D saltire = new MyDrawing2D();
		saltire.add(new MyRect2D(new MyPoint2D(200,200), new MyPoint2D(500,400), Color.BLUE));
		MyPolygon2D cross = new MyPolygon2D(Color.WHITE);
		cross.addPoint(new MyPoint2D(200,220));
		cross.addPoint(new MyPoint2D(200,200));
		cross.addPoint(new MyPoint2D(220,200));

		cross.addPoint(new MyPoint2D(500,380));
		cross.addPoint(new MyPoint2D(500,400));
		cross.addPoint(new MyPoint2D(480,400));
		saltire.add(cross);
		MyPolygon2D cross2 = new MyPolygon2D(Color.WHITE);
		cross2.addPoint(new MyPoint2D(200,380));
		cross2.addPoint(new MyPoint2D(200,400));
		cross2.addPoint(new MyPoint2D(220,400));

		cross2.addPoint(new MyPoint2D(500,220));
		cross2.addPoint(new MyPoint2D(500,200));
		cross2.addPoint(new MyPoint2D(480,200));
		saltire.add(cross2);
		drawing.add(saltire);

		square = new MyRect2D(new MyPoint2D(600,600), new MyPoint2D(800,800), Color.PINK);
		drawing.add(square);

		translate1 = new MyTransformation2D(); translate1.translate(-700, -700);
		rotate = new MyTransformation2D(); rotate.rotateByDegreesAroundOrigin(2);
		translate2 = new MyTransformation2D(); translate2.translate(700, 700);

		rotateSquareAroundSelf = new MyTransformation2D();
		rotateSquareAroundSelf.transform(translate1);
		rotateSquareAroundSelf.transform(rotate);
		rotateSquareAroundSelf.transform(translate2);

	}

	/* Draws the content of the Canvas - called whenever the Canvas needs redrawn
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint (Graphics g){
		if ( (bufferImage==null) || (bufferImage.getWidth(null)!=getWidth()) || (bufferImage.getHeight(null)!=getHeight()) ){
			bufferImage = createImage(getWidth(), getHeight());
			MyPoint2D.setDisplaySize(getWidth(), getHeight());
		}
		drawing.draw(bufferImage.getGraphics());
		g.drawImage(bufferImage, 0, 0, null);
	}

	private boolean running = false;

	@Override
	public void run() {
		try {
			while (running){
				square.transform(rotateSquareAroundSelf);
				paint(this.getGraphics());//should really be paint(g)
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (!running){
			//start rotating the square
			running = true;
			(new Thread(this)).start();
		} else {
			//stop rotating the square
			running = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
