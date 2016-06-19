package by.epam.tc.connection_pool.dao;

import java.sql.SQLException;
import java.util.List;

import by.epam.tc.connection_pool.domain.Person;
import by.epam.tc.connection_pool.exception.PersonDAOException;

public interface IPersonDAO {

	int registerPerson(String login, String password, String role)
			throws PersonDAOException,SQLException;

	boolean addPersonInformation(Person person) throws PersonDAOException;

	Person searchPersonByEmail(String email) throws PersonDAOException;

	List<Person> searchPersonByNames(String name, String surname,
			String middleName) throws PersonDAOException;

}
