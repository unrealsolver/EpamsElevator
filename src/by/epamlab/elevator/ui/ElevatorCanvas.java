package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

import core.ElevationTask;

@SuppressWarnings("serial")
public class ElevatorCanvas extends JPanel {
	private final ElevationTask elevationTask;
	private ObjectManager objects = new ObjectManager();
	private Clock clock;
	private WText fpsTextLabel;
	private float frameTime = 0;
	private float frameTimeSec = 0;
	private float tenFramesTime = 0;
	private int fpsUpdateCounter = 0;
	
	private WBase box;
	
	public ElevatorCanvas(ElevationTask elevationTask) {
		super();
		this.elevationTask = elevationTask; 
		init();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		fpsTextLabel = new WText(" ", new Vector2i(4, 30));
		objects.add(fpsTextLabel);
		
		for (int i = 0; i < 30; i++) {
			objects.add(new WRotatingBox());
		}
		
		box = new WBase();
		objects.add(box);
		
		//objects.add(new WStorey(new Vector2i(50, 250)));
		objects.add(new WStoreyArray(new Vector2i(10, 100), 5));
		
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
		
		//Update box representing elevator
		box.setPosition(new Vector2i(
				box.getPosition().x,
				elevationTask.getElevator().getStorey() * 10)
		);
		
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
