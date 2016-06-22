package by.epam.tc.connection_pool.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import by.epam.tc.hr_system.dao.ISkillDAO;
import by.epam.tc.hr_system.dao.impl.SkillDAOImpl;
import by.epam.tc.hr_system.exception.DAOException;

public class SkillDAOTest {
	private ISkillDAO skillDAO;

	// private int idUser;

	@Before
	public void initPersonDAO() {
		skillDAO = new SkillDAOImpl();
	}

	@After
	public void destroyPersonDAO() {
		skillDAO = null;
	}
	
	@Ignore
	@Test
	public void addNewSkill() {
		boolean result = false;
		try {
			result = skillDAO.addNewSkill("Ruby on Rails");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Adding new skill",
				result);
	}
	
	@Ignore
	@Test
	public void removeSkill() {
		boolean result = false;
		try {
			result = skillDAO.removeSkill("Ruby on Rails");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		org.junit.Assert.assertTrue("Remove skill",
				result);
	}
	

}
