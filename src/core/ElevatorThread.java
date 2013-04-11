package core;

public class ElevatorThread implements Runnable{
	private final ElevationTask elevationTask;
	
	public ElevatorThread(ElevationTask elevationTask) {
		this.elevationTask = elevationTask;
	}
	
	@Override
	public void run() {
		System.out.println("Another thread!");
		while (true /* Has untransported passengers */) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.err.println("thread was interrupted!");
			}
			System.out.println("Whatsup?");
			/* Open doors */
			/* Release elevators passengers */
			/* Invite passengers */
			/* Close doors */
			/* Move to next storey */
		}
	}
}
