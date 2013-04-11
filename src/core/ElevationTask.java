package core;

import java.util.*;

import org.apache.log4j.Logger;

public class ElevationTask {
	static final Logger log = Logger.getLogger(ElevationTask.class);
	protected final int totalStoreys;
	protected final int totalPassengers;
	protected final  Elevator elevator;
	protected final List<Storey> storeys;
	private final Thread elevatorThread;
	
	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		log.info("Hello loggy!");
		this.totalStoreys = totalPassengers;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>();
		
		for (int i = 0; i < totalStoreys; i++) {
			storeys.add(new Storey());
		}
		
		elevatorThread = new Thread(new ElevatorThread(this));
	}

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
	
	public void startElevation() {
		elevatorThread.start();
	}
}
