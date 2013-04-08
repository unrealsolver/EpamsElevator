package core;

import java.util.*;

public class ElevationTask {
	private final int totalStoreys;
	private final int totalPassengers;
	private final  Elevator elevator;
	private final List<Storey> storeys;;

	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		this.totalStoreys = totalPassengers;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>(totalStoreys);
		
		for (Storey storey : storeys) {
			storey = new Storey();
		}
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
}
