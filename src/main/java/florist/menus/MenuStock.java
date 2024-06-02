package florist.menus;

import static florist.menus.MenuFlorist.menuFlorist;
import static florist.menus.option.MenuStockOption.*;

import florist.exceptions.EmptySQLTableException;
import florist.exceptions.EmptyStringException;
import florist.services.sql.ConnectionSQL;

import java.sql.Connection;
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
                    case UPDATE_PRODUCT_QUANTITY -> updateProductFromStock(floristID);
                    case DELETE_PRODUCT -> deleteProduct(floristID);
                    case TOTAL_STOCK_PRICE -> getTotalStockPrice(floristID);
                    case LIST_STOCK -> stockListMenu(floristID);
                    case EXIT_STOCK -> menuFlorist(floristID);
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()+" Invalid input. Please enter a number.");
            }catch(EmptySQLTableException e){
                System.out.println(e.getMessage());
            }
        } while (optionStock != 5);

    }

    private static void handleMenu() {
        System.out.println("-----------STOCK MENU--------------");
        System.out.println("1- GET PRODUCT FROM MAIN STOCK");
        System.out.println("2- UPDATE PRODUCT QUANTITY FROM FLORIST");
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

    private static void addProductToStock(int floristID) {
        Connection conn = null;
        ConnectionSQL connectionSQL = ConnectionSQL.getInstance();

        try {

            conn = connectionSQL.getConnection();//TODO: ESTO
            conn.setAutoCommit(false);

            connectionSQL.connect();//I ESTO ES LO MISMO, MIRAD EL METODO

            listAllProducts();

            System.out.println("Enter product ID to add: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to add: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            int quantityProduct = connectionSQL.getProductQuantity(productId);

            int quantityUpdated = quantityProduct - quantity;

            connectionSQL.addProductBack2(quantityUpdated, productId);

            connectionSQL.addProductToStock(floristID, productId, quantity);

            conn.commit(); // Confirmar la transacción
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir la transacción en caso de error
                }
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.setAutoCommit(true);
                    connectionSQL.disconnect();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateProductFromStock(int floristID) throws EmptySQLTableException {
        try {
            ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
            connectionSQL.connect();

            printIndividualStockList(floristID);
            System.out.println("Enter product ID: ");
            int productId = Integer.parseInt(MainMenu.SC.nextLine());
            System.out.println("Enter quantity to update: ");
            int quantity = Integer.parseInt(MainMenu.SC.nextLine());

            connectionSQL.updateProductFromStock(floristID, productId, quantity);
            connectionSQL.disconnect();

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
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

        } catch (SQLException|NumberFormatException e) {
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

        } catch (SQLException e) {
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
