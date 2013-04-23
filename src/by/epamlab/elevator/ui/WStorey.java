package by.epamlab.elevator.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.jsfml.system.Vector2i;

public class WStorey extends WBase {
	private BufferedImage image;
	private boolean safemode;
	private WText text;
	
	private int untransportedCount;
	private int transportedCount;
	private int storeyNo;
	private WText signText;
	private WText arrivedText;
	private WText awaitingText;
	
	public WStorey() {
		super();
		init();
	}
	
	public WStorey(Vector2i position) {
		super(position);
		init();
	}
	
	public void init() {
		text = new WText(position);
		text.setFontFamily("Arilal");
		//text.setOrigin(new Vector2i(5, text.getFontSize()));
		text.setColor(new Color(40, 80, 30));
		
		signText = new WText(position);
		signText.setFontFamily("Garamond");
		signText.setColor(new Color(240, 220, 160));
		signText.setFontSize(26);
		
		arrivedText = new WText(position);
		awaitingText = new WText(position);
		//arrivedText.setFontFamily("Garamond");
		//signText.setColor(new Color(240, 220, 160));
		//signText.setFontSize(26);
		
		
		try {
			image = ImageIO.read(new File("resources/storey.png"));
			size = new Vector2i(image.getWidth(), image.getHeight());
			safemode = false;
		} catch (IOException e) {
			safemode = true;
			System.err.println("Cant load image! Working in safe mode!");
		}
	}
	
	@Override
	public void setPosition(Vector2i position) {
		super.setPosition(position);
		text.setPosition(position);
		//FIXME FIXME FIXME!!!
		//MAKE HUMAN UNDERSTANDABLE 'ORIGIN' IMPL
		//MAKE CONTAINERS! (see sfgui, gtk+, etc)
		signText.setPosition(Vector2i.add(position, new Vector2i(42, 4)));
		arrivedText.setPosition(Vector2i.add(position, new Vector2i(226, 30)));
		awaitingText.setPosition(Vector2i.add(position, new Vector2i(256, 30)));
	}
	
	public void setStoreyNo(int storeyNo) {
		this.storeyNo = storeyNo;
		rebuildText();
	}
	
	public void setUntransportedCount(int untransportedCount) {
		this.untransportedCount = untransportedCount;
		rebuildText();
	}
	
	public void setTransportedCount(int transportedCount) {
		this.transportedCount = transportedCount;
		rebuildText();
	}
	
	public void rebuildText() {
		text.setText("#" + storeyNo + "入 " + untransportedCount + /*出*/"  OUT " + transportedCount);
		signText.setText(String.valueOf(storeyNo));
		arrivedText.setText("" + transportedCount);
		awaitingText.setText("" + untransportedCount);
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		
		if (!safemode) {
			target.drawImage(image, position.x, position.y, null);
		}
		
		//text.draw(target);
		signText.draw(target);
		arrivedText.draw(target);
		awaitingText.draw(target);
	}
	
}
