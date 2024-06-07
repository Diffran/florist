package florist.services.ticket;

import florist.models.Florist;
import florist.models.Ticket;
import florist.models.factory.ProductFactory;
import florist.models.product.*;
import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {
    private static final ConnectionSQL CONNECTION = ConnectionSQL.getInstance();
    public static ResultSet res;
    public static PreparedStatement stmt;
    private static Connection connection;

    public static List<Ticket> listTicket(int floristID) {
        List<Ticket> tickets = new ArrayList<>();

        try {
            CONNECTION.connect();

            PreparedStatement stmt = CONNECTION.getConnection().prepareStatement(QueriesSQL.printAllTickets);
            stmt.setInt(1, floristID);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(res.getInt("id_ticket"));
                ticket.setDate(res.getDate("date"));
                ticket.setTotalPrice(res.getDouble("total_price"));

                Florist florist = new Florist();
                florist.setName(res.getString("florist_name"));
                ticket.setFlorist(florist);

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return tickets;
    }

    public static Ticket getTicketDetails(int ticketId, int floristId) {
        Ticket ticket = null;

        try {
            CONNECTION.connect();

            String query = QueriesSQL.printIndividualTicket;

            PreparedStatement stmt = CONNECTION.getConnection().prepareStatement(query);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, floristId);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                ticket = new Ticket();
                ticket.setId(res.getInt("id_ticket"));
                ticket.setDate(res.getDate("date"));

                Florist florist = new Florist();
                florist.setName(res.getString("name"));
                florist.setId(floristId);
                ticket.setFlorist(florist);

                HashMap<String, HashMap<String, Object>> productList = new HashMap<>();
                String productQuery = QueriesSQL.printProductToIndividualTicket;
                PreparedStatement productStmt = CONNECTION.getConnection().prepareStatement(productQuery);
                productStmt.setInt(1, ticketId);
                ResultSet productRes = productStmt.executeQuery();

                ProductFactory productFactory = new ProductFactory();

                while (productRes.next()) {
                    String productType = productRes.getString("type");
                    int productId = productRes.getInt("id_product");
                    int quantity = productRes.getInt("quantity");
                    double price = productRes.getDouble("price");

                    Map<String, Object> productParams = new HashMap<>();
                    productParams.put("price", price);

                    switch (productType) {
                        case "tree" -> {
                            double height = productRes.getDouble("height");
                            productParams.put("height", height);
                        }
                        case "flower" -> {
                            String color = productRes.getString("color");
                            productParams.put("color", color);
                        }
                        case "decoration" -> {
                            String materialType = productRes.getString("material_type");
                            productParams.put("material", MaterialDecorationType.valueOf(materialType.toUpperCase()));
                        }
                        default -> System.out.println("Opps");
                    }

                    Product product = productFactory.createProduct(productType, productParams);
                    product.setId(productId);
                    product.setName(productRes.getString("name"));

                    HashMap<String, Object> productDetails = new HashMap<>();
                    productDetails.put("name", product.getName());
                    productDetails.put("quantity", quantity);
                    productDetails.put("price", price);
                    productList.put(String.valueOf(productId), productDetails);
                }

                ticket.setProductList(productList);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching ticket details: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return ticket;
    }

    private static double calculateTotalPrice(HashMap<Integer, Integer> productList) {
        double totalPrice = 0.0;

        try {
            for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                String calcTotPrice = QueriesSQL.getProductPrice;
                PreparedStatement priceStmt = CONNECTION.getConnection().prepareStatement(calcTotPrice);
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
            CONNECTION.disconnect();
        }

        return totalPrice;
    }

    public static void completeTicket(int floristId, HashMap<Integer, Integer> productList) {
        try {
            String insertTicket = QueriesSQL.insertTicket;
            double totalPrice = calculateTotalPrice(productList);
            stmt = CONNECTION.getConnection().prepareStatement(insertTicket, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, totalPrice);
            stmt.setInt(2, floristId);
            stmt.executeUpdate();

            res = stmt.getGeneratedKeys();
            int ticketId = 0;

            if (res.next()) ticketId = res.getInt(1);


            for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                String updateStock = QueriesSQL.updateStock;
                PreparedStatement updateStmt = CONNECTION.getConnection().prepareStatement(updateStock);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, floristId);
                updateStmt.setInt(3, productId);
                updateStmt.executeUpdate();

                String insertProductTicket = QueriesSQL.insertProductTicket;
                PreparedStatement insertStmt = CONNECTION.getConnection().prepareStatement(insertProductTicket);
                insertStmt.setInt(1, quantity);
                insertStmt.setInt(2, ticketId);
                insertStmt.setInt(3, productId);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error completing ticket: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }
    }

    public static int countTickets() {
        int count = 0;

        try {
            CONNECTION.connect();
            String query = QueriesSQL.countTickets;
            Statement countStmt = connection.createStatement();
            ResultSet rs = countStmt.executeQuery(query);

            if (rs.next()) count = rs.getInt("total");

        } catch (SQLException e) {
            System.out.println("Error counting tickets: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return count;
    }

    public static double getProductPrice(int productId) {
        double price = 0.0;

        try {
            String query = QueriesSQL.getProductPrice;
            PreparedStatement priceStmt = CONNECTION.getConnection().prepareStatement(query);
            priceStmt.setInt(1, productId);
            ResultSet priceRes = priceStmt.executeQuery();

            if (priceRes.next()) price = priceRes.getDouble("price");

        } catch (SQLException e) {
            System.out.println("Error fetching product price: " + e.getMessage());

        } finally {
            CONNECTION.disconnect();
        }

        return price;
    }

    public static double listTotalTickets(int id_florist) {
        double totalSales = 0;
        String query = QueriesSQL.totalTickets;

        try (PreparedStatement stmt = CONNECTION.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id_florist);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) totalSales = rs.getDouble(1);

            }
        } catch (SQLException e) {
            System.out.println("Error calculating tickets total value: " + e.getMessage());
        } finally {
            CONNECTION.disconnect();
        }

        return totalSales;
    }

}
