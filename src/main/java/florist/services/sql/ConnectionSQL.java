package florist.services.sql;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.exceptions.InvalidDecorationType;
import florist.exceptions.NotValidIDException;
import florist.menus.MainMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionSQL {
    private Connection connection;
    public static PreparedStatement stmt;
    private static Statement st;
    public static ResultSet res;
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
        if (connection == null || connection.isClosed())
            connect();

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

    public int createFlorist() throws EmptyStringException, EmptySQLTableException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.createNewFlorist);

            System.out.println("Enter Florist name: ");
            userData = MainMenu.SC.nextLine();

            if (!userData.isEmpty()) {
                stmt.setString(1, userData);
                stmt.executeUpdate();
                System.out.println("florist: " + userData + " added to the database");
            } else {
                throw new EmptyStringException("at create Florist");
            }
            stmt = getConnection().prepareStatement(QueriesSQL.selectFloristID);
            stmt.setString(1,userData);

            res = stmt.executeQuery();

            while (res.next()) {
                return res.getInt("id_florist");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
        return 0;
    }

    public void printFlorist() {
        try {
            getConnection();
            st = connection.createStatement();
            res = st.executeQuery(QueriesSQL.printFlorist);

            while (res.next()) {
                System.out.println("ID: " + res.getInt("id_florist") + " - NAME: " + res.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public boolean floristExist(int id) throws EmptySQLTableException, SQLException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.floristExists);
            stmt.setInt(1, id);
            res = stmt.executeQuery();

            if (res.next()) {
                return true;
            } else {
                throw new EmptySQLTableException("florist with id: " + id);
            }
        } finally {
            disconnect();
        }
    }

    public String getFloristName(int floristId) throws SQLException {
        String floristName = null;
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.floristExists);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (res.next()) {
                floristName = res.getString("name");
            }
        } finally {
            disconnect();
        }
        return floristName;
    }

    public void deleteFlorist() throws NumberFormatException {
        int id;
        System.out.println("Please enter the ID of the florist you'd like to remove:");
        userData = MainMenu.SC.nextLine();
        id = Integer.parseInt(userData);

        try {
            if (floristExist(id)) {
                stmt = getConnection().prepareStatement(QueriesSQL.deleteFlorist);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("florist id: " + id + " successfully deleted");
            }
        } catch (SQLException | EmptySQLTableException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void addTree() throws SQLException, EmptyStringException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProduct);

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
                stmt.setNull(4, Types.VARCHAR);
                stmt.setDouble(5, height);
                stmt.setNull(6, Types.VARCHAR);
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
            stmt = getConnection().prepareStatement(QueriesSQL.addProduct);

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void addDecoration() throws  EmptyStringException, InvalidDecorationType, NumberFormatException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProduct);

            System.out.println("Enter Decoration name: ");
            String name = MainMenu.SC.nextLine();
            System.out.println("Enter Decoration price: ");
            double price = Double.parseDouble(MainMenu.SC.nextLine());
            System.out.println("Enter Decoration material type (wood/plastic): ");
            String materialType = MainMenu.SC.nextLine();
            if(!(materialType.equalsIgnoreCase("wood")) && !(materialType.equalsIgnoreCase("plastic"))) {
                throw new InvalidDecorationType();
            }

            System.out.println("Enter Decoration quantity: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            if (!name.isEmpty()) {
                stmt.setDouble(1, price);
                stmt.setString(2, name);
                stmt.setString(3, "decoration");
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.DOUBLE);
                stmt.setString(6, materialType);
                stmt.setInt(7, quantity);
                stmt.executeUpdate();
                System.out.println("Decoration: " + name + " added to the database");
            } else {
                throw new EmptyStringException("at add Decoration");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public int getStockProductQuantity(int floristId, int productId) throws SQLException {
        String query = QueriesSQL.doWeHaveProduct;
        int quantity = 0;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving product quantity from stock: " + e.getMessage());
            throw e;
        }
        return quantity;
    }

    public boolean isThereProduct(int floristId, int productId, int quantity) throws NotValidIDException{
        String doWeHaveProduct = QueriesSQL.doWeHaveProduct;

        try {
            stmt = getConnection().prepareStatement(doWeHaveProduct);
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                int availableQuantity = res.getInt("quantity");
                return availableQuantity >= quantity;
            } else {
                throw new NotValidIDException("id invalid, please enter a valid ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error checking product availability: " + e.getMessage());
            return false;
        } finally {
            disconnect();
        }
    }

    public String getProductName(int productId) {
        String getProdName = QueriesSQL.getProdName;

        try {
            stmt = getConnection().prepareStatement(getProdName);
            stmt.setInt(1, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                return res.getString("name");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error getting product name: " + e.getMessage());
            return null;
        } finally {
            disconnect();
        }
    }

    public int getProductQuantityFromFloristStock(int productId) {
        int quantity = 0;

        try {
            String query = QueriesSQL.searchProductQuantityInFloristStock;
            PreparedStatement quantityStmt = getConnection().prepareStatement(query);
            quantityStmt.setInt(1, productId);
            ResultSet quantityRes = quantityStmt.executeQuery();

            if (quantityRes.next()) {
                quantity = quantityRes.getInt("quantity");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product quantity from florist stock: " + e.getMessage());
        } finally {
            disconnect();
        }

        return quantity;
    }

    public int getProductQuantity(int productId) throws NotValidIDException{
        int quantity = 0;

        try {
            String query = QueriesSQL.searchProductQuantity;
            PreparedStatement quantityStmt = getConnection().prepareStatement(query);
            quantityStmt.setInt(1, productId);
            ResultSet quantityRes = quantityStmt.executeQuery();

            if (quantityRes.next()) {
                quantity = quantityRes.getInt("quantity");
            }else{
                throw new NotValidIDException("id invalid, please enter a valid ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product quantity: " + e.getMessage());
        } finally {
            disconnect();
        }

        return quantity;
    }

    public void updateMainProduct(int quantityUpdated, int productId) throws SQLException {
        String addBackQuery = QueriesSQL.updateProductByID;
        stmt = getConnection().prepareStatement(addBackQuery);
        stmt.setInt(1, quantityUpdated);
        stmt.setInt(2, productId);
        stmt.executeUpdate();
    }







    public void createNewStock(int floristID){
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.createNewStock);

            stmt.setInt(1, floristID);
            stmt.executeUpdate();

            System.out.println("stock initialized");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }

    }

}
