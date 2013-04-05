package core;

public class Passenger {
	private final int id;
	private final int destinationStorey;

	public Passenger(int destinationStorey) {
		id = IDManager.getNewID();
		this.destinationStorey = destinationStorey;
	}

	public int getId() {
		return id;
	}

	public int getDestinationStorey() {
		return destinationStorey;
	}

}
