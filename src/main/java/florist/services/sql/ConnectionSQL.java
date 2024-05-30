package florist.services.sql;
import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.menus.MainMenu;

import java.sql.*;

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

    public void addTree() throws SQLException, EmptyStringException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProductSQL);

            System.out.println("Enter Tree name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Tree price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Tree height in cm: ");
            double height = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Tree quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "tree");
                stmt.setNull(4, Types.VARCHAR); // color
                stmt.setDouble(5, height);
                stmt.setNull(6, Types.VARCHAR); // material_type
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Tree: " + name + " added to the database");
            } else {
                throw new EmptyStringException("at add Tree");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFlower() throws SQLException, EmptyStringException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProductSQL);

            System.out.println("Enter Flower name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Flower price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Flower color: ");
            String color = MainMenu.SC.nextLine();
            System.out.println("Enter Flower quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "flower");
                stmt.setString(4, color);
                stmt.setNull(5, Types.DOUBLE); // height
                stmt.setNull(6, Types.VARCHAR); // material_type
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Flower: " + name + " added to the database");
            } else {
                throw new EmptyStringException("at add Flower");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDecoration() throws SQLException, EmptyStringException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProductSQL);

            System.out.println("Enter Decoration name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Decoration price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Decoration material type (wood/plastic): ");
            //TODO: personalizar exception sql por tipo mal ingresado en programa
            String materialType = MainMenu.SC.nextLine();
            System.out.println("Enter Decoration quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "decoration");
                stmt.setNull(4, Types.VARCHAR); // color
                stmt.setNull(5, Types.DOUBLE); // height
                stmt.setString(6, materialType);
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Decoration: " + name + " added to the database");
            } else {
                throw new EmptyStringException("at add Decoration");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
