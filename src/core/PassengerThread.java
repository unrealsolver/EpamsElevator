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

	// FIXME Duplicated code! See ElevatorThread
	/**
	 * Wrapper for elevationTask.log(...)
	 * 
	 * @param level
	 *            - log level, Level.INFO, for example.
	 * @param message
	 *            - Any object with toString method.
	 */
	public void log(Level level, Object message) {
		elevationTask.log(level, message);
	}

	@Override
	public void run() {
		inElevator = false;

		passenger.setState(TransportationState.IN_PROGRESS);
		try {
			while (!inElevator) {

				synchronized (lock) {
					lock.wait();
				}

				try {
					elevatorController.takePassenger(passenger);
					log(Level.INFO,	 TransportationActions.BOARDING_OF_PASSENGER +
							" " + elevatorController.getStorey());
					inElevator = true;
				} catch (ElevatorException e) {

				}

			}

			lock = elevatorController.getContainerLock();

			while (inElevator) {

				synchronized (lock) {
					lock.wait();
					if (elevatorController.getStorey() == passenger
							.getDestinationStorey()) {
						elevatorController.deboardPassenger(passenger);
						elevationTask.transportPassenger();
						inElevator = false;
						log(Level.INFO, TransportationActions.DEBOARDING_OF_PASSENGER +
								" " + elevatorController.getStorey());
					}
				}
			}
		} catch (InterruptedException e) {
			System.err.println("Passenger has been interrupted");
			passenger.setState(TransportationState.ABORTED);
		}
		
		passenger.setState(TransportationState.COMPLETED);
	}
}
