package florist.menus;

import florist.exceptions.EmptyStringException;
import static florist.menus.option.AddProductOption.*;
import florist.services.sql.ConnectionSQL;

import java.sql.SQLException;

public class MenuAddProduct {
    public static void menuAddProduct() {
        ConnectionSQL connectionSQL = ConnectionSQL.getInstance();

        try {
            connectionSQL.connect();

            boolean exit = false;

            while (!exit) {
                System.out.println("1. Add Tree");
                System.out.println("2. Add Flower");
                System.out.println("3. Add Decoration");
                System.out.println("4. Exit");

                int choice = Integer.parseInt(MainMenu.SC.nextLine());

                switch (choice) {
                    case ADD_TREE -> connectionSQL.addTree();
                    case ADD_FLOWER -> connectionSQL.addFlower();
                    case ADD_DECORATION -> connectionSQL.addDecoration();
                    case EXIT -> MainMenu.mainMenu();
                    default -> System.out.println("Invalid choice");
                }
            }

        } catch (EmptyStringException | SQLException e) {
            throw new RuntimeException(e);

        } finally {
            connectionSQL.disconnect();
        }
    }

}
