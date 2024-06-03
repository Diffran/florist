package florist.services.sql;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.exceptions.InvalidDecorationType;
import florist.menus.MainMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionSQL {
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/florist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
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
                //System.out.println("Connected");
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
                //System.out.println("Disconnected");

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void printFlorist() {
        try {
            getConnection();
            st = connection.createStatement();
            res = st.executeQuery(QueriesSQL.printFloristSQL);

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
            stmt = getConnection().prepareStatement(QueriesSQL.floristExistsSQL);
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void addDecoration() throws  EmptyStringException, InvalidDecorationType, NumberFormatException {
        try {
            stmt = getConnection().prepareStatement(QueriesSQL.addProductSQL);

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

    public void returnQuantityToMainStock(int floristId, int productId, int quantity) {
        String query = QueriesSQL.returnProductToMainStock;

        try {
            int quantityProduct = getProductQuantity(productId);
            int quantityUpdated = quantityProduct + quantity;

            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, floristId);
            stmt.setInt(3, productId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                updateMainProduct(quantityUpdated, productId);

                System.out.println("Product quantity updated successfully.");
            } else {
                System.out.println("Product not found in stock or insufficient quantity.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public double getTotalStockValue(int floristId) {
        String query = QueriesSQL.getTotalStockValue;

        try {
            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (res.next()) {
                return res.getDouble("total_value");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            System.out.println("Error getting total stock value: " + e.getMessage());
            return 0.0;
        } finally {
            disconnect();
        }
    }

    public void printIndividualStockList(int floristId) throws EmptySQLTableException{
        String query = QueriesSQL.printIndividualStockList;

        try {
            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (!res.isBeforeFirst()) {
                throw new EmptySQLTableException("Empty Stock");
            } else {
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
            }
        } catch (SQLException e) {
            System.out.println("Error printing individual stock list: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void printGlobalStockList(int floristId) throws SQLException, EmptySQLTableException {
        String query = QueriesSQL.printGlobalStockList;

        try{
            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();
            if (!res.isBeforeFirst()) {
                throw new EmptySQLTableException("Empty Stock");
            } else {
                while (res.next()) {
                    System.out.println(
                            "Product: " + res.getString("name") +
                                    " - Total Quantity: " + res.getInt("total_quantity") +
                                    " - Total Price: " + res.getDouble("total_price") +
                                    "€"
                    );
                }
            } 
        }catch (SQLException e) {
            System.out.println("Error printing global stock list: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public boolean isThereProduct(int floristId, int productId, int quantity) {
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
                return false;
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

    public void completeTicket(int floristId, HashMap<Integer, Integer> productList) {
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
                PreparedStatement updateStmt = getConnection().prepareStatement(updateStock);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, floristId);
                updateStmt.setInt(3, productId);
                updateStmt.executeUpdate();

                String insertProductTicket = QueriesSQL.insertProductTicket;
                PreparedStatement insertStmt = getConnection().prepareStatement(insertProductTicket);
                insertStmt.setInt(1, quantity);
                insertStmt.setInt(2, ticketId);
                insertStmt.setInt(3, productId);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error completing ticket: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public double calculateTotalPrice(HashMap<Integer, Integer> productList) {
        double totalPrice = 0.0;

        try {
            for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                String calcTotPrice = QueriesSQL.getProductPrice;
                PreparedStatement priceStmt = getConnection().prepareStatement(calcTotPrice);
                priceStmt.setInt(1, productId);
                res = priceStmt.executeQuery();

                if (res.next()) {
                    double price = res.getDouble("price");
                    totalPrice += price * quantity;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculating total price: " + e.getMessage());
        } finally {
            disconnect();
        }

        return totalPrice;
    }

    public int countTickets() {
        int count = 0;

        try {
            connect();
            String query = QueriesSQL.countTickets;
            Statement countStmt = connection.createStatement();
            ResultSet rs = countStmt.executeQuery(query);

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

    public double getProductPrice(int productId) {
        double price = 0.0;

        try {
            String query = QueriesSQL.getProductPrice;
            PreparedStatement priceStmt = getConnection().prepareStatement(query);
            priceStmt.setInt(1, productId);
            ResultSet priceRes = priceStmt.executeQuery();

            if (priceRes.next()) {
                price = priceRes.getDouble("price");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product price: " + e.getMessage());
        } finally {
            disconnect();
        }

        return price;
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

    public int getProductQuantity(int productId) {
        int quantity = 0;

        try {
            String query = QueriesSQL.searchProductQuantity;
            PreparedStatement quantityStmt = getConnection().prepareStatement(query);
            quantityStmt.setInt(1, productId);
            ResultSet quantityRes = quantityStmt.executeQuery();

            if (quantityRes.next()) {
                quantity = quantityRes.getInt("quantity");
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

    public void deleteProductFromFloristStockByID(int floristId, int productId) {

        try {
            int quantityProduct = getProductQuantity(productId);
            int quantity = getProductQuantityFromFloristStock(productId);
            int quantityUpdated = quantityProduct + quantity;

            String deleteQuery = QueriesSQL.deleteProductFromFloristStockByID;
            stmt = getConnection().prepareStatement(deleteQuery);
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with ID: " + productId + " has been successfully deleted from florist stock.");

                updateMainProduct(quantityUpdated, productId);

            } else {
                System.out.println("Product not found in florist stock.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting product from florist stock: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("Error closing statement: " + ex.getMessage());
            }
            disconnect();
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
                        (res.getString("color") == null ? "" : " - Color: " + res.getString("color")) +
                        (res.getString("height") != null ? ", Height: " + res.getString("height") : "") +
                        (res.getString("material_type") != null ? ", Material Type: " + res.getString("material_type") : "") +
                        ", Quantity: " + res.getInt("quantity");
                products.add(productDetails);
            }
        } catch (SQLException e) {
            System.out.println("Error listing products: " + e.getMessage());
            throw e;
        } finally {
            try {
                if (res != null) res.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
            disconnect();
        }

        return products;
    }

    public void addProductToFloristStock(int quantity, int productId, int floristId) throws SQLException {
        try {
            int quantityMainProduct = getProductQuantity(productId);
            int quantityUpdated = quantityMainProduct - quantity;
            updateMainProduct(quantityUpdated, productId);

            String query = QueriesSQL.addProductToStock;
            stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, floristId);
            stmt.setInt(3, productId);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product with ID: " + productId + " has been successfully added to florist stock.");
            } else {
                System.out.println("Product not found in florist stock.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding product to florist stock: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.out.println("Error closing statement: " + ex.getMessage());
            }
            disconnect();
        }
    }

    public double listTotalTickets(int id_florist) {
        double totalSales = 0;
        String query = QueriesSQL.totalTicketsSQL;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, id_florist);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalSales = rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculating tickets total value: " + e.getMessage());
        } finally {
            disconnect();
        }

        return totalSales;
    }

}
