package florist.services.sql;

public class QueriesSQL {
    public static final String createNewFloristSQL = "INSERT INTO florist (name, total_stock_value) VALUES (?, 0.0)";
    public static final String printFloristSQL = "SELECT * FROM florist";
    public static final String floristExistsSQL = "SELECT * FROM florist WHERE id_florist = ?";
    public static final String deleteFloristSQL = "DELETE FROM florist WHERE id_florist = ?";

    // New queries for products
    public static final String addProductSQL = "INSERT INTO product (price, name, type, color, height, material_type, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

    // New queries for stock
    public static final String createNewStockSQL = "INSERT INTO stock (florist_id_florist) VALUES (?)";
    public static final String listAllStocksSQL = "SELECT * FROM stock";
    public static final String updateStockSQL = "UPDATE stock SET florist_id_florist = ? WHERE id_stock = ?";
    public static final String deleteStockSQL = "DELETE FROM stock WHERE id_stock = ?";

}
