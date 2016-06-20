package by.epam.tc.hr_system.exception;

public class ConnectionPoolException extends Exception {

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Exception e) {
		super(e);
	}

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}


}
