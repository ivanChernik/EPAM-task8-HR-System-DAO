package by.epam.tc.hr_system.dao.factory;

import by.epam.tc.hr_system.dao.IPersonDAO;
import by.epam.tc.hr_system.dao.impl.PersonDAOImpl;
import by.epam.tc.hr_system.exception.DAOException;

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
