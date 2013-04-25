package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.lang.model.element.ElementKind;
import javax.swing.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

import by.epamlab.elevator.core.ElevationTask;
import by.epamlab.elevator.core.TransportationState;

import com.sun.java.swing.plaf.windows.WindowsBorders;


@SuppressWarnings("serial")
public class ElevatorCanvas extends JPanel {
	private final ElevationTask elevationTask;
	private ObjectManager objects = new ObjectManager();
	private Clock clock;
	private TransportationState lastElevationState;
	
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
		
		//FIXME Make a ResourceManager
		try {
			elevator.loadFromFile("resources/elevator.png");
		} catch (IOException e) {
			System.err.println("Image file not found! " + e.getMessage());
		}
		
		Vector2i ownSize = elevator.getSize();
		Vector2i adjustment = new Vector2i(-4, 17);
		
		//FIXME Move to class
		pos = new Vector2i(	pos.x + origin.x/2 - ownSize.x/2 + adjustment.x,
							pos.y + origin.y/2 - ownSize.y/2 + adjustment.y);
		
		elevator.setPosition(pos);
	
		objects.addLast(storeys);
		objects.add(elevator);
		
		clock = new Clock();
		
		lastElevationState = TransportationState.ABORTED;
		
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
		
		//FIXME Make a class for a GodButton. It's bad idea to handle logic in main loop
		if (lastElevationState != elevationTask.getState()) {
			lastElevationState = elevationTask.getState();
			String newText = "";

			switch (lastElevationState) {
				case ABORTED: newText = "ABORTED: VIEW LOG"; break;
				case COMPLETED: newText = "DONE. VIEW LOG"; break;
				case IN_PROGRESS: newText = "ABORT"; break;
				case NOT_STARTED: newText = "START"; break;
			}
			
			godButton.setText(newText);
		}
		
		//FIXME Maybe it will be better with in-class handling
		//FIXME Too difficult
		storeys.setUntransportedDistribution(elevationTask.getUntransportedDistribution());
		storeys.setTransportedDistribution(elevationTask.getTransportedDistribution());
		storeys.moveToStorey(elevationTask.getElevator().getStorey());
		
		elevator.setText("" + elevationTask.getElevator().getPassengers());
		
		//DRAW
		super.paint(g);
		objects.drawAll(g);
		
	}
}
