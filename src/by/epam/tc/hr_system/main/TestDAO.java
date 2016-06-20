package by.epam.tc.hr_system.main;

import java.sql.Date;

import by.epam.tc.hr_system.dao.IPersonDAO;
import by.epam.tc.hr_system.dao.factory.DAOFactory;
import by.epam.tc.hr_system.domain.Person;
import by.epam.tc.hr_system.exception.DAOException;

public class TestDAO {

	public static void main(String[] args) throws Exception {
		IPersonDAO personDAO = null;
		personDAO = DAOFactory.getPersonDAO();

		Date dateSql = Date.valueOf("1981-10-10");
		Person person = null;
		person = new Person("олег", "гринюк", "сергеевич", dateSql,
				"olegk@mail.com", "+355291111111");

		try {

			// person.setId(personDAO.registerPerson("olegG", "olegG96",
			// "applicant"));
			//
			// personDAO.addPersonInformation(person);

			Person personEmail = personDAO
					.searchPersonByEmail("branovecA@gmail.com");
			System.out.println(personEmail);
		} catch (DAOException e) {
			throw e;
		}
	}

}
