package by.epam.tc.connection_pool.dao.factory;

import by.epam.tc.connection_pool.dao.impl.PersonDAOImpl;
import by.epam.tc.connection_pool.exception.PersonDAOException;

public class DAOFactory {
	private static DAOFactory instance = new DAOFactory();

	public static DAOFactory getInstance() {
		return instance;
	}

	public PersonDAOImpl getPersonDAO() throws PersonDAOException {
		return new PersonDAOImpl();
	}

}
