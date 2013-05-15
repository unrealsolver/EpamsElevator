package by.epamlab.elevator.core;

import org.apache.log4j.Level;

public class ElevatorController implements Runnable{
	private final ElevationTask elevationTask;
	private final Elevator elevator;
	private final Object lock;
	private final Object containerLock;
	private final Object waitingLock;
	private int totalPassengers;
	private boolean interactive;
	private int lastStorey;
	
	public ElevatorController(ElevationTask elevationTask) {
		this.elevationTask = elevationTask;
		this.elevator = elevationTask.getElevator();
		this.lock = elevationTask.getElevatorLock();
		this.totalPassengers = elevationTask.totalPassengers;
		containerLock = new Object();
		waitingLock = new Object();
		update();
	}
	
	public Object getLock() {
		return lock;
	}
	
	public  Object getContainerLock() {
		return containerLock;
	}
	
	public  Object getWaitingLock() {
		return waitingLock;
	}
	
	public int getStorey() {
		return elevator.getStorey();
	}
	
	//I don't know how this method could be named else
	public void update() {
		this.interactive = elevationTask.isInteractive();
	}
	
	/**
	 * Wrapper for elevationTask.log(...)
	 * @param level - log level, Level.INFO, for example.
	 * @param message - Any object with toString method.
	 */
	public void log(Level level, Object message) {
		elevationTask.log(level, message);
	}
	
	public int getPassengers() {
		return elevator.getPassengers();
	}
	
	private synchronized void removePassenger(Passenger passenger) {
		elevator.removePassenger(passenger);
	}
	
	public synchronized void deboardPassenger(Passenger passenger) {
		removePassenger(passenger);
		elevationTask.getStorey(elevator.getStorey()).takePassenger(passenger);
	}
	
	public synchronized void takePassenger(Passenger passenger) {
		if ((passenger.getDestinationStorey() > elevator.getStorey()) == elevator.isUpward()) {
			elevator.takePassenger(passenger);
			elevationTask.getStoreyByPassenger(passenger).removePassenger(passenger);
		} else {
			throw new ElevatorException("Wrong direction");
		}
		
	}
	
	public synchronized boolean atDistination() {
		return elevator.atDistination();
	}
	
	@Override
	public void run() {
		log(Level.INFO, TransportationActions.STARTING_TRANSPORTATION);
		
		log(Level.INFO, "Another thread?");
		lastStorey = getStorey();
		
		while (elevationTask.getPassengersTransported() < totalPassengers) {
			
			/* Open doors */
			/* Release elevators passengers */
			
			synchronized (waitingLock) {
				while ((elevator.getPassengers() > 0) && atDistination()) {
					synchronized (containerLock) {	
						containerLock.notifyAll();
						waitingLock.notifyAll();
					}
				}
			}
			
			/* Invite passenger */
			Object storeyLock = elevationTask.getStorey(elevator.getStorey()).getLock();
			
			synchronized (waitingLock) {
					synchronized (storeyLock) {
						storeyLock.notifyAll();
						//FIXME While-loop
						//FIXME Difficult to explain, because...
						//пассажиры телепортируются в лифт независимо от его положения
				}
			}
			
			/* Close doors */
			
			if (interactive || elevationTask.getAnimationBoost() != 0) {
				try {
					synchronized (lock) {
						lock.wait();
					}

				} catch (InterruptedException e) {
					log(Level.ERROR, TransportationActions.ABORTING_TRANSPORTATION);
					break;
				}
			}
			
			/* Move to next storey */
			elevator.gotoNextStorey();
			log(Level.INFO, TransportationActions.MOVING_ELEVATOR +
					" " + lastStorey + " " + getStorey());
			lastStorey = getStorey();
		}
		
		log(Level.INFO, TransportationActions.COMPLETION_TRANSPORTATION);
		elevationTask.setState(TransportationState.COMPLETED);
		elevationTask.validate();
	}
}
