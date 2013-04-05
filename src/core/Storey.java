package core;

import java.util.ArrayList;
import java.util.List;

public class Storey {
	private List<Passenger> dispatchContainer = new ArrayList<Passenger>();
	private List<Passenger> arrivalContainer = new ArrayList<Passenger>();
	
	public Storey() {
		//?
	}
	
	public Passenger givePassenger() {
		return null; //FIXME STRONLY!
	}
	
	public void takePassenger(Passenger passenger) { 
		arrivalContainer.add(passenger);
	}
	
	public int getTotalPassengers() {
		return dispatchContainer.size() + arrivalContainer.size();
	}
}
