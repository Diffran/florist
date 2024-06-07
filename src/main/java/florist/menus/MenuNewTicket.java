package florist.menus;

import com.google.gson.Gson;

import static florist.menus.option.NewTicketMenuOption.*;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.NotValidIDException;
import florist.models.Florist;
import florist.models.Ticket;
import florist.services.florist.FloristService;
import florist.services.product.ProductService;
import florist.services.stock.StockService;
import florist.services.ticket.TicketService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MenuNewTicket {
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
                    case EXIT_NEW_TICKET -> MenuTicket.ticketMenu(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NotValidIDException | EmptySQLTableException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage() + "Invalid input. Please enter a number.");
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

        } else
            return false;

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
            String productName = ProductService.getProductName(productId);
            double productPrice = TicketService.getProductPrice(productId);
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
        if (!directory.exists())
            directory.mkdirs();

        try (FileWriter writer = new FileWriter(directory + "/ticket_" + ticket.getId() + ".json")) {
            writer.write(json);
        }

        System.out.println("Ticket saved to: " + directory + "/ticket_" + ticket.getId() + ".json");
    }

    private static void addProduct() throws EmptySQLTableException, NotValidIDException {
        List<Integer> idProducts = StockService.printIndividualStockList(floristID);

        System.out.println("Enter product ID: ");
        userData = MainMenu.SC.nextLine();
        int productID = Integer.parseInt(userData);

        if (!idProducts.contains(productID))
            throw new NotValidIDException("ID " + productID + " is invalid, please enter a valid ID.");

        System.out.println("Enter product quantity: ");
        userData = MainMenu.SC.nextLine();
        int quantity = Integer.parseInt(userData);

        if (quantity <= 0)
            throw new NotValidIDException(quantity + " is invalid, please enter a valid quantity.");

        if (StockService.isThereProduct(floristID, productID, quantity)) {
            PRODUCT_LIST.put(productID, quantity);
            System.out.println("Product added to ticket.");
        } else
            System.out.println("Insufficient stock.");

    }

    private static void completeTicket(int floristId) {
        if (isListEmpty())
            return;

        TicketService.completeTicket(floristId, PRODUCT_LIST);
        System.out.println("Ticket completed and stock updated.");

        PRODUCT_LIST.clear();

        if (printTicketMenu())
            System.out.println("Printed ticket in JSON.");

        MenuTicket.ticketMenu(floristID);
    }

    private static int generateTicketId() {
        return TicketService.countTickets() + 1;
    }

    private static Florist getFlorist(int floristID) throws SQLException {
        String floristName = FloristService.getFloristName(floristID);

        Florist florist = new Florist();
        florist.setId(floristID);
        florist.setName(floristName);

        return florist;
    }

    private static void listTicketProducts() {
        if (isListEmpty())
            return;

        System.out.println("Products in the ticket:");

        for (HashMap.Entry<Integer, Integer> entry : PRODUCT_LIST.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            String productName = ProductService.getProductName(productId);
            System.out.println("Product ID: " + productId + ", Name: " + productName + ", Quantity: " + quantity);
        }
    }

    private static boolean isListEmpty() {
        if (PRODUCT_LIST.isEmpty()) {
            System.out.println("There are no products on the ticket.");

            return true;
        }

        return false;
    }
}