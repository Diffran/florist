package florist.menus;

import static florist.menus.MenuFlorist.menuFlorist;
import static florist.menus.option.MenuStockOption.*;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.exceptions.NotValidIDException;
import florist.services.sql.ConnectionSQL;

import java.sql.SQLException;
import java.util.List;

public class MenuStock {
    public static void stockMenu(int floristID) {
        int optionStock = 0;

        do {
            handleMenu();

            try {
                optionStock = Integer.parseInt(MainMenu.SC.nextLine().trim());

                switch (optionStock) {
                    case ADD_PRODUCT -> addProductToStock(floristID);
                    case RETURN_QUANTITY_TO_MAIN_STOCK -> returnQuantityToMainStock(floristID);
                    case DELETE_PRODUCT -> deleteProduct(floristID);
                    case TOTAL_STOCK_PRICE -> getTotalStockPrice(floristID);
                    case LIST_STOCK -> stockListMenu(floristID);
                    case EXIT_STOCK -> menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()+" Invalid input. Please enter a number.");
            }catch(EmptySQLTableException| NotValidIDException e){
                System.out.println(e.getMessage());
            }
        } while (optionStock != 5);

    }

    private static void handleMenu() {
        System.out.println("-----------STOCK MENU--------------");
        System.out.println("1- GET PRODUCT FROM MAIN STOCK");
        System.out.println("2- RETURN QUANTITY FROM FLORIST TO MAIN STOCK");
        System.out.println("3- DELETE PRODUCT FROM FLORIST");
        System.out.println("4- TOTAL FLORIST STOCK VALUE");
        System.out.println("5- LIST FLORIST STOCK");
        System.out.println("6- EXIT");
    }

    private static void listAllProducts() {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();
            List<String> products = connectionSQL.listAllProduct();

            System.out.println("All Products:");
            for (String product : products) {
                System.out.println(product);
            }

            connectionSQL.disconnect();
        } catch (SQLException e) {
            System.out.println("Error listing products: " + e.getMessage());
        }
    }

    private static void addProductToStock(int floristID) throws NotValidIDException{
        ConnectionSQL connectionSQL = ConnectionSQL.getInstance();

        try {
            listAllProducts();

            System.out.println("Enter product ID to add: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());

            if(productId<0){
                throw new NotValidIDException("id invalid, please enter a valid ID.");
            }
            System.out.println("Enter quantity to add: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.addProductToFloristStock(quantity, productId, floristID);


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Error: enter a number - "+e.getMessage());
        }

    }


    private static void returnQuantityToMainStock(int floristID) throws EmptySQLTableException {

        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            printIndividualStockList(floristID);
            System.out.println("Enter product ID: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());

            if(productId<0){
                throw new NotValidIDException("id invalid, please enter a valid ID.");
            }
            System.out.println("Enter quantity to update: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.returnQuantityToMainStock(floristID, productId, quantity);
            connectionSQL.disconnect();

        } catch (SQLException| NotValidIDException e) {
            System.out.println("Error: " + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Error: enter a number - "+e.getMessage());
        }
    }

    private static void printIndividualStockList(int floristID) throws EmptySQLTableException {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printIndividualStockList(floristID);

            connectionSQL.disconnect();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteProduct(int floristID) throws EmptySQLTableException{
        printIndividualStockList(floristID);

        try {
            System.out.println("Enter product ID: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());

            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.deleteProductFromFloristStockByID(floristID, productId);

            connectionSQL.disconnect();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }catch(NumberFormatException e){
            System.out.println("Error: enter a number - "+e.getMessage());
        }catch(NotValidIDException e){
            System.out.println("id invalid, please enter a valid ID.");
        }
    }

    private static void getTotalStockPrice(int floristID) {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            double totalStockPrice = connectionSQL.getTotalStockValue(floristID);
            System.out.println("Total stock price: " + totalStockPrice + "€");

            connectionSQL.disconnect();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printGlobalStockList(int floristID) throws EmptySQLTableException{
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            connectionSQL.printGlobalStockList(floristID);

            connectionSQL.disconnect();

        } catch (SQLException e) {
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

            } catch (EmptySQLTableException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (!option.equals("3"));
    }

}
