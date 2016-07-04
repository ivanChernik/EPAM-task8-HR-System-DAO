package by.epam.tc.hr_system.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epam.tc.hr_system.dao.IEducationDAO;
import by.epam.tc.hr_system.dao.connection_pool.ConnectionPool;
import by.epam.tc.hr_system.domain.Education;
import by.epam.tc.hr_system.exception.ConnectionPoolException;
import by.epam.tc.hr_system.exception.DAOException;

public class EducationDAOImpl implements IEducationDAO {

	private static final String ERROR_REMOVING_EDUCATION = "Error removing education";
	private static final String ERROR_UPDATING_EDUCATION = "Error updating education";
	private static final String ERROR_ADDING_NEW_EDUCATION = "Error adding new education";
	private static final String ERROR_CLOSING_CONNECTION_OR_STATEMENTS = "Error closing connection or statements";
	private static final String ERROR_CONNECTION_POOL_INSTANSE = "Error connection pool instanse";

	private static final String SQL_UPDATE_EDUCATION = "UPDATE `hr-system`.`education` SET `id_candidate`=?,`institution`= ?, `department`=?, `speciality`= ?, `form_of_education`= ?, `date_of_entry`= ?, `date_of_graduation`= ? WHERE `id_education`= ?;";
	private static final String SQL_ADD_EDUCATION_TO_CANDIDATE = "INSERT INTO `hr-system`.`education` (`id_candidate`,`institution`, `department`, `speciality`, `form_of_education`, `date_of_entry`, `date_of_graduation`) VALUES (?, ? , ?, ?, ? , ?, ?);";
	private static final String SQL_DELETE_EDUCATION = "DELETE FROM `hr-system`.`education` WHERE `id_education`= ?;";
	
	private static final Logger log = Logger.getLogger(EducationDAOImpl.class);
	ConnectionPool connectionPool;

	@Override
	public boolean addEduction(Education eduction) throws DAOException {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement addEducationPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			addEducationPS = connection.prepareStatement(SQL_ADD_EDUCATION_TO_CANDIDATE);
			
			addEducationPS.setInt(1, eduction.getIdPerson());
			addEducationPS.setString(2,eduction.getInstitution());
			addEducationPS.setString(3,eduction.getDepartament());
			addEducationPS.setString(4,eduction.getSpecialty());
			addEducationPS.setString(5,eduction.getFormEduction());
			addEducationPS.setDate(6,eduction.getDateEntry());
			addEducationPS.setDate(7,eduction.getDateGraduation());
			addEducationPS.executeUpdate();
			result = true;
		} catch (SQLException | ConnectionPoolException e) {
			log.error(ERROR_ADDING_NEW_EDUCATION, e);
			throw new DAOException(ERROR_ADDING_NEW_EDUCATION, e);
		}

		finally {
			try {
				addEducationPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}
		}
		return result;
	}

	@Override
	public boolean removeEduction(int idEduction) throws DAOException {
		
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement removeEducationPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();

			removeEducationPS = connection.prepareStatement(SQL_DELETE_EDUCATION);
			removeEducationPS.setInt(1, idEduction);
			removeEducationPS.executeUpdate();
			result = true;
			
		} catch (SQLException | ConnectionPoolException e) {
	
			log.error(ERROR_REMOVING_EDUCATION, e);
			throw new DAOException(ERROR_REMOVING_EDUCATION, e);

		} finally {
			try {
				removeEducationPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}

		}

		return result;
	}

	@Override
	public boolean updateEduction(Education eduction) throws DAOException {
		
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement updateEducationPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			updateEducationPS = connection.prepareStatement(SQL_UPDATE_EDUCATION);
			
			updateEducationPS.setInt(1, eduction.getIdPerson());
			updateEducationPS.setString(2,eduction.getInstitution());
			updateEducationPS.setString(3,eduction.getDepartament());
			updateEducationPS.setString(4,eduction.getSpecialty());
			updateEducationPS.setString(5,eduction.getFormEduction());
			updateEducationPS.setDate(6,eduction.getDateEntry());
			updateEducationPS.setDate(7,eduction.getDateGraduation());
			updateEducationPS.setInt(8,eduction.getId());
			updateEducationPS.executeUpdate();
			result = true;
		} catch (SQLException | ConnectionPoolException e) {
			log.error(ERROR_UPDATING_EDUCATION, e);
			throw new DAOException(ERROR_UPDATING_EDUCATION, e);
		}

		finally {
			try {
				updateEducationPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}
		}
		return result;
	}

}
