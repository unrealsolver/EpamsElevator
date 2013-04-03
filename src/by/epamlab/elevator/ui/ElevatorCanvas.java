package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import org.jsfml.system.Clock;

public class ElevatorCanvas extends JPanel {
	private DynamicWidget box;
	private JLabel fpsLabel;
	private Clock clock;
	private float frameTime;
	
	public ElevatorCanvas() {
		super();
		init();
		startAnimation();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		box = new BaseWidget();
		fpsLabel = new JLabel("<FPS>");
		fpsLabel.setLocation(0, 0);
		fpsLabel.setSize(50, 40);
		fpsLabel.setHorizontalTextPosition(0);
		fpsLabel.setBackground(Color.WHITE);
		fpsLabel.setOpaque(true);
		add(fpsLabel);
		clock = new Clock();
	}
	
	public void startAnimation() {
        SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                	repaint();
                    Thread.sleep(20);
                }
            }
        };

        sw.execute();
    }
	//TODO Or paint component?
	public void paint(Graphics g) {
		frameTime = clock.getElapsedTime().asMicroseconds();
		clock.restart();
		
		//UPDATE
		box.update(frameTime);
		fpsLabel.setText(String.valueOf(frameTime));
		
		//DRAW
		Graphics g2d = (Graphics2D) g;
		super.paint(g);
		box.draw(g2d);
	}
}
