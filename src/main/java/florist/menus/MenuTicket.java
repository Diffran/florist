package florist.menus;

import florist.models.Ticket;
import florist.services.ticket.TicketService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static florist.menus.MenuFlorist.menuFlorist;
import static florist.menus.option.MenuTicketOption.*;

public class MenuTicket {
    public static void ticketMenu(int floristID) {
        int optionTicket = 0;

        do {
            handleMenu();

            try {
                optionTicket = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionTicket) {
                    case NEW_TICKET -> MenuNewTicket.newTicketMenu(floristID);
                    case LIST_TICKETS -> listTicket(floristID);
                    case EXIT_TICKET -> menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid option. Please enter a number. " + e.getMessage());
            }

        } while (optionTicket != 3);
    }

    private static void handleMenu() {
        System.out.println("-------------TICKET MENU---------------");
        System.out.println("1- NEW TICKET");
        System.out.println("2- LIST TICKETS");
        System.out.println("3- EXIT");
    }

    private static void listTicket(int floristId) {
        List<Ticket> tickets = TicketService.listTicket(floristId);

        if (tickets.isEmpty()) {
            System.out.println("There are no tickets to show.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        tickets.forEach(ticket -> {
            String printInfo = "Date: " + sdf.format(ticket.getDate()) +
                    "\nTicket ID: " + ticket.getId() +
                    "\nTicket_" + ticket.getId() +
                    "\nTotal: " + ticket.getTotalPrice() + "€\n";

            System.out.println(printInfo);
        });

        MenuListTicket.showTicketMenu(floristId);
    }
}
