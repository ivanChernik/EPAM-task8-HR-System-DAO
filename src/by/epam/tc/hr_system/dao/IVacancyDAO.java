package by.epam.tc.hr_system.dao;

import by.epam.tc.hr_system.domain.Vacancy;
import by.epam.tc.hr_system.exception.DAOException;

public interface IVacancyDAO {

	boolean addVacancy(Vacancy vacancy, int idHR) throws DAOException;

	boolean updateVacancy(Vacancy vacancy, int idHR) throws DAOException;

	boolean removeVacancy(int idVacancy) throws DAOException;

}
