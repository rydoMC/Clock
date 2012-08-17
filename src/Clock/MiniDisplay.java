package Clock;
/**
 * @author RyanMcNulty
 * This class is to allow the representation of two miniature displays on the clock canvas.
 */
import java.awt.Color;
import PrivateGraphics.MyDrawing2D;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyRect2D;

public class MiniDisplay extends MyDrawing2D{
	private MyRect2D backPolygonLeft, insidePolygonLeft;
	
	public MiniDisplay(){
		backPolygonLeft = new MyRect2D(new MyPoint2D(100,550), new MyPoint2D(400,450), Color.WHITE);
		insidePolygonLeft = new MyRect2D(new MyPoint2D(110,540), new MyPoint2D(390, 460), Color.BLACK);
		addElements();
	}
	
	public void addElements(){
		add(backPolygonLeft);
		add(insidePolygonLeft);
	}	
}
