package core;

import org.apache.log4j.Level;

public class PassengerThread implements Runnable {
	private final ElevationTask elevationTask;
	private final Passenger passenger;
	
	public PassengerThread(ElevationTask elevationTask, Passenger passenger) {
		this.elevationTask = elevationTask;
		this.passenger = passenger;
	}
	
	//FIXME Duplicated code! See ElevatorThread
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
		while (true) {
			log(Level.INFO, "I'm alive!");
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
