package core;

import org.apache.log4j.Level;

public class ElevatorThread implements Runnable{
	private final ElevationTask elevationTask;
	private final Elevator elevator;
	
	public ElevatorThread(ElevationTask elevationTask) {
		this.elevationTask = elevationTask;
		this.elevator = elevationTask.getElevator();
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
		
		while (true /* Has untransported passengers */) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log(Level.ERROR, "thread was interrupted!");
			}
			
			/* Open doors */
			/* Release elevators passengers */
			/* Invite passengers */
			/* Close doors */
			
			/* Move to next storey */
			elevator.gotoNextStorey();
			log(Level.INFO, "Elevator has been reached " + elevator.getStorey() + " storey.");
		}
	}
}
