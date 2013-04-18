package by.epamlab.elevator.ui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jsfml.system.Vector2i;

public class WStorey extends WBase {
	private BufferedImage image;
	private boolean safemode;
	
	public WStorey() {
		super();
		init();
	}
	
	public WStorey(Vector2i position) {
		super(position);
		init();
	}
	
	public void init() {
		try {
			image = ImageIO.read(new File("resources/storey.png"));
			size = new Vector2i(image.getWidth(), image.getHeight());
			safemode = false;
		} catch (IOException e) {
			safemode = true;
			System.err.println("Cant load image! Working in safe mode!");
		}
	}
	
	public Vector2i getSize() {
		return size;
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		if (!safemode) {
			target.drawImage(image, position.x, position.y, null);
		}
	}
	
}
