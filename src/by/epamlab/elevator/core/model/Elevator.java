package by.epamlab.elevator.core.model;

import java.util.ArrayList;
import java.util.List;

import by.epamlab.elevator.core.ElevatorException;

public class Elevator {
	private final int capacity;
	private int currentStorey;
	private final int lastStorey;
	private List<Passenger> passengers = new ArrayList<Passenger>();
	private boolean upward;
	
	public Elevator(int capacity, int lastStorey) {
		this.capacity = capacity;
		this.lastStorey = lastStorey - 1; //Because we start count from zero
		currentStorey = 0;
		upward = true;
	}
	
	public int getStorey() {
		return currentStorey;
	}
	
	public boolean isUpward() {
		return upward;
	}
	
	public int getPassengers() {
		return passengers.size();
	}
	
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger);
	}
	
	public void takePassenger(Passenger passenger) {
		if (!isFull()) {			
			passengers.add(passenger);
		} else {
			throw new ElevatorException("Elevator is full");
		}
	}
	
	public boolean atDistination() {
		for (Passenger passenger : passengers) {
			if (passenger.getDestinationStorey() == currentStorey) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isFull() {
		return capacity == passengers.size();
	}
	
	private void changeDirection() {
		upward = !upward;
	}
	
	public void gotoNextStorey() {
		currentStorey += upward ? 1 : -1;
		
		if (currentStorey == 0 || currentStorey == lastStorey) {
			changeDirection();
		}
	}
}
