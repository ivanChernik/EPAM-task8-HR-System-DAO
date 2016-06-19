package by.epam.tc.connection_pool.exception;

public class PersonDAOException extends DAOException {

	public PersonDAOException(String message) {
		super(message);
	}

	public PersonDAOException(Exception e) {
		super(e);
	}

	public PersonDAOException(String message, Exception e) {
		super(message, e);
	}

}
