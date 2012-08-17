package Clock;
/**
 * @author RyanMcNulty
 * MyCanvas is a class which extends Canvas in order to become a paintable canvas for graphics to be drawn on.
 */
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

public class MyCanvas extends Canvas implements MouseListener, Runnable {
	private static final long serialVersionUID = 7186653402129841530L;
	private MyDrawing2D drw;
	private Hand bHand,smHand,scHand,stpHand, stpHandSm;
	private MiniDisplay mdL, mdR;
	private MyRect2D backGround;
	private MyText2D dateAndDay, timeStatus;
	private Image bufferImage;
	private long[] digits;
	private Stopwatch stpwtch = new Stopwatch();
	private Thread stpwtchThread;

	public MyCanvas(){
		drw = new MyDrawing2D();
		digits = new long[5]; 
		addMouseListener(this);
		backgroundSetup();

		clearHands();
		setupIntervals();
	}

	// Draws black rectangle to act as the background of the canvas
	public void backgroundSetup(){
		backGround = new MyRect2D(new MyPoint2D(0,0), new MyPoint2D(1000,1000), Color.black);
		drw.add(backGround);
	}

	// Reset all hands back to original colours and positions.
	public void clearHands(){
		bHand = new Hand(' ', Color.WHITE);
		scHand = new Hand(' ', Color.WHITE);
		smHand = new Hand('s', Color.WHITE);
		clearStopwatchHands();
	}

	// Resets particularly the stopwatch hands.
	public void clearStopwatchHands(){
		stpHand = new Hand('d', Color.RED);
		stpHandSm = new Hand('f', Color.GREEN);
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
	public void setMiniDisplayValues(){
		if(digits[0] > 12){
			timeStatus = new MyText2D("PM", new MyPoint2D(200,480));
		}
		else{
			timeStatus = new MyText2D("AM", new MyPoint2D(200,480));
		}

		int i = (int)digits[4];
		String[] days = {"Sun", "Mon","Tues","Wed","Thurs","Fri","Sat"};

		dateAndDay = new MyText2D(days[i-1] + " " + digits[3], (new MyPoint2D(650,480)));
	}

	// Arranges interval/mypolygon2d objects to allow arrangement of a dial.
	public void setupIntervals(){
		MyPolygon2D nextInterval;
		MyTransformation2D trn = new MyTransformation2D();
		Interval baseInterval = new Interval();


		// Each interval is 6 degrees apart
		for(int i = 0; i < 360; i=i+6){
			// if i is a value of a hour marking interval then mark as green.
			if(i % 30 == 0){
				nextInterval = new Interval(Color.GREEN);
			}
			else{
				nextInterval = (MyPolygon2D) baseInterval.clone();
			}
			trn.rotateByDegreesAroundOrigin(i);
			nextInterval.rotateAroundAnchor(trn);
			drw.add(nextInterval);
		}
	}

	// Arranges interval/mypolygon2d objects in order to represent the stopwatch dial
	public void setupMiniIntervals(){
		MyPolygon2D nextInterval = new Interval('d');
		MyTransformation2D trn = new MyTransformation2D();

		// Each interval is 6 degrees apart
		for(int i = 0; i < 360; i=i+6){
			nextInterval = (MyPolygon2D) nextInterval.clone();

			trn.rotateByDegreesAroundOrigin(6);
			nextInterval.rotateAroundAnchorSmall(trn);
			drw.add(nextInterval);
		}
	}

	// Sets digits[] to hold values of time and rotates the hand objects approp.
	public void setCurrentTime(){

		Calendar cal = Calendar.getInstance();

		digits[0] = (cal.get(Calendar.HOUR_OF_DAY));
		digits[1] = (cal.get(Calendar.MINUTE));
		digits[2] = (cal.get(Calendar.SECOND));
		digits[3] = (cal.get(Calendar.DAY_OF_MONTH));
		digits[4] = (cal.get(Calendar.DAY_OF_WEEK));

		int angle = 6;
		MyTransformation2D trn = new MyTransformation2D();

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

		int copyMin = (int) digits[1];
		trn = new MyTransformation2D();
		trn.rotateByDegreesAroundOrigin(angle);
		while(copyMin - 12 >= 0){
			smHand.rotateAroundAnchor(trn);
			copyMin = copyMin - 12;
		}
	}

	// Intialises mini displays
	public void setupMiniDisplays(){
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
	public void addDrawableObjects(){
		drw.add(mdL);
		drw.add(mdR);
		drw.add(dateAndDay);
		drw.add(timeStatus);
		drw.add(stpHandSm);
		drw.add(stpHand);
		drw.add(bHand);
		drw.add(scHand);
		drw.add(smHand);
	}

	// Mark all instances of intervals to be put onto the convas
	public void changeStopwatchThreadState(){
		if(!stpwtchThread.isAlive()){
			stpwtchThread.start();
		}	
		else{
			stpwtchThread = new Thread(stpwtch); 
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		stpwtch.changeRunStatus();	
		changeStopwatchThreadState();
	}

	class Stopwatch implements Runnable{
		private boolean runStopWatch = false;

		public void run() {
			long loopStartTime, loopEndTime, totalTime,lag = 0;
			MyTransformation2D trnSec = new MyTransformation2D();
			trnSec.rotateByDegreesAroundOrigin(6);
			int secs = 0;
			try {
				while(runStopWatch){
					loopStartTime = System.nanoTime();
					stpHand.rotateAroundAnchorSmall(trnSec);
					secs++;

					if(secs==60){
						stpHandSm.rotateAroundAnchorSmall(trnSec);
						secs = 0;
					}

					paint(getGraphics());
					loopEndTime = System.nanoTime();

					Thread.sleep(Math.max((1000000000- (loopEndTime-loopStartTime) - lag)/1000000,0));
					totalTime = System.nanoTime();
					lag = totalTime - loopStartTime - 1000000000;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void changeRunStatus(){
			if(runStopWatch){
				runStopWatch = false;
			}
			else{
				runStopWatch = true;
			}
		}

		public boolean getRunStatus(){
			return runStopWatch;
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
