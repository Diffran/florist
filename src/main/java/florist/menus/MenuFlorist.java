package florist.menus;

import static florist.menus.option.MenuFloristOption.*;

public class MenuFlorist {
    private static int floristID;

    public static void menuFlorist(int florist) {
        floristID = florist;
        int optionFlorist = 0;

        do {
            handleMenu();

            try {
                optionFlorist = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionFlorist) {
                    case STOCK -> MenuStock.stockMenu(floristID);
                    case TICKET -> MenuTicket.ticketMenu(floristID);
                    case TOTAL_TICKET_SELL -> System.out.println("print totalSell");
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
        System.out.println("3- TOTAL TICKET SELL");
        System.out.println("4- EXIT");
    }
}
