package florist.menus;

import com.google.gson.Gson;

import static florist.menus.option.NewTicketMenuOption.*;

import florist.models.Florist;
import florist.models.Ticket;
import florist.services.sql.ConnectionSQL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewTicketMenu {
    private static int floristID;
    private static String userData;
    private static final HashMap<Integer, Integer> PRODUCT_LIST = new HashMap<>();

    public static void newTicketMenu(int floristId) {
        floristID = floristId;
        int ticketOption;

        do {
            handleMenu();

            ticketOption = Integer.parseInt(MainMenu.SC.nextLine().trim());

            try {
                switch (ticketOption) {
                    case ADD_PRODUCT -> addProduct();
                    case LIST_ONGOING_TICKET -> listTicketProducts();
                    case COMPLETED -> completeTicket(floristID);
                    case EXIT_NEW_TICKET -> MenuFlorist.menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (ticketOption != 4);
    }

    private static void handleMenu() {
        System.out.println("-----------NEW TICKET--------------");
        System.out.println("1- ADD PRODUCT");
        System.out.println("2- LIST ONGOING TICKET");
        System.out.println("3- COMPLETED");
        System.out.println("4- EXIT");
    }

    private static boolean printTicketMenu() {
        System.out.println("Print ticket? [Y][N]");
        userData = MainMenu.SC.nextLine();

        if (userData.equalsIgnoreCase("y")) {
            try {
                printTicket(floristID);
                return true;
            } catch (IOException | SQLException e) {
                System.out.println("Error printing ticket: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    private static void printTicket(int floristID) throws IOException, SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(generateTicketId());
        ticket.setDate(new Date());

        Florist florist = getFlorist(floristID);
        ticket.setFlorist(florist);

        HashMap<String, HashMap<String, Object>> productList = new HashMap<>();

        for (HashMap.Entry<Integer, Integer> entry : PRODUCT_LIST.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            String productName = ConnectionSQL.getInstance().getProductName(productId);
            double productPrice = ConnectionSQL.getInstance().getProductPrice(productId);
            HashMap<String, Object> productDetails = new HashMap<>();

            productDetails.put("name", productName);
            productDetails.put("quantity", quantity);
            productDetails.put("price", productPrice);
            productList.put(String.valueOf(productId), productDetails);
        }

        ticket.setProductList(productList);

        Gson gson = new Gson();
        String json = gson.toJson(ticket);

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String dateFolderName = dateFormat.format(new Date());

        File directory = new File("tickets/" + dateFolderName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(directory + "/ticket_" + ticket.getId() + ".json")) {
            writer.write(json);
        }

        System.out.println("Ticket saved to: " + directory + "/ticket_" + ticket.getId() + ".json");
    }

    private static void addProduct() throws SQLException {
        ConnectionSQL.getInstance().printIndividualStockList(floristID);
        System.out.println("Enter product ID: ");
        userData = MainMenu.SC.nextLine();
        int productID = Integer.parseInt(userData);

        System.out.println("Enter product quantity: ");
        userData = MainMenu.SC.nextLine();
        int quantity = Integer.parseInt(userData);

        if (ConnectionSQL.getInstance().isThereProduct(floristID, productID, quantity)) {
            PRODUCT_LIST.put(productID, quantity);
            System.out.println("Product added to ticket.");
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    private static void completeTicket(int floristId) {
        if (PRODUCT_LIST.isEmpty()) {
            System.out.println("There are no products on the ticket.");
            return;
        }

        ConnectionSQL.getInstance().completeTicket(floristId, PRODUCT_LIST);
        System.out.println("Ticket completed and stock updated.");

        if (printTicketMenu()) {
            System.out.println("Printed ticket in JSON.");
        }

        MenuTicket.ticketMenu(floristID);
    }


    private static int generateTicketId() {
        return ConnectionSQL.getInstance().countTickets() + 1;
    }

    private static Florist getFlorist(int floristID) throws SQLException {
        String floristName = ConnectionSQL.getInstance().getFloristName(floristID);

        Florist florist = new Florist();
        florist.setId(floristID);
        florist.setName(floristName);

        return florist;
    }

    private static void listTicketProducts() throws SQLException {
        System.out.println("Products in the ticket:");
        for (HashMap.Entry<Integer, Integer> entry : PRODUCT_LIST.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            String productName = ConnectionSQL.getInstance().getProductName(productId);
            System.out.println("Product ID: " + productId + ", Name: " + productName + ", Quantity: " + quantity);
        }
    }

}