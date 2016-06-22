package by.epam.tc.hr_system.dao;

import java.util.List;

import by.epam.tc.hr_system.domain.Skill;
import by.epam.tc.hr_system.exception.DAOException;

public interface ISkillDAO {

	boolean addNewSkill(String skillName) throws DAOException;

	boolean removeSkill(String nameSkill) throws DAOException;

	List<Skill> getSkillsByIDApplicant(int applicantID) throws DAOException;
}
