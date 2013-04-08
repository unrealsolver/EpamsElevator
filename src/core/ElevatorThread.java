package core;

public class ElevatorThread extends Thread{
	private final ElevationTask elevationTask;
	
	public ElevatorThread(ElevationTask elevationTask) {
		super("Elevator");
		this.elevationTask = elevationTask;
	}
	
	@Override
	public void run() {
		
	}
}
