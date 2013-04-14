package core;

import org.apache.log4j.Level;

public class ElevatorThread implements Runnable{
	private final ElevationTask elevationTask;
	private final Elevator elevator;
	private final Object lock;
	private int totalPassengers;
	private boolean interactive;
	
	public ElevatorThread(ElevationTask elevationTask) {
		this.elevationTask = elevationTask;
		this.elevator = elevationTask.getElevator();
		this.lock= elevationTask.getElevatorLock();
		this.totalPassengers = elevationTask.totalPassengers;
		update();
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
	
	@Override
	public void run() {
		log(Level.INFO, "Another thread?");
		
		while (elevationTask.getPassengersTransported() < totalPassengers) {
			if (interactive) {
				try {
					synchronized (lock) {
						lock.wait();
					}

				} catch (InterruptedException e) {
					log(Level.ERROR, "thread was interrupted!");
				}
			}
			
			/* Open doors */
			/* Release elevators passengers */
			/* Invite passengers */
			Object storeyLock = elevationTask.getStorey(elevator.getStorey()).getLock();
			synchronized (storeyLock) {
				storeyLock.notifyAll();
			}
			
			/* Close doors */
			
			/* Move to next storey */
			elevator.gotoNextStorey();
			log(Level.INFO, "Elevator has been reached " + elevator.getStorey() + " storey.");
		}
	}
}
