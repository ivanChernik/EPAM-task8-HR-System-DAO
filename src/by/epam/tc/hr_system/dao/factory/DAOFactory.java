package by.epam.tc.hr_system.dao.factory;

import by.epam.tc.hr_system.dao.IEducationDAO;
import by.epam.tc.hr_system.dao.IPersonDAO;
import by.epam.tc.hr_system.dao.ISkillDAO;
import by.epam.tc.hr_system.dao.IVacancyDAO;
import by.epam.tc.hr_system.dao.impl.EducationDAOImpl;
import by.epam.tc.hr_system.dao.impl.PersonDAOImpl;
import by.epam.tc.hr_system.dao.impl.SkillDAOImpl;
import by.epam.tc.hr_system.dao.impl.VacancyDAOImpl;
import by.epam.tc.hr_system.exception.DAOException;

public class DAOFactory {
	private static DAOFactory instance = new DAOFactory();
	private static IPersonDAO personDAO = new PersonDAOImpl();
	private static IEducationDAO educationDAO = new EducationDAOImpl();
	private static ISkillDAO skillDAO = new SkillDAOImpl();
	private static IVacancyDAO vacancyDAO = new VacancyDAOImpl();

	public static DAOFactory getInstance() {
		return instance;
	}

	public static IPersonDAO getPersonDAO() throws DAOException {
		return personDAO;
	}
	
	public static IEducationDAO getEducationDAO() throws DAOException {
		return educationDAO;
	}
	
	public static ISkillDAO getSkillDAO() throws DAOException {
		return skillDAO;
	}
	
	public static IVacancyDAO getVacancyDAO() throws DAOException {
		return vacancyDAO;
	}

}
