package by.epam.tc.hr_system.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epam.tc.hr_system.dao.IVacancyDAO;
import by.epam.tc.hr_system.dao.connection_pool.ConnectionPool;
import by.epam.tc.hr_system.domain.Vacancy;
import by.epam.tc.hr_system.exception.ConnectionPoolException;
import by.epam.tc.hr_system.exception.DAOException;

public class VacancyDAOImpl implements IVacancyDAO{
	
	private static final String ERROR_UPDATING_VACANCY = "Error updating vacancy";
	private static final String ERROR_DELETING_VACANCY = "Error deleting vacancy";
	private static final String ERROR_ADDING_VACANCY = "Error adding vacancy";
	private static final String ERROR_CLOSING_CONNECTION_OR_STATEMENTS = "Error closing connection or statements";
	private static final String ERROR_CONNECTION_POOL_INSTANSE = "Error connection pool instanse";
	
	private static final String SQL_ADD_NEW_VACANCY = "INSERT INTO `hr-system`.`vacancy` (`name`, `description`, `requirement`, `company`, `salary`,  `date_of_submission`, `status`, `id_hr`) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
	private static final String SQL_DELETE_VACANCY = "DELETE FROM `hr-system`.`vacancy` WHERE  `id_vacancy`= ?;";
	private static final String SQL_UPDATE_VACANCY = "UPDATE `hr-system`.`vacancy` SET `name`= ?, `description`= ?, `requirement`=?, `company`= ?, `salary`= ?, `date_of_submission`=?, `status`=?, `id_hr`= ? WHERE `id_vacancy`= ?;";
	
	private static final Logger log = Logger.getLogger(VacancyDAOImpl.class);
	//private ConnectionPool connectionPool;

	@Override
	public boolean addVacancy(Vacancy vacancy, int idHR) throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement addVacancyPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			addVacancyPS = connection.prepareStatement(SQL_ADD_NEW_VACANCY);

			addVacancyPS.setString(1, vacancy.getName());
			addVacancyPS.setString(2, vacancy.getDescrption());
			addVacancyPS.setString(3, vacancy.getRequirement());
			addVacancyPS.setString(4, vacancy.getCompany());
			addVacancyPS.setInt(5, vacancy.getSalary());
			addVacancyPS.setDate(6, vacancy.getDateSubmission());
			addVacancyPS.setString(7, vacancy.getStatus());
			addVacancyPS.setInt(8, idHR);

			addVacancyPS.executeUpdate();
			result = true;
		} catch (SQLException | ConnectionPoolException e) {
			log.error(ERROR_ADDING_VACANCY, e);
			throw new DAOException(ERROR_ADDING_VACANCY, e);
		}

		finally {
			try {
				addVacancyPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}
		}
		return result;
	}

	@Override
	public boolean updateVacancy(Vacancy vacancy, int idHR) throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement updateVacancyPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			updateVacancyPS = connection.prepareStatement(SQL_UPDATE_VACANCY);

			updateVacancyPS.setString(1, vacancy.getName());
			updateVacancyPS.setString(2, vacancy.getDescrption());
			updateVacancyPS.setString(3, vacancy.getRequirement());
			updateVacancyPS.setString(4, vacancy.getCompany());
			updateVacancyPS.setInt(5, vacancy.getSalary());
			updateVacancyPS.setDate(6, vacancy.getDateSubmission());
			updateVacancyPS.setString(7, vacancy.getStatus());
			updateVacancyPS.setInt(8, idHR);
			updateVacancyPS.setInt(9, vacancy.getId());

			updateVacancyPS.executeUpdate();
			result = true;
		} catch (SQLException | ConnectionPoolException e) {
			log.error(ERROR_UPDATING_VACANCY, e);
			throw new DAOException(ERROR_UPDATING_VACANCY, e);
		}

		finally {
			try {
				updateVacancyPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}
		}
		return result;
	}

	@Override
	public boolean removeVacancy(int idVacancy) throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE, e);
		}
		Connection connection = null;
		PreparedStatement deketeVacancyPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			deketeVacancyPS = connection.prepareStatement(SQL_DELETE_VACANCY);

			deketeVacancyPS.setInt(1, idVacancy);

			deketeVacancyPS.executeUpdate();
			result = true;
		} catch (SQLException | ConnectionPoolException e) {
			log.error(ERROR_DELETING_VACANCY, e);
			throw new DAOException(ERROR_DELETING_VACANCY, e);
		}

		finally {
			try {
				deketeVacancyPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,
						e);
			}
		}
		return result;
	}

}
