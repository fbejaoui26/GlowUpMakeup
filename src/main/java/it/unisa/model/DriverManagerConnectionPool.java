package it.unisa.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {

	private static List<Connection> freeConnections;

	static {
		freeConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver non trovato: " + e.getMessage());
		}
	}

	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String url = "jdbc:mysql://localhost:3306/glowup_makeup?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root"; // 
		String password = "bejaoui.26."; 

		newConnection = DriverManager.getConnection(url, username, password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}

	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeConnections.isEmpty()) {
			connection = (Connection) freeConnections.get(0);
			freeConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = createDBConnection();
			} catch (SQLException e) {
				connection.close();
				connection = createDBConnection();
			}
		} else {
			connection = createDBConnection();
		}

		return connection;
	}

	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if (connection != null)
			freeConnections.add(connection);
	}
}