package core;

import java.util.*;

public class ElevationTask {
	protected final int totalStoreys;
	protected final int totalPassengers;
	protected final  Elevator elevator;
	protected List<Storey> storeys;;

	public ElevationTask(int totalStoreys, int totalPassengers, int elevatorCapacity) {
		this.totalStoreys = totalPassengers;
		this.totalPassengers = totalPassengers;
		
		elevator = new Elevator(elevatorCapacity, totalStoreys);
		storeys  = new ArrayList<Storey>();
		
		for (int i = 0; i < totalStoreys; i++) {
			storeys.add(new Storey());
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
