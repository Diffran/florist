package florist.services.sql;
import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.menus.MainMenu;

import java.sql.*;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sugoalpomodoro";
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

    // Add Stock
    public void createStock() {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.createNewStockSQL);
            System.out.println("Enter Florist ID for the stock: ");
            int floristId = Integer.parseInt(MainMenu.SC.nextLine());

            stmt.setInt(1, floristId);
            stmt.executeUpdate();
            System.out.println("Stock added for Florist ID: " + floristId);

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // List All Stocks
    public void listAllStocks() {
        try {
            st = getConnection().createStatement();
            res = st.executeQuery(QueriesSQL.listAllStocksSQL);

            while (res.next()) {
                int idStock = res.getInt("id_stock");
                int floristId = res.getInt("florist_id_florist");
                System.out.println("Stock ID: " + idStock + ", Florist ID: " + floristId);
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update Stock
    public void updateStock() {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.updateStockSQL);
            System.out.println("Enter Stock ID to update: ");
            int stockId = Integer.parseInt(MainMenu.SC.nextLine());

            System.out.println("Enter new Florist ID for the stock: ");
            int newFloristId = Integer.parseInt(MainMenu.SC.nextLine());

            stmt.setInt(1, newFloristId);
            stmt.setInt(2, stockId);
            stmt.executeUpdate();
            System.out.println("Stock ID: " + stockId + " updated with new Florist ID: " + newFloristId);

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete Stock
    public void deleteStock() {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.deleteStockSQL);
            System.out.println("Enter Stock ID to delete: ");
            int stockId = Integer.parseInt(MainMenu.SC.nextLine());

            stmt.setInt(1, stockId);
            stmt.executeUpdate();
            System.out.println("Stock ID: " + stockId + " deleted");

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Methods for managing stock
    public void addProductToStock(int floristId, int productId, int quantity) throws SQLException {
        String query = "INSERT INTO stock_has_product (quantity, stock_id_stock, product_id_product) " +
                "VALUES (?, (SELECT id_stock FROM stock WHERE florist_id_florist = ?), ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, quantity);
        stmt.setInt(2, floristId);
        stmt.setInt(3, productId);
        stmt.executeUpdate();
        System.out.println("Product added to stock successfully.");
    }

    public void deleteProductFromStock(int floristId, int productId, int quantity) throws SQLException {
        String query = "UPDATE stock_has_product SET quantity = quantity - ? " +
                "WHERE stock_id_stock = (SELECT id_stock FROM stock WHERE florist_id_florist = ?) AND product_id_product = ?";

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, quantity);
        stmt.setInt(2, floristId);
        stmt.setInt(3, productId);
        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Product quantity updated successfully.");
        } else {
            System.out.println("Product not found in stock or insufficient quantity.");
        }
    }

    public double getTotalStockValue(int floristId) throws SQLException {
        String query = "SELECT SUM(p.price * shp.quantity) AS total_value " +
                "FROM product p " +
                "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
                "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
                "WHERE s.florist_id_florist = ?";

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        if (res.next()) {
            return res.getDouble("total_value");
        } else {
            return 0.0;
        }
    }

    public void printIndividualStockList(int floristId) throws SQLException {
        String query = "SELECT p.name, shp.quantity " +
                "FROM product p " +
                "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
                "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
                "WHERE s.florist_id_florist = ?";

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println("Product: " + res.getString("name") + ", Quantity: " + res.getInt("quantity"));
        }
    }

    public void printGlobalStockList(int floristId) throws SQLException {
        String query = "SELECT p.name, SUM(shp.quantity) AS total_quantity " +
                "FROM product p " +
                "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
                "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
                "WHERE s.florist_id_florist = ? " +
                "GROUP BY p.name";

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println("Product: " + res.getString("name") + ", Total Quantity: " + res.getInt("total_quantity"));
        }
    }

}
