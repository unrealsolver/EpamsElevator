package by.epamlab.elevator.core;

public class IDManager {
	private static int id = 0;
	
	private IDManager() {};
	
	public static int getNewID() {
		return id++;
	}
}
