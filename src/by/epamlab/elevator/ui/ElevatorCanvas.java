package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

public class ElevatorCanvas extends JPanel {
	private DynamicWidget box;
	private Clock clock;
	private WText fpsTextLabel;
	private float frameTime;
	private float frameTimeSec;
	private float tenFramesTime;
	private int fpsUpdateCounter;
	
	public ElevatorCanvas() {
		super();
		init();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		box = new BaseWidget();
		fpsUpdateCounter = 0;
		tenFramesTime = 0;
		fpsTextLabel = new WText("TEST", new Vector2i(4, 30));
		clock = new Clock();
		
		// Running redrawning in its own thread
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                while (true) {
                	repaint();
                    Thread.sleep(25);//FIXME Constant
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
		box.update(frameTime);
		if (fpsUpdateCounter++ > 10) {
			fpsTextLabel.setText(String.format("%.0f", (tenFramesTime/11)*1000));
			fpsUpdateCounter = 0;
			tenFramesTime = 0;
		} else {
			tenFramesTime += frameTimeSec;
		}
		//DRAW
		Graphics g2d = (Graphics2D) g;
		super.paint(g);
		box.draw(g2d);
		fpsTextLabel.draw(g);
	}
}
