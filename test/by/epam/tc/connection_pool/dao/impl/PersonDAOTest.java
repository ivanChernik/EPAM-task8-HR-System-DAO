package by.epam.tc.connection_pool.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import by.epam.tc.hr_system.dao.IPersonDAO;
import by.epam.tc.hr_system.dao.impl.PersonDAOImpl;
import by.epam.tc.hr_system.domain.Person;
import by.epam.tc.hr_system.exception.DAOException;

public class PersonDAOTest {
	private IPersonDAO personDAO;

	// private int idUser;

	@Before
	public void initPersonDAO() {
		personDAO = new PersonDAOImpl();
	}

	@After
	public void destroyPersonDAO() {
		personDAO = null;
	}

	@Ignore
	@Test
	public void registerPerson() {
		int idUser = 0;
		try {
			idUser = personDAO.registerPerson("remove", "remove", "hr");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertEquals(20, idUser);
	}

	
	@Test
	public void addPersonInformation() {
		boolean resultContion = false;
		Person newPerson = new Person();
		newPerson.setId(20);
		newPerson.setName("вася");
		newPerson.setSurname("атрохов");
		newPerson.setEmail("atrohov@mail.ru");
		try {
			resultContion = personDAO.addPersonInformation(newPerson);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Add new personal information",
				resultContion);
	}

	@Ignore
	@Test
	public void searchPersonByEmail() {
		Date dateSql = Date.valueOf("1996-07-15");
		Person expectedPerson = new Person("Александр", "Брановец", "Юрьевич",
				dateSql, "branovecA@gmail.com", "+375291111111");

		Person personActual = null;
		try {
			personActual = personDAO.searchPersonByEmail("branovecA@gmail.com");
		} catch (DAOException e) {
			e.printStackTrace();
		}

		org.junit.Assert.assertEquals(expectedPerson, personActual);
	}

	@Ignore
	@Test
	public void searchPersonByNames() {
		List<Person> expectedPersonList = new ArrayList<Person>();
		Date dateSql = Date.valueOf("1996-07-15");
		Person expectedPerson = new Person("Александр", "Брановец", "Юрьевич",
				dateSql, "branovecA@gmail.com", "+375291111111");

		expectedPersonList.add(expectedPerson);

		List<Person> personActualPersonList = null;
		try {
			personActualPersonList = personDAO.searchPersonByNames("Александр",
					"Брановец", "Юрьевич");
		} catch (DAOException e) {
			e.printStackTrace();
		}

		org.junit.Assert.assertEquals(expectedPersonList,
				personActualPersonList);
	}

}
