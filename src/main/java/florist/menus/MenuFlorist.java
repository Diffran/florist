package florist.menus;

import florist.services.ticket.TicketService;

import static florist.menus.option.MenuFloristOption.*;


public class MenuFlorist {

    public static void menuFlorist(int florist) {
        int optionFlorist = 0;

        do {
            handleMenu();

            try {
                optionFlorist = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionFlorist) {
                    case STOCK -> MenuStock.stockMenu(florist);
                    case TICKET -> MenuTicket.ticketMenu(florist);
                    case TOTAL_SALES -> {
                        double totalSales = TicketService.listTotalTickets(florist);
                        System.out.println("Total sales: " + totalSales + "€");
                    }
                    case EXIT_FLORIST -> MainMenu.mainMenu();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                optionFlorist = -1;
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                optionFlorist = -1;
            }
        } while (optionFlorist != 4);
    }

    private static void handleMenu() {
        System.out.println("-----------FLORIST MENU--------------");
        System.out.println("1- STOCK");
        System.out.println("2- TICKET");
        System.out.println("3- TOTAL SALES");
        System.out.println("4- EXIT");
    }
}


