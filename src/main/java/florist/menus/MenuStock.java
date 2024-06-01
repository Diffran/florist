package florist.menus;

import static florist.menus.MenuFlorist.menuFlorist;
import static florist.menus.option.MenuStockOption.*;
import florist.services.sql.ConnectionSQL;

public class MenuStock {
    public static void stockMenu(int floristID) {
        int optionStock = 0;

        do {
            handleMenu();

            try {
                optionStock = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionStock) {
                    case ADD_PRODUCT -> addProductToStock(floristID);
                    case UPDATE_PRODUCT_QUANTITY -> updateProductFromStock(floristID);
                    case DELETE_PRODUCT -> deleteProduct(floristID);
                    case TOTAL_STOCK_PRICE -> getTotalStockPrice(floristID);
                    case LIST_STOCK -> stockListMenu(floristID);
                    case EXIT_STOCK -> menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (optionStock != 5);

    }

    private static void handleMenu() {
        System.out.println("-----------STOCK MENU--------------");
        System.out.println("1- ADD PRODUCT");
        System.out.println("2- UPDATE PRODUCT QUANTITY");
        System.out.println("3- DELETE PRODUCT");
        System.out.println("4- TOTAL STOCK PRICE");
        System.out.println("5- LIST STOCK");
        System.out.println("6- EXIT");
    }

    private static void addProductToStock(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            System.out.println("Enter product ID to add: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to add: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.addProductToStock(floristID, productId, quantity);
            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateProductFromStock(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            System.out.println("Enter product ID: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to update: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.updateProductFromStock(floristID, productId, quantity);
            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printIndividualStockList(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printIndividualStockList(floristID);

            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteProduct(int floristID) {
        printIndividualStockList(floristID);

        try {
            System.out.println("Enter product ID: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());

            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.deleteProductFromFloristStockByID(floristID, productId);

            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getTotalStockPrice(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            double totalStockPrice = connectionSQL.getTotalStockValue(floristID);
            System.out.println("Total stock price: " + totalStockPrice);

            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printGlobalStockList(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printGlobalStockList(floristID);

            connectionSQL.disconnect();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void stockListMenu(int floristID) {
        String option;

        do {
            System.out.println("-------------STOCK LIST MENU---------------");
            System.out.println("1- LIST PER ITEM");
            System.out.println("2- GLOBAL LIST");
            System.out.println("3- EXIT");

            option = MainMenu.SC.nextLine();
            try {
                switch (option) {
                    case "1" -> printIndividualStockList(floristID);
                    case "2" -> printGlobalStockList(floristID);
                    case "3" -> stockMenu(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (!option.equals("3"));
    }

}
