package by.epam.tc.connection_pool.dao.connection_pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

import by.epam.tc.connection_pool.exception.ConnectionPoolException;
import by.epam.tc.connection_pool.util.DBParameter;
import by.epam.tc.connection_pool.util.DBResourceManager;

public final class ConnectionPool {
	private static final String ERROR_CLOSING_RESULT_SET = "Error closing ResultSet";
	private static final String ERROR_CLOSING_STATEMENT = "Error closing statement";
	private static final String ERROR_GET_INSTANCE = "Error get instance";
	private static final String ERROR_CLOSING_CONNECTION = "Error closing connection";
	private static final String ERROR_CLEARING_CONNECTION_QUEUES = "Error clearing connection queues";
	private static final String ERROR_CONNECTION_TO_THE_DATA_SOURCES = "Error connection to the data sources";
	private static final String CONNECTION_IS_GOT = "Connection is got";
	private static final String WRONG_INITIALIZATION_POOLSIZE = "wrong initialization poolsize";
	private static final String CONNECTION_IS_NOT_GOT = "Connection is not got";
	private static final String DATABASE_DRIVER_CLASS_NOT_FOUND = "Database driver class not found";
	private static final Logger log = Logger.getLogger(ConnectionPool.class);
	private String driverName;
	private String userName;
	private String password;
	private String url;
	private int poolSize;
	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;
	private static ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {
		// validation??
		DBResourceManager dbManager = new DBResourceManager();
		driverName = dbManager.getValue(DBParameter.DB_DRIVER);
		userName = dbManager.getValue(DBParameter.DB_USER);
		password = dbManager.getValue(DBParameter.DB_PASSWORD);
		url = dbManager.getValue(DBParameter.DB_URL);
		try {
			poolSize = Integer.parseInt(dbManager
					.getValue(DBParameter.DB_POOL_SIZE));
		} catch (NumberFormatException e) {
			log.warn(WRONG_INITIALIZATION_POOLSIZE, e);
			poolSize = 5;
		}
	}

	public static ConnectionPool getInstance() throws ConnectionPoolException {
		try {
			instance.initConnectionPool();
		} catch (ConnectionPoolException e) {
			log.error(ERROR_GET_INSTANCE, e);
			throw e;
		}
		return instance;
	}

	public void initConnectionPool() throws ConnectionPoolException {
		givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
		connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

		try {
			Class.forName(driverName);
			for (int indexPool = 0; indexPool < poolSize; indexPool++) {
				Connection originalConnection = DriverManager.getConnection(
						url, userName, password);
				PooledConnection pooledConnection = new PooledConnection(
						originalConnection);
				connectionQueue.add(pooledConnection);
			}

		} catch (ClassNotFoundException e) {
			log.error(DATABASE_DRIVER_CLASS_NOT_FOUND, e);
			throw new ConnectionPoolException(DATABASE_DRIVER_CLASS_NOT_FOUND,
					e);

		} catch (SQLException e) {
			log.error(CONNECTION_IS_NOT_GOT, e);
			throw new ConnectionPoolException(CONNECTION_IS_NOT_GOT, e);
		}

		log.debug(CONNECTION_IS_GOT);

	}

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;

		try {
			connection = connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch (InterruptedException e) {
			log.error(ERROR_CONNECTION_TO_THE_DATA_SOURCES, e);
			throw new ConnectionPoolException(
					ERROR_CONNECTION_TO_THE_DATA_SOURCES, e);
		}
		return connection;

	}

	public void dispose() {
		clearConnectionQueue();
	}

	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(givenAwayConQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			log.error(ERROR_CLEARING_CONNECTION_QUEUES, e);
		}
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue)
			throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}
	
	public void closeStatement(Statement st) {
		try {
			st.close();
		} catch (SQLException e) {
			log.error(ERROR_CLOSING_STATEMENT, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);
		}
	}

	public void closeConnection(Connection con, Statement st, ResultSet rs) {
		try {
			con.close();
		} catch (SQLException e) {
			log.error(ERROR_CLOSING_CONNECTION, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);
		}
		try {
			rs.close();
		} catch (SQLException e) {

			log.error(ERROR_CLOSING_RESULT_SET, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);
		}

		try {
			st.close();
		} catch (SQLException e) {
			log.error(ERROR_CLOSING_STATEMENT, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);
		}
	}

	public void closeConnection(Connection con, Statement st) {
		try {
			con.close();
		} catch (SQLException e) {
			log.error(ERROR_CLOSING_CONNECTION, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);
		}

		try {
			st.close();
		} catch (SQLException e) {
			log.error(ERROR_CLOSING_STATEMENT, e);
			// throw new ConnectionPoolException(ERROR_CLOSING_CONNECTION, e);

		}
	}

	private class PooledConnection implements Connection {
		private static final String ERROR_ALLOCATING_CONNECTION_IN_THE_POOL = "Error allocating connection in the pool";
		private static final String ERROR_DELETING_CONNECTION_FROM_THE_GIVEN_AWAY_CONNECTIONS_POOL = "Error deleting connection from the given away connections pool";
		private static final String ATTEMPTING_TO_CLOSE_CLOSED_CONNECTION = "Attempting to close closed connection";
		private Connection connection;

		public PooledConnection(Connection connection) {
			this.connection = connection;
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();

		}

		@Override
		public void close() throws SQLException {
			if (connection.isClosed()) {
				log.error(ATTEMPTING_TO_CLOSE_CLOSED_CONNECTION);
				throw new SQLDataException(
						ATTEMPTING_TO_CLOSE_CLOSED_CONNECTION);
			}
			// if (connection.isReadOnly()) {
			// connection.setReadOnly(false);
			// }
			if (!givenAwayConQueue.remove(this)) {
				log.error(ERROR_DELETING_CONNECTION_FROM_THE_GIVEN_AWAY_CONNECTIONS_POOL);
				throw new SQLDataException(
						ERROR_DELETING_CONNECTION_FROM_THE_GIVEN_AWAY_CONNECTIONS_POOL);
			}

			if (!connectionQueue.offer(this)) {
				log.error(ERROR_DELETING_CONNECTION_FROM_THE_GIVEN_AWAY_CONNECTIONS_POOL);
				throw new SQLException(ERROR_ALLOCATING_CONNECTION_IN_THE_POOL);
			}

		}

		@Override
		public void commit() throws SQLException {
			connection.commit();

		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements)
				throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType,
					resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType,
				int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType,
					resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes)
				throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.prepareCall(sql, resultSetType,
					resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType,
					resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql)
				throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql,
				int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql,
				int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql,
				String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql,
				int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType,
					resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql,
				int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType,
					resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(Properties properties)
				throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public void setClientInfo(String name, String value)
				throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds)
				throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}

	}

}
