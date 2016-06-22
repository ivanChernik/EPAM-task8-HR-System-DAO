package by.epam.tc.hr_system.dao;

import by.epam.tc.hr_system.domain.Education;
import by.epam.tc.hr_system.exception.DAOException;

public interface IEducationDAO {

	boolean addEduction(Education eduction) throws DAOException;

	boolean removeEduction(int idEduction) throws DAOException;

	boolean updateEduction(Education eduction) throws DAOException;

}
