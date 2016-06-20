package by.epam.tc.hr_system.dao;

import java.util.List;

import by.epam.tc.hr_system.domain.Person;
import by.epam.tc.hr_system.exception.DAOException;

public interface IPersonDAO {

	int registerPerson(String login, String password, String role)
			throws DAOException;

	boolean addPersonInformation(Person person) throws DAOException;

	Person searchPersonByEmail(String email) throws DAOException;

	List<Person> searchPersonByNames(String name, String surname,
			String middleName) throws DAOException;

}
