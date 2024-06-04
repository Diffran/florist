package florist.menus;

import florist.models.Ticket;
import florist.services.ticket.TicketService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static florist.menus.option.ListTicketOption.*;

public class MenuListTicket {
    public static void showTicketMenu(int floristID) {
        int optionTicket = 0;

        do {
            handleMenu();

            try {
                optionTicket = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionTicket) {
                    case SHOW_INDIVIDUAL_TICKET -> showIndividualTicket(floristID);
                    case EXIT -> MenuTicket.ticketMenu(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid option. Please enter a number. "+ e.getMessage());
            }

        } while (optionTicket != 2);
    }

    private static void handleMenu() {
        System.out.println("-------------TICKET OPTION---------------");
        System.out.println("1- SHOW INDIVIDUAL TICKET");
        System.out.println("2- EXIT");
    }

    private static void showIndividualTicket(int floristId) throws NumberFormatException{
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        System.out.println("Enter Ticket ID: ");
        int ticketId = Integer.parseInt(MainMenu.SC.nextLine());

        Ticket ticket = TicketService.getTicketDetails(ticketId, floristId);

        if (ticket != null) {
            System.out.println("\n**********************************");
            System.out.println("Ticket ID: " + ticket.getId());
            System.out.println("Date: " + sdf.format(ticket.getDate()));
            System.out.println("Florist ID: " + ticket.getFlorist().getId());
            System.out.println("Florist Name: " + ticket.getFlorist().getName());

            HashMap<String, HashMap<String, Object>> productList = ticket.getProductList();
            if (productList.isEmpty()) {
                System.out.println("No products in this ticket.");
            } else {
                System.out.println("Products:");
                for (Map.Entry<String, HashMap<String, Object>> entry : productList.entrySet()) {
                    String productId = entry.getKey();
                    HashMap<String, Object> details = entry.getValue();
                    System.out.println("\tProduct ID: " + productId);
                    System.out.println("\tName: " + details.get("name"));
                    System.out.println("\tQuantity: " + details.get("quantity"));
                    System.out.println("\tPrice: " + details.get("price") + "\n");
                }
            }

            System.out.println("Total: " + ticket.getTotalPrice() + "€");
            System.out.println("**********************************\n");
        } else {
            System.out.println("Ticket not found.");
            showIndividualTicket(floristId);
        }

        MenuTicket.ticketMenu(floristId);
    }

}
