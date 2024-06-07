package florist.services.florist;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.exceptions.NoConnectedDBException;
import florist.menus.MainMenu;
import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.*;

public class FloristService {
    private static final ConnectionSQL CONNECTION = ConnectionSQL.getInstance();
    private static Connection connection;
    public static PreparedStatement stmt;
    public static ResultSet res;
    private static String userData;

    public static boolean checkingConnection() throws NoConnectedDBException {
        try {
            CONNECTION.connect();
            return true;

        } catch (SQLException e) {
            String msg = "**********************************************\n" +
                    "*       Not connected to the Database.       *\n" +
                    "*                                            *\n" +
                    "* Please connect to the Database to continue *\n" +
                    "* and restart the program again.             *\n" +
                    "**********************************************";

            throw new NoConnectedDBException(msg);

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static int createFlorist() throws EmptyStringException, EmptySQLTableException {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.createNewFlorist);

            System.out.println("Enter Florist name: ");
            userData = MainMenu.SC.nextLine();

            if (!userData.isEmpty()) {
                stmt.setString(1, userData);
                stmt.executeUpdate();
                System.out.println("florist: " + userData + " added to the database");
            } else throw new EmptyStringException("at create Florist");


            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.selectFloristID);
            stmt.setString(1, userData);

            res = stmt.executeQuery();

            while (res.next()) return res.getInt("id_florist");


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return 0;
    }

    public static void printFlorist() {
        try {
            CONNECTION.getConnection();
            Statement st = connection.createStatement();
            res = st.executeQuery(QueriesSQL.printFlorist);

            while (res.next())
                System.out.println("ID: " + res.getInt("id_florist") + " - NAME: " + res.getString("name"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static boolean floristExist(int id) throws EmptySQLTableException, SQLException {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.floristExists);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            if (res.next()) return true;
            else throw new EmptySQLTableException("florist with id: " + id);

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static void deleteFlorist() throws NumberFormatException {
        int id;
        System.out.println("Please enter the ID of the florist you'd like to remove:");
        userData = MainMenu.SC.nextLine();
        id = Integer.parseInt(userData);

        try {
            if (floristExist(id)) {
                stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.deleteFlorist);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("florist id: " + id + " successfully deleted");
            }

        } catch (SQLException | EmptySQLTableException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static String getFloristName(int floristId) throws SQLException {
        String floristName = null;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.floristExists);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (res.next()) floristName = res.getString("name");

        } finally {
            CONNECTION.disconnect();
        }

        return floristName;
    }
}
