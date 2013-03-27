package by.epamlab.elevator.ui;

import java.awt.Graphics;

public class WBox implements DynamicWidget {
	@Override
	public void draw(Graphics target) {
		target.drawRect(15, 15, 80, 50);
		
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

}
