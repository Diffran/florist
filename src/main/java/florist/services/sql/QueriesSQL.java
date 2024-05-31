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

    public static final String printGlobalStockList = "SELECT p.id_product, p.name, SUM(p.price * shp.quantity) AS total_price, p.color, p.height, p.material_type, SUM(shp.quantity) AS total_quantity " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ? " +
            "GROUP BY p.name, p.price";

    public static final String printIndividualStockList = "SELECT p.id_product, p.name, p.price, p.color, p.height, p.material_type, shp.quantity " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ?";

    public static final String getTotalStockValue = "SELECT SUM(p.price * shp.quantity) AS total_value " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ?";

    public static final String deleteProductFromStock = "UPDATE stock_has_product SET quantity = quantity - ? " +
            "WHERE stock_id_stock = (SELECT id_stock FROM stock WHERE florist_id_florist = ?) AND product_id_product = ?";

    public static final String addProductToStock = "INSERT INTO stock_has_product (quantity, stock_id_stock, product_id_product) " +
            "VALUES (?, (SELECT id_stock FROM stock WHERE florist_id_florist = ?), ?) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

    // Ticket queries
    public static final String insertTicket = "INSERT INTO ticket (total_price, florist_id_florist) VALUES (?, ?)";

    public static final String insertProductTicket = "INSERT INTO product_has_ticket (quantity, ticket_id_ticket, product_id_product) VALUES (?, ?, ?)";

    public static final String updateStock = "UPDATE stock_has_product shp " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "SET shp.quantity = shp.quantity - ? " +
            "WHERE s.florist_id_florist = ? AND shp.product_id_product = ?";

    public static final String doWeHaveProduct = "SELECT shp.quantity " +
            "FROM stock_has_product shp " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ? AND shp.product_id_product = ?";

    public static final String getProdName = "SELECT name FROM product WHERE id_product = ?";

    public static final String calcTotPrice = "SELECT price FROM product WHERE id_product = ?";

    public static final String countTickets = "SELECT COUNT(*) AS total FROM ticket";

    public static final String calculateTotalPrice = "SELECT price FROM product WHERE id_product = ?";
}

