package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code ConnectionFactory} is a singleton class responsible for managing connections to the MySQL database.
 * It loads the JDBC driver, creates database connections, and provides utility methods to close JDBC resources
 * such as {@link Connection}, {@link Statement}, and {@link ResultSet}.
 *
 * <p>This class is used throughout the application to ensure consistent and centralized database access.</p>
 */

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger((ConnectionFactory.class.getName()));
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/tp";
    private static final String USER = "root";
    private static final String PASS = "D1228o4a.";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Private constructor that loads the JDBC driver class. Implements the Singleton pattern.
     */

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new {@link Connection} to the configured database using the JDBC DriverManager.
     *
     * @return a new {@code Connection} object, or {@code null} if connection fails
     */

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returns a new database connection using the singleton instance.
     *
     * @return a {@link Connection} object
     */

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes a {@link Connection}, if not {@code null}.
     *
     * @param connection the {@code Connection} to close
     */

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Closes a {@link Statement}, if not {@code null}.
     *
     * @param statement the {@code Statement} to close
     */

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Closes a {@link ResultSet}, if not {@code null}.
     *
     * @param resultSet the {@code ResultSet} to close
     */

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}