package by.epamlab.elevator.ui;

import java.awt.Graphics;

import org.jsfml.system.Vector2i;

public class WElevator extends WTexturedBox {
	private WText inElevatorText;
	
	public WElevator(Vector2i position) {
		super(position);
		init();
	}
	
	public void init() {
		inElevatorText = new WText(position);
		inElevatorText.setFontSize(36);
	}

	//FIXME do not override setPosition
	@Override
	public void setPosition(Vector2i position) {
		super.setPosition(position);
		inElevatorText.setPosition(Vector2i.add(position, new Vector2i(24, 12)));
	}
	
	public void setText(String text) {
		inElevatorText.setText(text);
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		
		inElevatorText.draw(target);
	}
}
