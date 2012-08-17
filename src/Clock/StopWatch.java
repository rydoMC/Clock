package Clock;

import java.awt.Color;

import PrivateGraphics.MyDrawing2D;
import PrivateGraphics.MyTransformation2D;

public class StopWatch extends MyDrawing2D implements Runnable {

	private boolean runStopWatch;
	private Hand stpHand, stpHandSm;

	
	public StopWatch(){
		runStopWatch = false;
		initHands();
		
		add(stpHand);
		add(stpHandSm);
	}
	
	private void initHands(){
		stpHand = new Hand(HandSize.StopWatch, Color.RED);
		stpHandSm = new Hand(HandSize.StopWatchSmall, Color.GREEN);
	}
	
	
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


	public void clearHands() {
		initHands();
	}
	
}