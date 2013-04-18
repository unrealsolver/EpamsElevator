package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;

import org.jsfml.system.Vector2i;

public class WBase implements DynamicWidget {
	/* Inspired by SFML */
	static final Vector2i DEFAULT_POSITION = new Vector2i(0, 0);
	static final Vector2i DEFAULT_ORIGIN = new Vector2i(0 ,0);
	static final Vector2i DEFAULT_SIZE = new Vector2i(20, 20);
	
	protected Vector2i position = DEFAULT_POSITION;
	protected Vector2i origin = DEFAULT_ORIGIN;
	protected Vector2i size = DEFAULT_SIZE;
	
	public WBase() {

	}
	
	public WBase(Vector2i position) {
		this.position = position;
	}
	
	public WBase(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
	}
	
	public Vector2i getPosition() {
		return position;
	}

	public void setPosition(Vector2i position) {
		this.position = position;
	}

	public Vector2i getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector2i origin) {
		this.origin = origin;
	}
	
	@Override
	public void draw(Graphics target) {
		target.setColor(Color.WHITE);
		target.drawRect(position.x, position.y, size.x, size.y);
	}
	
	@Override
	public void update(float dt) {
		//pass
	}

}
