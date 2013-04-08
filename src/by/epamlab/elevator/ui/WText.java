package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import org.jsfml.system.Vector2i;

public class WText extends WBase {
	private String text;
	private AttributedString as;
	private Font font;

	public WText() {
		super();
		text = new String();
		init();
	}
	
	public WText(String text) {
		super();
		this.text = text;
		init();
	}

	public WText(String text, Vector2i position) {
		super();
		this.text = text;
		this.position = position;
		init();
	}

	public void init() {
		as = new AttributedString(text);
		as.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW); //I'm just lazy
		as.addAttribute(TextAttribute.FONT, new Font("Impact", 0, 30));
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		init();
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void draw(Graphics target) {
		target.drawString(as.getIterator(), position.x, position.y);
	}

	@Override
	public void update(float dt) {
		//Text do nothing, isn't it?
	}
}
