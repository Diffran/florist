package florist.menus;

import com.google.gson.Gson;
import florist.models.Florist;
import florist.models.Ticket;
import florist.services.sql.ConnectionSQL;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class NewTicketMenu {
    private static String ticketOption;
    private static int floristID;
    private static int productID;
    private static int quantity;
    private static String userData;
    private static HashMap<Integer, Integer> productList = new HashMap<>(); // key=productID and value=quantity

    public static void newTicketMenu(int floristId) {
        floristID = floristId; // This should be okay.
        do {
            System.out.println("-----------NEW TICKET--------------");
            System.out.println("1- ADD PRODUCT");
            System.out.println("2- LIST");
            System.out.println("3- COMPLETED");
            System.out.println("4- EXIT");

            ticketOption = MainMenu.SC.nextLine();
            try {
                switch (ticketOption) {
                    case "1":
                        ConnectionSQL.getInstance().printIndividualStockList(floristID);
                        System.out.println("Enter product ID: ");
                        userData = MainMenu.SC.nextLine();
                        productID = Integer.parseInt(userData);

                        System.out.println("Enter product quantity: ");
                        userData = MainMenu.SC.nextLine();
                        quantity = Integer.parseInt(userData);

                        if (ConnectionSQL.getInstance().isThereProduct(floristID, productID, quantity)) {
                            productList.put(productID, quantity);
                            System.out.println("Product added to ticket.");
                        } else {
                            System.out.println("Insufficient stock.");
                        }
                        break;
                    case "2":
                        listTicketProducts();
                        break;
                    case "3":
                        completeTicket(floristID, productList);
                        if (printTicketMenu()) {
                            System.out.println("Printed ticket in JSON");
                        }
                        MenuFlorist.ticketMenu();
                        break;
                    case "4":
                        MenuFlorist.menuFlorist(floristID);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!ticketOption.equals("4"));
    }

    private static boolean printTicketMenu() {
        System.out.println("Print ticket? [yes][no]");
        userData = MainMenu.SC.nextLine();
        if (userData.equals("yes")) {
            try {
                printTicket(floristID, productList);
                return true;
            } catch (IOException | SQLException e) {
                System.out.println("Error printing ticket: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    private static void printTicket(int floristID, HashMap<Integer, Integer> productList) throws IOException, SQLException {
        Ticket ticket = new Ticket();
        ticket.setIdTICKET(generateTicketId()); // Implement generateTicketId() to generate a unique ticket ID
        ticket.setDate(new Date());
        ticket.setFlorist(getFlorist(floristID)); // Implement getFlorist() to retrieve the Florist object
        ticket.setProductList(productList);

        Gson gson = new Gson();
        String json = gson.toJson(ticket);

        // Save the JSON string to a file
        try (FileWriter writer = new FileWriter("ticket_" + ticket.getIdTICKET() + ".json")) {
            writer.write(json);
        }
    }

    private static int generateTicketId() {
        // Implement logic to generate a unique ticket ID, e.g., by querying the database
        // For simplicity, return a random number here
        return (int) (Math.random() * 100000);
    }

    private static Florist getFlorist(int floristID) throws SQLException {
        // Implement logic to retrieve the Florist object from the database using floristID
        // For simplicity, return a dummy Florist object here
        Florist florist = new Florist();
        florist.setId(floristID);
        florist.setName("Florist Name");
        return florist;
    }

    private static void listTicketProducts() {
        System.out.println("Products in the ticket:");
        for (HashMap.Entry<Integer, Integer> entry : productList.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            try {
                String productName = ConnectionSQL.getInstance().getProductName(productId);
                System.out.println("Product ID: " + productId + ", Name: " + productName + ", Quantity: " + quantity);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void completeTicket(int floristId, HashMap<Integer, Integer> productList) {
        try {
            ConnectionSQL.getInstance().completeTicket(floristId, productList);
            System.out.println("Ticket completed and stock updated.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
