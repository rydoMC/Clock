package Clock;

import javax.swing.JFrame;

/**
 * This class' role is to create the window frame and allow others classes to undergo 
 * methods in order to create a clock instance.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @created Aug 17, 2012
 */
public class ClockSystem extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private MyCanvas cvs;

	
	public ClockSystem(){
		super();
		setupFrameAttributes();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Establish canvas
		initCanvas();
		
		// Add canvas and show
		add(cvs);
		setVisible(true);
		cvs.run();
	}
	
	
	/**
	 * Creates canvas and starts.
	 */
	private void initCanvas(){
		cvs = new MyCanvas();
	}
	
	
	/**
	 * Sets the attributes of the frame.
	 */
	private void setupFrameAttributes(){
		setTitle("Ryan McNultys Clock");
		setSize(400,422);
		setLocation(400,200);
		setResizable(false);
	}
	
	
	public static void main(String[] args){
		new ClockSystem();
	}	
}