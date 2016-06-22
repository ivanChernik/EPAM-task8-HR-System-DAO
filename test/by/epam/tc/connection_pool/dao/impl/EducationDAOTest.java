package by.epam.tc.connection_pool.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import by.epam.tc.hr_system.dao.IEducationDAO;
import by.epam.tc.hr_system.dao.impl.EducationDAOImpl;
import by.epam.tc.hr_system.domain.Education;
import by.epam.tc.hr_system.domain.Person;
import by.epam.tc.hr_system.exception.DAOException;


public class EducationDAOTest {
	private IEducationDAO educationDAO;

	// private int idUser;

	@Before
	public void initPersonDAO() {
		educationDAO = new EducationDAOImpl();
	}

	@After
	public void destroyPersonDAO() {
		educationDAO = null;
	}
	
	@Ignore
	@Test
	public void addEducationTest(){
		boolean  result = false;
		Education eduction = new Education();
		eduction.setIdPerson(2);
		eduction.setInstitution("gdgdgdgdg");
		try {
			result = educationDAO.addEduction(eduction);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Adding new education",
				result);
	}
	
	@Ignore
	@Test
	public void updateEducationTest(){
		boolean  result = false;
		Education eduction = new Education();
		eduction.setId(5);
		eduction.setIdPerson(2);
		eduction.setInstitution("qwerty");
		try {
			result = educationDAO.updateEduction(eduction);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Update education",
				result);
	}
	
	@Ignore
	@Test
	public void removeEducationTest(){
		boolean  result = false;
		try {
			result = educationDAO.removeEduction(5);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Removing education",
				result);
	}


}
