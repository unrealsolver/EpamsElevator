package by.epamlab.elevator.core.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import by.epamlab.elevator.core.ElevatorController;
import by.epamlab.elevator.core.TransportationState;

public class ElevationTask {
	/* Locks */
	private final Object elevatorLock = new Object();
	
	/* Logger */
	protected static final Logger log = Logger.getLogger(ElevationTask.class);
	
	/* Threads */
	protected final ThreadGroup passengersThreadGroup;	// \ Yes, I know, but ThreadGroup 
	protected final List<Thread> passengersThreads;		// / can't start threads
	private final Thread elevatorThread;
	
	/* Content */
	private TransportationState state = TransportationState.NOT_STARTED;
	protected final int totalStoreys;
	protected final int totalPassengers;
	private float animationBoost = 0;
	protected final  Elevator elevator;
	private final ElevatorController elevatorController;
	protected final List<Storey> storeys;
	private int passengersTransported = 0;
	private boolean interactive = false;

	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		this.totalStoreys = totalStoreys;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>();
		
		for (int i = 0; i < totalStoreys; i++) {
			storeys.add(new Storey());
		}
		
		passengersThreadGroup = new ThreadGroup("PASSENGERS");
		passengersThreads = new LinkedList<Thread>();
		
		elevatorController = new ElevatorController(this);
		elevatorThread = new Thread(elevatorController, "ELEVATOR");
		
		//I think it isn't good idea...
		state = TransportationState.COMPLETED;
	}

	/* Boilerplate */
	public int getTotalStoreys() {
		return totalStoreys;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public float getAnimationBoost() {
		return animationBoost;
	}

	public void setAnimationBoost(float animationBoost) {
		this.animationBoost = animationBoost;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public synchronized List<Storey> getStoreys() {
		return storeys;
	}
	
	public Logger getLogger() {
		return log;
	}
	
	public Object getElevatorLock() {
		return elevatorLock;
	}
	
	public synchronized int getPassengersTransported() {
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
	
	public ElevatorController getElevatorController() {
		return elevatorController;
	}
	
	public TransportationState getState() {
		return state;
	}
	
	public void setState(TransportationState state) {
		this.state = state;
	}
	
	/* Usefull stuff */
	public int[] getUntransportedDistribution() {
		int[] distribution = new int[totalStoreys];
		int i = 0;
		
		for (Storey storey : storeys) {
			distribution[i] = storey.getUntransportedPassengers();
			i++;
		}
		
		return distribution;
	}
	
	public int[] getTransportedDistribution() {
		int[] distribution = new int[totalStoreys];
		int i = 0;
		
		for (Storey storey : storeys) {
			distribution[i] = storey.getTransportedPassengers();
			i++;
		}
		
		return distribution;
	}
	
	public void updateAll() {
		elevatorController.update();
	}
	
	public void startElevation() {
		for (Thread thread : passengersThreads) {
			thread.start();
		}
		
		elevatorThread.start();
		
		state = TransportationState.IN_PROGRESS;
	}
	
	public void interrupt() {
		passengersThreadGroup.interrupt();
		elevatorThread.interrupt();
		state = TransportationState.ABORTED;
	}
	
	public synchronized void validate() {
		System.err.println("Threads: " + passengersThreadGroup.activeCount());
		
		int untransported = 0;
		for (Storey storey : storeys) {
			untransported += storey.getUntransportedPassengers();
		}
		
		System.err.println("Untransported: " + untransported);
		
		int transported = 0;
		for (Storey storey : storeys) {
			transported += storey.getTransportedPassengers();
		}
		
		System.err.println("Transported: " + transported + "/" + totalPassengers);
		
		System.err.println("In elevator: " + elevator.getPassengers());
		
		boolean passengersAtPlace = true;
		
		for (int i = 0; i < storeys.size(); i++) {
			for (Passenger passenger : storeys.get(i).getArrivedPassengers()) {
				if (passenger.getDestinationStorey() != i) {
					passengersAtPlace = false;
					System.err.println("Error! Wrong destination! " + i + " but needed " + passenger.getDestinationStorey());
				}
			}
		}
		
		if (passengersAtPlace) {
			System.err.println("All passengers transported correctly!");
		}
	}
	
	public Storey getStoreyByPassenger(Passenger passenger) {
		for (Storey storey : storeys) {
			if (storey.hasPassenger(passenger)) {
				return storey;
			}
		}
		
		return null;
	}
	
	public Storey getStorey(int storey) {
		return storeys.get(storey);
	}
	
	public synchronized void log(Level level, Object message) {
		log.log(level, message);
	}
}
