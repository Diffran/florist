package florist.menus;

import florist.exceptions.EmptyStringException;
import florist.exceptions.NoConnectedDBException;
import florist.services.florist.FloristService;
import florist.exceptions.EmptySQLTableException;
import florist.services.stock.StockService;

import java.sql.SQLException;
import java.util.Scanner;

import static florist.menus.option.MainMenuOption.*;

public class MainMenu {
    public static final Scanner SC = new Scanner(System.in);

    public static void mainMenu() throws NoConnectedDBException {
        int option = 0;

        if (!FloristService.checkingConnection())
            return;

        do {
            handleMenu();

            try {
                option = Integer.parseInt(SC.nextLine().trim());

                switch (option) {
                    case CREATE_NEW_FLORIST -> StockService.createNewStock(FloristService.createFlorist());
                    case LOAD_FLORIST -> loadMenuFlorist();
                    case DELETE_FLORIST -> deleteFlorist();
                    case ADD_NEW_PRODUCT -> addNewProduct();
                    case EXIT -> exit();
                    default -> System.out.println("Invalid option. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage() + " Invalid input. Please enter a number.");

            } catch (SQLException | EmptyStringException | EmptySQLTableException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        } while (option != 5);
    }

    private static void handleMenu() {
        System.out.println("-----------MAIN MENU--------------");
        System.out.println("1- CREATE NEW FLORIST");
        System.out.println("2- LOAD FLORIST");
        System.out.println("3- DELETE FLORIST");
        System.out.println("4- ADD NEW PRODUCT");
        System.out.println("5- EXIT");
    }

    private static void loadMenuFlorist() throws NumberFormatException, EmptySQLTableException, SQLException {
        FloristService.printFlorist();

        int id;
        String userData;

        System.out.println("Please enter the ID of the florist you'd like to manage:");
        userData = SC.nextLine().trim();
        id = Integer.parseInt(userData);

        if (FloristService.floristExist(id)) {
            MenuFlorist.menuFlorist(id);

        } else {
            System.out.println("Florist not found");
        }
    }

    private static void deleteFlorist() {
        FloristService.printFlorist();
        FloristService.deleteFlorist();
    }

    private static void addNewProduct() {
        System.out.println("Add a new product to general stock");
        MenuAddProduct.menuAddProduct();
    }

    private static void exit() {
        SC.close();
        System.exit(0);
    }
}
