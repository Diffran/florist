package florist.services.ticket;

import florist.models.Florist;
import florist.models.Ticket;
import florist.models.factory.ProductFactory;
import florist.models.product.*;
import florist.services.sql.ConnectionSQL;
import florist.services.sql.QueriesSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {
    private static final ConnectionSQL conn = ConnectionSQL.getInstance();

    public static List<Ticket> listTicket(int floristID) {
        List<Ticket> tickets = new ArrayList<>();

        try {
            conn.connect();

            PreparedStatement stmt = conn.getConnection().prepareStatement(QueriesSQL.printAllTickets);
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
            conn.disconnect();
        }

        return tickets;
    }

    public static Ticket getTicketDetails(int ticketId, int floristId) {
        Ticket ticket = null;

        try {
            conn.connect();

            String query = QueriesSQL.printIndividualTicket;

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, floristId);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                ticket = new Ticket();
                ticket.setId(res.getInt("id_ticket"));
                ticket.setDate(res.getDate("date"));
                //ticket.setTotalPrice(res.getDouble("total_price"));

                Florist florist = new Florist();
                florist.setName(res.getString("name"));
                florist.setId(floristId);
                ticket.setFlorist(florist);

                HashMap<String, HashMap<String, Object>> productList = new HashMap<>();
                String productQuery = QueriesSQL.printProductToIndividualTicket;
                PreparedStatement productStmt = conn.getConnection().prepareStatement(productQuery);
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
                        case "tree":
                            double height = productRes.getDouble("height");
                            productParams.put("height", height);
                            break;
                        case "flower":
                            String color = productRes.getString("color");
                            productParams.put("color", color);
                            break;
                        case "decoration":
                            String materialType = productRes.getString("material_type");
                            productParams.put("material", MaterialDecorationType.valueOf(materialType.toUpperCase()));
                            break;
                        default:
                            System.out.println("Opps");
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
            conn.disconnect();
        }

        return ticket;
    }
}
