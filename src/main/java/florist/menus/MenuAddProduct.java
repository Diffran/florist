package florist.menus;

import florist.exceptions.EmptyStringException;
import static florist.menus.option.AddProductOption.*;

import florist.exceptions.InvalidDecorationType;
import florist.exceptions.NoConnectedDBException;
import florist.services.sql.ConnectionSQL;

import java.sql.SQLException;

public class MenuAddProduct {
    public static void menuAddProduct() {
        ConnectionSQL connectionSQL = ConnectionSQL.getInstance();
        int choice=0;

        do {

            try {
                connectionSQL.connect();


                System.out.println("1. Add Tree");
                System.out.println("2. Add Flower");
                System.out.println("3. Add Decoration");
                System.out.println("4. Exit");

                choice = Integer.parseInt(MainMenu.SC.nextLine());

                switch (choice) {
                    case ADD_TREE -> connectionSQL.addTree();
                    case ADD_FLOWER -> connectionSQL.addFlower();
                    case ADD_DECORATION -> connectionSQL.addDecoration();
                    case EXIT -> MainMenu.mainMenu();
                    default -> System.out.println("Invalid choice");
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage() + " Invalid input. Please enter a number.");
            } catch (EmptyStringException | SQLException | InvalidDecorationType | NoConnectedDBException e) {
                System.out.println(e.getMessage());

            } finally {
                connectionSQL.disconnect();
            }
        }while(choice!=4);
    }

}
