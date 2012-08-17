package Clock;

import java.awt.Color;
import PrivateGraphics.MyDrawing2D;
import PrivateGraphics.MyPoint2D;
import PrivateGraphics.MyRect2D;

/**
 * This class is to allow the representation of two miniature displays on the clock canvas.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @created Aug 17, 2012
 */
public class MiniDisplay extends MyDrawing2D{
	public MiniDisplay(){
		MyRect2D backPolygonLeft = new MyRect2D(new MyPoint2D(100,550), new MyPoint2D(400,450), Color.WHITE);
		MyRect2D insidePolygonLeft = new MyRect2D(new MyPoint2D(110,540), new MyPoint2D(390, 460), Color.BLACK);
		
		add(backPolygonLeft);
		add(insidePolygonLeft);
	}
}
