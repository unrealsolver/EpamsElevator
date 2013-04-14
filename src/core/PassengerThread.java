package core;

import org.apache.log4j.Level;

public class PassengerThread implements Runnable {
	private final ElevationTask elevationTask;
	private final Passenger passenger;
	private final Object lock;
	private boolean inElevator;
	
	public PassengerThread(ElevationTask elevationTask, Passenger passenger) {
		this.elevationTask = elevationTask;
		this.passenger = passenger;
		lock = elevationTask.getStoreyByPassenger(passenger).getLock();
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
		inElevator = false;
		
		while (!inElevator) {
			log(Level.INFO, "I came!");
			try {
				synchronized (lock) {
					lock.wait();
				}
				inElevator = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (inElevator) {
			log(Level.INFO, "I came into elevator!");
			try {
				synchronized (lock) {
					lock.wait();
				}
				inElevator = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
