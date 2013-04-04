package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.awt.geom.Dimension2D;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class BaseWidget implements DynamicWidget {
	/* Inspired by SFML */
	static final Vector2i DEFAULT_POSITION = new Vector2i(0, 0);
	static final Vector2i DEFAULT_SIZE = new Vector2i(10, 10);
	protected Vector2i position;
	protected Vector2i size;
	
	//FIXME: JUST 4 FUN! TO BE REMOVED!:
	private Vector2i rotationCenter = new Vector2i(125, 125);
	private float rotation = 0;
	private int radius = 100;
	
	public BaseWidget() {
		position = DEFAULT_POSITION;
		size = DEFAULT_SIZE;
	}
	
	public BaseWidget(Vector2i position) {
		this.position = position;
	}
	
	public BaseWidget(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
	}
	
	@Override
	public void draw(Graphics target) {
		target.drawRect(position.x, position.y, size.x, size.y);
	}
	
	@Override
	public void update(float dt) {
		rotation += dt/100000;
		position = new Vector2i((int) (rotationCenter.x + Math.cos(rotation)*radius),
								(int) (rotationCenter.y + Math.sin(rotation)*radius));
	}

}
