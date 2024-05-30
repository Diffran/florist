package florist.menus;

import florist.exceptions.EmptyStringException;
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
                    case 1:
                        connectionSQL.addTree();
                        break;
                    case 2:
                        connectionSQL.addFlower();
                        break;
                    case 3:
                        connectionSQL.addDecoration();
                        break;
                    case 4:
                        exit = true;
                        //TODO: cuidado! termina el programa
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (EmptyStringException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionSQL.disconnect();
        }
    }

}