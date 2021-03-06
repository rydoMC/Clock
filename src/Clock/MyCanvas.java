package Clock;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import PrivateGraphics.MyDrawing2D;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyPolygon2D;
import PrivateGraphics.MyRect2D;
import PrivateGraphics.MyText2D;
import PrivateGraphics.MyTransformation2D;

/**
 * MyCanvas is a class which extends Canvas in order to become a paintable canvas for graphics to be drawn on.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @created Aug 17, 2012
 */
public class MyCanvas extends Canvas implements MouseListener, Runnable{
	private static final long serialVersionUID = 7186653402129841530L;
	
	private MyDrawing2D drw;
	private Hand bHand,smHand,scHand;
	private MiniDisplay mdL, mdR;
	private MyText2D dateAndDay, timeStatus;
	private Image bufferImage;
	private long[] digits;
	
	// Stop watch related
	private StopWatch stpwtch;
	private Thread stpwtchThread;

	
	/**
	 * Default Constructor
	 */
	public MyCanvas(){
		super();
		drw = new MyDrawing2D();
		digits = new long[5]; 
		
		stpwtch = new StopWatch();
		
		addMouseListener(this);
		
		backgroundSetup();
		clearHands();
		setupIntervals();
	}

	
	// Draws black rectangle to act as the background of the canvas
	private void backgroundSetup(){
		MyRect2D backGround = new MyRect2D(new MyPoint2D(0,0), new MyPoint2D(1000,1000), Color.black);
		drw.add(backGround);
	}

	
	// Reset all hands back to original colours and positions.
	private void clearHands(){
		bHand = new Hand(HandSize.BigHand, Color.WHITE);
		scHand = new Hand(HandSize.BigHand, Color.WHITE);
		smHand = new Hand(HandSize.SmallHand, Color.WHITE);
		
		stpwtch.clearHands();
	}

	
	public void paint(Graphics g){
		bufferImage = createImage(getWidth(), getHeight());
		MyPoint2D.setDisplaySize(getWidth(), getHeight());

		if(g == null){
			System.exit(1);
		}
		
		drw.draw(bufferImage.getGraphics());
		g.drawImage(bufferImage, 0, 0, null);
	}

	
	// Processes the values in which are to be shown on the mini displays.
	private void setMiniDisplayValues(){
		if(digits[0] > 12){
			timeStatus = new MyText2D("PM", new MyPoint2D(200,480));
		}
		else{
			timeStatus = new MyText2D("AM", new MyPoint2D(200,480));
		}

		int i = (int) digits[4];
		String[] days = {"Sun", "Mon","Tues","Wed","Thurs","Fri","Sat"};

		dateAndDay = new MyText2D(days[i-1] + " " + digits[3], (new MyPoint2D(650,480)));
	}

	
	// Arranges interval/mypolygon2d objects to allow arrangement of a dial.
	private void setupIntervals(){
		MyPolygon2D nextInterval;
		MyTransformation2D trn = new MyTransformation2D();
		Interval baseInterval = new Interval(Color.white, IntervalSize.Normal);


		// 60 intervals, each 6 degrees apart
		for(int i = 0; i < 60; i++){
			// if i is a value of a hour marking interval then mark as green.
			if(i % 5 == 0) nextInterval = new Interval(Color.GREEN, IntervalSize.Big);
			else nextInterval = (MyPolygon2D) baseInterval.clone();
			
			trn.rotateByDegreesAroundOrigin(i*6);
			nextInterval.rotateAroundAnchor(trn);
			drw.add(nextInterval);
		}
	}

	
	// Arranges interval/mypolygon2d objects in order to represent the stopwatch dial
	private void setupMiniIntervals(){
		MyPolygon2D nextInterval = new Interval(Color.WHITE,IntervalSize.Mini);
		MyTransformation2D trn = new MyTransformation2D();

		// Each interval is 6 degrees apart
		for(int i = 0; i < 60; i++){
			nextInterval = (MyPolygon2D) nextInterval.clone();

			trn.rotateByDegreesAroundOrigin(6);
			nextInterval.rotateAroundAnchorSmall(trn);
			drw.add(nextInterval);
		}
	}

	
	// Sets digits[] to hold values of time and rotates the hand objects approp.
	private void setCurrentTime(){
		Calendar cal = Calendar.getInstance();

		digits[0] = (cal.get(Calendar.HOUR_OF_DAY));
		digits[1] = (cal.get(Calendar.MINUTE));
		digits[2] = (cal.get(Calendar.SECOND));
		digits[3] = (cal.get(Calendar.DAY_OF_MONTH));
		digits[4] = (cal.get(Calendar.DAY_OF_WEEK));

		int angle = 6;
		MyTransformation2D trn = new MyTransformation2D();

		// For big hand, small hand and seconds hand.
		for(int i = 0; i < 3; i++){
			int currentHand = (int) digits[i];
			
			if(i==0){
				trn.rotateByDegreesAroundOrigin(currentHand * (angle*5));
				smHand.rotateAroundAnchor(trn);
			}
			else if(i==1){
				trn.rotateByDegreesAroundOrigin(currentHand * angle);
				bHand.rotateAroundAnchor(trn);
			}
			else{
				trn.rotateByDegreesAroundOrigin(currentHand * angle);
				scHand.rotateAroundAnchor(trn);
			}
		}
	}

	
	// Intialises mini displays
	private void setupMiniDisplays(){
		mdL = new MiniDisplay();
		mdR = new MiniDisplay();

		MyTransformation2D trn = new MyTransformation2D();
		trn.translate(500,0);
		mdR.transform(trn);
	}

	
	public void run(){
		boolean running = true,checked = true;
		double secs,mins;
		long loopStartTime, loopEndTime, totalTime,lag = 0;

		setCurrentTime();
		setMiniDisplayValues();
		MyTransformation2D trn = new MyTransformation2D();
		MyTransformation2D trnSec = new MyTransformation2D();

		trnSec.rotateByDegreesAroundOrigin(0.3);
		trn.rotateByDegreesAroundOrigin(6);

		mins = digits[1];
		secs = digits[2];

		setupMiniIntervals();
		setupMiniDisplays();
		addDrawableObjects();
		
		stpwtchThread = new Thread(stpwtch);

		try {
			while(running){
				loopStartTime = System.nanoTime();
				scHand.rotateAroundAnchor(trnSec);
				secs = secs + 0.05;

				// If the seconds has reached a full minute.
				if((int) secs==60){
					bHand.rotateAroundAnchor(trn);
					secs=0;
					mins++;
					checked = false;
				}

				if(mins % 12 == 0 && mins != 0 && !checked){
					smHand.rotateAroundAnchor(trn);
					mins = 0;
					checked = true;
				}
				
				// throws error here.
				paint(getGraphics());
				loopEndTime = System.nanoTime();

				Thread.sleep(Math.max((50000000 - (loopEndTime-loopStartTime) - lag)/1000000,0));
				totalTime = System.nanoTime();
				lag = totalTime - loopStartTime - 50000000;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	// In this method, you add all the objects to draw to the drawing instance. Order is specific in order to make it seem like a real clock.
	private void addDrawableObjects(){
		drw.add(mdL);
		drw.add(mdR);
		drw.add(dateAndDay);
		drw.add(timeStatus);
		drw.add(bHand);
		drw.add(scHand);
		drw.add(smHand);
		drw.add(stpwtch);
	}

	// Mark all instances of intervals to be put onto the convas
	private void changeStopwatchThreadState(){
		if(!stpwtchThread.isAlive()){
			stpwtchThread.start();
		}	
		else{
			stpwtchThread = new Thread(stpwtch); 
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("mouse click");
		stpwtch.changeRunStatus();	
		changeStopwatchThreadState();
	}

	
	// Unused mouse methods
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
