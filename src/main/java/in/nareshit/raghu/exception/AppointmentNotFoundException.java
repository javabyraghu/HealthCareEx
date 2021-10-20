package in.nareshit.raghu.exception;

public class AppointmentNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppointmentNotFoundException() {
	}

	public AppointmentNotFoundException(String message) {
		super(message);
	}
}
