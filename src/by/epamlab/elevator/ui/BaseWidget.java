package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.awt.geom.Dimension2D;

public class BaseWidget implements DynamicWidget {
	private Dimension2D position;
	private Dimension2D size;
	
	@Override
	public void draw(Graphics target) {
		target.drawRect(15, 15, 80, 50);
		size.x;
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

}
