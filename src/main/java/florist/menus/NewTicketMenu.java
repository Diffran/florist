package florist.menus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import florist.services.sql.ConnectionSQL;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class NewTicketMenu {
    private static String ticketOption;
    private static int floristID;
    private static int productID;
    private static int quantity;
    private static String userData;
    private static HashMap<Integer, Integer> productList = new HashMap<>(); // key=productID and value=quantity

    // TODO:1 -> private Ticket ticket;

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
        return userData.equalsIgnoreCase("yes");
    }

    private static boolean printTicket(int floristID, HashMap<Integer, Integer> productList) {
        class Ticket {
            int floristID;
            HashMap<Integer, Integer> productList;
            double totalPrice;

            Ticket(int floristID, HashMap<Integer, Integer> productList, double totalPrice) {
                this.floristID = floristID;
                this.productList = productList;
                this.totalPrice = totalPrice;
            }
        }

        try {
            double totalPrice = ConnectionSQL.getInstance().calculateTotalPrice(productList);
            Ticket ticket = new Ticket(floristID, productList, totalPrice);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(ticket);

            try (FileWriter fileWriter = new FileWriter("ticket.json")) {
                fileWriter.write(json);
            }

            return true;
        } catch (IOException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
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
