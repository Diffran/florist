package florist.services.stock;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.NotValidIDException;
import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockService {
    private static final ConnectionSQL CONNECTION = ConnectionSQL.getInstance();
    public static PreparedStatement stmt;
    public static ResultSet res;

    public static void createNewStock(int floristID) {
        try {
            stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.createNewStock);

            stmt.setInt(1, floristID);
            stmt.executeUpdate();

            System.out.println("stock initialized");

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

    }

    public static List<String> listAllProduct() throws SQLException {
        List<String> products = new ArrayList<>();
        String query = QueriesSQL.listAllProduct;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(query);
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

            CONNECTION.disconnect();
        }

        return products;
    }

    private static void updateMainProduct(int quantityUpdated, int productId) throws SQLException {
        String addBackQuery = QueriesSQL.updateProductByID;
        stmt = CONNECTION.getConnection().prepareStatement(addBackQuery);
        stmt.setInt(1, quantityUpdated);
        stmt.setInt(2, productId);
        stmt.executeUpdate();
    }

    public static void addProductToFloristStock(int quantity, int productId, int floristId) throws SQLException, NotValidIDException {
        int quantityMainProduct = getProductQuantity(productId);
        int quantityUpdated = quantityMainProduct - quantity;

        if (quantityMainProduct < quantity || quantity <= 0) {
            System.out.println("You are trying to get " + quantity + " Items but we have in stock " + quantityMainProduct + ", please check existence and try again");
            return;
        }

        updateMainProduct(quantityUpdated, productId);

        try {
            String query = QueriesSQL.addProductToStock;
            stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, floristId);
            stmt.setInt(3, productId);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0)
                System.out.println("Product with ID: " + productId + " has been successfully added to florist stock.");

            else System.out.println("Product not found in florist stock.");

        } catch (SQLException e) {
            System.out.println("Error adding product to florist stock: " + e.getMessage());

        } finally {
            try {
                if (stmt != null) stmt.close();

            } catch (SQLException ex) {
                System.out.println("Error closing statement: " + ex.getMessage());
            }

            CONNECTION.disconnect();
        }
    }

    public static List<Integer> printIndividualStockList(int floristId) throws EmptySQLTableException {
        String query = QueriesSQL.printIndividualStockList;
        List<Integer> idProducts = new ArrayList<>();

        try {
            stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (!res.isBeforeFirst()) throw new EmptySQLTableException("Empty Stock");

            else {
                while (res.next()) {
                    idProducts.add(res.getInt("id_product"));

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
            CONNECTION.disconnect();
        }

        return idProducts;
    }

    private static int getStockProductQuantity(int floristId, int productId) throws SQLException {
        String query = QueriesSQL.doWeHaveProduct;
        int quantity = 0;

        try (PreparedStatement stmt = CONNECTION.getConnection().prepareStatement(query)) {
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) quantity = rs.getInt("quantity");

            }

        } catch (SQLException e) {
            System.out.println("Error retrieving product quantity from stock: " + e.getMessage());
            throw e;
        }

        return quantity;
    }

    public static void returnQuantityToMainStock(int floristId, int productId, int quantity) throws SQLException, NotValidIDException {
        String query = QueriesSQL.returnProductToMainStock;
        int quantityProduct = getProductQuantity(productId);
        int quantityUpdated = quantityProduct + quantity;

        int quantityStock = getStockProductQuantity(floristId, productId);


        if (quantity > quantityStock || quantity <= 0) {
            System.out.println("You are trying to return " + quantity + " Items but we have in stock " + quantityStock + ", please check existence and try again");
            return;
        }

        try {

            stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, quantity);
            stmt.setInt(2, floristId);
            stmt.setInt(3, productId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                updateMainProduct(quantityUpdated, productId);
                System.out.println("Product quantity updated successfully.");

            } else System.out.println("Product not found in stock or insufficient quantity.");

        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    private static int getProductQuantityFromFloristStock(int productId) {
        int quantity = 0;

        try {
            String query = QueriesSQL.searchProductQuantityInFloristStock;
            PreparedStatement quantityStmt = CONNECTION.getConnection().prepareStatement(query);
            quantityStmt.setInt(1, productId);
            ResultSet quantityRes = quantityStmt.executeQuery();

            if (quantityRes.next()) quantity = quantityRes.getInt("quantity");

        } catch (SQLException e) {
            System.out.println("Error fetching product quantity from florist stock: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return quantity;
    }

    private static int getProductQuantity(int productId) throws NotValidIDException {
        int quantity = 0;

        try {
            String query = QueriesSQL.searchProductQuantity;
            PreparedStatement quantityStmt = CONNECTION.getConnection().prepareStatement(query);
            quantityStmt.setInt(1, productId);
            ResultSet quantityRes = quantityStmt.executeQuery();

            if (quantityRes.next()) quantity = quantityRes.getInt("quantity");

            else throw new NotValidIDException("id invalid, please enter a valid ID.");

        } catch (SQLException e) {
            System.out.println("Error fetching product quantity: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return quantity;
    }

    public static void deleteProductFromFloristStockByID(int floristId, int productId) throws NotValidIDException {

        try {
            int quantityProduct = getProductQuantity(productId);
            int quantity = getProductQuantityFromFloristStock(productId);
            int quantityUpdated = quantityProduct + quantity;

            String deleteQuery = QueriesSQL.deleteProductFromFloristStockByID;
            stmt = CONNECTION.getConnection().prepareStatement(deleteQuery);
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product with ID: " + productId + " has been successfully deleted from florist stock.");

                updateMainProduct(quantityUpdated, productId);

            } else System.out.println("Product not found in florist stock.");

        } catch (SQLException e) {
            System.out.println("Error deleting product from florist stock: " + e.getMessage());

        } finally {
            try {
                if (stmt != null)
                    stmt.close();

            } catch (SQLException ex) {
                System.out.println("Error closing statement: " + ex.getMessage());
            }

            CONNECTION.disconnect();
        }
    }

    public static double getTotalStockValue(int floristId) {
        String query = QueriesSQL.getTotalStockValue;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (res.next()) return res.getDouble("total_value");

            else {
                System.out.println("There are no items in stock");
                return 0.0;
            }

        } catch (SQLException e) {
            System.out.println("Error getting total stock value: " + e.getMessage());
            return 0.0;

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static void printGlobalStockList(int floristId) throws SQLException, EmptySQLTableException {
        String query = QueriesSQL.printGlobalStockList;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, floristId);
            res = stmt.executeQuery();

            if (!res.isBeforeFirst()) throw new EmptySQLTableException("Empty Stock");

            else {
                while (res.next()) {
                    System.out.println(
                            "Product: " + res.getString("name") +
                                    " - Total Quantity: " + res.getInt("total_quantity") +
                                    " - Total Price: " + res.getDouble("total_price") +
                                    "€"
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error printing global stock list: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static boolean isThereProduct(int floristId, int productId, int quantity) throws NotValidIDException {
        String doWeHaveProduct = QueriesSQL.doWeHaveProduct;

        try {
            stmt = CONNECTION.getConnection().prepareStatement(doWeHaveProduct);
            stmt.setInt(1, floristId);
            stmt.setInt(2, productId);
            res = stmt.executeQuery();

            if (res.next()) {
                int availableQuantity = res.getInt("quantity");
                return availableQuantity >= quantity;
            } else throw new NotValidIDException("id invalid, please enter a valid ID.");

        } catch (SQLException e) {
            System.out.println("Error checking product availability: " + e.getMessage());
            return false;

        } finally {
            CONNECTION.disconnect();
        }
    }
}
