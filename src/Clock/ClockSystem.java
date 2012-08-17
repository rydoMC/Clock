package Clock;
/**
 * @author RyanMcNulty
 * This class of ClockSystem is to create the window frame and allow others classes to undergo 
 * methods in order to create a clock instance
 * 
 * Version 3.1: Mon - 8/11/10
 */

import javax.swing.JFrame;

public class ClockSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	private MyCanvas cvs;

	public ClockSystem(){
		super();
		setupFrameAttributes();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		cvs = new MyCanvas();
		add(cvs);
		setVisible(true);
		cvs.run();
	}
	
	// Apply any JFrame attributes to setup the frame as wanted.
	public void setupFrameAttributes(){
		setTitle("Ryan McNultys Clock");
		setSize(400,422);
		setLocation(400,200);
		setResizable(false);
	}
	
	public static void main(String[] args){
		new ClockSystem();
	}
	
}

