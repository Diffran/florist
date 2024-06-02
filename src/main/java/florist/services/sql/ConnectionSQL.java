package florist.services.sql;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.menus.MainMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sugoalpomodoro";
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
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void createFlorist() throws EmptyStringException {

        try {
            stmt = getConnection().prepareStatement(QueriesSQL.createNewFloristSQL);

            System.out.println("Enter Florist name: ");
            userData = MainMenu.SC.nextLine();

            if (!userData.isEmpty()) {
                stmt.setString(1, userData);
                stmt.executeUpdate();
                System.out.println("florist: " + userData + " added to the database");
            } else {
                throw new EmptyStringException("at create Florist");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printFlorist() {
        try {
            getConnection();
            st = connection.createStatement();
            res = st.executeQuery(QueriesSQL.printFloristSQL);

            while (res.next()) {
                System.out.println("ID: " + res.getInt("id_florist") + " -NAME: " + res.getString("name"));
            }

            disconnect();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean floristExist(int id) throws EmptySQLTableException, SQLException {

        stmt = getConnection().prepareStatement(QueriesSQL.floristExistsSQL);
        stmt.setInt(1, id);
        res = stmt.executeQuery();

        if (res.next()) {
            disconnect();
            return true;
        } else {
            throw new EmptySQLTableException("florist with id: " + id);
        }
    }

    public String getFloristName(int floristId) throws SQLException {
        String floristName = null;

        try {
            stmt = getConnection().prepareStatement(QueriesSQL.floristExistsSQL);
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
                stmt = getConnection().prepareStatement(QueriesSQL.deleteFloristSQL);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("flotist id: " + id + " successfully deleted");

                disconnect();
            }
        } catch (SQLException | EmptySQLTableException e) {
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
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.DOUBLE);
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

    public void returnProductToMainStock(int floristId, int productId, int quantity) throws SQLException {
        String query = QueriesSQL.returnProductToMainStock;

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

        disconnect();
    }

    public double getTotalStockValue(int floristId) throws SQLException {
        String query = QueriesSQL.getTotalStockValue;

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        if (res.next()) {
            disconnect();
            return res.getDouble("total_value");

        } else {
            disconnect();
            return 0.0;
        }

    }

    public void printIndividualStockList(int floristId) throws SQLException {
        String query = QueriesSQL.printIndividualStockList;
        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println(
                    "Product ID: " + res.getInt("id_product") +
                            " - Product: " + res.getString("name") +
                            (res.getString("height") == null ? "" : " - Height: " + res.getString("height")) +
                            (res.getString("color") == null ? "" : " - Color: " + res.getString("color")) +
                            (res.getString("material_type") == null ? "" : " - Material type: " + res.getString("material_type")) +
                            " - Quantity: " + res.getInt("quantity") +
                            " - Unit Price: " + res.getDouble("price") + "€" + "\n"
            );
        }

        disconnect();
    }

    public void printGlobalStockList(int floristId) throws SQLException {
        String query = QueriesSQL.printGlobalStockList;

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println(
                    "Product ID: " + res.getInt("id_product") +
                            " - Product: " + res.getString("name") +
                            " - Total Quantity: " + res.getInt("total_quantity") +
                            " - Total Price: " + res.getDouble("total_price") +
                            "€"
            );
        }

        disconnect();
    }

    public boolean isThereProduct(int floristId, int productId, int quantity) throws SQLException {
        String doWeHaveProduct = QueriesSQL.doWeHaveProduct;
        stmt = getConnection().prepareStatement(doWeHaveProduct);
        stmt.setInt(1, floristId);
        stmt.setInt(2, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            int availableQuantity = res.getInt("quantity");

            disconnect();
            return availableQuantity >= quantity;

        } else {
            disconnect();
            return false;
        }
    }

    public String getProductName(int productId) throws SQLException {
        String getProdName = QueriesSQL.getProdName;
        stmt = getConnection().prepareStatement(getProdName);
        stmt.setInt(1, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            //disconnect();
            return res.getString("name");

        } else {
            //disconnect();
            return null;
        }
    }

    public void completeTicket(int floristId, HashMap<Integer, Integer> productList) throws SQLException {
        try {

            String insertTicket = QueriesSQL.insertTicket;
            double totalPrice = calculateTotalPrice(productList);
            stmt = getConnection().prepareStatement(insertTicket, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, totalPrice);
            stmt.setInt(2, floristId);
            stmt.executeUpdate();

            res = stmt.getGeneratedKeys();
            int ticketId = 0;

            if (res.next()) {
                ticketId = res.getInt(1);
            }

            for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                String updateStock = QueriesSQL.updateStock;
                stmt = getConnection().prepareStatement(updateStock);
                stmt.setInt(1, quantity);
                stmt.setInt(2, floristId);
                stmt.setInt(3, productId);
                stmt.executeUpdate();

                String insertProductTicket = QueriesSQL.insertProductTicket;
                stmt = getConnection().prepareStatement(insertProductTicket);
                stmt.setInt(1, quantity);
                stmt.setInt(2, ticketId);
                stmt.setInt(3, productId);
                stmt.executeUpdate();
            }


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }

    public double calculateTotalPrice(HashMap<Integer, Integer> productList) throws SQLException {
        double totalPrice = 0.0;

        for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            String calcTotPrice = QueriesSQL.getProductPrice;
            stmt = getConnection().prepareStatement(calcTotPrice);
            stmt.setInt(1, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                double price = res.getDouble("price");
                totalPrice += price * quantity;
            }
        }

        disconnect();
        return totalPrice;
    }

    public int countTickets() {
        int count = 0;

        try {
            connect();

            String query = QueriesSQL.countTickets;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error counting tickets: " + e.getMessage());

        } finally {
            disconnect();
        }

        return count;
    }

    public double getProductPrice(int productId) throws SQLException {
        String query = QueriesSQL.getProductPrice;
        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            //disconnect();
            return res.getDouble("price");

        } else {
            //disconnect();
            return 0.0;
        }
    }

    public int getProductQuantityFromFloristStock(int productId) throws SQLException {
        String query = QueriesSQL.searchProductQuantityInFloristStock;
        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            disconnect();
            return res.getInt("quantity");

        } else {
            disconnect();
            return 0;
        }
    }

    public int getProductQuantity(int productId) throws SQLException {
        String query = QueriesSQL.searchProductQuantity;
        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, productId);
        res = stmt.executeQuery();

        if (res.next()) {

            return res.getInt("quantity");

        } else {
         //   disconnect();
            return 0;
        }
    }

    public void updateMainProduct(Connection conn, int quantityUpdated, int productId) throws SQLException {
        String addBackQuery = QueriesSQL.updateProductByID;
        stmt = conn.prepareStatement(addBackQuery);
        stmt.setInt(1, quantityUpdated);
        stmt.setInt(2, productId);
        stmt.executeUpdate();
    }

    public void deleteProductFromFloristStockByID(int floristId, int productId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            int quantityProduct = getProductQuantity(productId);
            int quantity = getProductQuantityFromFloristStock(productId);
            int quantityUpdated = quantityProduct + quantity;

            String deleteQuery = QueriesSQL.deleteProductFromFloristStockByID;
            stmt = conn.prepareStatement(deleteQuery);
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with ID: " + productId + " has been successfully deleted from florist stock.");

                updateMainProduct(conn, quantityUpdated, productId);

                conn.commit();

            } else {
                System.out.println("Product not found in florist stock.");
                conn.rollback();
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex);
                }
            }
            throw e;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    disconnect();
                } catch (SQLException ex) {
                    System.out.println("Error disconnecting: " + ex);
                }
            }
        }
    }

    public List<String> listAllProduct() throws SQLException {
        List<String> products = new ArrayList<>();
        String query = QueriesSQL.listAllProduct;

        try {
            stmt = getConnection().prepareStatement(query);
            res = stmt.executeQuery();

            while (res.next()) {
                String productDetails = "ID: " + res.getInt("id_product") +
                        ", Name: " + res.getString("name") +
                        ", Price: " + res.getDouble("price") +
                        ", Color: " + res.getString("color") +
                        (res.getString("height") != null ? ", Height: " + res.getString("height") : "") +
                        (res.getString("material_type") != null ? ", Material Type: " + res.getString("material_type") : "") +
                        ", Quantity: " + res.getInt("quantity");
                products.add(productDetails);
            }
        } finally {
            if (res != null) res.close();
            if (stmt != null) stmt.close();
            disconnect();
        }

        return products;
    }

    public void addProductToFloristStock(int quantity, int productId, int floristId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            int quantityMainProduct = getProductQuantity(productId);
            int quantityUpdated = quantityMainProduct - quantity;
            updateMainProduct(conn, quantityUpdated, productId);

            String query = QueriesSQL.addProductToStock;
            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, floristId);
            stmt.setInt(3, productId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with ID: " + productId + " has been successfully added to florist stock.");

                conn.commit();
            } else {
                System.out.println("Product not found in florist stock.");
                conn.rollback();
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex);
                }
            }
            throw e;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    disconnect();
                } catch (SQLException ex) {
                    System.out.println("Error disconnecting: " + ex);
                }
            }
        }

    }

}
