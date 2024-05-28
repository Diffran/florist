package florist.connection;
import florist.Main;
import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.menus.MainMenu;

import java.sql.*;
import java.util.InputMismatchException;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static PreparedStatement stmt;
    private static Statement st;
    private static ResultSet res;
    private static String userData;

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

    public void createFlorist() throws EmptyStringException{

        try{
            stmt = getConnection().prepareStatement(QueriesSQL.createNewFloristSQL);

            System.out.println("Enter Florist name: ");
            userData=MainMenu.SC.nextLine();

            if(!userData.isEmpty()){
                stmt.setString(1, userData);
                stmt.executeUpdate();
                System.out.println("florist: "+userData+ " added to the database");
            }else{
                throw new EmptyStringException("at create Florist");
            }

            disconnect();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void printFlorist() throws EmptySQLTableException{
        try{
            getConnection();
            st = connection.createStatement();
            res = st.executeQuery(QueriesSQL.printFloristSQL);

            while (res.next()) {
                System.out.println("ID: " + res.getInt("idFLORIST") + " -NAME: " + res.getString("name"));
            }

            disconnect();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean floristExist(int id)throws EmptySQLTableException, SQLException{

        stmt = getConnection().prepareStatement(QueriesSQL.floristExistsSQL);
        stmt.setInt(1,id);
        res=stmt.executeQuery();

        if(res.next()){
            disconnect();
            return true;
        }else {
            throw new EmptySQLTableException("florist with id: "+id);
        }
    }

    public void deleteFlorist() throws NumberFormatException  {
        int id;
        System.out.println("Please enter the ID of the florist you'd like to remove:");
        userData= MainMenu.SC.nextLine();
        id= Integer.parseInt(userData);

        try{
            if(floristExist(id)) {
                stmt = getConnection().prepareStatement(QueriesSQL.deleteFloristSQL);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("flotist id: " + id + " successfully deleted");

                disconnect();
            }
        }catch(SQLException |EmptySQLTableException e){
            System.out.println(e.getMessage());
        }

    }


}
