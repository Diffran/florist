package florist.services.sql;

public class QueriesSQL {
    //SELECT
    public static final String printAllTickets = "SELECT t.id_ticket, t.date, t.total_price, f.name AS florist_name " +
            "FROM ticket t " +
            "JOIN florist f ON t.florist_id_florist = f.id_florist " +
            "WHERE f.id_florist = ?";

    public static final String printIndividualTicket = "SELECT t.id_ticket, t.date, t.total_price, f.name " +
            "FROM ticket t " +
            "JOIN florist f ON t.florist_id_florist = f.id_florist " +
            "WHERE t.id_ticket = ? AND t.florist_id_florist = ?";

    public static final String printProductToIndividualTicket = "SELECT p.id_product, p.name, p.type, pt.quantity, p.price, " +
            "p.color, p.height, p.material_type " +
            "FROM product_has_ticket pt " +
            "JOIN product p ON pt.product_id_product = p.id_product " +
            "WHERE pt.ticket_id_ticket = ?";

    public static final String totalTickets = "SELECT SUM(total_price) from ticket where florist_id_florist =?";

    public static final String floristExists = "SELECT * FROM florist WHERE id_florist = ?";

    public static final String printFlorist = "SELECT * FROM florist";

    public static final String listAllProduct = "SELECT * FROM product";

    public static final String getProdName = "SELECT name FROM product WHERE id_product = ?";

    public static final String getProductPrice = "SELECT price FROM product WHERE id_product = ?";

    public static final String countTickets = "SELECT COUNT(*) AS total FROM ticket";

    public static final String selectFloristID = "SELECT  id_florist FROM FLORIST WHERE name=?";

    public static final String searchProductQuantity = "SELECT quantity FROM product WHERE id_product = ?";

    public static final String getTotalStockValue = "SELECT SUM(p.price * shp.quantity) AS total_value " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ?";

    public static final String printGlobalStockList = "SELECT p.id_product, p.name, shp.quantity, SUM(p.price * shp.quantity) AS total_price, p.color, p.height, p.material_type, SUM(shp.quantity) AS total_quantity " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ? AND shp.quantity > 0 " +
            "GROUP BY p.name";

    public static final String printIndividualStockList = "SELECT p.id_product, p.name, p.price, p.color, p.height, p.material_type, shp.quantity " +
            "FROM product p " +
            "JOIN stock_has_product shp ON p.id_product = shp.product_id_product " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ? AND shp.quantity > 0";

    public static final String doWeHaveProduct = "SELECT shp.quantity " +
            "FROM stock_has_product shp " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "WHERE s.florist_id_florist = ? AND shp.product_id_product = ?";

    public static final String searchProductQuantityInFloristStock = "SELECT quantity FROM stock_has_product WHERE product_id_product = ?";

    //INSERTS
    public static final String createNewFlorist = "INSERT INTO florist (name, total_stock_value) VALUES (?, 0.0)";

    public static final String createNewStock = "INSERT INTO stock (florist_id_florist) VALUES (?)";

    public static final String insertTicket = "INSERT INTO ticket (total_price, florist_id_florist) VALUES (?, ?)";

    public static final String insertProductTicket = "INSERT INTO product_has_ticket (quantity, ticket_id_ticket, product_id_product) VALUES (?, ?, ?)";

    public static final String addProduct = "INSERT INTO product (price, name, type, color, height, material_type, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String addProductToStock = "INSERT INTO stock_has_product (quantity, stock_id_stock, product_id_product) " +
            "VALUES (?, (SELECT id_stock FROM stock WHERE florist_id_florist = ?), ?) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";


    //UPDATES
    public static final String updateProductByID = "UPDATE product SET quantity = ? WHERE id_product = ?";

    public static final String returnProductToMainStock = "UPDATE stock_has_product SET quantity = quantity - ? " +
            "WHERE stock_id_stock = (SELECT id_stock FROM stock WHERE florist_id_florist = ?) AND product_id_product = ?";

    public static final String updateStock = "UPDATE stock_has_product shp " +
            "JOIN stock s ON shp.stock_id_stock = s.id_stock " +
            "SET shp.quantity = shp.quantity - ? " +
            "WHERE s.florist_id_florist = ? AND shp.product_id_product = ?";


    //DELETES
    public static final String deleteFlorist = "DELETE FROM florist WHERE id_florist = ?";
    
    public static final String deleteProductFromFloristStockByID = "DELETE FROM stock_has_product WHERE stock_id_stock = (SELECT id_stock FROM stock WHERE florist_id_florist = ?) AND product_id_product = ?";

}
