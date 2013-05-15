package by.epamlab.elevator.ui.widgets;

import java.awt.Graphics;

public interface DynamicWidget {
	public void draw(Graphics target);
	public void update(float dt);
}
