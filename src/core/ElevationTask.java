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
	protected final List<Storey> storeys;

	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		log(Level.INFO, "Hello from main!");
		this.totalStoreys = totalPassengers;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>();
		
		for (int i = 0; i < totalStoreys; i++) {
			storeys.add(new Storey());
		}
		
		elevatorThread = new Thread(new ElevatorThread(this));
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
	
	/* Usefull stuff */
	public void startElevation() {
		elevatorThread.start();
	}
	
	
	public synchronized void log(Level level, Object message) {
		log.log(level, message);
	}
}
