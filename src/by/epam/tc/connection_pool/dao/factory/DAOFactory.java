package by.epam.tc.connection_pool.dao.factory;

import by.epam.tc.connection_pool.dao.IPersonDAO;
import by.epam.tc.connection_pool.dao.impl.PersonDAOImpl;
import by.epam.tc.connection_pool.exception.DAOException;

public class DAOFactory {
	private static DAOFactory instance = new DAOFactory();
	private static IPersonDAO personDAO = new PersonDAOImpl();
	

	public static DAOFactory getInstance() {
		return instance;
	}

	public static IPersonDAO getPersonDAO() throws DAOException {
		return personDAO;
	}

}
