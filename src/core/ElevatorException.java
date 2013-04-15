package core;

@SuppressWarnings("serial")
public class ElevatorException extends RuntimeException {
	
	public ElevatorException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
		
	}
}
