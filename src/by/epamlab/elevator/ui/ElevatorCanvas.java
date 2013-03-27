package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ElevatorCanvas extends JPanel {
	private DynamicWidget box;
	
	public ElevatorCanvas() {
		super();
		init();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		box = new WBox();
	}
	
	//TODO Or paint component?
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		box.draw(g2d);
	}
}
