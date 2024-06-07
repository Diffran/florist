package florist.services.sql;

import java.sql.*;

public class ConnectionSQL {
    private Connection connection;
    public static PreparedStatement stmt;
    public static ResultSet res;

    private ConnectionSQL() {
    }

    private static final class InstanceHolder {
        private static final ConnectionSQL instance = new ConnectionSQL();
    }

    public static ConnectionSQL getInstance() {
        return InstanceHolder.instance;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/florist";
        String username = "root";
        String password = "";

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                this.connection = DriverManager.getConnection(url, username, password);

            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) connect();

        return connection;
    }

    public boolean disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return false;
    }

}
