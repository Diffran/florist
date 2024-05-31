package florist.services.sql;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.menus.MainMenu;

import java.sql.*;
import java.util.HashMap;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Tysoncete24!";
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

    public void printFlorist() throws EmptySQLTableException {
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
        String query = QueriesSQL.addProductToStock;

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, quantity);
        stmt.setInt(2, floristId);
        stmt.setInt(3, productId);
        stmt.executeUpdate();
        System.out.println("Product added to stock successfully.");
    }

    public void deleteProductFromStock(int floristId, int productId, int quantity) throws SQLException {
        String query = QueriesSQL.deleteProductFromStock;

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
        String query = QueriesSQL.getTotalStockValue;

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
        String query = QueriesSQL.printIndividualStockList;

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println(
                    "Product: " + res.getString("name") +
                            (res.getString("height") == null ? "" : " - Height: " + res.getString("height")) +
                            (res.getString("color") == null ? "" : " - Color: " + res.getString("color")) +
                            (res.getString("material_type") == null ? "" : " - Material type: " + res.getString("material_type")) +
                            " - Quantity: " + res.getInt("quantity") +
                            " - Unit Price: " + res.getDouble("price") + "€" + "\n"
            );
        }
    }

    public void printGlobalStockList(int floristId) throws SQLException {
        String query = QueriesSQL.printGlobalStockList;

        stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, floristId);
        res = stmt.executeQuery();

        while (res.next()) {
            System.out.println("Product: " + res.getString("name") +
                    ", Total Quantity: " + res.getInt("total_quantity") +
                    ", Total Price: " + res.getDouble("total_price") + "€");
        }
    }

    // ticket
    public boolean isThereProduct(int floristId, int productId, int quantity) throws SQLException {
        String doWeHaveProduct = QueriesSQL.doWeHaveProduct;
        stmt = getConnection().prepareStatement(doWeHaveProduct);
        stmt.setInt(1, floristId);
        stmt.setInt(2, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            int availableQuantity = res.getInt("quantity");
            return availableQuantity >= quantity;
        } else {
            return false;
        }
    }

    public String getProductName(int productId) throws SQLException {
        String getProdName = QueriesSQL.getProdName;
        stmt = getConnection().prepareStatement(getProdName);
        stmt.setInt(1, productId);
        res = stmt.executeQuery();

        if (res.next()) {
            return res.getString("name");
        } else {
            return null;
        }
    }

    public void completeTicket(int floristId, HashMap<Integer, Integer> productList) throws SQLException {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);

            // Insert into ticket table
            String insertTicket = QueriesSQL.insertTicket;
            double totalPrice = calculateTotalPrice(productList);
            stmt = conn.prepareStatement(insertTicket, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, totalPrice);
            stmt.setInt(2, floristId);
            stmt.executeUpdate();

            res = stmt.getGeneratedKeys();
            int ticketId = 0;
            if (res.next()) {
                ticketId = res.getInt(1);
            }

            // Update stock and insert into product_has_ticket
            for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Update stock
                String updateStock = QueriesSQL.updateStock;
                stmt = conn.prepareStatement(updateStock);
                stmt.setInt(1, quantity);
                stmt.setInt(2, floristId);
                stmt.setInt(3, productId);
                stmt.executeUpdate();

                // Insert into product_has_ticket
                String insertProductTicket = QueriesSQL.insertProductTicket;
                stmt = conn.prepareStatement(insertProductTicket);
                stmt.setInt(1, quantity);
                stmt.setInt(2, ticketId);
                stmt.setInt(3, productId);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public double calculateTotalPrice(HashMap<Integer, Integer> productList) throws SQLException {
        double totalPrice = 0.0;
        for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            String calcTotPrice = QueriesSQL.calcTotPrice;
            stmt = getConnection().prepareStatement(calcTotPrice);
            stmt.setInt(1, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                double price = res.getDouble("price");
                totalPrice += price * quantity;
            }
        }
        return totalPrice;
    }



}
