package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

public interface DynamicWidget {
	public void draw(Graphics target);
	public void update(float dt);
}
