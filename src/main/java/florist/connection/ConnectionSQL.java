package florist.connection;
import java.sql.*;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private ConnectionSQL() {
    }

    private static final class InstanceHolder {
        private static final ConnectionSQL instance = new ConnectionSQL();
    }

    public static ConnectionSQL getInstance() {
        return InstanceHolder.instance;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Conectado");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
            connect();

        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Disconnected");

            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
