package florist.menus;

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
                    case NEW_TICKET -> NewTicketMenu.newTicketMenu(floristID);
                    case LIST_TICKETS -> System.out.println("lista los tiquets");
                    //TODO:7- printAllTickets(int idFlorist) metodo que imprime por pantalla todos los tiquets de la id de la florist
                    case EXIT_TICKET -> menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("sha de canvia lexception");//borrar
            }

        } while (optionTicket != 3);
    }

    private static void handleMenu() {
        System.out.println("-------------TICKET MENU---------------");
        System.out.println("1- NEW TICKET");
        System.out.println("2- LIST TICKETS");
        System.out.println("3- EXIT");
    }
}
