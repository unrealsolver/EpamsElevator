package core;

import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ElevationTask {
	/* Locks */
	private final Object elevatorLock = new Object();
	
	/* Logger */
	static final Logger log = Logger.getLogger(ElevationTask.class);
	
	/* Threads */
	private final Thread elevatorThread;
	
	/* Content */
	protected final int totalStoreys;
	protected final int totalPassengers;
	protected final  Elevator elevator;
	private final ElevatorThread elevatorController;
	protected final List<Storey> storeys;
	private int passengersTransported = 0;
	private boolean interactive = false;

	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		log(Level.INFO, "Hello from main!");
		this.totalStoreys = totalPassengers;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>();
		
		for (int i = 0; i < totalStoreys; i++) {
			storeys.add(new Storey());
		}
		
		elevatorController = new ElevatorThread(this);
		elevatorThread = new Thread(elevatorController);
	}

	/* Boilerplate */
	public int getTotalStoreys() {
		return totalStoreys;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public List<Storey> getStoreys() {
		return storeys;
	}
	
	public Logger getLogger() {
		return log;
	}
	
	public Object getElevatorLock() {
		return elevatorLock;
	}
	
	public int getPassengersTransported() {
		return passengersTransported;
	}
	
	public synchronized void transportPassenger() {
		passengersTransported++;
	}
	
	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
		updateAll();
	}
	
	public boolean isInteractive() {
		return interactive;
	}
	
	/* Usefull stuff */
	public void updateAll() {
		elevatorController.update();
	}
	
	public void startElevation() {
		elevatorThread.start();
	}
	
	public boolean hasPassengers() {
		return false;
	}
	
	public synchronized void log(Level level, Object message) {
		log.log(level, message);
	}
}
