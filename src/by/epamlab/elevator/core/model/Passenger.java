package by.epamlab.elevator.core.model;

import by.epamlab.elevator.core.IDManager;
import by.epamlab.elevator.core.TransportationState;

public class Passenger {
	private final int id;
	private final int destinationStorey;
	private TransportationState state;

	public Passenger(int destinationStorey) {
		id = IDManager.getNewID();
		this.destinationStorey = destinationStorey;
		this.setState(TransportationState.NOT_STARTED);
	}

	public int getId() {
		return id;
	}

	public int getDestinationStorey() {
		return destinationStorey;
	}

	public TransportationState getState() {
		return state;
	}

	public void setState(TransportationState state) {
		this.state = state;
	}

}
