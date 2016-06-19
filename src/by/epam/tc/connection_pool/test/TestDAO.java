package by.epam.tc.connection_pool.test;

import java.sql.Date;
import java.sql.SQLException;

import by.epam.tc.connection_pool.dao.IPersonDAO;
import by.epam.tc.connection_pool.dao.factory.DAOFactory;
import by.epam.tc.connection_pool.domain.Person;
import by.epam.tc.connection_pool.exception.PersonDAOException;

public class TestDAO {

	public static void main(String[] args) throws Exception {
		DAOFactory daoFactory = DAOFactory.getInstance();
		IPersonDAO personDAO = null;
		try {
			personDAO = daoFactory.getPersonDAO();
		} catch (PersonDAOException e) {
			throw e;
		}

		Date dateSql = Date.valueOf("1981-10-10");
		Person person = null;
		person = new Person("олег", "гринюк", "сергеевич", dateSql,
				"olegk@mail.com", "+355291111111");

		try {

//			person.setId(personDAO.registerPerson("olegG", "olegG96",
//					"applicant"));
//			
//			personDAO.addPersonInformation(person);

			Person personEmail = personDAO
					.searchPersonByEmail("branovecA@gmail.com");
			System.out.println(personEmail);
		} catch (PersonDAOException /*| SQLException */e) {
			throw e;
		}
	}

}
