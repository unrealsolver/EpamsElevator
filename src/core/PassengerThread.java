package core;

import org.apache.log4j.Level;

public class PassengerThread implements Runnable {
	private final ElevationTask elevationTask;
	private final Passenger passenger;
	private final ElevatorThread elevatorController;
	private Object lock;
	private boolean inElevator;
	
	public PassengerThread(ElevationTask elevationTask, Passenger passenger) {
		this.elevationTask = elevationTask;
		this.passenger = passenger;
		this.elevatorController = elevationTask.getElevatorController();
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
			//log(Level.INFO, "I came!");
			try {
				//System.out.println("Argh... Wait again...");
				synchronized (lock) {
					lock.wait();
				}
				
				try {
						synchronized (elevatorController.getWaitingLock()) {
							elevatorController.takePassenger(passenger);
							//passenger.setState(TransportationActions.BOARDING_OF_PASSENGER);
							log(Level.INFO, TransportationActions.BOARDING_OF_PASSENGER);
							inElevator = true;
						}
				} catch (ElevatorException e) {
					//System.err.println(e.getMessage());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		lock = elevatorController.getContainerLock();
		//log(Level.INFO, "I came into elevator!");
		Object waitingLock = elevatorController.getWaitingLock();
		
		while (inElevator) {
			try {
				//System.err.println("Not my storey!");
				synchronized (lock) {
					System.err.println("Passenger waiting");
					lock.wait();
				}
				
				synchronized (waitingLock) {
					if (elevatorController.getStorey() == passenger.getDestinationStorey()) {
						elevatorController.deboardPassenger(passenger);
						elevationTask.transportPassenger();
						inElevator = false;
						log(Level.INFO, TransportationActions.DEBOARDING_OF_PASSENGER);
						waitingLock.notifyAll();
				}
				}
				
				
				/*Object waitingLock = elevatorController.getWaitingLock();
				synchronized (waitingLock) {
					waitingLock.notify();
				}*/
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//log(Level.INFO, "I fell out :(");
	}
}
