package core;

import org.apache.log4j.Level;

import by.epamlab.elevator.ui.ElevatorCanvas;

public class ElevatorThread implements Runnable{
	private final ElevationTask elevationTask;
	private final Elevator elevator;
	private final Object lock;
	private final Object containerLock;
	private final Object waitingLock;
	private int totalPassengers;
	private boolean interactive;
	
	public ElevatorThread(ElevationTask elevationTask) {
		this.elevationTask = elevationTask;
		this.elevator = elevationTask.getElevator();
		this.lock = elevationTask.getElevatorLock();
		this.totalPassengers = elevationTask.totalPassengers;
		containerLock = new Object();
		waitingLock = new Object();
		update();
	}
	
	public synchronized Object getLock() {
		return lock;
	}
	
	public synchronized Object getContainerLock() {
		return containerLock;
	}
	
	public synchronized Object getWaitingLock() {
		return waitingLock;
	}
	
	public synchronized int getStorey() {
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
	
	public synchronized int getPassengers() {
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
			elevationTask.getStorey(elevator.getStorey()).removePassenger(passenger);
		} else {
			throw new ElevatorException("Wrong direction");
		}
		
	}
	
	@Override
	public void run() {
		log(Level.INFO, "Another thread?");
		
		while (elevationTask.getPassengersTransported() < totalPassengers) {
			//log(Level.INFO, "Elevator has been reached " + elevator.getStorey() + " storey.");
			
			/* Open doors */
			/* Release elevators passengers */
			synchronized (containerLock) {
				containerLock.notifyAll();
			}
			
			System.err.println("In elevator: " + elevator.getPassengers());
			
			synchronized (waitingLock) {
			if ((elevator.getPassengers() > 0) && elevator.atDistination()) {
				try {
					System.err.println("Elevator waiting");
					waitingLock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}
			/*synchronized (waitingLock) {
				try {
					waitingLock.wait(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			/* Invite passengers */
			Object storeyLock = elevationTask.getStorey(elevator.getStorey()).getLock();
			synchronized (storeyLock) {
				storeyLock.notifyAll();
			}
			
			/* Close doors */
			
			if (interactive) {
				try {
					synchronized (lock) {
						lock.wait();
					}

				} catch (InterruptedException e) {
					log(Level.ERROR, "thread was interrupted!");
				}
			}
			
			/* Move to next storey */
			elevator.gotoNextStorey();
		}
		
		elevationTask.validate();
	}
}
