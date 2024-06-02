package florist.menus;

import florist.exceptions.EmptyStringException;
import florist.services.sql.ConnectionSQL;
import florist.exceptions.EmptySQLTableException;

import java.sql.SQLException;
import java.util.Scanner;

import static florist.menus.option.MainMenuOption.*;

public class MainMenu {
    public static final Scanner SC = new Scanner(System.in);
    public static ConnectionSQL connection;

    public static void mainMenu() {
        connection = ConnectionSQL.getInstance();
        int option = 0;

        do {
            handleMenu();

            try {
                option = Integer.parseInt(SC.nextLine().trim());

                switch (option) {
                    case CREATE_NEW_FLORIST -> connection.createFlorist();
                    case LOAD_FLORIST -> {
                        connection.printFlorist();
                        loadMenuFlorist();
                    }
                    case DELETE_FLORIST -> {
                        connection.printFlorist();
                        connection.deleteFlorist();
                    }
                    case ADD_NEW_PRODUCT -> {
                        System.out.println("Add a new product to general stock");
                        MenuAddProduct.menuAddProduct();
                    }
                    case EXIT -> {
                        SC.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()+" Invalid input. Please enter a number.");
            } catch (SQLException | EmptyStringException |EmptySQLTableException e) {
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
        int id;
        String userData;

        System.out.println("Please enter the ID of the florist you'd like to manage:");
        userData = SC.nextLine().trim();
        id = Integer.parseInt(userData);

        if (connection.floristExist(id)) {
            MenuFlorist.menuFlorist(id);
        } else {
            System.out.println("Florist not found");
        }
    }
}
