package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ElevationTask {
	/* Locks */
	private final Object elevatorLock = new Object();
	
	/* Logger */
	static final Logger log = Logger.getLogger(ElevationTask.class);
	
	/* Threads */
	protected final ThreadGroup passengersThreadGroup;	// \ Yes, I know, but ThreadGroup 
	protected final List<Thread> passengersThreads;		// / can't start threads
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
		
		passengersThreadGroup = new ThreadGroup("PASSENGERS");
		passengersThreads = new LinkedList<Thread>();
		
		elevatorController = new ElevatorThread(this);
		elevatorThread = new Thread(elevatorController, "ELEVATOR");
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
		for (Thread thread : passengersThreads) {
			thread.start();
		}
		
		elevatorThread.start();
	}
	
	public boolean hasPassengers() {
		return false;
	}
	
	public synchronized void log(Level level, Object message) {
		log.log(level, message);
	}
}
