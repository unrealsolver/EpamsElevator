package by.epamlab.elevator.ui.widgets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jsfml.system.Vector2i;


public class WTexturedBox extends WBase {
	private BufferedImage image;
	private boolean safemode;
	
	public WTexturedBox(Vector2i position) {
		super(position);
	}

	public void loadFromFile(String path) throws IOException {
			image = ImageIO.read(new File(path));
			size = new Vector2i(image.getWidth(), image.getHeight());
			safemode = false;
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		if (!safemode) {
			target.drawImage(image, position.x, position.y, null);
		}
	}
}
