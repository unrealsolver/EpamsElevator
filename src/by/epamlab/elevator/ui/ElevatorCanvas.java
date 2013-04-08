package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

public class ElevatorCanvas extends JPanel {
	private ObjectManager objects = new ObjectManager();
	private Clock clock;
	private WText fpsTextLabel;
	private float frameTime = 0;
	private float frameTimeSec = 0;
	private float tenFramesTime = 0;
	private int fpsUpdateCounter = 0;
	
	public ElevatorCanvas() {
		super();
		init();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		fpsTextLabel = new WText(" ", new Vector2i(4, 30));
		objects.add(fpsTextLabel);
		
		for (int i = 0; i < 30; i++) {
			objects.add(new WRotatingBox());
		}

		clock = new Clock();
		
		// Running redrawning in its own thread
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                	repaint();
                    Thread.sleep(25); //FIXME Constant
                }
            }
        };

        sw.execute();
	}
	
	//TODO Or paint component?
	public void paint(Graphics g) {
		frameTime = clock.getElapsedTime().asMicroseconds();
		frameTimeSec =  clock.getElapsedTime().asSeconds();
		clock.restart();
		
		//UPDATE
		objects.updateAll(frameTime);
		
		//FIXME Remove this code! 
		if (fpsUpdateCounter++ > 10) {
			fpsTextLabel.setText(String.format("%.0f", (tenFramesTime/11)*1000));
			fpsUpdateCounter = 0;
			tenFramesTime = 0;
		} else {
			tenFramesTime += frameTimeSec;
		}
		
		//DRAW
		super.paint(g);
		objects.drawAll(g);
	}
}
