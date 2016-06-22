package by.epam.tc.connection_pool.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import by.epam.tc.hr_system.dao.IVacancyDAO;
import by.epam.tc.hr_system.dao.impl.SkillDAOImpl;
import by.epam.tc.hr_system.dao.impl.VacancyDAOImpl;
import by.epam.tc.hr_system.domain.Vacancy;
import by.epam.tc.hr_system.exception.DAOException;

public class VacancyDAOTest {
	private IVacancyDAO vacancyDAO ;
	
	@Before
	public void initPersonDAO() {
		vacancyDAO = new VacancyDAOImpl();
	}

	@After
	public void destroyPersonDAO() {
		vacancyDAO = null;
	}
	
	@Ignore
	@Test
	public void addNewSkillTest() {
		boolean result = false;
		Vacancy vacancy = new Vacancy();
		vacancy.setName("Ruby on Rails Middle Developer");
		
		try {
			result = vacancyDAO.addVacancy(vacancy, 2);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Adding new vacancy",
				result);
	}
	
	@Ignore
	@Test
	public void updateSkillTest() {
		boolean result = false;
		Vacancy vacancy = new Vacancy();
		vacancy.setName("QA Middle Developer");
		vacancy.setId(5);		
		try {
			result = vacancyDAO.updateVacancy(vacancy, 2);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Update vacancy",
				result);
	}
	
	@Test
	public void removeSkillTest() {
		boolean result = false;		
		try {
			result = vacancyDAO.removeVacancy(5);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Remove vacancy",
				result);
	}

}
