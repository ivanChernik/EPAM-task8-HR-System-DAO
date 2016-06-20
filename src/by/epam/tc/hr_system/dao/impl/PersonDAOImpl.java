package by.epam.tc.hr_system.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.tc.hr_system.dao.IPersonDAO;
import by.epam.tc.hr_system.dao.connection_pool.ConnectionPool;
import by.epam.tc.hr_system.domain.Person;
import by.epam.tc.hr_system.exception.ConnectionPoolException;
import by.epam.tc.hr_system.exception.DAOException;

public class PersonDAOImpl implements IPersonDAO {
	private static final String ERROR_SEARCHING_PERSON_BY_NAME = "Error searching person by name";
	private static final String FATAL_ERROR_ROLLBACK = "Fatal error rollback";
	private static final String ERROR_CLOSING_RESULT_SER_OR_STATEMENT_OR_CONNECTION = "Error closing resultSer or statement or connection";
	private static final String ERORR_CLOSING_STATEMENT = "Erorr closing statement";
	private static final String ERROR_CLOSING_CONNECTION_OR_STATEMENTS = "Error closing connection or statements";
	private static final String ERROR_CONNECTION_POOL_INSTANSE = "Error connection pool instanse";
	private static final String ERROR_ROLLBACK = "error rollback";
	private static final String ERROR_SEARCHING_PERSON_BY_EMAIL = "Error searching person by email";
	private static final String ERROR_ADDING_PERSON = "ERROR ADDING PERSON";
	
	private final static String SQL_ADD_PERSONS_DATA = "INSERT INTO `hr-system`.`person` (`name`, `surname`, `middle_name`, `date_of_birthday`, `email`, `phone`,`id_person`) VALUES (? ,?, ?, ?, ?, ?, ?);";
	private final static String SQL_ADD_NEW_APPLICANT = "INSERT INTO `hr-system`.`user` (`role`, `login`, `password`) VALUES (? ,?, ?);";
	private final static String SQL_SEARCH_PERSON_BY_EMAIL = "SELECT `name`, `surname`, `middle_name`, `date_of_birthday`, `email`, `phone` FROM `hr-system`.`person` WHERE `email` = ? ;";
	private final static String SQL_SEARCH_PERSONS_BY_NAMES = "SELECT `name`, `surname`, `middle_name`, `date_of_birthday`, `email`, `phone` FROM `hr-system`.`person` WHERE `name` = ? AND `surname` = ? AND `middle_name`=?;";
	private final static String SQL_SEARCH_ID_USER_BY_LOGIN_AND_PASSWORD = "SELECT `id_user` FROM `hr-system`.`user` WHERE `login` = ? AND `password` = ?;";
	
	private final static String SQL_NAME = "name";
	private final static String SQL_SURNAME = "surname";
	private final static String SQL_MIDDLE_NAME = "middle_name";
	private final static String SQL_DATE_OF_BIRTHDAY = "date_of_birthday";
	private final static String SQL_EMAIL = "email";
	private final static String SQL_PHONE = "phone";
	private final static String SQL_USER_ID = "id_user";
	private static final Logger log = Logger.getLogger(PersonDAOImpl.class);


	@Override
	public int registerPerson(String login, String password, String role)
			throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE,e);
		}
		Connection connection = null;
		PreparedStatement addUserDataPS = null;
		PreparedStatement searchIDPS = null;
		int userID = 0;
		try {
			connection = connectionPool.takeConnection();
			connection.setAutoCommit(false);

			// first
			addUserDataPS = connection.prepareStatement(SQL_ADD_NEW_APPLICANT);

			addUserDataPS.setString(1, role);
			addUserDataPS.setString(2, login);
			addUserDataPS.setString(3, password);
			addUserDataPS.executeUpdate();

			// second
			searchIDPS = connection
					.prepareStatement(SQL_SEARCH_ID_USER_BY_LOGIN_AND_PASSWORD);

			searchIDPS.setString(1, login);
			searchIDPS.setString(2, password);
			userID = getUserID(searchIDPS.executeQuery());
			connection.commit();
		}

		catch (ConnectionPoolException | SQLException e) {
			log.error(ERROR_ADDING_PERSON, e);
			try {
				connection.rollback();
			} catch (SQLException eSQL) {
				log.fatal(ERROR_ROLLBACK, eSQL);
				throw new DAOException(FATAL_ERROR_ROLLBACK,e);
			}
			throw new DAOException(ERROR_ADDING_PERSON, e);

		} finally {
			try {
				addUserDataPS.close();
				searchIDPS.close();
				connection.close();
			} catch (SQLException e) {
				throw new DAOException(ERROR_CLOSING_CONNECTION_OR_STATEMENTS,e);
			}
		}
		return userID;
	}

	@Override
	public boolean addPersonInformation(Person person)
			throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE,e);
		}
		Connection connection = null;
		PreparedStatement addPersonalDataPS = null;
		boolean result = false;
		try {
			connection = connectionPool.takeConnection();

			addPersonalDataPS = connection
					.prepareStatement(SQL_ADD_PERSONS_DATA);
			addPersonalDataPS.setString(1, person.getName());
			addPersonalDataPS.setString(2, person.getSurname());
			addPersonalDataPS.setString(3, person.getMiddleName());
			addPersonalDataPS.setDate(4, person.getDateOfBirthday());
			addPersonalDataPS.setString(5, person.getEmail());
			addPersonalDataPS.setString(6, person.getPhone());
			addPersonalDataPS.setInt(7, person.getId());
			addPersonalDataPS.executeUpdate();
			result = true;
		} catch (ConnectionPoolException | SQLException e) {
			log.error(ERROR_ADDING_PERSON, e);
			throw new DAOException(ERROR_ADDING_PERSON,e);

		} finally {
			try {
				addPersonalDataPS.close();
			} catch (SQLException e) {
				log.error(ERORR_CLOSING_STATEMENT, e);
				throw new DAOException(ERORR_CLOSING_STATEMENT,e);
			}
		}
		return result;
	}

	private int getUserID(ResultSet rs) throws SQLException {
		int userID = -1;
		rs.next();
		userID = rs.getInt(SQL_USER_ID);

		return userID;
	}

	@Override
	public Person searchPersonByEmail(String email) throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE,e);
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Person searchedPerson = null;
		try {

			connection = connectionPool.takeConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SEARCH_PERSON_BY_EMAIL);
			preparedStatement.setString(1, email);
			rs = preparedStatement.executeQuery();
			searchedPerson = getPerson(rs);
		} catch (ConnectionPoolException | SQLException e) {
			log.error(ERROR_SEARCHING_PERSON_BY_EMAIL, e);
			throw new DAOException(e);

		} finally {
			try {
				rs.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				log.error(ERROR_CLOSING_RESULT_SER_OR_STATEMENT_OR_CONNECTION, e);
				throw new DAOException(ERROR_CLOSING_RESULT_SER_OR_STATEMENT_OR_CONNECTION,e);
			}
		}
		return searchedPerson;
	}

	private Person getPerson(ResultSet rs) throws SQLException {
		Person searchedPerson = new Person();
		rs.next();
		searchedPerson.setName(rs.getString(SQL_NAME));
		searchedPerson.setSurname(rs.getString(SQL_SURNAME));
		searchedPerson.setMiddleName(rs.getString(SQL_MIDDLE_NAME));
		searchedPerson.setDateOfBirthday(rs.getDate(SQL_DATE_OF_BIRTHDAY));
		searchedPerson.setEmail(rs.getString(SQL_EMAIL));
		searchedPerson.setPhone(rs.getString(SQL_PHONE));
		return searchedPerson;
	}

	@Override
	public List<Person> searchPersonByNames(String name, String surname,
			String middleName) throws DAOException {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			log.fatal(ERROR_CONNECTION_POOL_INSTANSE, e);
			throw new DAOException(ERROR_CONNECTION_POOL_INSTANSE,e);
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Person> searchedPersonList = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection
					.prepareStatement(SQL_SEARCH_PERSONS_BY_NAMES);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, surname);
			preparedStatement.setString(3, middleName);
			rs = preparedStatement.executeQuery();
			searchedPersonList = getPersonList(rs);
		} catch (ConnectionPoolException | SQLException e) {
			log.error(ERROR_SEARCHING_PERSON_BY_NAME, e);
			throw new DAOException(ERROR_SEARCHING_PERSON_BY_NAME,e);

		} finally {

			try {
				rs.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				log.error(ERROR_CLOSING_RESULT_SER_OR_STATEMENT_OR_CONNECTION, e);
				throw new DAOException(ERROR_CLOSING_RESULT_SER_OR_STATEMENT_OR_CONNECTION,e);
			}
		}
		return searchedPersonList;
	}

	private List<Person> getPersonList(ResultSet rs) throws SQLException {
		List<Person> searchedPersonList = new ArrayList<Person>();
		while (rs.next()) {
			Person searchedPerson = new Person();
			searchedPerson.setName(rs.getString(SQL_NAME));
			searchedPerson.setSurname(rs.getString(SQL_SURNAME));
			searchedPerson.setMiddleName(rs.getString(SQL_MIDDLE_NAME));
			searchedPerson.setDateOfBirthday(rs.getDate(SQL_DATE_OF_BIRTHDAY));
			searchedPerson.setEmail(rs.getString(SQL_EMAIL));
			searchedPerson.setPhone(rs.getString(SQL_PHONE));
			searchedPersonList.add(searchedPerson);
		}
		return searchedPersonList;
	}

}
