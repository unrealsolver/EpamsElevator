package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storey {
	private final Object dispatchLock = new Object();
	private List<Passenger> dispatchContainer = new ArrayList<Passenger>();
	private List<Passenger> arrivalContainer = new ArrayList<Passenger>();
	
	public Storey() {
		//?
	}
	
	public Passenger givePassenger() {
		return null; //FIXME STRONGLY!
	}
	
	/**
	 * Little bit tricky. Adds passenger to dispatch container
	 * @param passenger - exactly this passenger
	 */
	public synchronized void addPassenger(Passenger passenger) {
		dispatchContainer.add(passenger);
	}
	
	/**
	 * Adds passenger to arrival container
	 * @param passenger
	 */
	public synchronized void takePassenger(Passenger passenger) { 
		arrivalContainer.add(passenger);
	}
	
	public synchronized int getUntransportedPassengers() {
		return dispatchContainer.size();
	}
	
	public synchronized int getTransportedPassengers() {
		return arrivalContainer.size();
	}
	
	public synchronized List<Passenger> getArrivedPassengers() {
		return arrivalContainer;
	}
	
	/**
	 * Returns total count of the passengers in both dispatch/arrival containers
	 * @return - count of the passengers 
	 */
	public synchronized int getTotalPassengers() {
		return dispatchContainer.size() + arrivalContainer.size();
	}
	
	public synchronized void removePassenger(Passenger passenger) {
		dispatchContainer.remove(passenger);
		System.err.println(dispatchContainer.size());
	}
	
	/**
	 * getLock means get-dispatch-lock.
	 * @return lock object
	 */
	public synchronized Object getLock() {
		return dispatchLock;
	}
	
	/**
	 * Searchs only in dispatch container!
	 * @param otherPassenger
	 * @return true, if passenger is presents on storey
	 */
	public synchronized boolean hasPassenger(Passenger otherPassenger) {
		for (Passenger passenger : dispatchContainer) {
			if (passenger == otherPassenger) {
				return true;
			}
		}
		
		return false;
	}
}
