package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

import com.sun.java.swing.plaf.windows.WindowsBorders;

import core.ElevationTask;

@SuppressWarnings("serial")
public class ElevatorCanvas extends JPanel {
	private final ElevationTask elevationTask;
	private ObjectManager objects = new ObjectManager();
	private Clock clock;
	
	//For controlling main button
	private JButton godButton;
	
	private WStoreyArray storeys;
	private WElevator elevator;
	private float frameTime = 0;
	
	public ElevatorCanvas(ElevationTask elevationTask, JButton godButton) {
		super();
		this.elevationTask = elevationTask; 
		this.godButton = godButton;
		init();
	}
	
	public void init() {
		this.setBackground(Color.gray);
		storeys = new WStoreyArray(elevationTask.getTotalStoreys());
		
		Vector2i pos = new Vector2i(40, 250);
		storeys.setPosition(pos);
		Vector2i origin = storeys.getOrigin();
		
		elevator = new WElevator(pos);
		
		try {
			elevator.loadFromFile("resources/elevator.png");
		} catch (IOException e) {
			System.err.println("Image file not found! " + e.getMessage());
		}
		
		Vector2i ownSize = elevator.getSize();
		Vector2i adjustment = new Vector2i(-4, 17);
		
		pos = new Vector2i(	pos.x + origin.x/2 - ownSize.x/2 + adjustment.x,
							pos.y + origin.y/2 - ownSize.y/2 + adjustment.y);
		
		elevator.setPosition(pos);
	
		objects.addLast(storeys);
		objects.add(elevator);
		
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
		clock.restart();
		
		//UPDATE
		objects.updateAll(frameTime);
		
		storeys.setUntransportedDistribution(elevationTask.getUntransportedDistribution());
		storeys.setTransportedDistribution(elevationTask.getTransportedDistribution());
		storeys.moveToStorey(elevationTask.getElevator().getStorey());
		
		elevator.setText("" + elevationTask.getElevator().getPassengers());
		
		//DRAW
		super.paint(g);
		objects.drawAll(g);
		
	}
}
