package PrivateGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MyText2D implements MyObject2D {
	public String contents;
	public MyPoint2D position;
	private Font myFont = new Font("Comic Sans", Font.BOLD, 24);
	
	public MyText2D(String s, MyPoint2D p){
		super();
		contents = s;
		position = p;
	}
	@Override
	public void draw(Graphics g) {
		g.setFont(myFont);
		g.setColor(Color.GREEN);
		g.drawString(contents, position.getDisplayX(), position.getDisplayY());
	}

	@Override
	public void transform(MyTransformation2D transformation) {
	}

	public MyText2D clone(){
		MyText2D copy = new MyText2D(contents,position);
		return copy;
	}
}
